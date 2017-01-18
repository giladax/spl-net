//
// Created by dorgreen on 1/17/17.
//
#include "../Packets/Packet.h"
#include "../../ClientTasks/KeyboardListener.h"
#include "../ConnectionHandler.h"
#include "../BidiProtocol/EncoderDecoder.h"
#include "../BidiProtocol/MessagingProtocol.h"
#include <string>


using namespace std;



KeyboardListener::KeyboardListener(ConnectionHandler &handler, EncoderDecoder &encdec, MessagingProtocol &protocol) :
        handler(handler),
        encdec(encdec),
        protocol(protocol) {
}


void KeyboardListener::run() {
    string line;

    // Wait for user's input
    while (std::cin>>line) {

        string token = strtok(line," ");





    }

}



