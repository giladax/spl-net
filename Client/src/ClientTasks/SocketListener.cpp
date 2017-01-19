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
   char *bytes;

   while (getBytes(bytes, 2)) {
      Packet ans = handler.getPacket(bytes);

      if()
   }
}
