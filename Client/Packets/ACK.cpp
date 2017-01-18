//
// Created by dorgreen on 1/17/17.
//

#include "ACK.h"
#include "Packet.h"
#include "Packet.cpp"

class ACK : public Packet {

public:
    ACK(char *incomming) {
        Packet(ACK);
    }

    ~ACK() {}


};