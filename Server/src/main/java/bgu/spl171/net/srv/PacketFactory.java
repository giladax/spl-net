package bgu.spl171.net.srv;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * Created by dorgreen on 13/01/2017.
 */
public class PacketFactory {
    private static final HashMap<Integer, Class<? extends Packet>> factoryMap =
            new HashMap<Integer, Class<? extends Packet>>() {{
                factoryMap.put(1, RRQ.class);
                factoryMap.put(2, WRQ.class);
                factoryMap.put(3, DATA.class);
                factoryMap.put(4, ACK.class);
                factoryMap.put(5, ERROR.class);
                factoryMap.put(6, DIRQ.class);
                factoryMap.put(7, LOGRQ.class);
                factoryMap.put(8, DELRQ.class);
                factoryMap.put(9, BCAST.class);
                factoryMap.put(10, DISC.class);
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
