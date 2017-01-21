package bgu.spl171.net.impl.rci.Client;

import java.util.Arrays;

/**
 * Created by dorgreen on 20/01/2017.
 */
public class LOGRQ extends ZeroRecognizedPacket {

    public LOGRQ() {
        super((short) 7);
    }

    @Override
    public Packet handle(MessagingProtocolImpl protocol) {

        String loginUsername = decodeUTF8(Arrays.copyOfRange(packetContent, 0, packetContent.length - 1));
        Packet ans = null;

        if (protocol.getUsername() != null || loginUsername.length() == 0) {
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
