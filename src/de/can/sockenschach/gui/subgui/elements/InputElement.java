package de.can.sockenschach.gui.subgui.elements;

import de.can.sockenschach.gui.subgui.key.MouseAndKeyListener;
import de.can.sockenschach.util.Logger;
import de.can.sockenschach.util.Point;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class InputElement extends ButtonElement{

    boolean focused;
    public String textString = "";

    public InputElement(int fontsize, int fontcolor, int hoveredfontcolor, int x, int y, int width, int height,
                        int buttoncolor, int hovercolor ){
        super("",fontsize,fontcolor,hoveredfontcolor,x,y,width,height,buttoncolor,hovercolor);
    }

    public String readClipboard(){
        try {
            return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException e) {
            Logger.log("Clipboard not pastable");
        } catch (IOException e) {
            Logger.log("Clipboard not pastable");
        }
        return "";
    }

    @Override
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
                    focused = true;
                    onClick();
                }else{
                    focused = false;
                }
            }

            @Override
            public void keyReleased(KeyEvent k){
                if(focused){

                    if(k.getKeyCode() == k.VK_V && ((k.getModifiers() & KeyEvent.CTRL_MASK) != 0)){
                        textString += readClipboard();
                    }

                    if(k.getKeyChar() == '.'){
                        textString += '.';
                    }

                    if(k.getKeyCode() == KeyEvent.VK_ESCAPE){
                        focused = false;
                    }
                    else if(k.getKeyCode() == KeyEvent.VK_ENTER && textString != ""){
                        onEnter();
                    }
                    else if(k.getKeyCode() == KeyEvent.VK_BACK_SPACE){
                        if(textString.length() >= 1){
                            textString = textString.substring(0, textString.length()-1);
                        }

                    }

                    if(Character.isDigit(k.getKeyChar()) || Character.isLetter(k.getKeyChar())
                    ||k.getKeyChar() == ' ' || k.getKeyChar() == '_'){
                        char typedChar = k.getKeyChar();
                        textString += typedChar;
                    }

                }

            }
        };
    }

    @Override
    public void onClick(){

    }

    public void onEnter(){

    }

    @Override
    public void render(Graphics2D g2){
        super.render(g2);
        int margin = 1;
        int strwidth = textString.length() == 0 ? 0: getTextWidth(textString) * fontsize;
        if(textString.length()>0){
            String todraw = textString;
            boolean found = false;
            int i = 0;
            while(!found){
                if(strwidth > (width - margin * 2)){
                    if(focused){
                        todraw = textString.substring(i) + "_";
                    }else{
                        todraw = textString.substring(i);
                    }

                    strwidth = getTextWidth(todraw) * fontsize;
                    i++;
                }else{
                    found = true;
                }

            }


            if(hovering){
                drawText(todraw, x + margin,
                        y + height / 2 - getTextHeight(textString)*fontsize / 2
                        , hoveredfontcolor, fontsize, g2);
            }else{
                drawText(todraw, x + margin,
                        y + height / 2 - getTextHeight(textString)*fontsize / 2
                        , fontcolor, fontsize, g2);
            }
        }

        if(strwidth == 0){
            if(focused){
                if(hovering){
                    drawText("_", x +margin,
                            y + height / 2 - getTextHeight(textString)*fontsize / 2
                            , hoveredfontcolor, fontsize, g2);
                }else{
                    drawText("_", x +margin,
                            y + height / 2 - getTextHeight(textString)*fontsize / 2
                            , fontcolor, fontsize, g2);
                }
            }

        }





    }

}
