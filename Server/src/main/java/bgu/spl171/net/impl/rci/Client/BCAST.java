package bgu.spl171.net.impl.rci.Client;

/**
 * Created by dorgreen on 20/01/2017.
 */
public class BCAST extends Packet {
    int addedOrDeleted;
    String fileName;

    public BCAST(int addedOrDeleted, String fileName) {
        super((short) 9);
        this.addedOrDeleted = addedOrDeleted;
        this.fileName = fileName;
    }


    @Override
    /**
     * Since this packet is never received from client, always return null
     */
    public Packet getPacket(byte[] bytes, int numOfBytes) {
        return null;
    }


    @Override
    /**
     * Since this packet is never received from client, always return null
     */
    public Packet handle(MessagingProtocolImpl protocol) {
        return null;
    }

    @Override
    public byte[] toBytes() {

        byte[] ans = new byte[OP_CODE_SIZE + 1 + fileName.getBytes().length + 1];

        insertArrayIntoArray(ans, shortToBytes(OP_CODE), 0);
        ans[2] = (byte) addedOrDeleted;
        insertArrayIntoArray(ans, fileName.getBytes(), 3);
        ans[ans.length] = (byte) 0;

        return ans;
    }

}
