package com.shoe.minebot;

import com.shoe.minebot.packets.Serverbound;

import java.io.IOException;

public class Jobs {
    public static void Move(Client client,String[] cmd) throws IOException, InterruptedException {
        client.MoveFailed=false;
        System.out.println("Started move");
        Serverbound.chatMessage(client, "Started Move");
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
                Serverbound.chatMessage(client, String.format("Invalid direction: %s", cmd[1]));
                return;
        }
        int distance;
        try {
            distance = Integer.parseInt(cmd[2]);
        } catch (NumberFormatException e) {
            Serverbound.chatMessage(client, String.format("Invalid distance: %s", cmd[2]));
            return;
        }
        for (int i=0;i<distance;i++) {
            Thread.sleep(50);
            if (client.MoveFailed) {
                Serverbound.chatMessage(client, "Move Failed");
                return;
            }
            Serverbound.playerPosition(client, client.Player_X + dX, client.Player_Y, client.Player_Z + dZ, true);
        }
    }

    public static void Centre(Client client) throws IOException { //ONLY DOES X AND Z
        client.MoveFailed=false;
        double X,Z;
        X=(double) ((long) client.Player_X)-0.5;
        Z=(double) ((long) client.Player_Z)+0.5;
        Serverbound.playerPosition(client,X,client.Player_Y,Z,true);
        if (client.MoveFailed) {
            Serverbound.chatMessage(client, "Centring Bot on block has failed!");
        } else {
            Serverbound.chatMessage(client, "Centred bot on block.");
        }
    }

    public static void Drop(Client client) throws InterruptedException, IOException {
        client.MoveFailed=false;
        double Y;
        Y=(int) client.Player_Y;
        Serverbound.playerPosition(client,client.Player_X,Y,client.Player_Z,true);
        while (!client.MoveFailed) {
            Thread.sleep(100);
            Y=(int) client.Player_Y-1;
            Serverbound.playerPosition(client,client.Player_X,Y,client.Player_Z,true);
        }
        client.MoveFailed=false;
        while (!client.MoveFailed) {
            Thread.sleep(100);
            Y=client.Player_Y-0.5;
            Serverbound.playerPosition(client,client.Player_X,Y,client.Player_Z,true);
        }
        Serverbound.chatMessage(client, "Dropped down a bit i think! :D");
    }
}
