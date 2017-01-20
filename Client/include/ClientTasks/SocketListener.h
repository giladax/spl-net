//
// Created by dorgreen on 1/17/17.
//

#ifndef CLIENT_SOCKETLISTENER_H
#define CLIENT_SOCKETLISTENER_H

#include "../ConnectionHandler.h"
#include "../BidiProtocol/EncoderDecoder.h"



class SocketListener {
public:
    SocketListener();
    virtual ~SocketListener();
    void run();


private:

};


#endif //CLIENT_SOCKETLISTENER_H
