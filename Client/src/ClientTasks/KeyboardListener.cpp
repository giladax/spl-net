//
// Created by dorgreen on 1/17/17.
//


#include <string>
#include "include/Packets/Packet.h"
#include "include/ConnectionHandler.h"
#include "include/ClientTasks/KeyboardListener.h"

using namespace std;


void KeyboardListener::run() {
    string line;

    // Wait for user's input
    do {
        std::cin>>line;
        string userInput = line.c_str(); // A deep copy. We'll need it.

        // Check if the client has requested to disconnect
        shouldTerminate = this->disconnectOpReceived(line);

        // Also resets the state, just in case.
        ConnectionHandler::getInstance().sendLine(line);

        std::vector<char> data(line.begin(), line.end());

        // This updates the state, as well as updating requestedFile string
        ConnectionHandler::getInstance().setRecievingState(data);


    }while (!shouldTerminate);

}

bool KeyboardListener::disconnectOpReceived(string line) {
    bool ans = line.compare("DISC") == 0;
    ConnectionHandler::getInstance().setShouldTerminate(ans);
    return (ans);
}

KeyboardListener::~KeyboardListener() {

}





