package de.can.sockenschach.net.packet;


import de.can.sockenschach.util.Logger;

public class Packet {

    public static String CODE = "NORMAL";
    public static String SPLITTER = ";";

    /*
    *  0xffff - 0000 0000 0000 0000
    *           ttxx xyyy piii iiii
    *
    * t = types of packets (hs, end, move, ping)    2bits
    * x = xpos of movement (0-7)                    3bits
    * y = ypos of movement (0-7)                    3bits
    * p = playerflag (white=1;black=0)              1bit
    * i = id (restbits)                             7bits
    *
     */

    public String content;
    /*public int typecode;
    public int xcode;
    public int ycode;
    public int playercode;
    public int idcode;*/

    public String getTypeCode(){
        return getSub(0);
    }

    protected String getSub(int i){
        return this.content.split(SPLITTER)[i];
    }


    public Packet(String content){
        this.content = content;
    }


}
