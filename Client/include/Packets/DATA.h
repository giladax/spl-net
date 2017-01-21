//
// Created by dorgreen on 1/17/17.
//

//#ifndef CLIENT_DATA_H
//#define CLIENT_DATA_H

#pragma once
#include "Packet.h"
#include "vector"


using  namespace std;
class DATA : public Packet {

public:
    DATA(short block_num, vector<char> data);
    virtual ~DATA();
    virtual char* toBytes();
    virtual int getBytesCount();

private:
    short packet_size;
    short block_num;
    vector<char> data;

};

//#endif //CLIENT_DATA_H


