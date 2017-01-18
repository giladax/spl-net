//
// Created by dorgreen on 1/17/17.
//

#include "../../include/Packets/ERROR.h"

#include "../../include/Packets/Packet.h"

// @ Receives these packets ==> constructor from char*
// @ Sends these packets to server ==> constructor from data
class ERROR : public Packet {

    short error_code;

public:
    ERROR::ERROR() : Packet(ERROR) {}

    ERROR::ERROR(short error_code) : Packet(ERROR), error_code(error_code) {}

    ERROR::~ERROR() {}

    Packet *ERROR::getPacket() {}

};