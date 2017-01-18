//
// Created by dorgreen on 1/17/17.
//

#include "../../include/Packets/Packet.h"

class Packet {

public: enum Opcode {

        RRQ = 1,
        WRQ = 2,
        DATA = 3,
        ACK = 4,
        ERROR = 5,
        DIRQ = 6,
        LOGRQ = 7,
        DELRQ = 8,
        BCAST = 9,
        DISC = 10
    };
    Opcode opcode;

    Packet::Packet(){

    }

    Packet::Packet(Opcode opcode) : opcode(opcode){}

    Packet::~Packet() {}

    Packet *Packet::getPacket(char *incomming){
        // All packets that shouldn't be responded will have that as default
        return nullptr;
    }
};


