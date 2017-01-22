package bgu.spl171.net.impl.rci.Client;

import java.io.IOException;

/**
 * Created by dorgreen on 20/01/2017.
 */
public class DISC extends Packet {

    public DISC() {
        super((short) 10);
    }

    @Override
    // No further information is needed
    public Packet getPacket(byte[] bytes, int numOfBytes) {
        return this;
    }

    @Override
    public Packet handle(MessagingProtocolImpl protocol) {
        Packet ans = null;

        // Not logged in
        if (protocol.getUsername() == null) {
            ans = new ERROR(0);
        } else {
            try {
                protocol.removeClient();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ans = new ACK(ACK_SUCCESSFUL);
        }
        return ans;
    }

    /**
     * @return null since server never sends these
     */
    @Override
    public byte[] toBytes() {
        return null;
    }

    @Override
    public boolean isComplete() {
        return true;
    }
}
