//
// Created by dorgreen on 1/17/17.
//

#ifndef CLIENT_ERROR_H
#define CLIENT_ERROR_H

#include "Packet.h"

class ERROR : public Packet {
private:
    short error_code;
public:

    ERROR(short error_code);

    virtual ~ERROR();

    // Only ERROR, DATA, ACK override this
    // Needed for ConnectionHandler::sendBytes
    virtual int getBytesCount(){
        return 4;
    }

    virtual char* toBytes();

};


#endif //CLIENT_ERROR_H
