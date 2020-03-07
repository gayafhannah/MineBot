package com.shoe.minebot;

import com.shoe.minebot.packets.Serverbound;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Client {
    public String username="testboi";
    public String ipAddr;
    public int compression=-1;
    public DataOutputStream dOut;
    public DataInputStream dIn;
    public double Player_X;
    public double Player_Y;
    public double Player_Z;
    public float Player_Yaw;
    public float Player_Pitch;
    public float Player_Health=-1;
    //public static int Player_Hunger; unused sofar
    public Queue queue = new LinkedList();
    public Actor actor = new Actor(this);

    public void Connect(String _ipAddr) throws IOException {
        Connect(_ipAddr, username);
    }
    public void Connect(String _ipAddr, String _username) throws IOException {
        ipAddr=_ipAddr;
        username=_username;
        System.out.println("Connecting to server");
        Socket sock = new Socket(ipAddr, 25565);
        dOut = new DataOutputStream(sock.getOutputStream());
        dIn = new DataInputStream(sock.getInputStream());
        System.out.println("Starting Handshake");
        Serverbound.handshake(this);
        Serverbound.login_start(this);
        actor.start();
    }
    public void SendPacket(byte[] data) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int length=data.length;
        if ((compression!=-1)&&(length>=compression)) {  /// If there is ANY compression
            System.out.printf("PACKET LENGTH %d\n",length);
            ByteArrayOutputStream tempOutputStream = new ByteArrayOutputStream();
            byte[] compressed = new byte[0];
            Deflater compresser = new Deflater();
            compresser.setInput(data);
            compresser.finish();
            int compressed_length = compresser.deflate(compressed);
            Utilities.writeVarInt(compressed_length,tempOutputStream);
            tempOutputStream.write(compressed);
            length=tempOutputStream.size();
            data=tempOutputStream.toByteArray();
        } else {   /// If there is NO compression
            if (compression!=-1){length+=1;}
            Utilities.writeVarInt(length,outputStream);
            if (compression!=-1){Utilities.writeVarInt(0,outputStream);}
            outputStream.write(data);
        }
        dOut.write(outputStream.toByteArray());

    }
    public byte[] RecievePacket() throws IOException, DataFormatException {
        int length=getPacketLength();

        byte[] data = dIn.readNBytes(length);

        if (compression != -1){ ///If compression is ENABLED
            ByteArrayInputStream tempData = new ByteArrayInputStream(data.clone());
            int data_length = Utilities.readVarInt(tempData);
            if (data_length != 0) {   //If packet is actually compressed
                int compressed_length=tempData.available();
                data = new byte[data_length];
                Inflater decompresser = new Inflater();
                decompresser.setInput(tempData.readAllBytes(),0,compressed_length);
                decompresser.inflate(data);
                decompresser.end();
            } else {
                data = tempData.readAllBytes();
            }
        }
        return data;
    }
    private int getPacketLength() throws IOException {
        int numRead = 0;
        int result = 0;
        byte read;
        do {
            read = dIn.readByte();
            int value = (read & 0b01111111);
            result |= (value << (7 * numRead));
            numRead++;
            if (numRead > 5) {
                throw new RuntimeException("VarInt is too big");
            }
        } while ((read & 0b10000000) != 0);
        return result;
    }
}
