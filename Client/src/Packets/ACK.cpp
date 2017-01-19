//
// Created by dorgreen on 1/17/17.
//

#include "../../include/Packets/ACK.h"
#include "../../include/Packets/Packet.h"
#include "Packet.cpp"
#include <vector>

using namespace std;

// @ Sends these packets to server ==> constructor from data
// @ Receives these packets ==> constructor from char*
class ACK : public Packet {

public:

    ACK::ACK(char *incoming) : Packet(ACK) {
        block_num = Packet::bytesToShort(incoming); // TODO: if does include the opcode in the char*, add logic here
    }

    ACK::ACK(short block_num) : Packet(ACK), block_num(block_num){}

    ~ACK() {}

    char* ACK::toBytes(){

        char* opcode_bytes;
        shortToBytes((short)ACK,opcode_bytes);
        char* block_num_bytes;
        shortToBytes(block_num, block_num_bytes);

        char toReturn[] = {opcode_bytes[0], opcode_bytes[1], block_num_bytes[0], block_num_bytes[1]};

        delete opcode_bytes;
        delete  block_num_bytes;

        return toReturn;

    }

private:
    short block_num;

}