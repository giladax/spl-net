//
// Created by dorgreen on 1/17/17.
//

#ifndef CLIENT_KEYBOARDLISTENER_H
#define CLIENT_KEYBOARDLISTENER_H

#include "../ConnectionHandler.h"
#include "../BidiProtocol/EncoderDecoder.h"
#include "../BidiProtocol/MessagingProtocol.h"


class KeyboardListener {
public:
    KeyboardListener();
    KeyboardListener(ConnectionHandler& handler, EncoderDecoder& encdec, MessagingProtocol& protocol);
    void run();

    virtual ~KeyboardListener();

private:
    ConnectionHandler handler;
    EncoderDecoder encdec;
    MessagingProtocol protocol;

};


#endif //CLIENT_KEYBOARDLISTENER_H