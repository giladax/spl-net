package bgu.spl171.net.impl.rci.Client;

/**
 * Created by dorgreen on 20/01/2017.
 */
public class DELRQ extends ZeroRecognizedPacket {

    public DELRQ() {
        super((short) 8);
    }

    @Override
    public Packet handle(MessagingProtocolImpl protocol) {
        Packet ans;
        String fileName = new String(packetContent);
        fileName = fileName.substring(0, fileName.length()-1); // Remove "\0" at the end
        if (!protocol.isFileAvailable(fileName)) {
            ans = new ERROR(1); // FILE NOT FOUND
        }
        // If deleted successfully, this also sends the BCAST
        else if (protocol.deleteFile(fileName)) {
            ans = new ACK(ACK_SUCCESSFUL);
        } else ans = new ERROR(2); // "Access violation – File cannot be written, read or deleted."

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
