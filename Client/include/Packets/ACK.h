//
// Created by dorgreen on 1/17/17.
//

#ifndef CLIENT_ACK_H
#define CLIENT_ACK_H


#include "Packet.h"

class ACK : public Packet {
public:
    ACK(char *incoming);

    ~ACK();

};


#endif //CLIENT_ACK_H
