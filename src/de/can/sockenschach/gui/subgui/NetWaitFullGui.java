package de.can.sockenschach.gui.subgui;

import de.can.sockenschach.Sockenschach;
import de.can.sockenschach.net.NetHandler;

import java.awt.*;

public class NetWaitFullGui extends FullGui{

    String nameToSend;
    GameFullGui gameFullGui;

    public NetWaitFullGui(String nameToSend){
        this.bgColor = 0xffC2C2CC;
        this.nameToSend = nameToSend;
        if(!Sockenschach.PROTOCOL_SWITCH){
            Sockenschach.instance.netHandler = new NetHandler(nameToSend);
            Sockenschach.instance.netHandler.initConnection();
        }
    }

    @Override
    public void render(Graphics2D g2){
        super.render(g2);
        String status = "Connecting to\nsomeone as\n" + nameToSend;
        if(Sockenschach.instance.netHandler.connected){

            if(Sockenschach.instance.netHandler.matchInitDone){
                initGameFullGui();

            }else{
                status = "Waiting\nfor Handshake";
            }
        }
        drawTotallyCenteredShadowedText(status, 0xff31c750, 4, g2);

    }

    public void initGameFullGui(){
        gameFullGui = new GameFullGui();
        Sockenschach.instance.renderHandler.addToQueue(gameFullGui);
    }

}
