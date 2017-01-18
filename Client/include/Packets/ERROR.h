//
// Created by dorgreen on 1/17/17.
//

#ifndef CLIENT_ERROR_H
#define CLIENT_ERROR_H

#include "Packet.h"

class ERROR : public Packet {

    short error_code;
public:
    ERROR::ERROR() : Packet(ERROR);

    ERROR::ERROR(short error_code) : Packet(ERROR), error_code(error_code);

    ERROR::~ERROR();

    Packet *ERROR::getPacket();

};


#endif //CLIENT_ERROR_H
