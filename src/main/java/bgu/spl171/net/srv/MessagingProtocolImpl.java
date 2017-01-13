package bgu.spl171.net.srv;

import bgu.spl171.net.api.MessagingProtocol;
import bgu.spl171.net.api.bidi.BidiMessagingProtocol;
import bgu.spl171.net.api.bidi.Connections;

/**
 * Created by dorgreen on 13/01/2017.
 */
public class MessagingProtocolImpl<T> implements BidiMessagingProtocol<T> {
    private int connectionId;
    private boolean shouldTerminate;
    private Connections<T> connections;


    public MessagingProtocolImpl(){
        shouldTerminate = false;
    }


    @Override
    public void start(int connectionId, Connections<T> connections) {
        this.connections = connections;
        this.connectionId = connectionId;

    }

    @Override
    public void process(T message) {
        // Uses connection.send() as per 2.2 in the document
        connections.send(connectionId, message);

    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }
}
