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
            try {
            if (client.queue.isEmpty()) {continue;}
            //if (client.queue.isEmpty()) {Jobs.Drop(client);continue;}
            String[] cmd = (String[])client.queue.remove();
            switch (cmd[0].toLowerCase()) {
                case "move":
                    Jobs.Move(client,cmd);
                    break;
                case "centre":
                    Jobs.Centre(client);
                    break;
                case "drop":
                    Jobs.Drop(client);
                    break;
                default:
                    System.out.println("Unknown Queue command: "+cmd.toString());
                    try {Serverbound.chatMessage(client, "Unknown Queue Command: "+cmd.toString());} catch (IOException e) {e.printStackTrace();}
                    break;
            }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }

            /*
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
            } else if (cmd[0].equalsIgnoreCase("centre")) {
                double X,Z;
                X=(double) ((long) client.Player_X)-0.5;
                Z=(double) ((long) client.Player_Z)+0.5;
                try { Serverbound.playerPosition(client,X,client.Player_Y,Z,true); } catch (IOException e) { e.printStackTrace(); }
                try {Serverbound.chatMessage(client, "Centred bot on block.");} catch (IOException ee) {ee.printStackTrace();}
            }*/
        }
    }
}
