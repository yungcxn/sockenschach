package de.can.sockenschach.gui.subgui;

import de.can.sockenschach.Sockenschach;
import de.can.sockenschach.gui.subgui.elements.InputElement;

import java.awt.*;

public class HomeFullGui extends FullGui{//starting

    NetWaitFullGui netWaitFullGui;
    CustomTCPFullGui customTCPFullGui;



    int buttoncolor = 0xff888888;
    int hovercolor = 0xffaaaaaa;
    int textcolor = 0xff222222;

    public HomeFullGui(){
        super();
        this.bgColor = 0xffC2C2CC;


        int buttonwidth = 64;
        int buttonheight = 16;
        /*ButtonElement playButton = new ButtonElement(
                "play",1,0xff000000,0xff444444,
                this.screenwidth / 2 - buttonwidth/2, this.screenheight / 2 - buttonheight / 2,
                buttonwidth, buttonheight, buttoncolor, hovercolor
        ){@Override
          public void onClick(){


          }

        };
        this.buttonList.add(playButton);*/


        InputElement inputButton = new InputElement(
            2,0xff000000,0xff444444,
                this.screenwidth / 2 - buttonwidth/2, this.screenheight / 2 - buttonheight / 2 + 16,
                buttonwidth, buttonheight, buttoncolor, hovercolor
        ){
            @Override
            public void onEnter(){
                if(Sockenschach.PROTOCOL_SWITCH){
                    customTCPFullGui = new CustomTCPFullGui(this.textString);
                    Sockenschach.instance.renderHandler.addToQueue(customTCPFullGui);
                }else{
                    netWaitFullGui = new NetWaitFullGui(this.textString); //TODO experimental
                    Sockenschach.instance.renderHandler.addToQueue(netWaitFullGui);
                }

            }
        };

        this.buttonList.add(inputButton);

    }

    @Override
    public void render(Graphics2D g2){
        super.render(g2);
        String name = Sockenschach.instance.name;
        String version = String.valueOf(Sockenschach.instance.version);
        String author = "by " + Sockenschach.instance.author;
        int subtextsize = 1;
        drawCenteredShadowedText(name, screenheight/4, textcolor, 4, g2);
        drawText(version, 0,0,0xffffffff,subtextsize, g2);
        drawText(author, screenwidth - getTextWidth(author) * subtextsize -1,
                screenheight - getTextHeight(author) * subtextsize - 2,0xffffffff, subtextsize, g2);

        drawCenteredText("Name:", screenheight/2 , 0xff222222, subtextsize, g2 );


    }

}
