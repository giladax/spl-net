#ifndef CONNECTION_HANDLER__
    #define CONNECTION_HANDLER__
                                               

    #include <iostream>
    #include <boost/asio.hpp>
    #include <string>

    #include "../include/Packets/Packet.h"

#include "BidiProtocol/EncoderDecoder.h"

    using namespace std;


    using boost::asio::ip::tcp;
     
    class ConnectionHandler {
    private:
        const std::string host_;
        const short port_;
        boost::asio::io_service io_service_;   // Provides core I/O functionality
        tcp::socket socket_;
        EncoderDecoder *encdec;
        bool shouldTerminate;
        string requestedFile;

        enum state {not_receving, DIRQ, RRQ, WRQ};
        bool requestApproved = false;
        const short MAX_PACKET_SIZE = 512;

        const short ACK_SUCCESSFUL_RESPONSE = 0;

     
    public:
        ConnectionHandler(std::string host, short port);
        virtual ~ConnectionHandler();
     
        // Connect to the remote machine
        bool connect();

        Packet * getPacket(char* bytes);
     
        // Read a fixed number of bytes from the server - blocking.
        // Returns false in case the connection is closed before bytesToRead bytes can be read.
        bool getBytes(char bytes[], unsigned int bytesToRead);
     
        // Send a fixed number of bytes from the client - blocking.
        // Returns false in case the connection is closed before all the data is sent.
        bool sendBytes(const char bytes[], int bytesToWrite);
        
        // Read an ascii line from the server
        // Returns false in case connection closed before a newline can be read.
        bool getLine(std::string& line);
        
        // Send an ascii line from the server
        // Returns false in case connection closed before all the data is sent.
        void sendLine(std::string& line);
     
        // Get Ascii data from the server until the delimiter character
        // Returns false in case connection closed before null can be read.
        bool getFrameAscii(std::string& frame, char delimiter);
     
        // Send a message to the remote host.
        // Returns false in case connection is closed before all the data is sent.
        bool sendFrameAscii(const std::string& frame, char delimiter);
        
        // Close down the connection properly.
        void close();

        bool setRecievingState(string line);

        void setShouldTerminate(bool value);

        bool getShouldTerminate();

        void setRequestedFile(string file);

        string getRequestesFile();


        bool setRecievingState(vector<char>);

        Packet* sendNextDataPacket(short block_num);
    }; //class ConnectionHandler
     
    #endif 
