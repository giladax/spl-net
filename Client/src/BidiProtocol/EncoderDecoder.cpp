//
// Created by dorgreen on 1/17/17.
//

#include "../../include/BidiProtocol/EncoderDecoder.h"
#include <string>
#include <vector>
#include <cstring>
#include <iostream>
#include <iterator>
#include <sstream>
#include "../Packets/Packet.cpp"
using namespace std;

// This gets an input from the user, returns data to be sent to server
const char * EncoderDecoder::encode(string line) {

    char *c;

    // Split input
    vector<string> splitInput;
    istringstream iss(line);
    copy(istream_iterator<string>(iss),
         istream_iterator<string>(),
         back_inserter(splitInput));

    if(splitInput[0].compare("DISC") == 0){
        // Input is valid
        if(splitInput.size() == 1){
            // Get the Opcode to char*, into c
            // That's all for this packet
            Packet::shortToBytes((short)10, c);
        }
        // Input is INVALID
        else{
            cout <<  "input invalid" << endl; // TODO: MAKE SURE THIS IS THE RIGHT PRINT
        }
    }

    else if(splitInput[0].compare("DIRQ") == 0){
        // Input is valid
        if(splitInput.size() == 1){
            // Get the Opcode to char*, into c
            // That's all for this packet
            Packet::shortToBytes((short)6, c);
        }
            // Input is INVALID
        else{
            cout <<  "input invalid" << endl; // TODO: MAKE SURE THIS IS THE RIGHT PRINT
        }
    }

    else if(splitInput[0].compare("LOGRQ") == 0){

        argumentsToStructuredCharArray(splitInput,(short)7, c);

    }

    else if(splitInput[0].compare("DELRQ") == 0){

        argumentsToStructuredCharArray(splitInput,(short)8, c);
    }

    else if(splitInput[0].compare("RRQ") == 0){

        argumentsToStructuredCharArray(splitInput,(short)1, c);

    }
    else if(splitInput[0].compare("WRQ") == 0){
        argumentsToStructuredCharArray(splitInput,(short)2, c);
    }

    else{
        cout << "Invalid operation!" << endl;
    }

    delete splitInput;
    return c;




}

void EncoderDecoder::pushBytesToVector(char* bytes, vector<char> vec){
    for (int i = 0; i < bytes[i] != 0 ; ++i) {
        vec.push_back(bytes[i]);
    }
}

// Convert string to bytes and assign to cstr
void EncoderDecoder::stringToChar(string line, char* cstr) {
    strcpy(cstr, line.c_str());
}

// Get user input, return char* that could be sent to the server
// If input is wrong, return nullptr and print the error to the screen
char* EncoderDecoder::argumentsToStructuredCharArray(vector<string> splitInput, short opcode, char* c){
    vector<char> toSend;
    if(splitInput.size() == 2){
        // OpName and another argument, as required
        // Structure is: OPCODE | Argument | '0'
        Packet::shortToBytes(opcode, c);
        pushBytesToVector(c, toSend);

        // This char array is needed because convertion of string to char* returns const char*
        char *stringConvertedToBytes = new char[splitInput[1].length() + 1];
        stringToChar(splitInput[1], stringConvertedToBytes);
        pushBytesToVector(stringConvertedToBytes, toSend);

        // Assign char* with chars held by the vector
        c = toSend.data();

        delete[] stringConvertedToBytes;
    }
    else if(splitInput.size() > 2){
        cout << "Too many arguments!" << endl; // TODO: IS THAT THE RIGHT WAY TO TREAT THAT?
    }
    else {
        cout << "Too few arguments!" << endl; // TODO: IS THAT THE RIGHT WAY TO TREAT THAT?
    }
    delete toSend;
}
