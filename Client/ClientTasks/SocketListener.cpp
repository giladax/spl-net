//
// Created by dorgreen on 1/17/17.
//

#include "SocketListener.h"

SocketListener::SocketListener(ConnectionHandler &handler, EncoderDecoder &encdec, MessagingProtocol &protocol):
        handler(handler),
        encdec(encdec),
        protocol(protocol){

}
