package de.can.sockenschach.net;

import de.can.sockenschach.Sockenschach;
import de.can.sockenschach.game.Match;
import de.can.sockenschach.net.packet.HandshakePacket;
import de.can.sockenschach.net.packet.MovePacket;
import de.can.sockenschach.net.packet.Packet;
import de.can.sockenschach.net.packet.RochadePacket;
import de.can.sockenschach.player.PlayerMP;
import de.can.sockenschach.player.PlayerSP;
import de.can.sockenschach.util.Logger;

import java.util.Random;

public class NetHandler {

    public String lastSent;
    public UDPEndpoint udp;
    public TCPEndpoint tcp;

    public String nameToSend;
    int randomToSend;

    public String receivedName;
    int receivedRandom;
    public boolean amIWhite;

    public boolean connected;
    public boolean matchInitDone;

    private Thread initThread;

    public NetHandler(String name){
        this.nameToSend = name;
        this.randomToSend = new Random().nextInt(2147483647);
        connected = false;
        matchInitDone = false;
    }

    public void initConnection(){
        this.initThread = new Thread(() -> {
            udp = new UDPEndpoint();
            udp.init();

            while(!udp.beginTCP){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            tcp = new TCPEndpoint(udp.initstate, udp.tcpport, udp.tcpip);
            tcp.init();


            sendHandshake();

            connected = true;
        });

        this.initThread.start();


    }

    //USED WHEN SOCKENSCHACH.ALTCON
    public void initAltConnection(String ip, int port){
        this.initThread = new Thread(() -> {
            if (ip.isEmpty()) {
                tcp = new TCPEndpoint(UDPEndpoint.State.RECEIVE, port, ip);

            }else{
                tcp = new TCPEndpoint(UDPEndpoint.State.SEND, port, ip);
            }
            tcp.init();


            sendHandshake();

            connected = true;
        });

        this.initThread.start();


    }

    private void sendHandshake(){
        sendFormattedString(HandshakePacket.CODE,this.nameToSend,String.valueOf(this.randomToSend));
    }


    public void onUpdate(){
        if(connected){
            updateLastSent();
        }
    }

    private void updateLastSent(){
        boolean isequal = this.lastSent == tcp.lastSent;
        this.lastSent = tcp.lastSent;
        if(!isequal && lastSent != null){
            onNewPacket();
        }
    }

    private void onNewPacket(){

        Packet p = new Packet(this.lastSent);

        if(p.getTypeCode().equalsIgnoreCase(MovePacket.CODE)){
            MovePacket mp = new MovePacket(p.content);
            callSentMove(mp);
        }

        if(p.getTypeCode().equalsIgnoreCase(RochadePacket.CODE)){
            RochadePacket rp = new RochadePacket(p.content);
            callSentMove_Rochade(rp);
        }

        if(p.getTypeCode().equalsIgnoreCase(HandshakePacket.CODE)){
            HandshakePacket hp = new HandshakePacket(p.content);
            receivedName = hp.name;
            receivedRandom = hp.random;

            amIWhite = randomToSend > receivedRandom;

            initMatch();
            matchInitDone = true;

        }


    }

    private void callSentMove(MovePacket p){
        if(Sockenschach.instance.currentMatch != null){
            if(p.changeindex == -1){
                Logger.netLog("calling move on playermp: " + p.figid + " to " + p.destination.toString());
                Sockenschach.instance.currentMatch.callMoveMP(p.figid, p.destination);
            }else{
                Logger.netLog("calling bauerchangemove on playermp: " + p.figid + " to " + p.destination.toString());
                Sockenschach.instance.currentMatch.callMoveMP_BauerChange(p.figid, p.destination, p.changeindex);
            }

        }else{
            Logger.error("received MovePacket without match!");
        }
    }


    private void callSentMove_Rochade(RochadePacket p){
        if(Sockenschach.instance.currentMatch != null){
            Logger.netLog("calling rochade on playermp: " + p.kingid + " to " + p.kingdest.toString() + " and "
            + p.towerid + " to " + p.towerdest.toString());
            Sockenschach.instance.currentMatch.callMoveMP_Rochade(p.kingid, p.towerid, p.kingdest, p.towerdest);

        }else{
            Logger.error("received MovePacket without match!");
        }
    }




    public void sendFormattedString(String... str){
        tcp.sendln(String.join(Packet.SPLITTER, str));
    }

    private void initMatch() {
        Sockenschach.instance.thePlayer = new PlayerSP(nameToSend, amIWhite);
        Sockenschach.instance.otherPlayer = new PlayerMP(receivedName, !amIWhite);


        //TODO: experimental
        if(amIWhite){
            Sockenschach.instance.currentMatch =
                    new Match(Sockenschach.instance.thePlayer, Sockenschach.instance.otherPlayer);
        }else{
            Sockenschach.instance.currentMatch =
                    new Match(Sockenschach.instance.otherPlayer, Sockenschach.instance.thePlayer);
        }

        Sockenschach.instance.currentMatch.startMatch();
    }

}
