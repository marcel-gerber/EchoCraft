package de.marcelgerber.echocraft;

import de.marcelgerber.echocraft.netty.Server;

public class Main {

    public static void main(String[] args) {
        try {
            Server.start(25565);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
