package bgu.spl171.net.impl.rci.Client;

/**
 * Created by dorgreen on 20/01/2017.
 */
public class WRQ extends ZeroRecognizedPacket {

    public WRQ() {
        super((short) 2);
    }

    @Override
    public Packet handle(MessagingProtocolImpl protocol) {
        Packet ans = null;
        String fileName = new String(packetContent);
        fileName = fileName.substring(0, fileName.length() - 1); // Remove "\0" in the end
        if (!protocol.isFileAvailable(fileName)) {

            protocol.setFileWritePathTempFolder(fileName);
            if (protocol.createFile()) {
                // After the file was created, everything is being handled by incoming DATA packets.
                ans = new ACK(ACK_SUCCESSFUL);
            } else {
                ans = new ERROR(2); // "Access violation – File cannot be written, read or deleted"
            }
        } else {
            ans = new ERROR(5); // FILE ALREADY EXIST!
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
