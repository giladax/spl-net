//
// Created by dorgreen on 1/17/17.
//

#include "../../include/Packets/LOGRQ.h"
#include <stri0ng>

// @ Sends these packets to server ==> constructor from data
class LOGRQ : public Packet {
public:
    LOGRQ::LOGRQ() : Packet(LOGRQ){}

    LOGRQ::LOGRQ(string input) : Packet(LOGRQ){
        string token = strtok(input, " ");

        // User input was "LOGRQ xxx" where "xxx" is one word
        if(input == "" | input == null){
            username = token;
        }
    }

    LOGRQ::~LOGRQ(){}

    LOGRQ::getPacket(){}


private:
    string username;
};
