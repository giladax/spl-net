package bgu.spl171.net.impl.rci.Client;

import bgu.spl171.net.srv.Server;

/**
 * Created by dorgreen on 21/01/2017.
 */

public class Main {
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Please enter a port to listen on");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.threadPerClient(port,
                ()-> new MessagingProtocolImpl(),()->new BidiEncoderDecoder<Packet>()).serve();


    }
}
