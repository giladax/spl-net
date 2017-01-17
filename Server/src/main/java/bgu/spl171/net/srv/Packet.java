package bgu.spl171.net.srv;

import bgu.spl171.net.api.bidi.BidiMessagingProtocol;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by dorgreen on 13/01/2017.
 */
public abstract class Packet {
    final protected short OP_CODE_SIZE = 2;
    final protected short OP_CODE;
    final protected Charset UTF8_CHARSET = Charset.forName("UTF-8");
    final protected int ACK_SUCCESSFUL = 0;
    private final int MAX_PACKET_SIZE = 512; // Bytes. as per protocol
    protected static HashMap<Integer, String> ERROR_CODES = null; // Initialized when the first packet is created

    /**
     * This holds all of bytes from the packets that aren't the opcode
     */
    protected byte[] packetContent;


    // TODO: MAYBE THIS SHOULD BE AN ENUM INSTEAD OF INT???
    protected Packet(short opCode) {
        OP_CODE = opCode;

        if (ERROR_CODES == null) {
            ERROR_CODES = new HashMap<Integer, String>() {{
                ERROR_CODES.put(0, "Not defined, see error message (if any).");
                ERROR_CODES.put(1, "File not found – RRQ \\ DELRQ of non-existing file");
                ERROR_CODES.put(2, "Access violation – File cannot be written, read or deleted.");
                ERROR_CODES.put(3, "Disk full or allocation exceeded – No room in disk.");
                ERROR_CODES.put(4, "Illegal TFTP operation – Unknown Opcode.");
                ERROR_CODES.put(5, "File already exists – File name exists on WRQ.");
                ERROR_CODES.put(6, "User not logged in – Any opcode received before Login completes.");
                ERROR_CODES.put(7, "User already logged in – Login username already connected.");
            }};
        }

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


    public abstract class ZeroRecognizedPacket extends Packet {

        protected ZeroRecognizedPacket(short opCode) {
            super(opCode);
        }


        public Packet getPacket(byte[] bytes, int numOfBytes) {
            if (bytes[numOfBytes] == (byte) 0) {
                packetContent = Arrays.copyOfRange(bytes, OP_CODE_SIZE, numOfBytes); //Maybe numOfBytes-1 ?
                return this;
            } else {
                return null;
            }
        }
    }

    public class RRQ extends ZeroRecognizedPacket {
        public RRQ() {
            super((short) 1);
        }

        // TODO: IMPLEMENT
        @Override
        public Packet handle(MessagingProtocolImpl protocol) {
            Packet ans = null;
            protocol.setFileReadPath(new String(packetContent)); // TODO: this already has the '/0' at the end, make sure this isn't an issue

            if(protocol.isFileAvailable(protocol.getFileReadPath())){
                ans = new ACK(ACK_SUCCESSFUL);

                // This handles separating into packets, sending, awaiting ACK and clearing parameters in the end
                try{
                    protocol.sendFile();
                }
                catch (IOException ex){
                    ans = new ERROR(2); // ERROR: "Access violation – File cannot be written, read or deleted." as per 2.2 on document
                }
            }

            else{
                ans = new ERROR(1); // FILE NOT FOUND
            }

            return ans;
        }

        /**
         * @return null since server never sends this
         */
        @Override
        public byte[] toBytes() {
            return null;
        }
    }

    public class WRQ extends ZeroRecognizedPacket {

        protected WRQ() {
            super((short) 2);
        }

        @Override
        public Packet handle(MessagingProtocolImpl protocol) {
            Packet ans = null;
            String fileName = new String(packetContent); // TODO: this already has the '/0' at the end, make sure this isn't an issue

            if(!protocol.isFileAvailable(fileName)){

                protocol.setFileWritePath(fileName);
                if(protocol.createFile()){
                    // After the file was created, everything is being handled by incoming DATA packets.
                    ans = new ACK(ACK_SUCCESSFUL);
                }
                else{
                    ans = new ERROR(2); // "Access violation – File cannot be written, read or deleted"
                }
            }

            else{
                ans = new ERROR(5); // FILE ALREADY EXIST!
            }

            return ans;
        }

        /**
         * @return null since server never sends this
         */
        @Override
        public byte[] toBytes() {
            return null;
        }
    }

    public class DATA extends Packet {

        short packetSize;
        short blockNumber;
        byte[] data;

        protected DATA() {
            super((short) 3);
        }

        public DATA(short packetSize, short blockNumber, byte[] data){
            super((short) 3);
            this.packetSize = packetSize;
            this.blockNumber = blockNumber;
            this.data = Arrays.copyOf(data, packetSize);
        }

        @Override
        public Packet getPacket(byte[] bytes, int numOfBytes) {

            Packet ans = null;

            // Too short to be a data packet, made of Opcode, Packet Size, Block # and at least 1 bytes of data.
            if(numOfBytes < 2+2+2+1){
                return ans; //
            }

            else{

                short packetSize = bytesToShort(Arrays.copyOfRange(bytes,OP_CODE_SIZE,OP_CODE_SIZE+2));

                if(numOfBytes == OP_CODE_SIZE + 2 + 2 + packetSize){ // "data" part of the packet is in the right size

                    ans = new DATA(
                            packetSize,
                            bytesToShort(Arrays.copyOfRange(bytes,OP_CODE_SIZE+2,OP_CODE_SIZE+2+2)),
                            Arrays.copyOfRange(bytes,OP_CODE_SIZE+2+2,bytes.length)
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

            }
            catch (ArrayIndexOutOfBoundsException ex){
                ans = new ERROR(0); // blockNumber is illegal
            }
            catch (IOException ex){
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
            byte[] ans = new byte[OP_CODE_SIZE+2+2+packetSize];

            // Insert Opcode
            insertArrayIntoArray(ans,shortToBytes(OP_CODE),0);

            // Insert Packet size
            insertArrayIntoArray(ans,shortToBytes(packetSize),OP_CODE_SIZE);

            // Insert Block #
            insertArrayIntoArray(ans, shortToBytes(blockNumber),OP_CODE_SIZE+2);

            // Insert Data
            insertArrayIntoArray(ans, data, OP_CODE_SIZE + 2 + 2);

            return ans;
        }

    }

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

            // Send the next DATA packet awaiting confirmation
            if(block_number > 0){
                protocol.sendFile();
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

    public class ERROR extends Packet {

        protected int error_code;

        protected ERROR() {
            super((short) 5);
        }

        protected ERROR(int error_code) {
            super((short) 5);
            this.error_code = error_code;
        }

        // TODO: IMPLEMENT
        // If server can receive these, implement. otherwise, return null by default
        @Override
        public Packet getPacket(byte[] bytes, int numOfBytes) {
            return null;
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
            ans[ans.length] = (byte) 0; // End of packet

            return ans;
        }

    }

    public class DIRQ extends Packet {

        protected DIRQ() {
            super((short) 6);
        }

        @Override
        // No further information is needed
        public Packet getPacket(byte[] bytes, int numOfBytes) {
            return this;
        }

        // TODO: IMPLEMENT
        @Override
        public Packet handle(MessagingProtocolImpl protocol) {
            return null;
        }

        // TODO: IMPLEMENT
        @Override
        public byte[] toBytes() {
            return new byte[0];
        }
    }

    public class LOGRQ extends ZeroRecognizedPacket {

        protected LOGRQ() {
            super((short) 7);
        }

        @Override
        public Packet handle(MessagingProtocolImpl protocol) {

            String loginUsername = decodeUTF8(Arrays.copyOfRange(packetContent, 0, packetContent.length - 1));
            Packet ans = null;

            if (protocol.getUsername() != null) {
                ans = new ERROR(0); //TODO: this instance has a username already! Find if it's ERROR 0 or ERROR 7
            } else if (protocol.isLoggedIn(loginUsername)) {
                ans = new ERROR(7);
            } else {
                protocol.logIn(loginUsername);
                ans = new ACK(ACK_SUCCESSFUL);
            }

            return ans;
        }

        /**
         * @return null since server never sends this
         */
        @Override
        public byte[] toBytes() {
            return null;
        }
    }

    public class DELRQ extends ZeroRecognizedPacket {

        protected DELRQ() {
            super((short) 8);
        }

        // TODO: IMPLEMENT
        @Override
        public Packet handle(MessagingProtocolImpl protocol) {
            return null;
        }

        /**
         * @return null since server never sends this
         */
        @Override
        public byte[] toBytes() {
            return null;
        }
    }

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

    public class DISC extends Packet {

        protected DISC() {
            super((short) 10);
        }

        @Override
        // No further information is needed
        public Packet getPacket(byte[] bytes, int numOfBytes) {
            return this;
        }

        @Override
        public Packet handle(MessagingProtocolImpl protocol) {
            Packet ans = null;

            // Not logged in
            if (protocol.getUsername() == null) {
                ans = new ERROR(0);
            } else {
                try {
                    protocol.removeClient();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ans = new ACK(ACK_SUCCESSFUL);
            }
            return ans;
        }

        /**
         * @return null since server never sends these
         */
        @Override
        public byte[] toBytes() {
            return null;
        }

    }

}



