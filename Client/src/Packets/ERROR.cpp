//
// Created by dorgreen on 1/17/17.
//

#include "../../include/Packets/ERROR.h"


// @ Receives these packets ==> constructor from char*
// @ Sends these packets to server ==> constructor from data







ERROR::ERROR(short error_code) : Packet(Packet::Opcode::ERROR), error_code(error_code) {}

ERROR::~ERROR() {}


char *ERROR::toBytes() {
    //TODO: implement this shit
    return nullptr;
}


