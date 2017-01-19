//
// Created by dorgreen on 1/17/17.
//

#include "BCAST.h"

// @ Receives these packets ==> constructor from char*
BCAST::BCAST() : Packet((short)9) {}

BCAST:BCAST(char* incoming) : Packet((short)9), 