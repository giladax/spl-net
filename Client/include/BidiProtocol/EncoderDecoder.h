//
// Created by dorgreen on 1/17/17.
//
#include <string>
#ifndef CLIENT_ENCODERDECODER_H
#define CLIENT_ENCODERDECODER_H

using namespace std;


class EncoderDecoder {
public:
    virtual ~EncoderDecoder();
    const char * encode(string line);

};


#endif //CLIENT_ENCODERDECODER_H
