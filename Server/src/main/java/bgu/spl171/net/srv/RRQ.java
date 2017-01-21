package bgu.spl171.net.srv;

import java.io.IOException;

/**
 * Created by dorgreen on 20/01/2017.
 */
public class RRQ extends Packet.ZeroRecognizedPacket {
    public RRQ() {
        super((short) 1);
    }

    // TODO: IMPLEMENT
    @Override
    public Packet handle(MessagingProtocolImpl protocol) {
        Packet ans = null;
        protocol.setFileReadPath(new String(packetContent)); // TODO: this already has the '/0' at the end, make sure this isn't an issue

        if (protocol.isFileAvailable(protocol.getFileReadPath())) {
            ans = new ACK(ACK_SUCCESSFUL);

            // This handles separating into packets, sending, awaiting ACK and clearing parameters in the end
            try {
                protocol.sendFile();
            } catch (IOException ex) {
                ans = new ERROR(2); // ERROR: "Access violation – File cannot be written, read or deleted." as per 2.2 on document
            }
        } else {
            ans = new ERROR(1); // FILE NOT FOUND
        }

        return ans;
    }

    /**
     * @return null since server never sends this
     */
    @Override
    public byte[] toBytes() {
        return null;
    }
}
