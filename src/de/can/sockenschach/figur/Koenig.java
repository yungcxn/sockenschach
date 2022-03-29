package de.can.sockenschach.figur;

import de.can.sockenschach.game.GameBoard;
import de.can.sockenschach.game.GameField;
import de.can.sockenschach.game.Match;
import de.can.sockenschach.Sockenschach;
import de.can.sockenschach.util.MoveMath;
import de.can.sockenschach.util.Point;

import java.util.ArrayList;

public class Koenig extends Figur{

    public Koenig(int id, Point pos, boolean iswhite) {
        super(id, "koenig", "Koenig", pos, iswhite);
    }


    @Override
    public ArrayList<Point> getNextMoves() {
        if(Sockenschach.instance == null){
            return null;
        }
        ArrayList<Point> possibles = new ArrayList<>();
        GameBoard gb = Sockenschach.instance.gameBoard;

        ArrayList<Point> points = new ArrayList<>();


        points.add(MoveMath.getFrontPos(this.pos, this.iswhite));
        points.add(MoveMath.getFrontLeftPos(this.pos, this.iswhite));
        points.add(MoveMath.getFrontRightPos(this.pos, this.iswhite));
        points.add(MoveMath.getLeftPos(this.pos, this.iswhite));
        points.add(MoveMath.getRightPos(this.pos, this.iswhite));
        points.add(MoveMath.getBackPos(this.pos, this.iswhite));
        points.add(MoveMath.getBackLeftPos(this.pos, this.iswhite));
        points.add(MoveMath.getBackRightPos(this.pos, this.iswhite));

        for(Point p : points){
            if(!p.isNeg()){
                GameField f = gb.getFieldByPos(p);
                if(f.getHolder() != null){
                    if(f.getHolder().iswhite != iswhite){
                        possibles.add(p);
                    }
                }else{
                    possibles.add(p);
                }
            }
        }


        return possibles;
    }
}
