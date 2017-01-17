package bgu.spl171.net.srv;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * Created by dorgreen on 13/01/2017.
 */
public class PacketFactory {
    private static final HashMap<Integer, Class<? extends Packet>> factoryMap =
            new HashMap<Integer, Class<? extends Packet>>() {{
                factoryMap.put(1, Packet.RRQ.class);
                factoryMap.put(2, Packet.WRQ.class);
                factoryMap.put(3, Packet.DATA.class);
                factoryMap.put(4, Packet.ACK.class);
                factoryMap.put(5, Packet.ERROR.class);
                factoryMap.put(6, Packet.DIRQ.class);
                factoryMap.put(7, Packet.LOGRQ.class);
                factoryMap.put(8, Packet.DELRQ.class);
                factoryMap.put(9, Packet.BCAST.class);
                factoryMap.put(10, Packet.DISC.class);
            }};

    public static Packet get(short opcode){
        Packet ans = null;
        try {
            ans = factoryMap.get(new Integer(opcode)).getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return ans;

    }
}
