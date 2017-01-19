//
// Created by dorgreen on 1/17/17.
//

#include "../../include/Packets/Packet.h"
#include <iterator>
#include <algorithm>

using namespace std;

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

    Packet::Packet(Opcode opcode) : opcode(opcode) {}

    Packet::~Packet() {}

    Packet *Packet::getPacket(char *incomming) {
        // All packets that shouldn't be responded will have that as default
        return nullptr;
    }

    /*
     * Supplied by BGU staff
     */
    static short bytesToShort(char *bytesArr) {
        short result = (short) ((bytesArr[0] & 0xff) << 8);
        result += (short) (bytesArr[1] & 0xff);
        return result;
    }

    /*
     * Supplied by BGU Staff
     */
    static void Packet:: shortToBytes(short num, char *bytesArr) {
        bytesArr[0] = ((num >> 8) & 0xFF);
        bytesArr[1] = (num & 0xFF);
    }

    static void Packet::insertByteArrayToVector(char* bytesArr, vector<char> vec, int size){
        // TODO: if this shit works -> put a petek in the kotel
        copy(bytesArr, bytesArr + size, back_inserter(vec));
    }




