//
// Created by dorgreen on 1/17/17.
//
#pragma once
#include "Packet.h"

class ACK : public Packet {
public:
    ACK(char *incoming);
    ACK(short block_num);
    virtual char* toBytes();

    // Only ERROR, DATA, ACK override this
    // Needed for ConnectionHandler::sendBytes
    virtual int getBytesCount();


    virtual ~ACK();

private:
    short block_num;
};



