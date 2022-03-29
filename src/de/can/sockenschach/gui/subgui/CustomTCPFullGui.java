package de.can.sockenschach.gui.subgui;

import de.can.sockenschach.Sockenschach;
import de.can.sockenschach.gui.subgui.elements.ButtonElement;
import de.can.sockenschach.gui.subgui.elements.InputElement;
import de.can.sockenschach.gui.subgui.elements.SubGuiElement;
import de.can.sockenschach.net.NetHandler;
import de.can.sockenschach.util.Logger;

import java.awt.*;

public class CustomTCPFullGui extends FullGui{
    String nameToSend;
    NetWaitFullGui netWaitFullGui;

    public static boolean validIP (String ip) {
        try {
            if ( ip == null || ip.isEmpty() ) {
                return false;
            }

            String[] parts = ip.split( "\\." );
            if ( parts.length != 4 ) {
                return false;
            }

            for ( String s : parts ) {
                int i = Integer.parseInt( s );
                if ( (i < 0) || (i > 255) ) {
                    return false;
                }
            }
            if ( ip.endsWith(".") ) {
                return false;
            }

            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public CustomTCPFullGui(String nameToSend){
        this.bgColor = 0xffC2C2CC;
        this.nameToSend = nameToSend;

        int buttoncolor = 0xff31c750;
        int hovercolor = 0xff22b740;
        int buttonwidth = 64;
        int buttonheight = 16;

        int inputcolor = 0xff888888;
        int inputhover = 0xffaaaaaa;
        InputElement ipInput = new InputElement(
                2,0xff000000,0xff444444,
                this.screenwidth / 2 - buttonwidth/2, this.screenheight / 2 - buttonheight / 2 -tilesize*3 ,
                buttonwidth, buttonheight, inputcolor, inputhover
        );

        InputElement portInput = new InputElement(
                2,0xff000000,0xff444444,
                this.screenwidth / 2 - buttonwidth/2, this.screenheight / 2 - buttonheight / 2 - tilesize ,
                buttonwidth, buttonheight, inputcolor, inputhover
        );


        this.buttonList.add(portInput);
        this.buttonList.add(ipInput);



        buttonwidth = 32;


        ButtonElement goButton = new ButtonElement(
                "Go",1,0xff000000,0xff444444,
                this.screenwidth / 2 - buttonwidth/2, this.screenheight / 2 - buttonheight / 2 + tilesize,
                buttonwidth, buttonheight, buttoncolor, hovercolor
        ){@Override
          public void onClick(){
            String ip = ipInput.textString;
            String port0 = portInput.textString;

            if(!port0.isEmpty()){
                int port = 0;
                try{
                    port = Integer.parseInt(port0);

                }catch(Exception e){
                    Logger.error("Port & IP input unprocessable.");
                }
                if(!ip.isEmpty()){
                    if(validIP(ip.trim())){
                        Logger.log("got IP-Input...");
                        Sockenschach.instance.netHandler = new NetHandler(nameToSend);
                        Sockenschach.instance.netHandler.initAltConnection(ip.trim(), port);
                        initGameFullGui();
                    }
                }else{
                    try{
                        port = Integer.parseInt(port0);

                    }catch(Exception e){
                        Logger.error("Port & IP input unprocessable.");
                    }
                    Sockenschach.instance.netHandler = new NetHandler(nameToSend);
                    Sockenschach.instance.netHandler.initAltConnection("", port);
                    initGameFullGui();
                }
            }

          }

        };
        this.buttonList.add(goButton);

    }

    @Override
    public void render(Graphics2D g2){
        super.render(g2);
        drawCenteredShadowedText("IP:\nleave blank for server", 26, 0xff222222, 1, g2);
        drawCenteredShadowedText("same Port:", 65, 0xff222222, 1, g2);
    }


    public void initGameFullGui(){
        netWaitFullGui = new NetWaitFullGui(nameToSend);
        Sockenschach.instance.renderHandler.addToQueue(netWaitFullGui);
    }

}
