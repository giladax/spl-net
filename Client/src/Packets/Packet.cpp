

#include "include/Packets/Packet.h"



using namespace std;

Packet::Packet(Opcode opcode) : opcode(opcode) {}

Packet::~Packet() {}

/*
 * Supplied by BGU Staff

void Packet::shortToBytes(short num, char *bytesArr) {
    bytesArr[0] = (char)((num >> 8) & 0xFF);
    bytesArr[1] = (char)(num & 0xFF);
}

void Packet::insertByteArrayToVector(char *bytesArr, vector<char> vec, int size) {
    // TODO: if this shit works -> put a petek in the kotel
    copy(bytesArr, bytesArr + size, back_inserter(vec));
}


 * Supplied by BGU staff

short Packet::bytesToShort(char *bytesArr) {
    short result = (short) ((bytesArr[0] & 0xff) << 8);
    result += (short) (bytesArr[1] & 0xff);
    return result;
}
*/

char *Packet::toBytes() {
    return nullptr;
}

int Packet::getBytesCount() {
    return 0;
}




