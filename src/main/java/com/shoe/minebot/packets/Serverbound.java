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
        client.SendPacket(outputStream.toByteArray());
    }
    public static void login_start(Client client) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Utilities.writeVarInt(0x00,outputStream);
        Utilities.writeString(client.username,outputStream);
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
    public static void windowConfirmation(Client client, byte[] data) throws IOException {  //0x07
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Utilities.writeVarInt(0x07,outputStream);
        outputStream.writeBytes(data);
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
        Utilities.writeVarInt(0x11,outputStream);
        outputStream.writeBytes(Utilities.doubleToByteArray(X));
        outputStream.writeBytes(Utilities.doubleToByteArray(Y));
        outputStream.writeBytes(Utilities.doubleToByteArray(Z));
        if (onGround==true) { outputStream.write(0x01); } else { outputStream.write(0x00); } //onGround Byte
        client.SendPacket(outputStream.toByteArray());
        System.out.println("Sent position packet");
        client.Player_X=X;
        client.Player_Y=Y;
        client.Player_Z=Z;
    }
    public static void blockPlacement(Client client, long X, long Y, long Z) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int hand=0;
        int face=1;
        float Xx = (float) 0.5,Yy = (float) 0.05,Zz = (float) 0.5;
        int insideBlock = 0x00;
        byte[] location = ByteBuffer.allocate(8).putLong( ((X & 0x3FFFFFF) << 38) | ((Z & 0x3FFFFFF) << 12) | (Y & 0xFFF) ).array();
        Utilities.writeVarInt(0x2c,outputStream);
        Utilities.writeVarInt(0,outputStream);
        outputStream.writeBytes(location);
        Utilities.writeVarInt(face,outputStream);
        outputStream.writeBytes(Utilities.floatToByteArray(Xx));
        outputStream.writeBytes(Utilities.floatToByteArray(Yy));
        outputStream.writeBytes(Utilities.floatToByteArray(Zz));
        outputStream.write(insideBlock);
        System.out.println("About to send open packet");
        client.SendPacket(outputStream.toByteArray());
        System.out.println(String.format("Sent %d bytes, location length is %d bytes", outputStream.toByteArray().length,location.length));
    }
}
