//
// Created by dorgreen on 1/21/17.
//

#ifndef CLIENT_UTILS_H
#define CLIENT_UTILS_H

#include "vector"

using namespace std;

// Gonna put all of the fucking static functions here
class Utils {
public:
    static short bytesToShort(char* bytesArr);
    static void  shortToBytes(short num, char *bytesArr);
    static void  insertByteArrayToVector(char* bytesArr, vector<char> vec, int size);
//    static void printVector(vector vec);

};


#endif //CLIENT_UTILS_H
