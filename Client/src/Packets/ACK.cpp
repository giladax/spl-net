//
// Created by dorgreen on 1/17/17.
//

#include "include/Packets/ACK.h"
#include "Packet.cpp"


using namespace std;

// @ Sends these packets to server ==> constructor from data
// @ Receives these packets ==> constructor from char*
ACK::ACK(char *incoming) : Packet(Opcode::_ACK) {
    block_num = Packet::bytesToShort(incoming); // TODO: if does include the opcode in the char*, add logic here
}

ACK::ACK(short block_num) : Packet(Opcode::_ACK), block_num(block_num) {}



char *ACK::toBytes() {

    char opcode_bytes[2];
    shortToBytes(Opcode::_ACK, opcode_bytes);
    char block_num_bytes[2];
    shortToBytes(block_num, block_num_bytes);

    char *toReturn = new char[4]{opcode_bytes[0], opcode_bytes[1], block_num_bytes[0], block_num_bytes[1]};


    return toReturn;

}

ACK::~ACK() {

}

int ACK::getBytesCount() {
    return 4;
}



