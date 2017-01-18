//
// Created by dorgreen on 1/17/17.
//


#include <string>
#include ""

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



