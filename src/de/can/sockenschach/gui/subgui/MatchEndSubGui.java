package de.can.sockenschach.gui.subgui;

import de.can.sockenschach.Sockenschach;
import de.can.sockenschach.game.Match;
import de.can.sockenschach.gui.subgui.key.MouseAndKeyListener;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MatchEndSubGui extends SubGui{

    Match.Winner winner;
    int bgColor = 0xaa000000;

    int buttoncolor = 0xff888888;
    int hovercolor = 0xffaaaaaa;
    int textcolor = 0xff222222;

    public MatchEndSubGui(Match.Winner winner){
        this.winner = winner;

        this.mklistener = new MouseAndKeyListener(){
            @Override
            public void keyReleased(KeyEvent e){

                Sockenschach.instance.netHandler.tcp.stop();
                Sockenschach.instance.netHandler = null;

                Sockenschach.instance.renderHandler.addToQueue(
                Sockenschach.instance.renderHandler.homeGui);
            }
        };



    }

    @Override
    public void render(Graphics2D g2){
        super.render(g2);
        fillRect(0,0,screenwidth,screenheight, bgColor,g2);

        String printing = "You lost";

        int color = 0xffff1616;
        if(winner == Match.Winner.WHITE && Sockenschach.instance.thePlayer.iswhite){
            printing = "You won";
            color = 0xff31c750;
        }
        if(winner == Match.Winner.BLACK && !Sockenschach.instance.thePlayer.iswhite){
            printing = "You won";
            color = 0xff31c750;
        }
        drawCenteredShadowedText(printing, screenheight / 2 - 20, color, 3,g2);
        drawCenteredShadowedText("Press Key to leave...", screenheight / 2 + 20 , color, 2,g2);

    }
}
