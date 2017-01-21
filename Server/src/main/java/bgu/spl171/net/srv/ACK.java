package bgu.spl171.net.srv;

import java.util.Arrays;

/**
 * Created by dorgreen on 20/01/2017.
 */
public class ACK extends Packet {

    protected int block_number;

    protected ACK() {
        super((short) 4);
        block_number = ACK_SUCCESSFUL; // Defined in the protocol as the default value for anything but data packets
    }

    protected ACK(int block_number) {
        super((short) 4);
        this.block_number = block_number;
    }

    @Override
    public Packet getPacket(byte[] bytes, int numOfBytes) {
        if (numOfBytes == 4) {
            this.block_number = bytesToShort(Arrays.copyOfRange(bytes, 2, 4));
            return this;
        } else return null;
    }

    // TODO: IMPLEMENT
    @Override
    public Packet handle(MessagingProtocolImpl protocol) {
        // This should communicate with packets awaiting acknowledgment

        // Send the next DATA packet awaiting confirmation, whether it's RRQ or DIRQ
        if (block_number > 0) {
            protocol.sendNextDataPacket();
        }

        return null;
    }

    @Override
    public byte[] toBytes() {
        byte[] ans = new byte[OP_CODE_SIZE + 2];
        insertArrayIntoArray(ans, shortToBytes(OP_CODE), 0);
        insertArrayIntoArray(ans, shortToBytes((short) block_number), 2);
        return ans;
    }
}
