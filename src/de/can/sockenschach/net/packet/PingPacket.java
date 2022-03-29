package de.can.sockenschach.net.packet;

public class PingPacket extends Packet{

    public static String CODE = "PING";

    public PingPacket(String content) {
        super(content);
    }
}