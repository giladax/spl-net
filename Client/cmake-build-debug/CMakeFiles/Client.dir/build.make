# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.6

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /users/studs/bsc/2016/dorgreen/Downloads/clion-2016.3.2/bin/cmake/bin/cmake

# The command to remove a file.
RM = /users/studs/bsc/2016/dorgreen/Downloads/clion-2016.3.2/bin/cmake/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /users/studs/bsc/2016/dorgreen/spl-net/Client

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /users/studs/bsc/2016/dorgreen/spl-net/Client/cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/Client.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/Client.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/Client.dir/flags.make

CMakeFiles/Client.dir/src/ConnectionHandler.cpp.o: CMakeFiles/Client.dir/flags.make
CMakeFiles/Client.dir/src/ConnectionHandler.cpp.o: ../src/ConnectionHandler.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/users/studs/bsc/2016/dorgreen/spl-net/Client/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/Client.dir/src/ConnectionHandler.cpp.o"
	/usr/bin/c++   $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/Client.dir/src/ConnectionHandler.cpp.o -c /users/studs/bsc/2016/dorgreen/spl-net/Client/src/ConnectionHandler.cpp

CMakeFiles/Client.dir/src/ConnectionHandler.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Client.dir/src/ConnectionHandler.cpp.i"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /users/studs/bsc/2016/dorgreen/spl-net/Client/src/ConnectionHandler.cpp > CMakeFiles/Client.dir/src/ConnectionHandler.cpp.i

CMakeFiles/Client.dir/src/ConnectionHandler.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Client.dir/src/ConnectionHandler.cpp.s"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /users/studs/bsc/2016/dorgreen/spl-net/Client/src/ConnectionHandler.cpp -o CMakeFiles/Client.dir/src/ConnectionHandler.cpp.s

CMakeFiles/Client.dir/src/ConnectionHandler.cpp.o.requires:

.PHONY : CMakeFiles/Client.dir/src/ConnectionHandler.cpp.o.requires

CMakeFiles/Client.dir/src/ConnectionHandler.cpp.o.provides: CMakeFiles/Client.dir/src/ConnectionHandler.cpp.o.requires
	$(MAKE) -f CMakeFiles/Client.dir/build.make CMakeFiles/Client.dir/src/ConnectionHandler.cpp.o.provides.build
.PHONY : CMakeFiles/Client.dir/src/ConnectionHandler.cpp.o.provides

CMakeFiles/Client.dir/src/ConnectionHandler.cpp.o.provides.build: CMakeFiles/Client.dir/src/ConnectionHandler.cpp.o


CMakeFiles/Client.dir/src/ClientTasks/KeyboardListener.cpp.o: CMakeFiles/Client.dir/flags.make
CMakeFiles/Client.dir/src/ClientTasks/KeyboardListener.cpp.o: ../src/ClientTasks/KeyboardListener.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/users/studs/bsc/2016/dorgreen/spl-net/Client/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Building CXX object CMakeFiles/Client.dir/src/ClientTasks/KeyboardListener.cpp.o"
	/usr/bin/c++   $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/Client.dir/src/ClientTasks/KeyboardListener.cpp.o -c /users/studs/bsc/2016/dorgreen/spl-net/Client/src/ClientTasks/KeyboardListener.cpp

CMakeFiles/Client.dir/src/ClientTasks/KeyboardListener.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Client.dir/src/ClientTasks/KeyboardListener.cpp.i"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /users/studs/bsc/2016/dorgreen/spl-net/Client/src/ClientTasks/KeyboardListener.cpp > CMakeFiles/Client.dir/src/ClientTasks/KeyboardListener.cpp.i

CMakeFiles/Client.dir/src/ClientTasks/KeyboardListener.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Client.dir/src/ClientTasks/KeyboardListener.cpp.s"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /users/studs/bsc/2016/dorgreen/spl-net/Client/src/ClientTasks/KeyboardListener.cpp -o CMakeFiles/Client.dir/src/ClientTasks/KeyboardListener.cpp.s

CMakeFiles/Client.dir/src/ClientTasks/KeyboardListener.cpp.o.requires:

.PHONY : CMakeFiles/Client.dir/src/ClientTasks/KeyboardListener.cpp.o.requires

CMakeFiles/Client.dir/src/ClientTasks/KeyboardListener.cpp.o.provides: CMakeFiles/Client.dir/src/ClientTasks/KeyboardListener.cpp.o.requires
	$(MAKE) -f CMakeFiles/Client.dir/build.make CMakeFiles/Client.dir/src/ClientTasks/KeyboardListener.cpp.o.provides.build
.PHONY : CMakeFiles/Client.dir/src/ClientTasks/KeyboardListener.cpp.o.provides

CMakeFiles/Client.dir/src/ClientTasks/KeyboardListener.cpp.o.provides.build: CMakeFiles/Client.dir/src/ClientTasks/KeyboardListener.cpp.o


CMakeFiles/Client.dir/src/ClientTasks/SocketListener.cpp.o: CMakeFiles/Client.dir/flags.make
CMakeFiles/Client.dir/src/ClientTasks/SocketListener.cpp.o: ../src/ClientTasks/SocketListener.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/users/studs/bsc/2016/dorgreen/spl-net/Client/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_3) "Building CXX object CMakeFiles/Client.dir/src/ClientTasks/SocketListener.cpp.o"
	/usr/bin/c++   $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/Client.dir/src/ClientTasks/SocketListener.cpp.o -c /users/studs/bsc/2016/dorgreen/spl-net/Client/src/ClientTasks/SocketListener.cpp

CMakeFiles/Client.dir/src/ClientTasks/SocketListener.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Client.dir/src/ClientTasks/SocketListener.cpp.i"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /users/studs/bsc/2016/dorgreen/spl-net/Client/src/ClientTasks/SocketListener.cpp > CMakeFiles/Client.dir/src/ClientTasks/SocketListener.cpp.i

CMakeFiles/Client.dir/src/ClientTasks/SocketListener.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Client.dir/src/ClientTasks/SocketListener.cpp.s"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /users/studs/bsc/2016/dorgreen/spl-net/Client/src/ClientTasks/SocketListener.cpp -o CMakeFiles/Client.dir/src/ClientTasks/SocketListener.cpp.s

CMakeFiles/Client.dir/src/ClientTasks/SocketListener.cpp.o.requires:

.PHONY : CMakeFiles/Client.dir/src/ClientTasks/SocketListener.cpp.o.requires

CMakeFiles/Client.dir/src/ClientTasks/SocketListener.cpp.o.provides: CMakeFiles/Client.dir/src/ClientTasks/SocketListener.cpp.o.requires
	$(MAKE) -f CMakeFiles/Client.dir/build.make CMakeFiles/Client.dir/src/ClientTasks/SocketListener.cpp.o.provides.build
.PHONY : CMakeFiles/Client.dir/src/ClientTasks/SocketListener.cpp.o.provides

CMakeFiles/Client.dir/src/ClientTasks/SocketListener.cpp.o.provides.build: CMakeFiles/Client.dir/src/ClientTasks/SocketListener.cpp.o


CMakeFiles/Client.dir/src/Main.cpp.o: CMakeFiles/Client.dir/flags.make
CMakeFiles/Client.dir/src/Main.cpp.o: ../src/Main.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/users/studs/bsc/2016/dorgreen/spl-net/Client/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_4) "Building CXX object CMakeFiles/Client.dir/src/Main.cpp.o"
	/usr/bin/c++   $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/Client.dir/src/Main.cpp.o -c /users/studs/bsc/2016/dorgreen/spl-net/Client/src/Main.cpp

CMakeFiles/Client.dir/src/Main.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Client.dir/src/Main.cpp.i"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /users/studs/bsc/2016/dorgreen/spl-net/Client/src/Main.cpp > CMakeFiles/Client.dir/src/Main.cpp.i

CMakeFiles/Client.dir/src/Main.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Client.dir/src/Main.cpp.s"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /users/studs/bsc/2016/dorgreen/spl-net/Client/src/Main.cpp -o CMakeFiles/Client.dir/src/Main.cpp.s

CMakeFiles/Client.dir/src/Main.cpp.o.requires:

.PHONY : CMakeFiles/Client.dir/src/Main.cpp.o.requires

CMakeFiles/Client.dir/src/Main.cpp.o.provides: CMakeFiles/Client.dir/src/Main.cpp.o.requires
	$(MAKE) -f CMakeFiles/Client.dir/build.make CMakeFiles/Client.dir/src/Main.cpp.o.provides.build
.PHONY : CMakeFiles/Client.dir/src/Main.cpp.o.provides

CMakeFiles/Client.dir/src/Main.cpp.o.provides.build: CMakeFiles/Client.dir/src/Main.cpp.o


CMakeFiles/Client.dir/src/BidiProtocol/EncoderDecoder.cpp.o: CMakeFiles/Client.dir/flags.make
CMakeFiles/Client.dir/src/BidiProtocol/EncoderDecoder.cpp.o: ../src/BidiProtocol/EncoderDecoder.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/users/studs/bsc/2016/dorgreen/spl-net/Client/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_5) "Building CXX object CMakeFiles/Client.dir/src/BidiProtocol/EncoderDecoder.cpp.o"
	/usr/bin/c++   $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/Client.dir/src/BidiProtocol/EncoderDecoder.cpp.o -c /users/studs/bsc/2016/dorgreen/spl-net/Client/src/BidiProtocol/EncoderDecoder.cpp

CMakeFiles/Client.dir/src/BidiProtocol/EncoderDecoder.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Client.dir/src/BidiProtocol/EncoderDecoder.cpp.i"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /users/studs/bsc/2016/dorgreen/spl-net/Client/src/BidiProtocol/EncoderDecoder.cpp > CMakeFiles/Client.dir/src/BidiProtocol/EncoderDecoder.cpp.i

CMakeFiles/Client.dir/src/BidiProtocol/EncoderDecoder.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Client.dir/src/BidiProtocol/EncoderDecoder.cpp.s"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /users/studs/bsc/2016/dorgreen/spl-net/Client/src/BidiProtocol/EncoderDecoder.cpp -o CMakeFiles/Client.dir/src/BidiProtocol/EncoderDecoder.cpp.s

CMakeFiles/Client.dir/src/BidiProtocol/EncoderDecoder.cpp.o.requires:

.PHONY : CMakeFiles/Client.dir/src/BidiProtocol/EncoderDecoder.cpp.o.requires

CMakeFiles/Client.dir/src/BidiProtocol/EncoderDecoder.cpp.o.provides: CMakeFiles/Client.dir/src/BidiProtocol/EncoderDecoder.cpp.o.requires
	$(MAKE) -f CMakeFiles/Client.dir/build.make CMakeFiles/Client.dir/src/BidiProtocol/EncoderDecoder.cpp.o.provides.build
.PHONY : CMakeFiles/Client.dir/src/BidiProtocol/EncoderDecoder.cpp.o.provides

CMakeFiles/Client.dir/src/BidiProtocol/EncoderDecoder.cpp.o.provides.build: CMakeFiles/Client.dir/src/BidiProtocol/EncoderDecoder.cpp.o


CMakeFiles/Client.dir/src/Packets/Packet.cpp.o: CMakeFiles/Client.dir/flags.make
CMakeFiles/Client.dir/src/Packets/Packet.cpp.o: ../src/Packets/Packet.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/users/studs/bsc/2016/dorgreen/spl-net/Client/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_6) "Building CXX object CMakeFiles/Client.dir/src/Packets/Packet.cpp.o"
	/usr/bin/c++   $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/Client.dir/src/Packets/Packet.cpp.o -c /users/studs/bsc/2016/dorgreen/spl-net/Client/src/Packets/Packet.cpp

CMakeFiles/Client.dir/src/Packets/Packet.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Client.dir/src/Packets/Packet.cpp.i"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /users/studs/bsc/2016/dorgreen/spl-net/Client/src/Packets/Packet.cpp > CMakeFiles/Client.dir/src/Packets/Packet.cpp.i

CMakeFiles/Client.dir/src/Packets/Packet.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Client.dir/src/Packets/Packet.cpp.s"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /users/studs/bsc/2016/dorgreen/spl-net/Client/src/Packets/Packet.cpp -o CMakeFiles/Client.dir/src/Packets/Packet.cpp.s

CMakeFiles/Client.dir/src/Packets/Packet.cpp.o.requires:

.PHONY : CMakeFiles/Client.dir/src/Packets/Packet.cpp.o.requires

CMakeFiles/Client.dir/src/Packets/Packet.cpp.o.provides: CMakeFiles/Client.dir/src/Packets/Packet.cpp.o.requires
	$(MAKE) -f CMakeFiles/Client.dir/build.make CMakeFiles/Client.dir/src/Packets/Packet.cpp.o.provides.build
.PHONY : CMakeFiles/Client.dir/src/Packets/Packet.cpp.o.provides

CMakeFiles/Client.dir/src/Packets/Packet.cpp.o.provides.build: CMakeFiles/Client.dir/src/Packets/Packet.cpp.o


CMakeFiles/Client.dir/src/Packets/DATA.cpp.o: CMakeFiles/Client.dir/flags.make
CMakeFiles/Client.dir/src/Packets/DATA.cpp.o: ../src/Packets/DATA.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/users/studs/bsc/2016/dorgreen/spl-net/Client/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_7) "Building CXX object CMakeFiles/Client.dir/src/Packets/DATA.cpp.o"
	/usr/bin/c++   $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/Client.dir/src/Packets/DATA.cpp.o -c /users/studs/bsc/2016/dorgreen/spl-net/Client/src/Packets/DATA.cpp

CMakeFiles/Client.dir/src/Packets/DATA.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Client.dir/src/Packets/DATA.cpp.i"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /users/studs/bsc/2016/dorgreen/spl-net/Client/src/Packets/DATA.cpp > CMakeFiles/Client.dir/src/Packets/DATA.cpp.i

CMakeFiles/Client.dir/src/Packets/DATA.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Client.dir/src/Packets/DATA.cpp.s"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /users/studs/bsc/2016/dorgreen/spl-net/Client/src/Packets/DATA.cpp -o CMakeFiles/Client.dir/src/Packets/DATA.cpp.s

CMakeFiles/Client.dir/src/Packets/DATA.cpp.o.requires:

.PHONY : CMakeFiles/Client.dir/src/Packets/DATA.cpp.o.requires

CMakeFiles/Client.dir/src/Packets/DATA.cpp.o.provides: CMakeFiles/Client.dir/src/Packets/DATA.cpp.o.requires
	$(MAKE) -f CMakeFiles/Client.dir/build.make CMakeFiles/Client.dir/src/Packets/DATA.cpp.o.provides.build
.PHONY : CMakeFiles/Client.dir/src/Packets/DATA.cpp.o.provides

CMakeFiles/Client.dir/src/Packets/DATA.cpp.o.provides.build: CMakeFiles/Client.dir/src/Packets/DATA.cpp.o


CMakeFiles/Client.dir/src/Packets/ACK.cpp.o: CMakeFiles/Client.dir/flags.make
CMakeFiles/Client.dir/src/Packets/ACK.cpp.o: ../src/Packets/ACK.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/users/studs/bsc/2016/dorgreen/spl-net/Client/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_8) "Building CXX object CMakeFiles/Client.dir/src/Packets/ACK.cpp.o"
	/usr/bin/c++   $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/Client.dir/src/Packets/ACK.cpp.o -c /users/studs/bsc/2016/dorgreen/spl-net/Client/src/Packets/ACK.cpp

CMakeFiles/Client.dir/src/Packets/ACK.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Client.dir/src/Packets/ACK.cpp.i"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /users/studs/bsc/2016/dorgreen/spl-net/Client/src/Packets/ACK.cpp > CMakeFiles/Client.dir/src/Packets/ACK.cpp.i

CMakeFiles/Client.dir/src/Packets/ACK.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Client.dir/src/Packets/ACK.cpp.s"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /users/studs/bsc/2016/dorgreen/spl-net/Client/src/Packets/ACK.cpp -o CMakeFiles/Client.dir/src/Packets/ACK.cpp.s

CMakeFiles/Client.dir/src/Packets/ACK.cpp.o.requires:

.PHONY : CMakeFiles/Client.dir/src/Packets/ACK.cpp.o.requires

CMakeFiles/Client.dir/src/Packets/ACK.cpp.o.provides: CMakeFiles/Client.dir/src/Packets/ACK.cpp.o.requires
	$(MAKE) -f CMakeFiles/Client.dir/build.make CMakeFiles/Client.dir/src/Packets/ACK.cpp.o.provides.build
.PHONY : CMakeFiles/Client.dir/src/Packets/ACK.cpp.o.provides

CMakeFiles/Client.dir/src/Packets/ACK.cpp.o.provides.build: CMakeFiles/Client.dir/src/Packets/ACK.cpp.o


CMakeFiles/Client.dir/src/Packets/ERROR.cpp.o: CMakeFiles/Client.dir/flags.make
CMakeFiles/Client.dir/src/Packets/ERROR.cpp.o: ../src/Packets/ERROR.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/users/studs/bsc/2016/dorgreen/spl-net/Client/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_9) "Building CXX object CMakeFiles/Client.dir/src/Packets/ERROR.cpp.o"
	/usr/bin/c++   $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/Client.dir/src/Packets/ERROR.cpp.o -c /users/studs/bsc/2016/dorgreen/spl-net/Client/src/Packets/ERROR.cpp

CMakeFiles/Client.dir/src/Packets/ERROR.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Client.dir/src/Packets/ERROR.cpp.i"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /users/studs/bsc/2016/dorgreen/spl-net/Client/src/Packets/ERROR.cpp > CMakeFiles/Client.dir/src/Packets/ERROR.cpp.i

CMakeFiles/Client.dir/src/Packets/ERROR.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Client.dir/src/Packets/ERROR.cpp.s"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /users/studs/bsc/2016/dorgreen/spl-net/Client/src/Packets/ERROR.cpp -o CMakeFiles/Client.dir/src/Packets/ERROR.cpp.s

CMakeFiles/Client.dir/src/Packets/ERROR.cpp.o.requires:

.PHONY : CMakeFiles/Client.dir/src/Packets/ERROR.cpp.o.requires

CMakeFiles/Client.dir/src/Packets/ERROR.cpp.o.provides: CMakeFiles/Client.dir/src/Packets/ERROR.cpp.o.requires
	$(MAKE) -f CMakeFiles/Client.dir/build.make CMakeFiles/Client.dir/src/Packets/ERROR.cpp.o.provides.build
.PHONY : CMakeFiles/Client.dir/src/Packets/ERROR.cpp.o.provides

CMakeFiles/Client.dir/src/Packets/ERROR.cpp.o.provides.build: CMakeFiles/Client.dir/src/Packets/ERROR.cpp.o


# Object files for target Client
Client_OBJECTS = \
"CMakeFiles/Client.dir/src/ConnectionHandler.cpp.o" \
"CMakeFiles/Client.dir/src/ClientTasks/KeyboardListener.cpp.o" \
"CMakeFiles/Client.dir/src/ClientTasks/SocketListener.cpp.o" \
"CMakeFiles/Client.dir/src/Main.cpp.o" \
"CMakeFiles/Client.dir/src/BidiProtocol/EncoderDecoder.cpp.o" \
"CMakeFiles/Client.dir/src/Packets/Packet.cpp.o" \
"CMakeFiles/Client.dir/src/Packets/DATA.cpp.o" \
"CMakeFiles/Client.dir/src/Packets/ACK.cpp.o" \
"CMakeFiles/Client.dir/src/Packets/ERROR.cpp.o"

# External object files for target Client
Client_EXTERNAL_OBJECTS =

Client: CMakeFiles/Client.dir/src/ConnectionHandler.cpp.o
Client: CMakeFiles/Client.dir/src/ClientTasks/KeyboardListener.cpp.o
Client: CMakeFiles/Client.dir/src/ClientTasks/SocketListener.cpp.o
Client: CMakeFiles/Client.dir/src/Main.cpp.o
Client: CMakeFiles/Client.dir/src/BidiProtocol/EncoderDecoder.cpp.o
Client: CMakeFiles/Client.dir/src/Packets/Packet.cpp.o
Client: CMakeFiles/Client.dir/src/Packets/DATA.cpp.o
Client: CMakeFiles/Client.dir/src/Packets/ACK.cpp.o
Client: CMakeFiles/Client.dir/src/Packets/ERROR.cpp.o
Client: CMakeFiles/Client.dir/build.make
Client: CMakeFiles/Client.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/users/studs/bsc/2016/dorgreen/spl-net/Client/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_10) "Linking CXX executable Client"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/Client.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/Client.dir/build: Client

.PHONY : CMakeFiles/Client.dir/build

CMakeFiles/Client.dir/requires: CMakeFiles/Client.dir/src/ConnectionHandler.cpp.o.requires
CMakeFiles/Client.dir/requires: CMakeFiles/Client.dir/src/ClientTasks/KeyboardListener.cpp.o.requires
CMakeFiles/Client.dir/requires: CMakeFiles/Client.dir/src/ClientTasks/SocketListener.cpp.o.requires
CMakeFiles/Client.dir/requires: CMakeFiles/Client.dir/src/Main.cpp.o.requires
CMakeFiles/Client.dir/requires: CMakeFiles/Client.dir/src/BidiProtocol/EncoderDecoder.cpp.o.requires
CMakeFiles/Client.dir/requires: CMakeFiles/Client.dir/src/Packets/Packet.cpp.o.requires
CMakeFiles/Client.dir/requires: CMakeFiles/Client.dir/src/Packets/DATA.cpp.o.requires
CMakeFiles/Client.dir/requires: CMakeFiles/Client.dir/src/Packets/ACK.cpp.o.requires
CMakeFiles/Client.dir/requires: CMakeFiles/Client.dir/src/Packets/ERROR.cpp.o.requires

.PHONY : CMakeFiles/Client.dir/requires

CMakeFiles/Client.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/Client.dir/cmake_clean.cmake
.PHONY : CMakeFiles/Client.dir/clean

CMakeFiles/Client.dir/depend:
	cd /users/studs/bsc/2016/dorgreen/spl-net/Client/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /users/studs/bsc/2016/dorgreen/spl-net/Client /users/studs/bsc/2016/dorgreen/spl-net/Client /users/studs/bsc/2016/dorgreen/spl-net/Client/cmake-build-debug /users/studs/bsc/2016/dorgreen/spl-net/Client/cmake-build-debug /users/studs/bsc/2016/dorgreen/spl-net/Client/cmake-build-debug/CMakeFiles/Client.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/Client.dir/depend

