package bgu.spl171.net.srv;

import bgu.spl171.net.api.MessageEncoderDecoder;

import java.nio.charset.Charset;

import static bgu.spl171.net.srv.PacketFactory.get;

/**
 * Created by dorgreen on 13/01/2017.
 */
public class BidiEncoderDecoder<T> implements MessageEncoderDecoder<T> {


    private byte[] bytesRead = new byte[1 << 13]; // The same size as ByteBuffer so we won't overflow
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
                packet = get(bytesToShort(bytesRead)); // Static reference to PackFactory.get(short opcode)
            }

            // Get packet returns null if read is not complete
            return packet.getPacket(bytesRead, numOfBytes);
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
    public static short bytesToShort(byte[] byteArr) {
        short result = (short) ((byteArr[0] & 0xff) << 8);
        result += (short) (byteArr[1] & 0xff);
        return result;
    }


}
