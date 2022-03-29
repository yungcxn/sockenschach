package de.can.sockenschach.gui.subgui.elements;

import de.can.sockenschach.gui.subgui.SubGui;

import java.awt.*;

public class SubGuiElement extends SubGui {

    public int x;
    public int y;

    public int width;
    public int height;
    public int color;


    public SubGuiElement(int x, int y, int width, int height, int color){
        super();

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }


}
