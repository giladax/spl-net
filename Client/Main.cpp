
#include <stdlib.h>
#include <iostream>
#include <string>
#include "ConnectionHandler.h"
#include "Client.h"

using namespace std;

/**
* This code assumes that the server replies the exact text the client sent it (as opposed to the practical session example)
*/
int main (int argc, char *argv[]) {
    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }

    string host = argv[1];
    short port = atoi(argv[2]);

    Client client(host, port);





    return 0;
}