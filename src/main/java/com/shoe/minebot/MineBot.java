package com.shoe.minebot;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.Socket;
import java.util.zip.DataFormatException;

public class MineBot {
    public static void main(String[] args) throws IOException, InterruptedException, DataFormatException {
        //Networking.connect("192.168.1.1");
        //Packets.handshake();
        //Packets.login_start();
        Client client     = new Client();
        //Listener listener = new Listener(client);
        client.Connect("shoeeater.ddns.net","bot1");
        //Actor t = new Actor(client);
        //t.start();
        Listener.listen(client);
        System.out.println("Stopping.");
    }
}
