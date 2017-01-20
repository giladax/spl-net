cmake_minimum_required(VERSION 3.6)
project(Client)

set(CMAKE_CXX_STANDARD 11)

find_package(Boost COMPONENTS system filesystem REQUIRED)

set(SOURCE_FILES
        src/ConnectionHandler.cpp
        include/ConnectionHandler.h
        src/ClientTasks/KeyboardListener.cpp include/ClientTasks/KeyboardListener.h src/ClientTasks/SocketListener.cpp include/ClientTasks/SocketListener.h src/Main.cpp src/BidiProtocol/EncoderDecoder.cpp include/BidiProtocol/EncoderDecoder.h src/Packets/Packet.cpp include/Packets/Packet.h src/Packets/DATA.cpp include/Packets/DATA.h src/Packets/ACK.cpp include/Packets/ACK.h src/Packets/ERROR.cpp include/Packets/ERROR.h )

include_directories(.)
include_directories(include)
include_directories(src)

include_directories(${Boost_INCLUDE_DIR})


link_libraries(boost_system)
link_libraries(boost_locale)
link_libraries(boost_thread)

add_executable(Client ${SOURCE_FILES})