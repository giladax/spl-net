
#include <boost/thread.hpp>
#include "include/ConnectionHandler.h"
#include "include/ClientTasks/KeyboardListener.h"
#include "include/ClientTasks/SocketListener.h"


//using namespace std;

/**
* This code assumes that the server replies the exact text the client sent it (as opposed to the practical session example)
*/
int main (int argc, char *argv[]) {

    // TODO: Shouldn't this be (argc != 2) ?
    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }

    string host = argv[1];
    short port = (short) atoi(argv[2]);

    ConnectionHandler::getInstance();
    if(!ConnectionHandler::getInstance().connect(host, port)){
        cout << "Error connecting to server @ " << host << " : " << port << endl;
        return -1;
    }

    // Initiate KeyboardListener, SocketListener
    KeyboardListener keyboardListener = KeyboardListener();
    SocketListener socketListener = SocketListener();

    boost::thread keyboardListenerThread(boost::bind(&KeyboardListener::run, &keyboardListener));
    boost::thread socketListenerThread(boost::bind(&SocketListener::run, &socketListener));


    //Client client(host, port); // Will be dealt with @ Main. Class TO BE DELETED

    // Join both threads before termination
    keyboardListenerThread.join();
    socketListenerThread.join();



    return 0;
}