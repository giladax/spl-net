//
// Created by dorgreen on 1/17/17.
//

#include "BCAST.h"

// @ Receives these packets ==> constructor from char*
string fileName;
bool added;

BCAST::BCAST() : Packet((short)9) {}


/*
 * BCAST PACKET STRUCTURE
 *
 * | 2 bytes| 1 byte        | string   | 1 byte |
 * | Opcode | Deleted/Added | Filename | 0      |
 *
 */
// Assuming incomning doesn't hold the OPCODE
BCAST::BCAST(char* incoming) : Packet((short)9) {


};

/*
 * BCAST PACKET STRUCTURE
 *
 * | 2 bytes| 1 byte        | string   | 1 byte |
 * | Opcode | Deleted/Added | Filename | 0      |
 *
 * When receiving:
 * - print to screen
 */

/*
 * ACK PACKET STRUCTURE
 * |2 bytes| 2 bytes|
 * |Opcode | Block# |
 *
 * When receiving:
 * - print to screen
 * - if user sent DISC, disconnect.
 * - if sending file to server, send next packet
 *
 */

/*
 * DATA PACKET STRUCTURE
 * | 2 bytes| 2 bytes     | 2 bytes | n bytes |
 * | Opcode | Packet Size | Block # | Data    |
 *
 * When receiving:
 * - if last block (Packet Size < MAX_PACKET_SIZE = 512), add it and finish transfer
 * - if last block && was during RRQ, print "RRQ filename complete"
 * - if last block && was during DIRQ, print the data
 * - if not last block, add it to where data is stored
 * - either way, send ACK(Block #)
 */

/*
 * ERROR PACKET STRUCTURE
 * | 2 bytes|   2 bytes | string |  1 byte |
 * | Opcode | ErrorCode | ErrMsg | 0       |
 *
 * When receiving:
 * - print to screen
 *
 */