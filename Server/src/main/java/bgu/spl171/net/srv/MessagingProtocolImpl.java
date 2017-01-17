package bgu.spl171.net.srv;

import bgu.spl171.net.api.MessagingProtocol;
import bgu.spl171.net.api.bidi.BidiMessagingProtocol;
import bgu.spl171.net.api.bidi.Connections;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
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
    private RandomAccessFile ramFile = null;
    private FileChannel fileChannel = null;
    private int sentPacketNum = 0;
    ByteBuffer buffer = ByteBuffer.allocate(MAX_PACKET_SIZE); // TODO: SHOULD WE KILL IT WHEN CLOSING CONNECTION?

    // WRQ HANDLING PARAMETERS
    // When the user asks to send a file, this holds all of the data related to this transaction
    private byte[] dataRecived;
    private int packetsReceived = 0;
    private int numOfPacketsToReceive = 0;
    private Path fileWritePath = null;

    // DIRQ HANDLING PARAMETERS
    private byte[] dirqToSend = null;
    private int dirqPacketCounter = 0;
    private int dirqPackets = 0;


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

        Packet packet = message.handle(this);

        if (packet != null) {
            connections.send(connectionId, packet); // TODO: MAYBE THIS SHOULD BE packet.toBytes() ???
        }
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

    public boolean createFile() {
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


    // TODO: How do we know the length of the array? maybe use some other data structure? String is weird but could work!
    public void initiateDataArray(int packets) {
        this.dataRecived = new byte[packets * MAX_PACKET_SIZE];
    }

    public void insertIntoDataArray(byte[] data, int packetNum) throws IOException {
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

    public boolean isFileAvailable(String fileName) {

        Path path = FileSystems.getDefault().getPath(FILES_DIR);
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(path)) {
            for (Path p : dirStream) {
                if (p.getFileName().toString().compareTo(fileName) == 0) { // TODO: toString() might return FULL PATH not filename.
                    return true;
                }
            }

        } catch (IOException ex) {
        }
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
        // TODO: if we decide that file should have .tmp endings, change it here
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
    public void sendFile() throws IOException {
        // Read from the file "fileReadPath" into a buffer if not initiated
        if (fileChannel == null || ramFile == null) {
            ramFile = new RandomAccessFile(getFileReadPath(), "r");
            fileChannel = ramFile.getChannel();

        }
        // Fill the buffer with MAX_PACKET_SIZE bytes from file (or smaller if this is the terminal packet)
        int packetSize = fileChannel.read(buffer, sentPacketNum * MAX_PACKET_SIZE);
        buffer.flip();

        // Send just ONE packet
        if (packetSize > 0) {
            connections.send(connectionId, new Packet.DATA(packetSize, sentPacketNum, buffer.array()));
            ++sentPacketNum;
            buffer.clear();
        }

        // IF THERE IS NOTHING MORE TO SEND, Reset all parameters that holds data related to this transaction
        else {

            fileChannel.close();
            fileChannel = null;
            ramFile.close();
            ramFile = null;
            sentPacketNum = 0;
            buffer.clear();
            fileWritePath = null;
            // TODO: ADD ALL OF THE OTHER PARAMETERS HERE FOR RESTARTING
        }
    }


    /**
     * Send the next packet. Each call sends ONE packet.
     * The last call (the packet being sent has less than MAX_PACKET_SIZE bytes) clears everything created by this process.
     */
    public void sendFileListing() {

        // Initiate...
        if (dirqToSend == null) {
            Path path = FileSystems.getDefault().getPath(FILES_DIR);
            String listing = "";
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
                for (Path p : stream) {
                    // TODO: IF ADDED .tmp TO FILES BEING TRANSFERRED, ADD 'if' HERE
                    listing.concat(p.getFileName().toString()+ "/0"); // Separate lines with "\0", as per protocol
                }
                dirqToSend = listing.getBytes();
                dirqPacketCounter = 0;
                dirqPackets = (dirqToSend.length / MAX_PACKET_SIZE) + 1;
            } catch (IOException ex) {
            }
        }

        // Send the packet if it's not the last
        if (dirqPacketCounter < dirqPackets) {
            connections.send(connectionId,
                    new Packet.DATA(
                            MAX_PACKET_SIZE,
                            dirqPacketCounter,
                            Arrays.copyOfRange(dirqToSend, MAX_PACKET_SIZE * dirqPacketCounter, MAX_PACKET_SIZE * (dirqPacketCounter + 1))
                    ));
            ++dirqPacketCounter;
        }

        // It's the last packet. send it and reset
        else {
            int lastPacketLength = dirqToSend.length % MAX_PACKET_SIZE;
            connections.send(connectionId,
                    new Packet.DATA(
                            (short) lastPacketLength,
                            dirqPacketCounter,
                            Arrays.copyOfRange(dirqToSend, MAX_PACKET_SIZE * dirqPacketCounter, dirqToSend.length)
                    ));

            // Reset everything
            dirqToSend = null;
            dirqPacketCounter = 0;
            dirqPackets = 0;
        }
    }

    /**
     * This method only handles ongoing transfers
     * Is being called by incoming ACK(n) where n > 0
     * Assuming: no RRQ and DIRQ operations are ongoing concurrently
     * both sendFile() and sendFileListing() reset their parameters when finished
     */
    public void sendNextDataPacket() {

        // File is being sent, continue sending it
        if (fileReadPath != null) {
            sendFile();
        }

        // Directory listing is being sent, continue sending it
        else if (dirqToSend != null) {
            sendFileListing();
        }
    }

    public boolean deleteFile(String fileName){
        boolean ans = false;
        try{
            Files.delete(FileSystems.getDefault().getPath(FILES_DIR + fileName));
            connections.broadcast(new Packet.BCAST(1, fileName));
        }
        catch(IOException ex){
            return false;
        }

        return ans;
    }

}
