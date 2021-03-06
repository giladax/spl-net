package bgu.spl171.net.impl.rci.Client;

import bgu.spl171.net.api.MessageEncoderDecoder;
import bgu.spl171.net.api.bidi.BidiMessagingProtocol;
import bgu.spl171.net.srv.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Supplier;

public abstract class BaseServer<T> implements Server<T> {

    private final int port;
    private final Supplier<BidiMessagingProtocol<T>> protocolFactory;
    private final Supplier<MessageEncoderDecoder<T>> encdecFactory;
    private ServerSocket sock;
    private final ConnectionsImpl<T> connections;

    public enum Opcode {


        RRQ("Read request"),
        WRQ("Write request"),
        DATA("Data"),
        ACK("Acknowledgment"),
        ERROR("Error"),
        DIRQ("Directory listing request"),
        LOGRQ("Login request"),
        DELRQ("Delete request"),
        BCAST("Broadcast file added/deleted"),
        DISC("Disconnect");

        // Saves the long name of every Opcode
        private final String operation;

        Opcode(String operation) {
            this.operation = operation;
        }

        public String getOperation() {
            return operation;
        }
    }


    public BaseServer(
            int port,
            Supplier<BidiMessagingProtocol<T>> protocolFactory,
            Supplier<MessageEncoderDecoder<T>> encdecFactory) {

        this.port = port;
        this.protocolFactory = protocolFactory;
        this.encdecFactory = encdecFactory;
        this.sock = null;
        this.connections = new ConnectionsImpl<>();
    }

    @Override
    public void serve() {

        System.out.print("You just got served");

        try (ServerSocket serverSock = new ServerSocket(port)) {

            this.sock = serverSock; //just to be able to close

            while (!Thread.currentThread().isInterrupted()) {
                BidiMessagingProtocol<T> protocol = protocolFactory.get();
                Socket clientSock = serverSock.accept();



                BlockingConnectionHandler<T> handler = new BlockingConnectionHandler<T>(
                        clientSock,
                        encdecFactory.get(),
                        protocol);

                int connectionId = connections.add(handler);
                protocol.start(connectionId, connections);

                execute(handler);
            }
        } catch (IOException ex) {
        }

        System.out.println("server closed!!!");
    }

    @Override
    public void close() throws IOException {
        if (sock != null)
            sock.close();
    }

    protected abstract void execute(BlockingConnectionHandler<T> handler);

}
