#include "include/ConnectionHandler.h"

#include <fstream>
#include <include/Packets/ERROR.h>
#include <include/Packets/ACK.h>
#include <include/Packets/DATA.h>
#include "Utils.h"
#include <boost/algorithm/string/split.hpp>
#include <boost/algorithm/string/classification.hpp>


using boost::asio::ip::tcp;

using std::cin;
using std::cout;
using std::cerr;
using std::endl;
using std::string;

ConnectionHandler::ConnectionHandler() :
        io_service_(),
        socket_(io_service_),
        state(not_receving) {
    encdec = new EncoderDecoder();
}

ConnectionHandler::~ConnectionHandler() {
    close();
}


bool ConnectionHandler::connect(string& host, short port) {
    this->host_ = host;
    port_ = port;
    std::cout << "Starting connect to "
              << host_ << ":" << port_ << std::endl;
    try {
        tcp::endpoint endpoint(boost::asio::ip::address::from_string(host_), (unsigned short) port_); // the server endpoint
        boost::system::error_code error;
        socket_.connect(endpoint, error);
        if (error)
            throw boost::system::system_error(error);
    }
    catch (std::exception &e) {
        std::cerr << "Connection failed (Error: " << e.what() << ')' << std::endl;
        return false;
    }
    return true;
}

bool ConnectionHandler::getBytes(char bytes[], unsigned int bytesToRead) {
    size_t tmp = 0;
    boost::system::error_code error;
    try {
        while (!error && bytesToRead > tmp) {
            tmp += socket_.read_some(boost::asio::buffer(bytes + tmp, bytesToRead - tmp), error);
        }
        if (error)
            throw boost::system::system_error(error);
    } catch (std::exception &e) {
        std::cerr << "recv failed (Error: " << e.what() << ')' << std::endl;
        return false;
    }
    return true;
}

bool ConnectionHandler::sendBytes(const char bytes[], int bytesToWrite) {
    int tmp = 0;
    boost::system::error_code error;
    try {
        while (!error && bytesToWrite > tmp) {
            tmp += socket_.write_some(boost::asio::buffer(bytes + tmp, (size_t) (bytesToWrite - tmp)), error);
        }
        if (error)
            throw boost::system::system_error(error);
    } catch (std::exception &e) {
        std::cerr << "recv failed (Error: " << e.what() << ')' << std::endl;
        return false;
    }
    return true;
}




void ConnectionHandler::sendLine(std::string &line) {

    // New user input overrides operations
    requestApproved = false;

    // Receive user input encoded as a vector of chars
    vector<char> encodedLine = encdec->encode(line);
    
    const char *bytes = encodedLine.data();
    sendBytes(bytes, (int) encodedLine.size());

    // return sendFrameAscii(line, '\n');
}


/*
bool ConnectionHandler::sendFrameAscii(const std::string& frame, char delimiter) {
   bool result=sendBytes(frame.c_str(),frame.length());
   if(!result) return false;
   return sendBytes(&delimiter,1);
}
 */



// Close down the connection properly.
void ConnectionHandler::close() {
    try {
        socket_.close();
    } catch (...) {
        std::cout << "closing failed: connection already closed" << std::endl;
    }
}

Packet *ConnectionHandler::getPacket(char *bytes) {
    Packet *ans = nullptr;


    short opcode = Utils::bytesToShort(bytes);
    short block_num;

    switch (opcode) {

        // Data received
        /*
         * DATA PACKET STRUCTURE
         * | 2 bytes| 2 bytes     | 2 bytes | n bytes |
         * | Opcode | Packet Size | Block # | Data    |
         *
         * When receiving:
         * - if last block (Packet Size < MAX_PACKET_SIZE = 512), add it and finish transfer
         * - if last block && was during RRQ, print "RRQ filename complete"
         * - if last block && was during DIRQ, print the data
         * - if not last block, add it to where data is stored
         * - either way, send ACK(Block #)
         */
        case 3: {
            this->getBytes(bytes, 2);
            short packet_size = Utils::bytesToShort(bytes);

            this->getBytes(bytes, 2);
            block_num = Utils::bytesToShort(bytes);

            // We indeed have packet_size bytes of data
            // bytes now hold the data part of the packet
            char data[packet_size];
            if (this->getBytes(data, (unsigned int) (packet_size))) {
                // Packet is complete. Check state.
                if (state == not_receving) {
                    // We shouldn't be receiving
                    ans = new ERROR((short )0);
                    break;
                }


                switch (state) {

                    case RRQ: {
                        // Add to saved data
                        std::ofstream outfile(requestedFile);
                        outfile.open(requestedFile, std::ios_base::app);
                        outfile << data;
                        outfile.close();

                        // If last packet, print and RESET
                        if (packet_size < MAX_PACKET_SIZE) {
                            cout << "RRQ " << requestedFile << " complete" << endl;
                            requestApproved = false;
                            state = not_receving;
                            requestedFile = "";
                        }
                        ans = new ACK(block_num);
                        break;
                    }
                    case DIRQ: {
                        // Break the line instead of \0
                        for (int i = 0; i < packet_size; ++i) {
                            if(data[i]=='\0'){
                                data[i] = '\n';
                            }
                        }

                        dirqResult.append(string(data));

                        // Last packet. wrap up and reset everything
                        if (packet_size < MAX_PACKET_SIZE) {
                            // output must be complete now, we can break the line now
                            cout << dirqResult;
                            dirqResult = "";
                            requestApproved = false;
                            state = not_receving;

                        }
                        ans = new ACK(block_num);
                        break;
                    }
                    default:
                        break;

                }
            }

            break;
        }

            // Ack received
            /*
            * ACK PACKET STRUCTURE
            * |2 bytes| 2 bytes|
            * |Opcode | Block# |
            *
            * When receiving:
            * - print to screen
            * - if user sent DISC, disconnect.
            * - if sending file to server, send next packet
            */
        case 4: {

            this->getBytes(bytes, 2);
            block_num = Utils::bytesToShort(bytes);

            cout << "ACK " << block_num << endl;

            // ACK for DISC and packet is valid, shutdown everything
            if (shouldTerminate && block_num == ACK_SUCCESSFUL_RESPONSE && !getBytes(bytes, 1)) {
                break;
            } else if (state == WRQ) {
                ans = sendNextDataPacket(block_num);
            }

                // Packet means server accept the client's request.
            else if ((state == RRQ || state == DIRQ) && !requestApproved && block_num == ACK_SUCCESSFUL_RESPONSE) {
                requestApproved = true;
                if (state == RRQ) {
                    // Create the file
                    std::ofstream outfile(requestedFile);
                    outfile.close();

                }
            }



            // TODO: MORE LOGIC IS NEEDED!


            break;
        }

            // Error received
            /*
            * ERROR PACKET STRUCTURE
            * | 2 bytes|   2 bytes | string |  1 byte |
            * | Opcode | ErrorCode | ErrMsg | 0       |
            *
            * When receiving:
            * - print to screen
            */
        case 5: {
            this->getBytes(bytes, 2);
            short error_code = Utils::bytesToShort(bytes);
            cout << "ERROR " << error_code << endl;

            // "read" the rest of the package to clear the buffer.
            bytes[0] = 1;
            while(bytes[0] != 0 && this->getBytes(bytes,1)){
            }

            // Reset everything!
            state = not_receving;
            requestApproved = false;

            break;
        }

            //Bcast received
            /*
            * BCAST PACKET STRUCTURE
            *
            * | 2 bytes| 1 byte        | string   | 1 byte |
            * | Opcode | Deleted/Added | Filename | 0      |
            *
            * When receiving:
            * - print to screen
            */
            case 9: {
                this->getBytes(bytes, 1);
                string addedOrDeleted;
                if (bytes[0] == 0) {
                    addedOrDeleted = "del";
                } else {
                    addedOrDeleted = "add";
                }

                vector<char> filename;

                // Add chars to vector until the ending char '0'
                do {
                    getBytes(bytes, 1);
                    filename.push_back(bytes[0]);
                } while (bytes[0] != '0');

                cout << "BCAST: " << addedOrDeleted << " " << filename.data() << endl;


                break;
            }

        default:
            ans = nullptr;
    }


    //delete bytes;


    return ans;
}

// From the packet to be sent from user input, figure if state should be changed and change it
// We do this to avoid some weird ObjectOriented stuff
bool ConnectionHandler::setRecievingState(string& userInput) {
    std::vector<std::string> splitInput;
    boost::split(splitInput, userInput, boost::is_any_of(" "));
    short newState;

    if(splitInput[0].compare("RRQ") == 0){
        newState = 1;
    }

    else if(splitInput[0].compare("WRQ") == 0){
        newState = 2;
    }

    else if(splitInput[0].compare("DIRQ") == 0){
        newState = 3;
    }

    bool ans = false;

    switch (newState) {

        case (1):
            state = RRQ;
            // Create a new string from vector, cut the first 2 chars, set as "requestedFile"
            setRequestedFile(splitInput[1]);
            ans = true;
            break;

        case (2):
            state = WRQ;
            // Create a new string from vector, cut the first 2 chars, set as "requestedFile"
            setRequestedFile(splitInput[1]);
            ans = true;
            break;

        case (3):
            state = DIRQ;
            ans = true;
            break;

        default:
            state = not_receving;
            break;

    }
    return ans;
}

void ConnectionHandler::setShouldTerminate(bool value) {
    shouldTerminate = value;
}



void ConnectionHandler::setRequestedFile(string& file) {
    requestedFile = file;

}

Packet *ConnectionHandler::sendNextDataPacket(short block_num) {
    DATA *ans = nullptr;
    vector<char> vec;

    // Assign all of requestedFile to vec
    ifstream file;
    file.open(requestedFile);

    if(file.is_open()) {

        if (!file.eof() && !file.fail()) {
            file.seekg(0, ios_base::end); // go to end of file
            streampos fileSize = file.tellg();
            vec.resize((unsigned long) fileSize);

            file.seekg(0, ios_base::beg); // back to the beginning of the file
            file.read(&vec[0], fileSize); // reade fixed size to vector
        }

        vector<char>::const_iterator first = vec.begin() + block_num * MAX_PACKET_SIZE;
        vector<char>::const_iterator last = vec.begin() + (block_num + 1) * MAX_PACKET_SIZE;
        vector<char> newVec(first, last);


        ans = new DATA((short) (block_num + 1), newVec);

    }
    return ans;


}

ConnectionHandler &ConnectionHandler::getInstance() {
    static ConnectionHandler instance;
    return instance;
}

































