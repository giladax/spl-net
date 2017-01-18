//
// Created by dorgreen on 1/17/17.
//

#include "../../include/Packets/RRQ.h"

// @ Sends these packets to server ==> constructor from data

switch(opcode){
    case(RRQ) : new RRQ(line).toBytes();
}