package de.can.sockenschach.gui.subgui;

import de.can.sockenschach.Sockenschach;
import de.can.sockenschach.gui.render.Font;
import de.can.sockenschach.gui.sprite.Sprite;

import de.can.sockenschach.gui.render.*;
import de.can.sockenschach.gui.subgui.elements.SubGuiElement;
import de.can.sockenschach.gui.subgui.key.MouseAndKeyListener;
import de.can.sockenschach.util.Point;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SubGui {

    public MouseAndKeyListener mklistener;

    protected int screenwidth;
    protected int screenheight;

    private int framewidth;
    private int frameheight;

    protected Point renderPoint;

    protected int scale;

    protected int tilesize;

    public ArrayList<SubGuiElement> buttonList;

    public class Tile{
        public Sprite sprite;
        public int x;    //drawpos
        public int y;   //drawpos
        public int width;
        public int height;

        public Tile(Sprite s, int x, int y){
            this.sprite = s;
            this.x = x;
            this.y = y;
            this.width = s.width;
            this.height = s.height;
        }

    }


    public SubGui(){
        this.buttonList = new ArrayList<>();
        mklistener = new MouseAndKeyListener();
        updateDimensions();

    }

    protected void updateDimensions(){
        this.tilesize = Sockenschach.instance.gameFrame.gamePane.tilesize;
        this.scale = Sockenschach.instance.gameFrame.gamePane.scalemod;
        this.screenwidth = Sockenschach.instance.gameFrame.gamePane.width;
        this.screenheight = Sockenschach.instance.gameFrame.gamePane.height;

        //used when g2 called as offset
        this.frameheight = Sockenschach.instance.gameFrame.gamePane.getHeight();
        this.framewidth = Sockenschach.instance.gameFrame.gamePane.getWidth();

        this.renderPoint = new Point((this.framewidth-(this.screenwidth*scale))/2, (this.frameheight-(scale * this.screenheight))/2);
    }

    public void render(Graphics2D g2) {
        updateDimensions();
        for(SubGuiElement e: buttonList){
            e.render(g2);
        }
        //extending classes are able to render other stuff here...

    }

    protected static BufferedImage scale(BufferedImage src, int scale){
        return _verticalScale(_horizontalScale(src, scale), scale);
    }

    private static BufferedImage _horizontalScale(BufferedImage src, int scale){
        int width = src.getWidth();
        int height = src.getHeight();
        BufferedImage toreturn = new BufferedImage(width*scale, height , BufferedImage.TYPE_INT_ARGB);


        for(int i=0; i<scale; i++){
            for(int y=0;y<height;y++){
                for(int x=0;x<width;x++){
                    int color = src.getRGB(x,y);

                    toreturn.setRGB((x * scale)+i,y,color);
                }
            }

        }





        return toreturn;
    }

    private static BufferedImage _verticalScale(BufferedImage src, int scale){
        int width = src.getWidth();
        int height = src.getHeight();
        BufferedImage toreturn = new BufferedImage(width, height * scale, BufferedImage.TYPE_INT_ARGB);



        for(int i=0; i<scale; i++){
            for(int x=0;x<width;x++){
                for(int y=0;y<height;y++){
                    int color = src.getRGB(x,y);

                    toreturn.setRGB(x,(y * scale) + i ,color);
                }
            }

        }

        return toreturn;
    }


    protected void drawShadowedText(String text, int x, int y, int color, int fontsize, Graphics2D g2){
        drawText(text, x - getTextWidth(" ") * fontsize / 12, y + getTextHeight(" ") * fontsize / 12, 0xff555555, fontsize, g2);
        drawText(text, x, y, color, fontsize, g2);

    }

    protected void drawText(String text, int x, int y, int color, int fontsize, Graphics2D g2){

        int xoffset = 0;
        int yoffset = 0;


        for(int i = 0; i<text.length(); i++){

            if(text.charAt(i) != '\n'){
                Sprite charSprite = Sockenschach.instance.spriteHandler
                        .getSpriteByName(String.valueOf(text.charAt(i))).getChangedColorVersion(0xffffffff, color);


                if(fontsize != 1){
                    charSprite = new Sprite(
                            scale(charSprite.getBufferedImage(),fontsize), charSprite.name);
                }

                Tile charTile = new Tile(charSprite, x+xoffset , y+yoffset);

                drawImage(charTile, g2);

                xoffset += charSprite.width;
            }else{
                yoffset += Sockenschach.instance.spriteHandler.getSpriteByName(" ").height * fontsize;
                xoffset = 0;
            }


        }

    }

    protected void drawCenteredText(String text, int y, int color, int fontsize, Graphics2D g2){
        drawText(text, screenwidth/2 -(fontsize * getTextWidth(text)) / 2, y, color, fontsize, g2);
    }

    protected void drawCenteredShadowedText(String text, int y, int color, int fontsize, Graphics2D g2){
        drawShadowedText(text, screenwidth/2 -(fontsize * getTextWidth(text)) / 2, y, color, fontsize, g2);
    }



    protected void drawTotallyCenteredText(String text, int color, int fontsize, Graphics2D g2){
        drawCenteredText(text, screenheight / 2 - (fontsize * getTextHeight(text)) / 2, color, fontsize, g2);
    }

    protected void drawTotallyCenteredShadowedText(String text, int color, int fontsize, Graphics2D g2){
        drawCenteredShadowedText(text, screenheight / 2 - (fontsize * getTextHeight(text)) / 2, color, fontsize, g2);
    }





    protected int getTextWidth(String text){
        int lastlines = 0;
        int currentlines = 0;
        for(int i = 0;i<text.length(); i++){
            if(text.charAt(i) == '\n'){
                if(lastlines < currentlines){
                    lastlines = currentlines;
                    currentlines = 0;
                }else{
                    currentlines = 0;
                }

            }else{
                currentlines += Sockenschach.instance.spriteHandler.getSpriteByName(String.valueOf(text.charAt(i))).width;
            }

        }
        return currentlines > lastlines ? currentlines : lastlines;
    }

    protected int getTextHeight(String text){
        int height = Sockenschach.instance.font.FONT_HEIGHT;

        int count = height;
        for(int i = 0;i<text.length(); i++){
            if(text.charAt(i) == '\n'){
                count += height;
            }

        }

        return count;
    }

    protected void fillRect(int x, int y, int width, int height, int color, Graphics2D g2){
        g2.setColor(new Color(color, true));
        g2.fillRect(x*scale + renderPoint.x,y*scale + renderPoint.y, width*scale, height*scale);
        g2.setColor(Color.WHITE);

    }


    protected void drawImage(BufferedImage img, int x, int y, Graphics2D g2){

        g2.drawImage(scale(img, scale), x * this.scale + renderPoint.x, y * this.scale + renderPoint.y, img.getWidth() * this.scale,
                img.getHeight() * this.scale, null);
    }

    protected void drawImage(Tile t, Graphics2D g2){
        drawImage(t.sprite.getBufferedImage(), t.x, t.y, g2);
    }
}
