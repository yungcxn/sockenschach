package de.can.sockenschach.figur;

import de.can.sockenschach.Sockenschach;
import de.can.sockenschach.gui.sprite.Sprite;
import de.can.sockenschach.util.Point;

import java.util.ArrayList;

public abstract class Figur {

    public int id;
    private String spriteName;
    public String name;
    public Point pos;
    public boolean movedThisMatch;
    public boolean iswhite;

    public Figur(int id, String spriteName, String name, Point pos, boolean iswhite){
        this.id = id;
        this.spriteName = spriteName;
        this.name = name;
        this.pos = pos;
        this.movedThisMatch = false;
        this.iswhite = iswhite;

    }

    public Sprite getSprite(){

        if(!iswhite){
            return Sockenschach.instance.spriteHandler.getSpriteByName(this.spriteName).getDarkerVersion(150);
        }
        return Sockenschach.instance.spriteHandler.getSpriteByName(this.spriteName);
    }

    public void move(Point pos){
        if(!this.movedThisMatch) {this.movedThisMatch = true;}
        this.pos = pos;
    }

    abstract public ArrayList<Point> getNextMoves();




}
