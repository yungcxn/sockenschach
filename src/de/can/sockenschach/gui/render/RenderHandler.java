package de.can.sockenschach.gui.render;

import de.can.sockenschach.Sockenschach;
import de.can.sockenschach.gui.subgui.*;
import de.can.sockenschach.gui.subgui.elements.SubGuiElement;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class RenderHandler {

    private ArrayList<SubGui> guiQueue;

    public HomeFullGui homeGui;

    public RenderHandler(){
        guiQueue = new ArrayList<>();

        homeGui = new HomeFullGui();
        addToQueue(homeGui);

    }

    public void render(Graphics2D g2){
        for(SubGui subgui: new ArrayList<>(guiQueue)){
            subgui.render(g2);

        }
    }

    public void addToQueue(SubGui s){
        if(Sockenschach.instance.gameFrame.gamePane != null){
            if(s instanceof FullGui){

                clearQueue();
            }

            guiQueue.add(s);
            Sockenschach.instance.gameFrame.gamePane.changeCurrentListener(s.mklistener);
            for(SubGuiElement e: s.buttonList){
                Sockenschach.instance.gameFrame.gamePane.addListener(e.mklistener);
            }


            if(s instanceof FullGui){
                FullGui fg = (FullGui)s;
                if(fg.subguis.size() > 0){
                    for(SubGui sg: fg.subguis){
                        addToQueue(sg);
                    }
                }
            }

        }
    }

    public void removeFromQueue(SubGui s){
        if(Sockenschach.instance.gameFrame.gamePane != null){
            guiQueue.remove(s);
            Sockenschach.instance.gameFrame.gamePane.changeCurrentListener(getCurrentSubGui().mklistener);
            for(SubGuiElement e: getCurrentSubGui().buttonList){
                Sockenschach.instance.gameFrame.gamePane.addListener(e.mklistener);
            }

        }
    }

    public void clearQueue(){
        this.guiQueue = new ArrayList<>();
        Sockenschach.instance.gameFrame.gamePane.removeAllListeners();
    }

    public SubGui getCurrentSubGui(){
        return(guiQueue.get(guiQueue.size()-1));
    }

}
