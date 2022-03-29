package de.can.sockenschach.gui.subgui;

import java.awt.*;
import java.util.ArrayList;

public class FullGui extends SubGui{

    public int bgColor;

    public ArrayList<SubGui> subguis;

    public FullGui(){
        super();
        subguis = new ArrayList<>();
    }

    @Override
    public void render(Graphics2D g2){
        updateDimensions();
        fillRect(0,0,screenwidth,screenheight,bgColor, g2);
        super.render(g2);

    }

}
