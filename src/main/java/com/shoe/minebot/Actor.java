package com.shoe.minebot;

import com.shoe.minebot.packets.Serverbound;

import java.io.IOException;

public class Actor extends Thread{
    private Client client;
    public Actor(Client _client) {
        client=_client;
    }
    public void run() {
        while (true) {
            try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
            System.out.println("Trying");
            if (client.queue.isEmpty()) {continue;}
            Object job = client.queue.remove();
            if (job=="f") {
                for (int i=0;i<20;i++) {
                    try {
                        Thread.sleep(50);
                        Serverbound.playerPosition(client,client.Player_X+1,client.Player_Y,client.Player_Z,true);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
