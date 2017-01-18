//
// Created by dorgreen on 1/17/17.
//

#ifndef CLIENT_PACKET_H
#define CLIENT_PACKET_H


class Packet {

public:
    Packet(Opcode opcode);

    enum Opcode {
        RRQ = 1,
        WRQ = 2,
        DATA = 3,
        ACK = 4,
        ERROR = 5,
        DIRQ = 6,
        LOGRQ = 7,
        DELRQ = 8,
        BCAST = 9,
        DISC = 10
    };

    Opcode opcode;
    Packet();
    ~Packet();

    virtual Packet* getPacket(char *incomming) = 0;

    static short bytesToShort(char* bytesArr);
    static void  shortToBytes(short num, char *bytesArr);

};


#endif //CLIENT_PACKET_H
