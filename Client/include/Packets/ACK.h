//
// Created by dorgreen on 1/17/17.
//

#ifndef CLIENT_ACK_H
#define CLIENT_ACK_H


#include "Packet.h"

class ACK : public Packet {
public:
    ACK(char *incoming);
    ACK(short block_num);
    virtual char* toBytes();

    // Only ERROR, DATA, ACK override this
    // Needed for ConnectionHandler::sendBytes
    virtual int getBytesCount(){
        return 4;
    }


    virtual ~ACK();

private:
    short block_num;
};


#endif //CLIENT_ACK_H
