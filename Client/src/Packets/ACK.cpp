//
// Created by dorgreen on 1/17/17.
//

#include "../../include/Packets/ACK.h"
#include "../../include/Packets/Packet.h"
#include "Packet.cpp"

class ACK : public Packet {

public:
    ACK::ACK(char *incoming) : Packet(ACK) {

    }

    ~ACK() {}

private:
    short block_num;

}