package bgu.spl171.net.impl.rci.Client;

import bgu.spl171.net.api.MessageEncoderDecoder;

/**
 * Created by dorgreen on 13/01/2017.
 */
public class BidiEncoderDecoder<T> implements MessageEncoderDecoder<Packet> {


    private byte[] bytesRead = new byte[1 << 13]; // The same size as ByteBuffer so we won't overflow
    private Packet packet;
    private int numOfBytes = 0;

    public BidiEncoderDecoder() {

    }

    @Override
    public Packet decodeNextByte(byte nextByte) {
        bytesRead[numOfBytes] = nextByte;
        ++numOfBytes;
        Packet tmp = null;

        if (numOfBytes > 1) {
            // Initiate packet
            if (packet == null) {
                short opCode = bytesToShort(bytesRead);
                packet = PacketFactory.get(opCode);
            }

            // Get packet will return null if there are more bytes to be read
            tmp = packet.getPacket(bytesRead, numOfBytes);
        }

        // Packet is complete return it and reset the parameters for future usage by the client
        if (tmp != null) {
            tmp = packet;
            reset();

        }

        return tmp;

    }

    @Override
    public byte[] encode(Packet message) {
        return message.toBytes();
    }


    /**
     * Supplied by SPL staff
     **/
    public static short bytesToShort(byte[] byteArr) {
        short result = (short) ((byteArr[0] & 0xff) << 8);
        result += (short) (byteArr[1] & 0xff);
        return result;
    }

    public void reset() {
        packet = null;
        numOfBytes = 0;
        bytesRead = new byte[1 << 13];
    }


}
