package com.shoe.minebot.packets;

import com.shoe.minebot.Client;
import com.shoe.minebot.Utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Serverbound {
    public static void handshake(Client client) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Utilities.writeVarInt(0x00,outputStream);           //Packet ID 0x00
        Utilities.writeVarInt(704,outputStream);            //Protocol 702
        Utilities.writeString(client.ipAddr,outputStream);
        //outputStream.writeBytes("192.168.1.1".getBytes("UTF-8"));
        outputStream.write(25565); //Sends port(25565)
        Utilities.writeVarInt(2,outputStream); //Mode to login
        Utilities.writeVarInt(2,outputStream); //Mode to login... again... dont ask why, but it just works and i dont know whats wrong with it but it has to be like this
        //outputStream.write(2);     //Mode to login
        client.SendPacket(outputStream.toByteArray());
        /*Utils.writeVarInt(704);
        Utils.writeVarInt(0x00);
        Utils.writeVarInt(704);
        Networking.writeString("192.168.1.1");
        Networking.writeByte((byte) 0x63);Networking.writeByte((byte) 0xDD);
        Utils.writeVarInt(2);*/
    }
    public static void login_start(Client client) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Utilities.writeVarInt(0x00,outputStream);
        Utilities.writeString(client.username,outputStream);
        //outputStream.writeBytes("testbot1".getBytes("UTF-8"));
        System.out.println(Utilities.bytesToHex(outputStream.toByteArray()));
        client.SendPacket(outputStream.toByteArray());
    }



    public static void teleportConfirm(Client client, int teleportID) throws IOException { //0x00
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Utilities.writeVarInt(0x00,outputStream);
        Utilities.writeVarInt(teleportID,outputStream);
        client.SendPacket(outputStream.toByteArray());
    }
    public static void chatMessage(Client client, String message) throws IOException { //0x03
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Utilities.writeVarInt(0x03,outputStream);
        Utilities.writeString(message,outputStream);
        client.SendPacket(outputStream.toByteArray());
    }
    public static void clientStatus(Client client) throws IOException { //0x04
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Utilities.writeVarInt(0x04,outputStream);
        Utilities.writeVarInt(0,outputStream);
        client.SendPacket(outputStream.toByteArray());
    }
    public static void clientSettings(Client client) throws IOException { //0x05
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Utilities.writeVarInt(0x05,outputStream);
        Utilities.writeString("en_US",outputStream);
        outputStream.write(32);
        Utilities.writeVarInt(0,outputStream);
        outputStream.write(0x00);
        outputStream.write(0xFF);
        Utilities.writeVarInt(0,outputStream);
        client.SendPacket(outputStream.toByteArray());
    }
    public static void keepalive(Client client, byte[] id) throws IOException {  //0x0F
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Utilities.writeVarInt(0x0F,outputStream);
        outputStream.write(id);
        client.SendPacket(outputStream.toByteArray());
    }
    public static void playerPosition(Client client,double X,double Y,double Z,boolean onGround) throws IOException { //0x11
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Utilities.writeVarInt(0x011,outputStream);

    }
}
