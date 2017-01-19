//
// Created by dorgreen on 1/17/17.
//

#ifndef CLIENT_SOCKETLISTENER_H
#define CLIENT_SOCKETLISTENER_H

#include "../ConnectionHandler.h"
#include "../BidiProtocol/EncoderDecoder.h"



class SocketListener {
public:
    SocketListener(ConnectionHandler& handler);
    ~SocketListener();
    void run();


private:
    ConnectionHandler handler;
    EncoderDecoder encdec;


};


#endif //CLIENT_SOCKETLISTENER_H
