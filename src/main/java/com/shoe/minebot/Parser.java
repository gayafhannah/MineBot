package com.shoe.minebot;

import com.shoe.minebot.packets.Serverbound;

import java.io.IOException;

public class Parser {
    public static void parseCommand(Client client, String command) throws IOException {
        String[] cmd = command.split(" ");
        switch (cmd[0].toLowerCase()) {
            case "ping": //ping command
                Serverbound.chatMessage(client,"Pong");
                break;
            case "disconnect": //disconnect bot from server
                Serverbound.chatMessage(client,"Disconnecting from server. bye");
                System.out.println("Disconnected by user");
                throw new RuntimeException("Disconnect by user");
            case "move": //move bot
                if (cmd.length!=3) {
                    Serverbound.chatMessage(client,"Invalid number of arguments, command is: move <direction> <distance>");
                    break;
                }
                client.queue.add(cmd);
                break;
            case "centre": //centre to centre of current block (useful for when bot is between two blocks which would stop it from going through a 1x2)
                client.queue.add(cmd);
                break;
            case "drop": //drop like gravity
                client.queue.add(cmd);
                break;
            case "help": //Help Menu
                Serverbound.chatMessage(client,"Commands:");
                Serverbound.chatMessage(client," - ping");
                Serverbound.chatMessage(client," - disconnect");
                Serverbound.chatMessage(client," - centre");
                Serverbound.chatMessage(client," - drop");
                Serverbound.chatMessage(client," - move <compass dir> <distance>");
                Serverbound.chatMessage(client," - getpos");
                Serverbound.chatMessage(client," - place <X> <Y> <Z>");
                break;
            case "getpos":
                Serverbound.chatMessage(client, String.format("Position: %f %f %f", client.Player_X,client.Player_Y,client.Player_Z));
                break;
            case "place":
                long X,Y,Z;
                if (cmd.length!=4) {
                    Serverbound.chatMessage(client,"Invalid number of arguments, command isL place <X> <Y> <Z>");
                }
                try {
                    X = Integer.parseInt(cmd[1]);
                    Y = Integer.parseInt(cmd[2]);
                    Z = Integer.parseInt(cmd[3]);
                } catch (NumberFormatException e) {
                    Serverbound.chatMessage(client, String.format("Invalid distance: %s", cmd[2]));
                    break;
                }
                Serverbound.blockPlacement(client,X,Y,Z);
                break;
            default: //Unknown command
                Serverbound.chatMessage(client, String.format("Unknown command: %s", cmd[0]));
                return;
        }
    }
}
