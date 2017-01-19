//
// Created by dorgreen on 1/17/17.
//

#ifndef CLIENT_BCAST_H
#define CLIENT_BCAST_H


#include "Packet.h"

class BCAST : public Packet {

public:

    Opcode opcode;

    string fileName;

    bool added;

    BCAST();

    BCAST(char *incoming);

    ~BCAST();

    Packet *getPacket(char *incomming) = 0;


};


#endif //CLIENT_BCAST_H
