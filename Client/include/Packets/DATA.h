//
// Created by dorgreen on 1/17/17.
//

#ifndef CLIENT_DATA_H
#define CLIENT_DATA_H


#include "Packet.h"
#include <vector>
using  namespace std;
class DATA : public Packet {

public:
    DATA(short block_num, vector<char> data);
    DATA::~DATA() {}
    virtual char* toBytes();

private:
    short packet_size;
    short block_num;
    vector<char> data;

};


