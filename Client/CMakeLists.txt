cmake_minimum_required(VERSION 3.6)
project(Client)

set(CMAKE_CXX_STANDARD 11)




set(SOURCE_FILES
        src/ConnectionHandler.cpp
        include/ConnectionHandler.h
        src/EchoClient.cpp src/ClientTasks/KeyboardListener.cpp include/ClientTasks/KeyboardListener.h src/ClientTasks/SocketListener.cpp include/ClientTasks/SocketListener.h src/Main.cpp src/BidiProtocol/EncoderDecoder.cpp include/BidiProtocol/EncoderDecoder.h  src/Packets/RRQ.cpp include/Packets/RRQ.h src/Packets/WRQ.cpp include/Packets/WRQ.h src/Packets/DATA.cpp include/Packets/DATA.h src/Packets/ACK.cpp include/Packets/ACK.h src/Packets/ERROR.cpp include/Packets/ERROR.h src/Packets/DIRQ.cpp include/Packets/DIRQ.h src/Packets/LOGRQ.cpp include/Packets/LOGRQ.h src/Packets/DELRQ.cpp include/Packets/DELRQ.h src/Packets/BCAST.cpp include/Packets/BCAST.h src/Packets/DISC.cpp include/Packets/DISC.h src/Packets/Packet.cpp include/Packets/Packet.h src/Client.cpp include/Client.h)

add_executable(Client ${SOURCE_FILES})
include_directories(.)
include_directories(include)
include_directories(src)

include_directories(${Boost_INCLUDE_DIR})



link_libraries(boost_system)
link_libraries(boost_locale)
link_libraries(boost_thread)

find_package(Boost 1.45.0 COMPONENTS system filesystem REQUIRED)



