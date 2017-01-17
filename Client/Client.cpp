//
// Created by dorgreen on 1/17/17.
//
#include "ConnectionHandler.h"
#include "BidiProtocol/MessagingProtocol.h"
#include "BidiProtocol/EncoderDecoder.h"
#include "Client.h"
using namespace std;



Client::Client(string host, short port) :
        handler(host, port),
        messagingProtocol(),
        encoderDecoder(){
}
