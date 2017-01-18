//
// Created by dorgreen on 1/17/17.
//

#include <vector>
#include <string>
#include "../../include/Packets/DATA.h"

using namespace std;

/**
 *  Packet structure:
 *
 * | 2 bytes | 2 bytes     |2 bytes  | n bytes |
 * | Opcode  | Packet Size | Block # | Data    |
 *
 */

// @ Sends these packets to server ==> constructor from data
// @ Receives these packets ==> constructor from char*
class DATA : public Packet {

public:
    DATA::DATA(char *incoming) : Packet(DATA) {
        //TODO: FILL IN packet_size, block_num, data FROM incoming
    }

    DATA::DATA(short block_num, vector<char>* data) : Packet(DATA), packet_size((short)data->size()), block_num(block_num), data(data) {
        //TODO: SHOULD THIS RECEIVE THESE PARAMETERS?
    }

    DATA::~DATA() {}

    Packet DATA::getPacket(string input){

    }

private:
    short packet_size;
    short block_num;
    vector<char>* data;
}
