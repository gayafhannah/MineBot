package com.shoe.minebot;

import com.shoe.minebot.packets.Clientbound;
import com.shoe.minebot.packets.Serverbound;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;

public class Listener{
    public static void listen(Client client) throws IOException, DataFormatException, InterruptedException {
        Thread.sleep(1000);
        ByteArrayInputStream data;
        int mode=0; //0=handshake 1=play
        int id;     //ID of current packet
        System.out.println("Listener started");
        while (true) {
        //for (int i=0;i<20;i++) {
            data = new ByteArrayInputStream(client.RecievePacket());
            /*if (Utilities.readVarInt(data)==0x03) {
                System.out.println("Compression is enabled with value:");
                System.out.println(Utilities.readVarInt(data));
                System.out.println("Expect errors");
                continue;
            }*/
            id   = Utilities.readVarInt(data);
            //System.out.println(id);
            if (mode==0) {
                switch(id) {
                    case 0x02: //Login_success
                        Clientbound.login_success();
                        mode = 1;
                        Serverbound.chatMessage(client,"Hewwo World, I am your botty boi");
                        //ADD ACTOR START IN HERE
                        break;
                    case 0x03: //Set Compression
                        Clientbound.setCompression(client,data);
                        break;
                    default:
                        System.out.println("something brokeded");
                        System.out.printf("UNKNOWN ID: %x\n",id);
                        break;
                }
            }
            switch(id) {
                case 0x00: //Spawn object
                    break;
                case 0x01: //Spawn EXP Orb
                    break;
                case 0x02: //Login_success or not idk yet
                    //Clientbound.login_success();
                    break;
                case 0x03: //New mob entity is spawned
                    break;
                case 0x05: //Player has come into render distance
                    break;
                case 0x06: //Entity Animation
                    break;
                case 0x0A: //Update Block Entity
                    break;
                case 0x0B: //Block Action
                    break;
                case 0x0C: //Block change
                    break;
                case 0x0E: //Server Difficulty
                    break;
                case 0x0F: //Chat Message
                    Clientbound.chatMessage(client, data);
                    break;
                case 0x10: //Multiple blocks change in 1 chunk
                    break;
                case 0x12: //Declare Commands
                    break;
                case 0x15: //Window Items
                    break;
                case 0x17: //Item in slot is added/removed
                    break;
                case 0x19: //Plugin Message
                    break;
                case 0x1B: //Disconnect
                    Clientbound.disconnectByServer();
                    return;
                case 0x1C: //Entity status
                    break;
                case 0x1d: //Explosion
                    break;
                case 0x1F: //Change Game State
                    break;
                case 0x21: //KeepAlive
                    Clientbound.keepalive(client,data);
                case 0x22: //Chunk Data (Blocks and etc)
                    break;
                case 0x23: //Effect
                    break;
                case 0x24: //Particle
                    break;
                case 0x25: //Update light levels for a given chunk
                    break;
                case 0x26: //Join Game
                    Clientbound.join_game(client);
                    break;
                case 0x29: //Entity Position
                    break;
                case 0x2A: //Entity Position and Rotation
                    break;
                case 0x2B: //Entity Rotation
                    break;
                case 0x32: //Player Abilities
                    break;
                case 0x33: //Combat Event
                    break;
                case 0x34: //Player Info
                    break;
                case 0x36: //Player Position and Direction
                    Clientbound.player_pos_dir(client,data);
                    break;
                case 0x37: //Unlocked Recipies
                    break;
                case 0x38: //Destroy Entities
                    break;
                case 0x39: //Remove Entity Effect
                    break;
                case 0x3B: //Sent when the player respawns or goes ot another dimension
                    break;
                case 0x3C: //Entity Head Yaw/Look
                    break;
                case 0x3E: //World Border
                    break;
                case 0x40: //Active hotbar slot
                    break;
                case 0x41: //Update View Position
                    break;
                case 0x44: //Update entity metadata
                    break;
                case 0x46: //Entity Velocity
                    break;
                case 0x47: //Entity equipment
                    break;
                case 0x48: //Set EXP
                    break;
                case 0x49: //Update Health
                    Clientbound.updateHealth(client,data);
                    break;
                case 0x4B: //Set Passengers
                    break;
                case 0x4E: //Spawn Position (Only used for compases)
                    break;
                case 0x4F: //Time update
                    break;
                case 0x52: //Sound Effect
                    break;
                case 0x56: //When ANY player picks up an item from ground
                    break;
                case 0x57: //Entity Teleport (Entity moved more than 8 blocks)
                    break;
                case 0x58: //Advancements
                    break;
                case 0x59: //Sets attributes on given entity
                    break;
                case 0x5A: //Entity Effect
                    break;
                case 0x5B: //Declare recipies
                    break;
                case 0x5C: //Declare block/item/fluid/entity tags
                    break;
                default:
                    System.out.println("");
                    System.out.println("");
                    System.out.printf("Unknown Packet ID: %x\n",id);
                    //System.out.println(Utilities.bytesToHex(data.readAllBytes()));
                    return;
                    //break;
            }
        }
    }
}
