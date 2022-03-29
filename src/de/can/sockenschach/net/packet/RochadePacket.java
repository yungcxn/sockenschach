package de.can.sockenschach.net.packet;

import de.can.sockenschach.util.Point;

public class RochadePacket extends Packet{

    public static String CODE = "ROCHADE";

    public int kingid;
    public int towerid;
    public Point kingdest;
    public Point towerdest;

    private int getKingId(){
        return Integer.parseInt(getSub(1));
    }

    private int getTowerId(){
        return Integer.parseInt(getSub(2));
    }

    private Point getKingDest(){
        return new Point(Integer.parseInt(getSub(3)), Integer.parseInt(getSub(4)));
    }

    private Point getTowerDest(){
        return new Point(Integer.parseInt(getSub(5)), Integer.parseInt(getSub(6)));
    }

    public RochadePacket(String content){
        super(content);
        this.kingid = getKingId();
        this.towerid = getTowerId();
        this.kingdest = getKingDest();
        this.towerdest = getTowerDest();
    }



}
