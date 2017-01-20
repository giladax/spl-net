cmake_minimum_required(VERSION 3.6)
project(Client)

set(CMAKE_CXX_STANDARD 11)




set(SOURCE_FILES
        src/ConnectionHandler.cpp
        include/ConnectionHandler.h
        src/ClientTasks/KeyboardListener.cpp include/ClientTasks/KeyboardListener.h src/ClientTasks/SocketListener.cpp include/ClientTasks/SocketListener.h src/Main.cpp src/BidiProtocol/EncoderDecoder.cpp include/BidiProtocol/EncoderDecoder.h src/Packets/DATA.cpp include/Packets/DATA.h src/Packets/ACK.cpp include/Packets/ACK.h src/Packets/ERROR.cpp include/Packets/ERROR.h src/Packets/Packet.cpp include/Packets/Packet.h)

add_executable(Client ${SOURCE_FILES})
include_directories(.)
include_directories(include)
include_directories(src)

include_directories(${Boost_INCLUDE_DIR})



link_libraries(boost_system)
link_libraries(boost_locale)
link_libraries(boost_thread)

find_package(Boost 1.45.0 COMPONENTS system filesystem REQUIRED)



