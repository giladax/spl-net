#include "../include/ConnectionHandler.h"
#include "../include/Packets/Packet.h"

#include <string>
#include <iostream>
#include <tkDecls.h>
#include <include/Packets/ERROR.h>
#include <include/Packets/ACK.h>
#include <fstream>
#include <include/Packets/DATA.h>

using boost::asio::ip::tcp;

using std::cin;
using std::cout;
using std::cerr;
using std::endl;
using std::string;

ConnectionHandler::ConnectionHandler(string host, short port) : host_(host), port_(port), io_service_(),
                                                                socket_(io_service_), state(not_receving) {
    encdec = new EncoderDecoder();
}

ConnectionHandler::~ConnectionHandler() {
    close();
}


bool ConnectionHandler::connect() {
    std::cout << "Starting connect to "
              << host_ << ":" << port_ << std::endl;
    try {
        tcp::endpoint endpoint(boost::asio::ip::address::from_string(host_), port_); // the server endpoint
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
            tmp += socket_.write_some(boost::asio::buffer(bytes + tmp, bytesToWrite - tmp), error);
        }
        if (error)
            throw boost::system::system_error(error);
    } catch (std::exception &e) {
        std::cerr << "recv failed (Error: " << e.what() << ')' << std::endl;
        return false;
    }
    return true;
}

bool ConnectionHandler::getLine(std::string &line) {
    return getFrameAscii(line, '\n');
}

void ConnectionHandler::sendLine(std::string &line) {

    // New user input overrides operations
    requestApproved = false;

    // Receive user input incoded as a vector of chars
    vector<char> encodedLine  = encdec->encode(line);




    //
    const char * bytes = encodedLine.data();
    sendBytes(bytes, encodedLine.size());

    // return sendFrameAscii(line, '\n');
}

bool ConnectionHandler::getFrameAscii(std::string &frame, char delimiter) {
    char ch;
    // Stop when we encounter the null character.
    // Notice that the null character is not appended to the frame string.
    try {
        do {
            getBytes(&ch, 1);
            frame.append(1, ch);
        } while (delimiter != ch);
    } catch (std::exception &e) {
        std::cerr << "recv failed (Error: " << e.what() << ')' << std::endl;
        return false;
    }
    return true;
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

Packet* ConnectionHandler::getPacket(char *bytes) {
    Packet* ans = nullptr;


    short opcode = Packet::bytesToShort(bytes);
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
        case 3:
            this -> getBytes(bytes, 2);
            short packet_size = Packet::bytesToShort(bytes);

            this -> getBytes(bytes, 2);
            block_num = Packet::bytesToShort(bytes);

            // We indeed have packet_size bytes of data
            // bytes now hold the data part of the packet
            if(this->getBytes(bytes,(int)packet_size)){
                // Packet is complete. Check state.
                if(!requestApproved || state == not_receving){
                    // We shouldn't be receiving
                    ans = new ERROR(0);
                    break;
                }


                switch(state){

                    case RRQ:
                        // Add to saved data
                        std::ofstream outfile (requestedFile);
                        outfile.open(requestedFile, std::ios_base::app);
                        outfile << string(bytes);
                        outfile.close();

                        // If last packet, print and RESET
                        if(packet_size < MAX_PACKET_SIZE){
                            cout << "RRQ " << requestedFile << " complete" << endl;
                            requestApproved = false;
                            state = not_receving;
                            requestedFile = "";
                        }
                        ans = new ACK(block_num);
                        break;

                    case DIRQ:

                        // We won't break the line because the outputed filename might be incomplete
                        cout << string(bytes);

                        // Last packet. wrap up and reset everything
                        if(packet_size < MAX_PACKET_SIZE){
                            // output must be complete now, we can break the line now
                            cout << endl;
                            requestApproved = false;
                            state = not_receving;

                        }
                        ans = new ACK(block_num);
                        break;

                    default:
                        break;
                }
            }

            break;

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
        case 4:

            this -> getBytes(bytes, 2);
            block_num = Packet::bytesToShort(bytes);

            cout << "ACK " << block_num << endl;

            // ACK for DISC and packet is valid, shutdown everything
            if(shouldTerminate && block_num == ACK_SUCCESSFUL_RESPONSE && !getBytes(bytes, 1)){
                break;
            }

            else if(state == WRQ){
                ans = sendNextDataPacket();
            }

            else if((state == RRQ || state == DIRQ) && !requestApproved && block_num == ACK_SUCCESSFUL_RESPONSE){
                requestApproved = true;
                if(state == RRQ){
                    // Create the file
                    std::ofstream outfile (requestedFile);
                    outfile.close();

                }
            }
            // TODO: MORE LOGIC IS NEEDED!



            break;

         // Error received
         /*
         * ERROR PACKET STRUCTURE
         * | 2 bytes|   2 bytes | string |  1 byte |
         * | Opcode | ErrorCode | ErrMsg | 0       |
         *
         * When receiving:
         * - print to screen
         */
        case 5:
            this -> getBytes(bytes, 2);
            short error_code = Packet::bytesToShort(bytes);
            cout << "ERROR " << error_code << endl;

            // Reset everything!
            state = not_receving;
            requestApproved = false;

            break;

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
        case 9:
            this -> getBytes(bytes, 1);
            string addedOrDeleted;
            if(bytes[0]==0){
                addedOrDeleted = "del";
            }
            else{
                addedOrDeleted = "add";
            }

            vector<char> filename;

            // Add chars to vector until the ending char '0'
            do{
                getBytes(bytes, 1);
                filename.push_back(bytes[0]);
            } while (bytes[0]!= '0');

            cout << "BCAST: " << addedOrDeleted << " " << filename.data() << endl;

            delete filename;
            break;

        default: ans = nullptr;
    }


    delete bytes;


    return ans;
}

// From the packet to be sent from user input, figure if state should be changed and change it
// We do this to avoid some weird ObjectOriented stuff
bool ConnectionHandler::setRecievingState(vector<char> userInput) {
    char bytes[2] = {userInput[0], userInput[1]};
    short newState = Packet::bytesToShort(bytes);
    bool ans = false;
    string string(userInput.begin(), userInput.end())
    switch(newState){

        case(1): state = RRQ;
            // Create a new string from vector, cut the first 2 chars, set as "requestedFile"
            setRequestedFile(string(userInput.begin(), userInput.end()).substr(2));
            ans = true;
            break;

        case(2): state = WRQ;
            // Create a new string from vector, cut the first 2 chars, set as "requestedFile"
            setRequestedFile(string(userInput.begin(), userInput.end()).substr(2));
            ans = true;
            break;

        default:
            //state = not_receving;
            break;

    }
    return ans;
}

void ConnectionHandler::setShouldTerminate(bool value){
        shouldTerminate = value;
}

bool ConnectionHandler::getShouldTerminate(){
    return shouldTerminate;
}

string ConnectionHandler::getRequestesFile() {
    return requestedFile;
}

void ConnectionHandler::setRequestedFile(string file) {
    requestedFile = file;

}

Packet* ConnectionHandler::sendNextDataPacket(short block_num) {

    vector<char> vec;

    // Assign all of requestedFile to vec
    ifstream file(requestedFile);
    assert(file.is_open());

    if (!file.eof() && !file.fail()) {
        file.seekg(0, ios_base::end); // go to end of file
        streampos fileSize = file.tellg();
        vec.resize(fileSize);

        file.seekg(0, ios_base::beg); // back to the beginning of the file
        file.read(&vec[0], fileSize); // reade fixed size to vector
    }

    vector<char>::const_iterator first = vec.begin() + block_num * MAX_PACKET_SIZE;
    vector<char>::const_iterator last = vec.begin() + (block_num + 1) * MAX_PACKET_SIZE;
    vector<char> newVec(first, last);

    char* bytes = newVec.data();
    Packet ans = new DATA(block_num+1, newVec);
    delete vec;
    delete first;
    delete last;
    delete newVec;

    return ans;


}

































