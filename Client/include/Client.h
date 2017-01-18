//
// Created by dorgreen on 1/17/17.
//

#ifndef CLIENT_CLIENT_H
#define CLIENT_CLIENT_H

#include "ConnectionHandler.h"
#include "BidiProtocol/MessagingProtocol.h"
#include "BidiProtocol/EncoderDecoder.h"
using namespace std;


class Client {
public:
    Client(string host, short port);

private:
    ConnectionHandler handler;
    MessagingProtocol messagingProtocol;
    EncoderDecoder encoderDecoder;

};


#endif //CLIENT_CLIENT_H
