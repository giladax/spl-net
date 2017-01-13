package bgu.spl171.net.srv;

/**
 * Created by dorgreen on 13/01/2017.
 */
public abstract class Packet {
    public abstract Packet getPacket();


    public class RRQ extends Packet{

        @Override
        public Packet getPacket() {
            return null;
        }
    }

    public class WRQ extends Packet{

        @Override
        public Packet getPacket() {
            return null;
        }

    }

    public class DATA extends Packet{

        @Override
        public Packet getPacket() {
            return null;
        }

    }

    public class ACK extends Packet{

        @Override
        public Packet getPacket() {
            return null;
        }

    }

    public class ERROR extends Packet{

        @Override
        public Packet getPacket() {
            return null;
        }

    }

    public class DIRQ extends Packet{

        @Override
        public Packet getPacket() {
            return null;
        }

    }

    public class LOGRQ extends Packet{

        @Override
        public Packet getPacket() {
            return null;
        }

    }

    public class DELRQ extends Packet{

        @Override
        public Packet getPacket() {
            return null;
        }

    }

    public class BCAST extends Packet{

        @Override
        public Packet getPacket() {
            return null;
        }

    }


    public class DISC extends Packet{

        @Override
        public Packet getPacket() {
            return null;
        }

    }

}



