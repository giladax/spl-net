package bgu.spl171.net.srv;

import bgu.spl171.net.api.bidi.Connections;
import bgu.spl171.net.srv.*

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * Created by dorgreen on 09/01/2017.
 *
 * This class is OUR implementation of Connections<T>.
 *
 * From the PDF:
 **  Implement this class to hold a list of the new ConnectionHandler interface for each active client.
 **  Use it to implement the interface functions.
 **  Notice that given a connections implementation, any protocol should run.
 **  This means that you keep your implementation of Connections on T.
 */
public class ConnectionsImpl<T> implements Connections<T> {

    private HashMap<Integer, NonBlockingConnectionHandler<T>> handlers;


    @Override
    public void broadcast(T msg) {
        // Should be something like: for(Connection C : connections) C.send(msg);
    }

    @Override
    public boolean send(int connectionId, T msg) {
        // Should be something like:  get(connecionId).sent(msg);
        return false;
    }

    @Override
    public void disconnect(int connectionId) {
        // Should use the close() promised by Closable interface
    }


}
