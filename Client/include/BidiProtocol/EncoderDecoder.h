//
// Created by dorgreen on 1/17/17.

#pragma once
#include <string>
#include <vector>


using namespace std;


class EncoderDecoder {
public:
    virtual ~EncoderDecoder();
    vector<char> encode(string line);
    void pushBytesToVector(char *bytes, std::vector<char> vec);
    void stringToChar(string line, char *cstr);
    vector<char> argumentsToStructuredCharArray(vector<string> splitInput, short opcode, char *c);

};


