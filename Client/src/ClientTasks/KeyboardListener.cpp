//
// Created by dorgreen on 1/17/17.
//

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

    while (std::cin>>line) {
        string input;
        getline(cin, input);

        // each call to strtok will get the next token, separated by space char.
        // each call REMOVES the token and separator from the string
        string token = strtok(input, " ");

        // At this point, "token" holds the first word, that should be the packet name
        // input holds all of the other input the user typed in


    }

}
