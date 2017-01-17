package bgu.spl171.net.srv;

import bgu.spl171.net.api.MessagingProtocol;
import bgu.spl171.net.api.bidi.BidiMessagingProtocol;
import bgu.spl171.net.api.bidi.Connections;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;

import static java.nio.file.StandardOpenOption.APPEND;

/**
 * Created by dorgreen on 13/01/2017.
 */
public class MessagingProtocolImpl implements BidiMessagingProtocol<Packet> {
    private final int MAX_PACKET_SIZE = 512; // Bytes. as per protocol

    private int connectionId;
    // TODO: HOW IS shouldTerminate BEING UPDATED?!
    private boolean shouldTerminate;
    private Connections<Packet> connections;
    private String username;
    private static ConcurrentHashMap<String, BidiMessagingProtocol> users;
    private final String FILES_DIR = "Files/";

    // RRQ HANDLING PARAMETERS
    // When the user asks for a file, this holds all of the data related to this transaction
    private Path fileReadPath = null;
    private FileReader fileReader = null; // = new FileReader(inputFileName);
    private int sentPacketNum = 0;
    ByteBuffer buffer = ByteBuffer.allocate(MAX_PACKET_SIZE);

    // WRQ HANDLING PARAMETERS
    // When the user asks to send a file, this holds all of the data related to this transaction
    private byte[] dataRecived;
    private int packetsReceived = 0;
    private int numOfPacketsToReceive = 0;
    private Path fileWritePath = null;


    public MessagingProtocolImpl() {

        shouldTerminate = false;
        username = null;

        if (users == null) {
            users = new ConcurrentHashMap<>();
        }

    }


    @Override
    public void start(int connectionId, Connections<Packet> connections) {
        this.connections = connections;
        this.connectionId = connectionId;

    }

    @Override
    /**
     * Since ConnectionHandler.run() uses this, We'll hold all protocol-specific logic in this function
     * Assuming message is a valid packet
     *
     * Has to use connection.send(), as per 2.2 in the document
     **/
    public void process(Packet message) {
        Packet packet = message;

        packet.handle(this); // Is this the right return type? what in case of multiple DATA packets?

        //connections.send(connectionId, packet);

    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }

    public BidiMessagingProtocol getInstanceFromUsername(String username) {
        return users.get(username);
    }

    public String getUsername() {
        return username;
    }

    public boolean isLoggedIn(String username) {
        return users.containsKey(username);
    }

    public void logIn(String username) {
        this.username = username;
        users.put(username, this);
    }

    public void removeClient() throws IOException {
        connections.disconnect(connectionId);
        users.remove(username);
    }

    public void setFileReadPath(String fileName) {
        this.fileReadPath = FileSystems.getDefault().getPath(FILES_DIR + fileName);
    }

    public String getFileReadPath() {
        return fileReadPath.toString();
    }

    public void setFileWritePath(String fileName) {
        this.fileWritePath = FileSystems.getDefault().getPath(FILES_DIR + fileName);
    }

    public boolean createFile(){
        try {
            Files.createFile(fileWritePath);
            return true;
        } catch (IOException e) {
            return false;
            //e.printStackTrace();
        }
    }

    public String getFileWritePath() {
        return fileWritePath.toString();
    }

    public void initiateDataArray(int packets) {
        this.dataRecived = new byte[packets * MAX_PACKET_SIZE];
    }

    public void insertIntoDataArray(byte[] data, int packetNum) {
        // Data packets does not care for order, We'll just insert the data
        // According to protocol, each packet has 512 bytes of data, unless it's the last one
        // This also checks for completeness, saves the file and clear parameters used for this WRQ

        for (int i = 0; i < data.length; ++i) {
            dataRecived[(packetNum - 1) * MAX_PACKET_SIZE + i] = data[i]; // packetNum starts with 1 not 0
        }

        if (data.length < MAX_PACKET_SIZE) {
            this.numOfPacketsToReceive = packetNum;
        }

        ++packetsReceived;

        if (numOfPacketsToReceive == packetsReceived && numOfPacketsToReceive != 0) {
            saveReceivedFile();
        }

    }

    // TODO: IMPLEMENT
    public boolean isFileAvailable(String fileName) {
        // This should probably run a "ls" command and search for "fileName" in it
        return false;
    }

    public void broadcast(Packet.BCAST packet) {
        connections.broadcast(packet);
    }

    private void saveReceivedFile() {
        // File was already created. Fill it with data from dataReceived array
        try {
            Files.write(fileWritePath, dataRecived, APPEND);
        } catch (IOException e) {
            connections.send(connectionId, new Packet.ERROR(2)); // ERROR: "Access violation – File cannot be written, read or deleted."
            e.printStackTrace();
        }

        broadcast(new Packet.BCAST(0, fileWritePath));

        // Reset all parameters that holds data related to this transaction
        dataRecived = null;
        packetsReceived = 0;
        numOfPacketsToReceive = 0;
        fileWritePath = null;

    }

    /**
     * Each call of this method sends ONE data packet
     * If there is nothing more to send, it clears everything it made
     * First call is made after receiving a RRQ packet, the next are made after receiving ACK packets for previous packets
     */
    public void sendFile() {
        // TODO: Read from the file "fileReadPath" into a buffer if not initiated
        // TODO: Send ONE data packet of size MAX_PACKET_SIZE (or smaller if this is the terminal packet)
        if (fileReader == null) {
            fileReader = new FileReader(fileReadPath);
        }

        byte[] toSend = new byte[MAX_PACKET_SIZE];
        int packetSize;

        if (packetSize > 0) {
            connections.send(connectionId, new Packet.DATA(packetSize, sentPacketNum, toSend));
        }

        // IF THERE IS NOTHING MORE TO SEND, Reset all parameters that holds data related to this transaction
        else (packetSize == 0) {
            fileWritePath = null;
            fileReader.close();
            fileReader = null;
            sentPacketNum = 0;
            buffer = null;
            // TODO: ADD ALL OF THE OTHER PARAMETERS HERE FOR RESTARTING
        }
    }

}
