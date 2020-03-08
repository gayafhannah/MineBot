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
                Serverbound.chatMessage(client, String.format("Command %s added to queue", command));
                break;
            default: //Unknown command
                Serverbound.chatMessage(client, String.format("Unknown command: %s", cmd[0]));
        }
    }
}
