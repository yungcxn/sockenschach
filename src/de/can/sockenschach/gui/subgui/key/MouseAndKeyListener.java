package de.can.sockenschach.gui.subgui.key;

import de.can.sockenschach.Sockenschach;
import de.can.sockenschach.util.Point;

import java.awt.event.*;

public class MouseAndKeyListener implements KeyListener, MouseListener,MouseMotionListener {


    protected boolean isBetween(int testx, int testy, int x, int y, int x2, int y2){
        return (testx >= x && testx <= x2 && testy >=y && testy<=y2);
    }

    public Point getShiftedMousePos(MouseEvent e, Point shiftpoint){
        return new Point(e.getX()-shiftpoint.x,e.getY()-shiftpoint.y);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
