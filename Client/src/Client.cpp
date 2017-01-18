//
// Created by dorgreen on 1/17/17.
//
#include "../include/ConnectionHandler.h"
#include "../include/BidiProtocol/MessagingProtocol.h"
#include "../include/BidiProtocol/EncoderDecoder.h"
#include "../include/Client.h"
using namespace std;



Client::Client(string host, short port) :
        handler(host, port),
        messagingProtocol(),
        encoderDecoder(){
}
