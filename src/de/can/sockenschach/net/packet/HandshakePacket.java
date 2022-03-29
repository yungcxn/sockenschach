package de.can.sockenschach.net.packet;

import de.can.sockenschach.util.Point;

import java.util.Random;

public class HandshakePacket extends Packet{

    public static String CODE = "HAND";

    public String name;
    public int random;

    private String getName(){
        return getSub(1);
    }

    private int getRand(){
        return Integer.parseInt(getSub(2));
    }


    public HandshakePacket(String content) {
        super(content);

        this.name = getName();
        this.random = getRand();
    }

}