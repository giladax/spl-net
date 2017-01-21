package bgu.spl171.net.srv;

import bgu.spl171.net.api.MessageEncoderDecoder;
import bgu.spl171.net.api.MessagingProtocol;
import bgu.spl171.net.api.bidi.BidiMessagingProtocol;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class BlockingConnectionHandler<Packet> implements Runnable, bgu.spl171.net.srv.ConnectionHandler<Packet> {


    private final BidiMessagingProtocol<Packet> protocol;
    private final BidiEncoderDecoder<Packet> encdec;
    private final Socket sock;
    private BufferedInputStream in;
    private BufferedOutputStream out;
    private volatile boolean connected = true;

    public BlockingConnectionHandler(Socket sock, BidiEncoderDecoder<Packet> reader, BidiMessagingProtocol<Packet> protocol) {
        this.sock = sock;
        this.encdec = reader;
        this.protocol = protocol;
    }

    @Override
    public void run() {

        try (Socket sock = this.sock) { // Just for automatic closing
            int read;

            in = new BufferedInputStream(sock.getInputStream());
            out = new BufferedOutputStream(sock.getOutputStream());

            while (!protocol.shouldTerminate() && connected && (read = in.read()) >= 0) { // Changed as per staff instruction
                Packet nextMessage = encdec.decodeNextByte((byte) read); // Not null <==> Packet is complete
                if (nextMessage != null) {
                    protocol.process(nextMessage); // Protocol-specific logic is to be implemented in this function

                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void close() throws IOException {
        connected = false;
        sock.close();
    }

    @Override
    public void send(Packet msg) {
        try {
            out.write(encdec.encode(msg));
            out.flush();
        } catch (IOException ex) {
            return;
        }
    }
}

