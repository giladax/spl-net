//
// Created by dorgreen on 1/17/17.
//

#include "include/BidiProtocol/EncoderDecoder.h"
#include <iostream>
#include <sstream>
#include "Utils.h"
#include <boost/algorithm/string/split.hpp>
#include <boost/algorithm/string/classification.hpp>

using namespace std;

// This gets an input from the user, returns data to be sent to server
vector<char> EncoderDecoder::encode(string line) {

    vector<char> toSend;
    char c[2];

    // Split input
    std::vector<std::string> splitInput;
    boost::split(splitInput, line, boost::is_any_of(" "));
    if (splitInput[0].compare("DISC") == 0) {
        // Input is valid
        if (splitInput.size() == 1) {
            // Get the Opcode to char*, into c
            // That's all for this packet
            Utils::shortToBytes((short) 10, c);
            pushBytesToVector(c,2,toSend);
        }
            // Input is INVALID
        else {
            cout << "input invalid" << endl; // TODO: MAKE SURE THIS IS THE RIGHT PRINT
        }
    } else if (splitInput[0].compare("DIRQ") == 0) {
        // Input is valid
        if (splitInput.size() == 1) {
            // Get the Opcode to char*, into c
            // That's all for this packet
            Utils::shortToBytes((short) 6, c);
            pushBytesToVector(c,2,toSend);
        }
            // Input is INVALID
        else {
            cout << "input invalid" << endl; // TODO: MAKE SURE THIS IS THE RIGHT PRINT
        }

    } else if (splitInput[0].compare("LOGRQ") == 0) {
        toSend = argumentsToStructuredCharArray(splitInput,line, (short) 7);
    }
    else if (splitInput[0].compare("DELRQ") == 0) {
        toSend = argumentsToStructuredCharArray(splitInput,line, (short) 8);
    }
    else if (splitInput[0].compare("RRQ") == 0) {
        toSend = argumentsToStructuredCharArray(splitInput,line, (short) 1);
    }
    else if (splitInput[0].compare("WRQ") == 0) {
        toSend = argumentsToStructuredCharArray(splitInput,line, (short) 2);
    }
    else {
        cout << "Invalid operation!" << endl;
    }

    return toSend;

}

void EncoderDecoder::pushBytesToVector(char *bytes, int size, vector<char> &vec) {
    for (int i = 0; i < size; ++i) {
        vec.push_back(bytes[i]);
    }
}

// Convert string to bytes and assign to cstr
void EncoderDecoder::stringToChar(string line, char *cstr) {
    strcpy(cstr, line.c_str());
}

// Get user input, return char* that could be sent to the server
// If input is wrong, return nullptr and print the error to the screen
vector<char> EncoderDecoder::argumentsToStructuredCharArray(vector<string> splitInput, string line, short opcode) {
    vector<char> helper;
    if (splitInput.size() == 2 || opcode == 7) {



        // OpName and another argument, as required
        // Structure is: OPCODE | Argument | '0'
        char bytes[2];
        Utils::shortToBytes(opcode, bytes);
        pushBytesToVector(bytes, 2, helper);

        /* This char array is needed because conversion of string to char* returns const char*

        char *stringConvertedToBytes = new char[splitInput[1].length() + 1];
        stringToChar(splitInput[1], stringConvertedToBytes);
        pushBytesToVector(stringConvertedToBytes, (int) (splitInput[1].length() + 1), helper);
        */
        if (splitInput.size() > 0 ) {
        line = line.substr(splitInput[0].length() + 1);
        }else{
            line = "";
        }
        char* token = new char;
        strcpy(token, line.c_str());


        pushBytesToVector(token, line.length() + 1, helper);


        /*
        //cout<<bytes[0]<<" SDF"<< bytes[1]<< endl;
        for (auto i = helper.begin(); i != helper.end(); ++i) {
            cout << *i << "SHIT" << endl;
        }
         */

        delete[] token;
    } else if (splitInput.size() > 2) {
        cout << "Too many arguments!" << endl; // TODO: IS THAT THE RIGHT WAY TO TREAT THAT?
    } else {
        cout << "Too few arguments!" << endl; // TODO: IS THAT THE RIGHT WAY TO TREAT THAT?
    }

    return helper;
}

EncoderDecoder::~EncoderDecoder() {

}

