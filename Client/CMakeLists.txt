cmake_minimum_required(VERSION 3.6)
project(Client)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES
        ConnectionHandler.cpp
        ConnectionHandler.h
        EchoClient.cpp ClientTsaks/KeyboardInput.cpp ClientTsaks/KeyboardInput.h ClientTsaks/SocketListener.cpp ClientTsaks/SocketListener.h Main.cpp BidiProtocol/EncoderDecoder.cpp BidiProtocol/EncoderDecoder.h BidiProtocol/MessagingProtocol.cpp BidiProtocol/MessagingProtocol.h Packets/RRQ.cpp Packets/RRQ.h Packets/WRQ.cpp Packets/WRQ.h Packets/DATA.cpp Packets/DATA.h Packets/ACK.cpp Packets/ACK.h Packets/ERROR.cpp Packets/ERROR.h Packets/DIRQ.cpp Packets/DIRQ.h Packets/LOGRQ.cpp Packets/LOGRQ.h Packets/DELRQ.cpp Packets/DELRQ.h Packets/BCAST.cpp Packets/BCAST.h Packets/DISC.cpp Packets/DISC.h Packets/Packet.cpp Packets/Packet.h Client.cpp Client.h)

add_executable(Client ${SOURCE_FILES})