package de.can.sockenschach.gui;


import de.can.sockenschach.Sockenschach;
import de.can.sockenschach.gui.render.RenderHandler;
import de.can.sockenschach.gui.subgui.key.MouseAndKeyListener;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GamePane extends JPanel {


    /*for gfx*/
    public final int tilesize = 16;
    public int scalemod = 4;
    public final int maxTileAmountX = 16;
    public final int maxTileAmountY = 12;
    public final int width = tilesize * maxTileAmountX;
    public final int height = tilesize * maxTileAmountY;

    Color bgColor = new Color(0xffC2C2CC);

    public GamePane(){
        this.setBackground(bgColor);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.requestFocus();

    }


    @Override
    public Dimension getPreferredSize(){
        return new Dimension(width*scalemod,height*scalemod);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        int framewidth = this.getWidth();
        int frameheight = this.getHeight();
        int xquo = framewidth / width;
        int yquo = frameheight / height;
        int quo = (xquo > yquo) ? yquo : xquo;
        this.scalemod = quo;

        Sockenschach.instance.renderHandler.render((Graphics2D) g);
    }

    public void changeCurrentListener(MouseAndKeyListener kl){
        removeAllListeners();
        addListener(kl);
    }

    public void removeAllListeners(){
        for(KeyListener k: this.getKeyListeners()){
            removeKeyListener(k);
        }
        for(MouseListener m: this.getMouseListeners()){
            removeMouseListener(m);
        }

        for(MouseMotionListener m: this.getMouseMotionListeners()){
            removeMouseMotionListener(m);
        }
    }

    public void addListener(MouseAndKeyListener kl){
        addKeyListener(kl);
        addMouseListener(kl);
        addMouseMotionListener(kl);
    }

    public void removeListener(MouseAndKeyListener kl){
        removeKeyListener(kl);
        removeMouseListener(kl);
        removeMouseMotionListener(kl);
    }

}