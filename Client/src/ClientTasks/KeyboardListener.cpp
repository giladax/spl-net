//
// Created by dorgreen on 1/17/17.
//


#include <string>
#include "../../include/Packets/Packet.h"
#include "../../include/ConnectionHandler.h"
#include "../../include/ClientTasks/KeyboardListener.h"

using namespace std;



KeyboardListener::KeyboardListener(ConnectionHandler& handler) :
        handler(handler){
    shouldTerminate = false;
}


void KeyboardListener::run() {
    string line;

    // Wait for user's input
    do {
        std::cin>>line;

        // Check if the client has requested to disconnect
        shouldTerminate = this->disconnectOpReceived(line);

        handler.sendLine(line);



    }while (!shouldTerminate);

}

bool KeyboardListener::disconnectOpReceived(string line) {
    bool ans = line.compare("DISC") == 0;
    handler.setShouldTerminate(ans);
    return (ans);
}





