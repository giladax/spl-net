//
// Created by dorgreen on 1/17/17.
//

#include "vector"

#include "include/Packets/DATA.h"

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

DATA::DATA(short block_num, vector<char> data) : Packet(Opcode::_DATA), data(data), block_num(block_num) {
    packet_size = (short) data.size();
}

char *DATA::toBytes() {

    // We'll delete that later!
    vector<char> packet;
    char bytes[2];

    // Opcode to Bytes
    Packet::shortToBytes(Opcode::_DATA, bytes);
    // Insert to vector
    Packet::insertByteArrayToVector(bytes, packet, 2);

    // Packet Size to Bytes
    Packet::shortToBytes(packet_size, bytes);
    // Insert to vector
    Packet::insertByteArrayToVector(bytes,packet, 2);

    // Block # to Bytes
    Packet::shortToBytes(block_num, bytes);
    // Insert to vector
    Packet::insertByteArrayToVector(bytes,packet, 2);

    // Concate data to the end of the packet
    packet.insert(packet.end(), data.begin(), data.end());

    // Convert the vector to char*
    char* ans = packet.data();
    // Delete everything



    return ans;






}

int DATA::getBytesCount() {
    // Opcode, Packet Size, Block #, Data
    return (2+2+2+packet_size);
}

DATA::~DATA() {


}


