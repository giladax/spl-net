//
// Created by dorgreen on 1/21/17.
//

#include <iostream>
#include "Utils.h"
using namespace std;
/*
 * Supplied by BGU Staff
 */
void Utils::shortToBytes(short num, char *bytesArr) {
    bytesArr[0] = (char)((num >> 8) & 0xFF);
    bytesArr[1] = (char)(num & 0xFF);
}

void Utils::insertByteArrayToVector(char *bytesArr, vector<char> vec, int size) {
    // TODO: if this shit works -> put a petek in the kotel
    copy(bytesArr, bytesArr + size, back_inserter(vec));
}

/*
 * Supplied by BGU staff
 */
short Utils::bytesToShort(char *bytesArr) {
    short result = (short) ((bytesArr[0] & 0xff) << 8);
    result += (short) (bytesArr[1] & 0xff);
    return result;


}
/*
void Utils::printVector(vector vec) {
    for (auto i = vec.begin(); i != vec.end(); ++i)

        cout<< *i << ' ';

}
*/