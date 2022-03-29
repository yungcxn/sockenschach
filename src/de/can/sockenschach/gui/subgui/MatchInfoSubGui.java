package de.can.sockenschach.gui.subgui;

import de.can.sockenschach.Sockenschach;
import de.can.sockenschach.util.Point;

import java.awt.*;
import java.util.ArrayList;

public class MatchInfoSubGui extends SubGui{

    boolean iswhite;
    private String oppname;
    private Point chessStartingPoint;


    public MatchInfoSubGui(boolean iswhite, Point startingPoint){
        this.oppname = Sockenschach.instance.otherPlayer.name;
        this.chessStartingPoint = startingPoint;
        this.iswhite = iswhite;
    }

    @Override
    public void render(Graphics2D g2) {
        super.render(g2);

        int oppnamesize = 1;
        int turn1color = 0xffe92121;
        int turn2color = 0xff57e823;
        int mynamecolor = Sockenschach.instance.currentMatch.whitesTurn
                == Sockenschach.instance.thePlayer.iswhite ? turn2color : turn1color;
        int oppnamecolor = Sockenschach.instance.currentMatch.whitesTurn
                == Sockenschach.instance.otherPlayer.iswhite ? turn2color : turn1color;


        int textx = chessStartingPoint.x - getTextWidth(oppname) * oppnamesize / 2;
        int texty = chessStartingPoint.y - getTextHeight(oppname) * oppnamesize - 10;
        int decorcolor = 0xff111111;
        int decormargin = 3;
        int decorwidth = textx - decormargin;
        int decorheight = getTextHeight(oppname) * oppnamesize;

        int otherstartx = textx + getTextWidth(oppname) * oppnamesize + decormargin;
        int otherwidth = this.screenwidth - otherstartx;

        drawText(oppname, textx, texty, oppnamecolor, oppnamesize, g2 );
        fillRect(0,texty + 1, decorwidth,decorheight,decorcolor,g2 );
        fillRect(otherstartx,texty + 1, otherwidth,decorheight,decorcolor,g2 );

        String myname = Sockenschach.instance.thePlayer.name;
        textx = chessStartingPoint.x - getTextWidth(myname) * oppnamesize / 2;

        texty = screenheight - texty - getTextHeight(myname) * oppnamesize;

        decorwidth = textx - decormargin;
        decorheight = getTextHeight(myname) * oppnamesize;

        otherstartx = textx + getTextWidth(myname) * oppnamesize + decormargin;
        otherwidth = this.screenwidth - otherstartx;

        drawText(myname, textx, texty, mynamecolor, oppnamesize, g2 );
        fillRect(0,texty + 1, decorwidth,decorheight,decorcolor,g2 );
        fillRect(otherstartx,texty + 1, otherwidth,decorheight,decorcolor,g2 );

        int logwidthtiles = 4;
        int logheighttiles = 8;
        int logwidth = logwidthtiles * tilesize;
        int logheight = logheighttiles * tilesize;
        int tilemarginx = 1;
        int marginx = tilemarginx * tilesize;

        int logcolor = 0xffbe813c;

        Point logstart = new Point(screenwidth - marginx - logwidth, screenheight / 2 - logheight / 2);

        fillRect(logstart.x, logstart.y , logwidth,logheight,logcolor,g2 );




        int maxlogamount = 18;

        ArrayList<String> sublog = new ArrayList<>(Sockenschach.instance.currentMatch.log);

        if(Sockenschach.instance.currentMatch.log.size() > maxlogamount){
            sublog =  new ArrayList<String>(Sockenschach.instance.currentMatch.log.subList(
                    Sockenschach.instance.currentMatch.log.size()-maxlogamount-1,
                    Sockenschach.instance.currentMatch.log.size()-1));

        }

        drawText("log",logstart.x, logstart.y + (getTextHeight("log") +2) * (-1), decorcolor,oppnamesize, g2  );

        for(int i = 0; i < sublog.size(); i++){
            drawText(sublog.get(i),logstart.x, logstart.y + (getTextHeight(sublog.get(i)) +2) * i, decorcolor,oppnamesize, g2  );
        }

    }
}
