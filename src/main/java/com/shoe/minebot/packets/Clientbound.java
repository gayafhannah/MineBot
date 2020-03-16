package com.shoe.minebot.packets;

import com.shoe.minebot.Client;
import com.shoe.minebot.Parser;
import com.shoe.minebot.Utilities;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Clientbound {
    public static void login_success() {    //0x02
        System.out.println("Joined server");
    }
    public static void setCompression(Client client, ByteArrayInputStream data) throws IOException { //0x03
        int compression = Utilities.readVarInt(data);
        System.out.printf("Network Compression Threshold set to: %d\n",compression);
        client.compression = compression;
    }


    public static void chatMessage(Client client, ByteArrayInputStream data) throws IOException{ //0x0F
        int length = Utilities.readVarInt(data);
        byte[] bytes = new byte[length];
        data.read(bytes,0,length);
        String string = new String(bytes,"UTF-8");
        System.out.println(string);
        String type = string.substring(string.indexOf("\"translate\":\"")+13,string.indexOf("\",\"",string.indexOf("\"translate\":\"")+13));
        System.out.println(type);
        String sender;
        String message = "";
        switch(type) {
            case "chat.type.announcement": //Message sent by server using "/say xxx"
                System.out.println("Announcement");
                sender  = string.substring(string.indexOf("{\"text\":\"")+9,string.indexOf("\"},{\"text"));
                message = string.substring(string.lastIndexOf("{\"text\":\"")+9,string.lastIndexOf("\"}]}"));
                break;
            case "chat.type.text": //Message sent by a player
                System.out.println("Text Chat");
                sender  = string.substring(string.indexOf("{\"text\":\"")+28,string.indexOf("\"}",string.indexOf("{\"text\":\""))-1);
                message = string.substring(string.lastIndexOf("\"},\"")+4,string.lastIndexOf("\"]}"));
                System.out.printf("<%s> %s\n",sender,message);
                break;
            default: //Other message types
                System.out.printf("Unknown message type: %s\n",type);
                break;
        }
        if (message.startsWith("&")) {
            Parser.parseCommand(client,message.substring(1));
        }
    }
    public static void disconnectByServer() { //0x1B
        System.out.println("Disconnected By Server");
    }
    public static void keepalive(Client client, ByteArrayInputStream data) throws IOException { //0x21
        byte[] id = new byte[8];
        data.read(id,0,8);
        System.out.printf("Recieved Keepalive ID:%d\n",ByteBuffer.wrap(id).getLong());
        Serverbound.keepalive(client,id);
    }
    public static void join_game(Client client) throws IOException {
        System.out.println("Joined game");  //0x26
        Serverbound.clientSettings(client);
    }
    public static void player_pos_dir(Client client, ByteArrayInputStream data) throws IOException {
        byte[] X = new byte[8];
        byte[] Y = new byte[8];
        byte[] Z = new byte[8];
        byte[] Yaw = new byte[4];
        byte[] Pitch = new byte[4];
        byte[] Flags = new byte[1];
        int TeleportID;
        data.read(X,0,8);
        data.read(Y,0,8);
        data.read(Z,0,8);
        data.read(Yaw,0,4);
        data.read(Pitch,0,4);
        data.read(Flags,0,1);
        client.MoveFailed = true; //SETS MOVEFAILED FLAG TO TRUE
        if (((Flags[0]>>0)&1)==1) {
            client.Player_X+=ByteBuffer.wrap(X).getDouble();
        } else {
            client.Player_X=ByteBuffer.wrap(X).getDouble();
        }
        if (((Flags[0]>>1)&1)==1) {
            client.Player_Y+=ByteBuffer.wrap(Y).getDouble();
        } else {
            client.Player_Y=ByteBuffer.wrap(Y).getDouble();
        }
        if (((Flags[0]>>2)&1)==1) {
            client.Player_Z+=ByteBuffer.wrap(Z).getDouble();
        } else {
            client.Player_Z=ByteBuffer.wrap(Z).getDouble();
        }
        if (((Flags[0]>>3)&1)==1) {
            client.Player_Yaw+=ByteBuffer.wrap(Yaw).getFloat();
        } else {
            client.Player_Yaw=ByteBuffer.wrap(Yaw).getFloat();
        }
        if (((Flags[0]>>4)&1)==1) {
            client.Player_Pitch+=ByteBuffer.wrap(Pitch).getFloat();
        } else {
            client.Player_Pitch=ByteBuffer.wrap(Pitch).getFloat();
        }
        TeleportID = Utilities.readVarInt(data);
        Serverbound.teleportConfirm(client,TeleportID);
        System.out.println("Player Position:");
        System.out.printf("X:%f Y:%f Z:%f Yaw:%f Pitch:%f\n",client.Player_X,client.Player_Y,client.Player_Z,client.Player_Yaw,client.Player_Pitch);
    }
    public static void updateHealth(Client client, ByteArrayInputStream data) throws IOException { //0x49
        byte[] Health = new byte[8];
        data.read(Health,0,8);
        client.Player_Health=ByteBuffer.wrap(Health).getFloat();
        if (client.Player_Health<=0.0) {
            Serverbound.chatMessage(client,"I dieded :(");
            Serverbound.clientStatus(client);
        }
    }
}
