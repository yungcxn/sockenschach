package de.can.sockenschach.game;

import de.can.sockenschach.figur.Figur;
import de.can.sockenschach.util.Point;

public class GameField {

    public String name;
    private Figur holder;
    public Point pos;

    public GameField(String name, Point pos){
        this.name = name;
        this.pos = pos;
    }

    public void setHolder(Figur fig){
        this.holder = fig;
    }

    public Figur getHolder(){
        return this.holder;
    }



}
