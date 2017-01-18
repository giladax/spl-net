cmake_minimum_required(VERSION 3.6)
project(spl_net)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES
        Client/BidiProtocol/EncoderDecoder.cpp
        Client/BidiProtocol/EncoderDecoder.h
        Client/BidiProtocol/MessagingProtocol.cpp
        Client/BidiProtocol/MessagingProtocol.h
        Client/ClientTasks/KeyboardListener.cpp
        Client/ClientTasks/KeyboardListener.h
        Client/ClientTasks/SocketListener.cpp
        Client/ClientTasks/SocketListener.h
        Client/Packets/ACK.cpp
        Client/Packets/ACK.h
        Client/Packets/BCAST.cpp
        Client/Packets/BCAST.h
        Client/Packets/DATA.cpp
        Client/Packets/DATA.h
        Client/Packets/DELRQ.cpp
        Client/Packets/DELRQ.h
        Client/Packets/DIRQ.cpp
        Client/Packets/DIRQ.h
        Client/Packets/DISC.cpp
        Client/Packets/DISC.h
        Client/Packets/ERROR.cpp
        Client/Packets/ERROR.h
        Client/Packets/LOGRQ.cpp
        Client/Packets/LOGRQ.h
        Client/Packets/Packet.cpp
        Client/Packets/Packet.h
        Client/Packets/RRQ.cpp
        Client/Packets/RRQ.h
        Client/Packets/WRQ.cpp
        Client/Packets/WRQ.h
        Client/Client.cpp
        Client/Client.h
        Client/ConnectionHandler.cpp
        Client/ConnectionHandler.h
        Client/EchoClient.cpp
        Client/Main.cpp)

add_executable(spl_net ${SOURCE_FILES})