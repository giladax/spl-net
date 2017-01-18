//
// Created by dorgreen on 1/17/17.
//

#ifndef CLIENT_SOCKETLISTENER_H
#define CLIENT_SOCKETLISTENER_H

#include "../ConnectionHandler.h"
#include "../BidiProtocol/EncoderDecoder.h"
#include "../BidiProtocol/MessagingProtocol.h"



class SocketListener {
public:
    SocketListener(ConnectionHandler& handler, EncoderDecoder& encdec, MessagingProtocol& protocol);
    ~SocketListener();

private:
    ConnectionHandler handler;
    EncoderDecoder encdec;
    MessagingProtocol protocol;

};


#endif //CLIENT_SOCKETLISTENER_H
