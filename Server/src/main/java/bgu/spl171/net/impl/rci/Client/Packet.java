package bgu.spl171.net.impl.rci.Client;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dorgreen on 13/01/2017.
 */
public abstract class Packet {
    final protected short OP_CODE_SIZE = 2;
    final protected short OP_CODE;
    final protected Charset UTF8_CHARSET = Charset.forName("UTF-8");
    final protected int ACK_SUCCESSFUL = 0;
    private final int MAX_PACKET_SIZE = 512; // Bytes. as per protocol
    public static Map<Integer, String> ERROR_CODES;

    static {
        ERROR_CODES = new HashMap<>();

        ERROR_CODES.put(0, "Not defined, see error message (if any).");
        ERROR_CODES.put(1, "File not found – RRQ \\ DELRQ of non-existing file");
        ERROR_CODES.put(2, "Access violation – File cannot be written, read or deleted.");
        ERROR_CODES.put(3, "Disk full or allocation exceeded – No room in disk.");
        ERROR_CODES.put(4, "Illegal TFTP operation – Unknown Opcode.");
        ERROR_CODES.put(5, "File already exists – File name exists on WRQ.");
        ERROR_CODES.put(6, "User not logged in – Any opcode received before Login completes.");
        ERROR_CODES.put(7, "User already logged in – Login username already connected.");
    }
    /**
     * This holds all of bytes from the packets that aren't the opcode
     */
    protected byte[] packetContent;


    // TODO: MAYBE THIS SHOULD BE AN ENUM INSTEAD OF INT???
    public Packet(short opCode) {
        OP_CODE = opCode;
    }


    public short getOP_CODE() {
        return OP_CODE;
    }


    /**
     * @return a complete packet if it's complete, otherwise return null
     **/
    public abstract Packet getPacket(byte[] bytes, int numOfBytes);

    public abstract Packet handle(MessagingProtocolImpl protocol);

    public boolean isLoggedIn(MessagingProtocolImpl protocol) {
        return protocol.getUsername() == null;
    }

    /**
     * @return byte[] of this packet. if packet is incomplete return null
     */
    public abstract byte[] toBytes();

    String decodeUTF8(byte[] bytes) {
        return new String(bytes, UTF8_CHARSET);
    }

    /**
     * Supplied by BGU staff
     *
     * @param num
     * @return
     */
    public byte[] shortToBytes(short num) {
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte) ((num >> 8) & 0xFF);
        bytesArr[1] = (byte) (num & 0xFF);
        return bytesArr;
    }

    public void insertArrayIntoArray(byte[] toFill, byte[] toInsert, int insertStartIndex) {
        for (int i = 0; i < toInsert.length && i + insertStartIndex < toFill.length; ++i) {
            toFill[insertStartIndex + i] = toInsert[i];
        }
    }

    /**
     * Supplied by BGU staff
     *
     * @param byteArr
     * @return
     */
    public short bytesToShort(byte[] byteArr) {
        short result = (short) ((byteArr[0] & 0xff) << 8);
        result += (short) (byteArr[1] & 0xff);
        return result;
    }


}



