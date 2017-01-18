#include "boost"
#include <stdlib.h>
#include <iostream>
#include <string>
#include "ConnectionHandler.h"
#include "Client.h"
#include "ClientTasks/KeyboardListener.h"
#include "ClientTasks/SocketListener.h"

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

    ConnectionHandler* connectionHandler = new ConnectionHandler(host, port);

    // Initiate KeyboardListener, SocketListener as new threads via BOOST
    KeyboardListener* keyboardListener = new KeyboardListener(); // TODO: Create this class, run via BOOST
    SocketListener* socketListener = new SocketListener();


    //Client client(host, port); // Will be dealt with @ Main. Class TO BE DELETED

    // TODO: "join" BOTH THREADS BEFORE TERMINATION

    delete keyboardListener;
    delete connectionHandler;
    delete socketListener;

    return 0;
}