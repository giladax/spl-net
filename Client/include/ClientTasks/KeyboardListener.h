//
// Created by dorgreen on 1/17/17.
//

#ifndef CLIENT_KEYBOARDLISTENER_H
#define CLIENT_KEYBOARDLISTENER_H

#include "../ConnectionHandler.h"
#include "../BidiProtocol/EncoderDecoder.h"
#include "../Packets/Packet.h"
#include <string>


class KeyboardListener {
public:
    KeyboardListener();
    KeyboardListener(ConnectionHandler& handler);
    void run();
    virtual ~KeyboardListener();

private:
    ConnectionHandler handler;
    bool disconnectOpReceived(string line);
    bool shouldTerminate;




};


#endif //CLIENT_KEYBOARDLISTENER_H
