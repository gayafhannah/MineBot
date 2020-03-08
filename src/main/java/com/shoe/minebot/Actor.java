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
            if (client.queue.isEmpty()) {continue;}
            String[] cmd = (String[])client.queue.remove();
            if (cmd[0].equalsIgnoreCase("move")) {
                System.out.println("Started move");
                try {Serverbound.chatMessage(client, "started move");} catch (IOException e) {e.printStackTrace();}
                int dX,dZ;
                switch (cmd[1].toLowerCase()) {
                    case "north":
                        dX = 0;
                        dZ =-1;
                        break;
                    case "east":
                        dX = 1;
                        dZ = 0;
                        break;
                    case "south":
                        dX = 0;
                        dZ = 1;
                        break;
                    case "west":
                        dX =-1;
                        dZ = 0;
                        break;
                    default:
                        try {Serverbound.chatMessage(client, String.format("Invalid direction: %s", cmd[1]));} catch (IOException e) {e.printStackTrace();}
                        continue;
                }
                int distance;
                try {
                distance = Integer.parseInt(cmd[2]);
                } catch (NumberFormatException e) {
                    try {Serverbound.chatMessage(client, String.format("Invalid distance: %s", cmd[2]));} catch (IOException ee) {ee.printStackTrace();}
                    continue;
                }
                for (int i=0;i<distance;i++) {
                    try {
                        Thread.sleep(50);
                        Serverbound.playerPosition(client,client.Player_X+dX,client.Player_Y,client.Player_Z+dZ,true);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
