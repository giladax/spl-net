package bgu.spl171.net.srv;

import bgu.spl171.net.api.MessageEncoderDecoder;
import bgu.spl171.net.api.MessagingProtocol;

import java.util.function.Supplier;

/**
 * Created by dorgreen on 10/01/2017.
 * This is our implementation of Thread Per Client server
 * It is basically BaseServer, only we create a new thread for each connection
 */
public class ThreadPerClientServer<T> extends BaseServer<T> {

    public ThreadPerClientServer(
            int port,
            Supplier<MessagingProtocol<T>> protocolFactory,
            Supplier<MessageEncoderDecoder<T>> encdecFactory) {

        super(port, protocolFactory, encdecFactory);
    }

    @Override
    protected void execute(BlockingConnectionHandler<T> handler) {
        new Thread(handler);
    }
}
