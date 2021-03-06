package bgu.spl171.net.impl.rci.Client;

/**
 * Created by dorgreen on 20/01/2017.
 */
public class DIRQ extends Packet {

    public DIRQ() {
        super((short) 6);
    }

    @Override
    // No further information is needed
    public Packet getPacket(byte[] bytes, int numOfBytes) {
        return this;
    }

    @Override
    public Packet handle(MessagingProtocolImpl protocol) {
        // Initiates the sending process and SENDS THE FIRST PACKET. a call from incoming ACK packet will continue it.
        protocol.sendFileListing();

        // No need to send anything as sendFileListing() will take case of this.
        //return new ACK(ACK_SUCCESSFUL);

        return null;
    }

    /**
     * @return null since server never sends this
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
