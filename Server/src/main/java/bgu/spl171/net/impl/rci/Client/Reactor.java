package bgu.spl171.net.impl.rci.Client;

import bgu.spl171.net.api.bidi.BidiMessagingProtocol;
import bgu.spl171.net.srv.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

public class Reactor<T> implements Server<T> {

    private final int port;
    // TODO: should be changed to BIDI
    private final Supplier<BidiMessagingProtocol<T>> protocolFactory;
    private final Supplier<BidiEncoderDecoder<T>> readerFactory;
    private final ActorThreadPool pool;
    private Selector selector;
    private ConnectionsImpl<T> connections;
    private Thread selectorThread;
    private final ConcurrentLinkedQueue<Runnable> selectorTasks = new ConcurrentLinkedQueue<>();

    public Reactor(
            int numThreads,
            int port,
            Supplier<BidiMessagingProtocol<T>> protocolFactory,
            Supplier<BidiEncoderDecoder<T>> readerFactory) {

        this.pool = new ActorThreadPool(numThreads);
        this.port = port;
        this.protocolFactory = protocolFactory;
        this.readerFactory = readerFactory;

        this.connections = new ConnectionsImpl<T>();
    }

    @Override
    public void serve() {

        System.out.println("You just got served!"); // TODO: DEBUG ONLY
        selectorThread = Thread.currentThread();
        try (Selector selector = Selector.open();
                ServerSocketChannel serverSock = ServerSocketChannel.open()) {

            this.selector = selector; //just to be able to close

            serverSock.bind(new InetSocketAddress(port));
            serverSock.configureBlocking(false);
            serverSock.register(selector, SelectionKey.OP_ACCEPT);

            while (!Thread.currentThread().isInterrupted()) {

                selector.select();
                runSelectionThreadTasks();

                for (SelectionKey key : selector.selectedKeys()) {

                    if (!key.isValid()) {
                        continue;
                    } else if (key.isAcceptable()) {
                        handleAccept(serverSock, selector);
                    } else {
                        handleReadWrite(key);
                    }
                }

                selector.selectedKeys().clear(); //clear the selected keys set so that we can know about new events

            }

        } catch (ClosedSelectorException ex) {
            //do nothing - server was requested to be closed
        } catch (IOException ex) {
            //this is an error
            ex.printStackTrace();
        }

        System.out.println("server closed!!!");
        pool.shutdown();
    }

    /*package*/ void updateInterestedOps(SocketChannel chan, int ops) {
        final SelectionKey key = chan.keyFor(selector);
        if (Thread.currentThread() == selectorThread) {
            key.interestOps(ops);
        } else {
            selectorTasks.add(() -> {
                key.interestOps(ops);
            });
            selector.wakeup();
        }
    }


    private void handleAccept(ServerSocketChannel serverChan, Selector selector) throws IOException {
        SocketChannel clientChan = serverChan.accept();
        clientChan.configureBlocking(false);
        BidiMessagingProtocol<T> msgProtocol= protocolFactory.get();
        final NonBlockingConnectionHandler handler = new NonBlockingConnectionHandler(
                readerFactory.get(),
                msgProtocol,
                clientChan,
                this);

        // Add this handler to the ConnectionsImpl instance. We also increment the ID so that no two handlers get the same ID
        connections.add(handler);


        clientChan.register(selector, SelectionKey.OP_READ, handler);

    }

    private void handleReadWrite(SelectionKey key) {
        NonBlockingConnectionHandler handler = (NonBlockingConnectionHandler) key.attachment();
        if (key.isReadable()) {
            Runnable task = handler.continueRead();
            if (task != null) {
                pool.submit(handler, task);
            }
        }
        if (key.isValid() && key.isWritable()) { // Added instead of "else" as per staff instruction
            handler.continueWrite();
        }

    }

    private void runSelectionThreadTasks() {
        while (!selectorTasks.isEmpty()) {
            selectorTasks.remove().run();
        }
    }

    @Override
    public void close() throws IOException {
        selector.close();
    }

}
