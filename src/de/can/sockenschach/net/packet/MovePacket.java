package de.can.sockenschach.net.packet;

import de.can.sockenschach.util.Point;

public class MovePacket extends Packet{

    public static String CODE = "MOVE";

    public int figid;
    public Point destination;
    public int changeindex;

    private int getId(){
        return Integer.parseInt(getSub(1));
    }

    private int getDestX(){
        return Integer.parseInt(getSub(2));
    }

    private int getDestY(){
        return Integer.parseInt(getSub(3));
    }

    private int getBauerChangeIndex(){
        if(content.split(SPLITTER).length > 4){
            return Integer.parseInt(getSub(4));
        }
        return -1;
    }


    public MovePacket(String content) {
        super(content);
        figid = getId();
        destination = new Point(getDestX(), getDestY());
        changeindex = getBauerChangeIndex();
    }
}