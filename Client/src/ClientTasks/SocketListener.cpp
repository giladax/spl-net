//
// Created by dorgreen on 1/17/17.
//


#include "include/ClientTasks/SocketListener.h"


SocketListener::SocketListener() {


}

void SocketListener::run() {
   char bytes [2];

   while (ConnectionHandler::getInstance().getBytes(bytes, 2)) {
      Packet* ans = ConnectionHandler::getInstance().getPacket(bytes);

      if(ans != nullptr){
         ConnectionHandler::getInstance().sendBytes(ans->toBytes(), ans->getBytesCount());
      }
   }
}

SocketListener::~SocketListener() {

}
