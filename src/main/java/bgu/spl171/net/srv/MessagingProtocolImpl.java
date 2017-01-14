package bgu.spl171.net.srv;

import bgu.spl171.net.api.MessagingProtocol;
import bgu.spl171.net.api.bidi.BidiMessagingProtocol;
import bgu.spl171.net.api.bidi.Connections;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dorgreen on 13/01/2017.
 */
public class MessagingProtocolImpl<T> implements BidiMessagingProtocol<T> {
    private int connectionId;
    // TODO: HOW IS shouldTerminate BEING UPDATED?!
    private boolean shouldTerminate;
    private Connections<T> connections;
    private String username;
    private static ConcurrentHashMap<String, BidiMessagingProtocol> users;


    public MessagingProtocolImpl() {

        shouldTerminate = false;
        username = null;

        if (users == null) {
            users = new ConcurrentHashMap<>();
        }

    }


    @Override
    public void start(int connectionId, Connections<T> connections) {
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
    public void process(T message) {
        Packet packet = (Packet) message;

        packet.handle(this);

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

}
