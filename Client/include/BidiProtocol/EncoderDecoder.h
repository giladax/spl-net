//
// Created by dorgreen on 1/17/17.
//
#include <string>
#include <vector>

#ifndef CLIENT_ENCODERDECODER_H
#define CLIENT_ENCODERDECODER_H

using namespace std;


class EncoderDecoder {
public:
    virtual ~EncoderDecoder();
    const char * encode(string line);
    char *stringToChar(string line);
    void pushBytesToVector(char *bytes, std::vector<char> vec);

    void stringToChar(string line, char *cstr);


    char *argumentsToStructuredCharArray(vector<string> splitInput, short opcode, char *c);
};


#endif //CLIENT_ENCODERDECODER_H
