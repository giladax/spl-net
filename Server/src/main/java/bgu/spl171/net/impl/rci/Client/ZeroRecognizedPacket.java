package bgu.spl171.net.impl.rci.Client;

import java.util.Arrays;

/**
 * Created by dorgreen on 21/01/2017.
 */
public abstract class ZeroRecognizedPacket extends Packet {

    public ZeroRecognizedPacket(short opCode) {
        super(opCode);
    }


    public Packet getPacket(byte[] bytes, int numOfBytes) {
        if (bytes[numOfBytes - 1] == (byte) 0) {
            packetContent = Arrays.copyOfRange(bytes, OP_CODE_SIZE, numOfBytes); //Maybe numOfBytes-1 ?
            return this;
        } else {
            return null;
        }
    }
}
