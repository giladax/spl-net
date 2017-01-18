//
// Created by dorgreen on 1/17/17.
//

#ifndef CLIENT_PACKET_H
#define CLIENT_PACKET_H


class Packet {

public: enum Packets {
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

    Packets opcode;

    Packet::Packet();

    Packet::Packet(Packets opcode);

    Packet::~Packet();

    Packet *Packet::getPacket(char *incomming) = 0;
};


#endif //CLIENT_PACKET_H
