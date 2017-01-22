package bgu.spl171.net.impl.rci.Client;

import java.util.Arrays;

/**
 * Created by dorgreen on 20/01/2017.
 */
public class ERROR extends Packet {

    protected int error_code;

    public ERROR() {
        super((short) 5);
    }

    public ERROR(int error_code) {
        super((short) 5);
        this.error_code = error_code;
    }

    // TODO: IMPLEMENT
    // If server can receive these, implement. otherwise, return null by default
    @Override
    public Packet getPacket(byte[] bytes, int numOfBytes) {
        if (bytes[numOfBytes - 1] == (byte) 0) {
            packetContent = Arrays.copyOfRange(bytes, OP_CODE_SIZE, numOfBytes); //Maybe numOfBytes-1 ?
            this.error_code = bytesToShort(Arrays.copyOfRange(packetContent,0,2 )); // 2 is the length of the ErrorCode
            return this;
        } else {
            return null;
        }
    }

    // TODO: IMPLEMENT
    // If server can receive these, implement. otherwise, return null by default
    @Override
    public Packet handle(MessagingProtocolImpl protocol) {
        return null;
    }

    @Override
    public byte[] toBytes() {

        byte[] ans = new byte[OP_CODE_SIZE + 2 + ERROR_CODES.get(error_code).getBytes().length + 1];

        insertArrayIntoArray(ans, shortToBytes(OP_CODE), 0);
        insertArrayIntoArray(ans, shortToBytes((short) error_code), 2);
        insertArrayIntoArray(ans, ERROR_CODES.get(error_code).getBytes(), 4);
        ans[ans.length-1] = (byte) 0; // End of packet

        return ans;
    }


    @Override
    public boolean isComplete() {
        return false;
    }
}
