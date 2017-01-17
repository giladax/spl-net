package bgu.spl171.net.srv;

import bgu.spl171.net.api.bidi.Connections;
import bgu.spl171.net.srv.*;

import java.io.IOException;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * Created by dor green on 09/01/2017.
 * <p>
 * This class is OUR implementation of Connections<T>.
 * <p>
 * From the PDF:
 * *  Implement this class to hold a list of the new ConnectionHandler interface for each active client.
 * *  Use it to implement the interface functions.
 * *  Notice that given a connections implementation, any protocol should run.
 * *  This means that you keep your implementation of Connections on T.
 */
public class ConnectionsImpl<T> implements Connections<T> {

    private WeakHashMap<Integer, ConnectionHandler<T>> handlers; // Maybe WeakHashMap per today's lecture

    public ConnectionsImpl() {
        handlers = new WeakHashMap<>();
    }


    @Override
    public void broadcast(T msg) {
        // Should be something like: for(Connection C : connections) C.send(msg);
        Set<Integer> keyset = handlers.keySet();

        for (Integer key : keyset) {
            handlers.get(key).send(msg);
        }
    }

    @Override
    public boolean send(int connectionId, T msg) {
        handlers.get(new Integer(connectionId)).send(msg);

        //Should this return something? maybe "false" only if no ACK received / received ERROR?
        return false; // TODO: DEBUG ONLY
    }

    @Override
    public void disconnect(int connectionId) throws IOException {
        // Should use the close() promised by Closable interface
        Integer key = new Integer(connectionId);
        handlers.get(key).close();
        handlers.remove(key);
    }

    public void add(int connectionID, ConnectionHandler handler) {
        handlers.put(connectionID, handler);
    }
        // We won't register the same user twice - this is handled by Packet and MessagingProtocolImpl
        // Packet and MessagingProtocolImpl promise that an ERROR packet of the right will be sent in that case
}
