//
// Created by dorgreen on 1/17/17.
//

#ifndef CLIENT_PACKET_H
#define CLIENT_PACKET_H

#include <vector>

using namespace std;


class Packet {

public:
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
    Packet(Opcode opcode);



    Opcode opcode;
    Packet();
    virtual ~Packet();

    static short bytesToShort(char* bytesArr);
    static void  shortToBytes(short num, char *bytesArr);
    static void  insertByteArrayToVector(char* bytesArr, vector<char> vec, int size);

    // Only ERROR, DATA, ACK override this
    virtual char* toBytes(){
        return nullptr;
    }

    // Only ERROR, DATA, ACK override this
    // Needed for ConnectionHandler::sendBytes
    virtual int getBytesCount(){
        return 0;
    }
};


#endif //CLIENT_PACKET_H
