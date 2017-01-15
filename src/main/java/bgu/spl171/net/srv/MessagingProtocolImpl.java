package bgu.spl171.net.srv;

import bgu.spl171.net.api.MessagingProtocol;
import bgu.spl171.net.api.bidi.BidiMessagingProtocol;
import bgu.spl171.net.api.bidi.Connections;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

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

    // When the user asks for a file, this holds the path so that all packets can see it
    private String fileReadPath = null;

    // When the user asks to send a file, this holds all of the data
    private byte[] dataRecived;


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
     * Assuming T message is a valid packet
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

    public String getUsername(){
        return username;
    }

    public boolean isLoggedIn(String username){
        return users.containsKey(username);
    }

    public void logIn(String username){
        this.username = username;
        users.put(username, this);
    }

    public void removeClient() throws IOException {
        connections.disconnect(connectionId);
        users.remove(username);
    }

    public void setFileReadPath(String path){
        this.fileReadPath = path;
    }

    public String getFileReadPath(){
        return fileReadPath;
    }

    public void initiateDataArray(int packets){
        this.dataRecived = new byte[packets*MAX_PACKET_SIZE];
    }

    public void insertIntoDataArray(byte[] data, int packetNum){
        // Data packets does not care for order, We'll just insert the data
        // According to protocol, each packet has 512 bytes of data

    }

    // TODO: IMPLEMENT
    public boolean isFileAvailable(String fileName){

        return false;
    }

}
