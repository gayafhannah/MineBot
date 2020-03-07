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
            default: //Unknown command
                Serverbound.chatMessage(client, String.format("Unknown command: %s", cmd[0]));
        }
    }
}
