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
    vector<char> encode(string line);
    void pushBytesToVector(char *bytes, std::vector<char> vec);
    void stringToChar(string line, char *cstr);
    vector<char> argumentsToStructuredCharArray(vector<string> splitInput, short opcode, char *c);
    vector<char> toSend;
};


#endif //CLIENT_ENCODERDECODER_H
