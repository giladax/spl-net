//
// Created by dorgreen on 1/17/17.
//
#pragma once


#include <vector>

using namespace std;


enum Opcode {
    _DATA = 3,
    _ACK = 4,
    _ERROR = 5,
    _DIRQ = 6,
};


class Packet {

public:

    Packet(Opcode opcode);

    virtual ~Packet();


    // Only ERROR, DATA, ACK override this
    virtual char* toBytes();

    // Only ERROR, DATA, ACK override this
    // Needed for ConnectionHandler::sendBytes
     virtual int getBytesCount();

private:
    Opcode opcode;
};



