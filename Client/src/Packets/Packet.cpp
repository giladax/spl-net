//
// Created by dorgreen on 1/17/17.
//

#include "../../include/Packets/Packet.h"
#include <string>

class Packet {

public:
    enum Opcode {

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

    Packet::Packet() {

    }

    virtual Packet::Packet(Opcode opcode) : opcode(opcode) {}

    virtual Packet::~Packet() {}

    virtual Packet *Packet::getPacket(char *incomming) {
        // All packets that shouldn't be responded will have that as default
        return nullptr;
    }

    // Also validate arguments are valid
    virtual const char *userInputToBytes(string arguments) = 0;

    static short bytesToShort(char *bytesArr) {
        short result = (short) ((bytesArr[0] & 0xff) << 8);
        result += (short) (bytesArr[1] & 0xff);
        return result;
    }

    static void shortToBytes(short num, char *bytesArr) {
        bytesArr[0] = ((num >> 8) & 0xFF);
        bytesArr[1] = (num & 0xFF);
    }
};


