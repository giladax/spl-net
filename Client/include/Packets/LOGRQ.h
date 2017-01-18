//
// Created by dorgreen on 1/17/17.
//

#ifndef CLIENT_LOGRQ_H
#define CLIENT_LOGRQ_H

#include <string>;
#include "Packet.h";

class LOGRQ : public Packet {
public:
    LOGRQ::LOGRQ() : Packet(LOGRQ);

    LOGRQ::LOGRQ(string input);

    LOGRQ::~LOGRQ();

    LOGRQ::getPacket();

};


#endif //CLIENT_LOGRQ_H
