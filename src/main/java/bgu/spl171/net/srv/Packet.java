package bgu.spl171.net.srv;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created by dorgreen on 13/01/2017.
 */
public abstract class Packet {
    final protected int OP_CODE_SIZE = 2;
    final protected int OP_CODE;

    protected Packet(int opCode) {
        OP_CODE = opCode;
    }

    public int getOP_CODE(){
        return OP_CODE;
    }



    public abstract Packet getPacket(byte[] bytes, int numOfBytes);
    protected final Charset UTF8_CHARSET = Charset.forName("UTF-8");


    String decodeUTF8(byte[] bytes) {
        return new String(bytes, UTF8_CHARSET);
    }


    public abstract class ZeroRecognizedPacket extends Packet {
        String result;

        protected ZeroRecognizedPacket(int opCode) {
            super(opCode);
        }


        public Packet getPacket(byte[] bytes, int numOfBytes) {
            if (bytes[numOfBytes] == (byte) 0) {
                result = decodeUTF8(Arrays.copyOfRange(bytes, OP_CODE_SIZE, numOfBytes - 1));
                return this;
            } else {
                return null;
            }
        }
    }

    public class RRQ extends ZeroRecognizedPacket {
        public RRQ(){
            super(1);
        }

    }

    public class WRQ extends ZeroRecognizedPacket {

        protected WRQ() {
            super(2);
        }
    }

    public class DATA extends Packet {

        protected DATA() {
            super(3);
        }

        @Override
        public Packet getPacket(byte[] bytes, int numOfBytes) {
            return null;
        }

    }

    public class ACK extends Packet {

        protected ACK() {
            super(4);
        }

        @Override
        public Packet getPacket(byte[] bytes, int numOfBytes) {
            return null;
        }

    }

    public class ERROR extends Packet {

        protected ERROR() {
            super(5);
        }

        @Override
        public Packet getPacket(byte[] bytes, int numOfBytes) {
            return null;
        }

    }

    public class DIRQ extends Packet {

        protected DIRQ() {
            super(6);
        }

        @Override
        // No further information is needed
        public Packet getPacket(byte[] bytes, int numOfBytes) {
            return this;
        }

    }

    public class LOGRQ extends ZeroRecognizedPacket {

        protected LOGRQ() {
            super(7);
        }
    }

    public class DELRQ extends ZeroRecognizedPacket {

        protected DELRQ() {
            super(8);
        }
    }

    public class BCAST extends Packet {

        protected BCAST() {
            super(9);
        }

        @Override
        public Packet getPacket(byte[] bytes, int numOfBytes) {

            return null;
        }

    }

    public class DISC extends Packet {

        protected DISC() {
            super(10);
        }

        @Override
        // No further information is needed
        public Packet getPacket(byte[] bytes, int numOfBytes) {
            return this;
        }

    }

}



