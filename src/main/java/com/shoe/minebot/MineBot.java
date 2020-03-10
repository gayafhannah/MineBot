package com.shoe.minebot;

import java.io.*;
import java.util.zip.DataFormatException;

public class MineBot {
    public static void main(String[] args) throws IOException, InterruptedException, DataFormatException {
        Client client = new Client();
        client.Connect("shoeeater.ddns.net","bot1");
        Listener.listen(client);
        System.out.println("Stopping.");
    }
}
