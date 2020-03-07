package com.shoe.minebot;

import com.shoe.minebot.packets.Serverbound;

import java.io.IOException;
import java.util.concurrent.ThreadFactory;

public class Actor extends Thread{
    private Client client;
    public Actor(Client _client) {
        client=_client;
    }
    public void run() {
        for (int i=0; i<3;i++) {
            try {
                Serverbound.chatMessage(client,"Test");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
