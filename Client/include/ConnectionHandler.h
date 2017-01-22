
#pragma once
    #include <iostream>
    #include <boost/asio.hpp>
    #include <string>

    #include "include/Packets/Packet.h"

#include "BidiProtocol/EncoderDecoder.h"

    using namespace std;


    using boost::asio::ip::tcp;
     
    class ConnectionHandler {
    private:
        std::string host_;
        short port_;
        boost::asio::io_service io_service_;   // Provides core I/O functionality
        tcp::socket socket_;
        EncoderDecoder *encdec;
        bool shouldTerminate;
        string requestedFile;
        string dirqResult = "";
        bool requestApproved = false;
        const short MAX_PACKET_SIZE = 512;
        const short ACK_SUCCESSFUL_RESPONSE = 0;
        ConnectionHandler();


     
    public:
        enum State {not_receving, DIRQ, RRQ, WRQ};
        State state;

        virtual ~ConnectionHandler();
        static ConnectionHandler& getInstance();
        // Connect to the remote machine
        bool connect(std::string& host, short port);

        Packet* getPacket(char* bytes);
     
        // Read a fixed number of bytes from the server - blocking.
        // Returns false in case the connection is closed before bytesToRead bytes can be read.
        bool getBytes(char bytes[], unsigned int bytesToRead);
     
        // Send a fixed number of bytes from the client - blocking.
        // Returns false in case the connection is closed before all the data is sent.
        bool sendBytes(const char bytes[], int bytesToWrite);

        // Send an ascii line from the server
        // Returns false in case connection closed before all the data is sent.
        void sendLine(std::string& line);

        // Close down the connection properly.
        void close();


        void setShouldTerminate(bool value);


        void setRequestedFile(string& file);

        bool setRecievingState(string& userInput);

        Packet* sendNextDataPacket(short block_num);
    }; //class ConnectionHandler
     
