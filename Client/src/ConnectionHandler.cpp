#include "../include/ConnectionHandler.h"
#include "../include/Packets/Packet.h"

#include <string>

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

    // TODO: CHANGE STATE ACCORDING TO OUTPUT

    sendBytes(encdec->encode(line),);

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

Packet ConnectionHandler::getPacket() {
    Packet ans;

    char *bytes;

    this -> getBytes(bytes, 2);
    short nextShort = Packet::bytesToShort(bytes);

    switch (nextShort) {

        // Data received
        case 3:
            this -> getBytes(bytes, 2);
            short packet_size = Packet::bytesToShort(bytes);

            this -> getBytes(bytes, 2);
            short block_num = Packet::bytesToShort(bytes);

            // We indeed have packet_size bytes of data
            if(this->getBytes(bytes,(int)packet_size)){
                // Packet is complete. Check state.
                switch(state){
                    case not_receving:
                        // TODO: RETURN ERROR PACKET
                        break;

                    case RRQ:
                        //TODO: ADD LOGIC
                        break;

                    case DIRQ:
                        //TODO: ADD LOGIC
                        break;

                }

            }

            break;

            // Ack received
        case 4:
            break;

            // Error received
        case 5:
            break;

            //Bcast received
        case 9:
            break;
    }


    delete bytes;


    return Packet();
}

bool ConnectionHandler::setRecievingState(int newState) {
    bool ans = false;
    switch(newState){

        case(0): state = not_receving;
            ans = true;
            break;

        case(1): state = DIRQ;
            ans = true;
            break;

        case(2): state = RRQ;
            ans = true;
            break;

    }
    return ans;
}


























