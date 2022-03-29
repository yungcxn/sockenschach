package de.can.sockenschach.gui.subgui.elements;

import de.can.sockenschach.gui.subgui.key.MouseAndKeyListener;
import de.can.sockenschach.util.Point;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ButtonElement extends SubGuiElement{

    public String text;
    public int fontsize;
    public int fontcolor;
    public int hovercolor;
    public int hoveredfontcolor;

    public boolean hovering;

    private int offset = 2;
    private int shadowcolor = 0xff555555;

    public ButtonElement(String text, int fontsize, int fontcolor, int hoveredfontcolor, int x, int y, int width, int height,
                         int buttoncolor, int hovercolor ){
        super(x,y,width,height,buttoncolor);
        this.text = text;
        this.fontsize = fontsize;
        this.hoveredfontcolor = hoveredfontcolor;
        this.fontcolor = fontcolor;
        this.hovercolor = hovercolor;
        this.hovering = false;

        postInit();


    }

    protected void postInit(){
        this.mklistener = new MouseAndKeyListener(){
            @Override
            public void mouseMoved(MouseEvent e) {

                Point shiftedMouse = this.getShiftedMousePos(e, renderPoint);


                int realx = shiftedMouse.x;
                int realy = shiftedMouse.y;
                if(isBetween(realx, realy, x*scale, y*scale, (x+width)*scale, (y+height)*scale)){
                    hovering = true;
                }else{
                    hovering = false;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e){
                if(hovering){
                    onClick();
                }
            }

        };
    }

    @Override
    public void render(Graphics2D g2){
        super.render(g2);
        Point buttonCenter = new Point(x+ width/2, y+height/2);
        fillRect(x-offset,y+offset,width,height,shadowcolor,g2);
        if(hovering){
            fillRect(x,y,width,height,hovercolor,g2);
            drawText(text,buttonCenter.x - (getTextWidth(text)*fontsize) / 2,  buttonCenter.y - ((int)((getTextHeight(text)*fontsize) / 1.5)),
                    hoveredfontcolor, fontsize ,g2);
        }else{
            fillRect(x,y,width,height,color,g2);
            drawText(text,buttonCenter.x - (getTextWidth(text)*fontsize) / 2,  buttonCenter.y - ((int)((getTextHeight(text)*fontsize) / 1.5)),
                    fontcolor, fontsize,g2);
        }



    }

    public void onClick(){
    }

}
