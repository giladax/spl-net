package bgu.spl171.net.srv;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dorgreen on 13/01/2017.
 */
public class Codes {

    Map<Integer, String> Errorcode;

    public Codes(){
        this.Errorcode = new HashMap<>();
        Errorcode.put(0, "Not defined, see error message (if any).");
        Errorcode.put(1, "File not found – RRQ \\ DELRQ of non-existing file");
        Errorcode.put(2, "Access violation – File cannot be written, read or deleted.");
        Errorcode.put(3, "Disk full or allocation exceeded – No room in disk.");
        Errorcode.put(4, "Illegal TFTP operation – Unknown Opcode.");
        Errorcode.put(5, "File already exists – File name exists on WRQ.");
        Errorcode.put(6, "User not logged in – Any opcode received before Login completes.");
        Errorcode.put(7, "User already logged in – Login username already connected.");
    }

    public enum Opcode {

        RRQ("Read request"),
        WRQ("Write request"),
        DATA("Data"),
        ACK("Acknowledgment"),
        ERROR("Error"),
        DIRQ("Directory listing request"),
        LOGRQ("Login request"),
        DELRQ("Delete request"),
        BCAST("Broadcast file added/deleted"),
        DISC("Disconnect");

        // Saves the long name of every Opcode
        private final String operation;

        Opcode(String operation) {
            this.operation = operation;
        }

        public String getOperation() { return operation; }
    }




}
