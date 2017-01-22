//
// Created by dorgreen on 1/17/17.
//

#pragma once
#include "ConnectionHandler.h"
#include "BidiProtocol/EncoderDecoder.h"
#include "Packets/Packet.h"
#include <string>



class KeyboardListener {
public:
    KeyboardListener(){
        shouldTerminate = false;
    }
    void run();
    virtual ~KeyboardListener();

private:
    bool disconnectOpReceived(string& line);
    bool shouldTerminate;




};



