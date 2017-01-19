//
// Created by dorgreen on 1/17/17.
//

#include "../ClientTasks/"
#include "../../include/ClientTasks/SocketListener.h"
#include "../../include/Packets/Packet.h"

SocketListener::SocketListener(ConnectionHandler& handler):
        handler(handler){


}

void SocketListener::run() {
   Packet ans = handler.getPacket();
}
