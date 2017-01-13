package bgu.spl171.net.srv;

import bgu.spl171.net.api.MessageEncoderDecoder;

import java.nio.charset.Charset;

/**
 * Created by dorgreen on 13/01/2017.
 */
public class BidiEncoderDecoder<T> implements MessageEncoderDecoder<T> {

    private final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    private byte[] bytesRead = new byte[1 << 13];
    Packet packet;
    int numOfBytes = 0;

    public BidiEncoderDecoder() {

    }

    @Override
    public T decodeNextByte(byte nextByte) {
        bytesRead[numOfBytes] = nextByte;
        ++numOfBytes;

        if (numOfBytes > 1) {
            if (packet == null) {
                packet = PacketFactory.get(bytesToShort(bytesRead));
            }

            // Get packet return's null if read is not complete
            return packet.getPacket(bytesRead);
        } else {
            return null;
        }


    }

    @Override
    public byte[] encode(T message) {
        return new byte[0];
    }


    /**
     * Supplied by SPL staff
     **/
    public short bytesToShort(byte[] byteArr) {
        short result = (short) ((byteArr[0] & 0xff) << 8);
        result += (short) (byteArr[1] & 0xff);
        return result;
    }


}
