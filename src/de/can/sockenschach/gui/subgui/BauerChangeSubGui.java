package de.can.sockenschach.gui.subgui;

import de.can.sockenschach.Sockenschach;
import de.can.sockenschach.figur.Bauer;
import de.can.sockenschach.gui.sprite.Sprite;
import de.can.sockenschach.gui.subgui.key.MouseAndKeyListener;
import de.can.sockenschach.util.Point;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class BauerChangeSubGui extends SubGui{

    ArrayList<Sprite> sprites = new ArrayList<Sprite>();

    Point hoveredPixelPos;
    int changeindex = -1;

    boolean iswhite;

    Point from;
    Point to;


    int margin = 5;

    public BauerChangeSubGui(boolean iswhite, Point from, Point to){

        this.iswhite = iswhite;
        this.from = from;
        this.to = to;

        if(iswhite){
            sprites.add(Sockenschach.instance.spriteHandler.getSpriteByName("dame"));
            sprites.add(Sockenschach.instance.spriteHandler.getSpriteByName("springer"));
            sprites.add(Sockenschach.instance.spriteHandler.getSpriteByName("laeufer"));
            sprites.add(Sockenschach.instance.spriteHandler.getSpriteByName("turm"));
        }else{
            sprites.add(Sockenschach.instance.spriteHandler.getSpriteByName("dame").getDarkerVersion(150));
            sprites.add(Sockenschach.instance.spriteHandler.getSpriteByName("springer").getDarkerVersion(150));
            sprites.add(Sockenschach.instance.spriteHandler.getSpriteByName("laeufer").getDarkerVersion(150));
            sprites.add(Sockenschach.instance.spriteHandler.getSpriteByName("turm").getDarkerVersion(150));
        }



        this.mklistener = new MouseAndKeyListener(){

            @Override
            public void mouseMoved(MouseEvent e){
                boolean found = false;
                Point center = new Point(screenwidth/2, screenheight/2);
                int w0 = tilesize * sprites.size() + margin * (1+sprites.size());
                int h0 = tilesize + 2*margin;
                Point start = new Point(center.x-w0/2, center.x-h0/2);

                Point mousePoint = this.getShiftedMousePos(e, renderPoint);

                for(int i = 0; i<sprites.size();i++){
                    int x = start.x+margin + i*(tilesize+margin);
                    int y = start.y+margin;
                    if(isBetween(mousePoint.x,mousePoint.y,
                            x*scale,y*scale, (x+tilesize)*scale, (y+tilesize)*scale)){

                        hoveredPixelPos = new Point(x,y);
                        changeindex = i;
                        found = true;
                    }
                }

                if(!found){
                    hoveredPixelPos = null;
                    changeindex = -1;
                }
            }


            @Override
            public void mouseReleased(MouseEvent e){

                if(hoveredPixelPos != null){
                    changeTo();
                }


            }
        };
    }

    public void changeTo(){
        Sockenschach.instance.currentMatch.callMoveSP_BauerChange(
                Sockenschach.instance.gameBoard.getFieldByPos(from).getHolder().id, to, changeindex);
        Sockenschach.instance.renderHandler.removeFromQueue(this);
    }


    @Override
    public void render(Graphics2D g2){
        super.render(g2);
        int bgcolor = 0xdd000000;
        fillRect(0,0,screenwidth, screenheight, bgcolor, g2);
        drawCenteredShadowedText("summon:", 30, 0xffcccccc, 3, g2);
        if(hoveredPixelPos != null){
            fillRect(hoveredPixelPos.x, hoveredPixelPos.y, tilesize, tilesize, 0xffdaff16, g2);
        }
        drawFigures(g2);

    }

    public void drawFigures(Graphics2D g2){
        Point center = new Point(screenwidth/2, screenheight/2);
        int w0 = tilesize * sprites.size() + margin * (1+sprites.size());
        int h0 = tilesize + 2*margin;
        Point start = new Point(center.x-w0/2, center.x-h0/2);
        for(int i = 0; i<sprites.size();i++){
            Tile t = new Tile(sprites.get(i), start.x+margin + i*(tilesize+margin),
                    start.y+margin);
            drawImage(t,g2);
        }

    }

}
