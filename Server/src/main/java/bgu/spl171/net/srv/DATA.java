package bgu.spl171.net.srv;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by dorgreen on 20/01/2017.
 */
public class DATA extends Packet {

    short packetSize;
    short blockNumber;
    byte[] data;

    protected DATA() {
        super((short) 3);
    }

    public DATA(short packetSize, short blockNumber, byte[] data) {
        super((short) 3);
        this.packetSize = packetSize;
        this.blockNumber = blockNumber;
        this.data = Arrays.copyOf(data, packetSize);
    }

    @Override
    public Packet getPacket(byte[] bytes, int numOfBytes) {

        Packet ans = null;

        // Too short to be a data packet, made of Opcode, Packet Size, Block # and at least 1 bytes of data.
        if (numOfBytes < 2 + 2 + 2 + 1) {
            return ans; //
        } else {

            short packetSize = bytesToShort(Arrays.copyOfRange(bytes, OP_CODE_SIZE, OP_CODE_SIZE + 2));

            if (numOfBytes == OP_CODE_SIZE + 2 + 2 + packetSize) { // "data" part of the packet is in the right size

                ans = new bgu.spl171.net.srv.DATA(
                        packetSize,
                        bytesToShort(Arrays.copyOfRange(bytes, OP_CODE_SIZE + 2, OP_CODE_SIZE + 2 + 2)),
                        Arrays.copyOfRange(bytes, OP_CODE_SIZE + 2 + 2, bytes.length)
                );
            }
        }

        return ans;
    }

    @Override
    public Packet handle(MessagingProtocolImpl protocol) {
        Packet ans = null;
        try {
            // If all data received, insertIntoDataArray will take care of everything, e.g save the file, broadcast the new file and reset parameters
            protocol.insertIntoDataArray(data, blockNumber);
            ans = new ACK(blockNumber);

        } catch (ArrayIndexOutOfBoundsException ex) {
            ans = new ERROR(0); // blockNumber is illegal
        } catch (IOException ex) {
            ans = new ERROR(2); // Access violation – File cannot be written, read or deleted.
        }


        return ans;
    }

    @Override
    public byte[] toBytes() {
        /**Packet format is:
         * | 2 bytes|    2 bytes  | 2 bytes | n bytes|
         * | Opcode | Packet Size | Block # | Data   |
         */
        byte[] ans = new byte[OP_CODE_SIZE + 2 + 2 + packetSize];

        // Insert Opcode
        insertArrayIntoArray(ans, shortToBytes(OP_CODE), 0);

        // Insert Packet size
        insertArrayIntoArray(ans, shortToBytes(packetSize), OP_CODE_SIZE);

        // Insert Block #
        insertArrayIntoArray(ans, shortToBytes(blockNumber), OP_CODE_SIZE + 2);

        // Insert Data
        insertArrayIntoArray(ans, data, OP_CODE_SIZE + 2 + 2);

        return ans;
    }

}
