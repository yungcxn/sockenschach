package de.can.sockenschach.figur;
import de.can.sockenschach.util.MoveMath;
import de.can.sockenschach.util.Point;

import java.util.ArrayList;
import java.util.Arrays;

public class Dame extends Figur{

    public Dame(int id, Point pos, boolean iswhite) {
        super(id, "dame", "Dame", pos, iswhite);
    }

    @Override
    public ArrayList<Point> getNextMoves() {

        ArrayList<Point> straight = new ArrayList<Point>(Arrays.asList(MoveMath.getStraights(this.pos, this.iswhite)));
        ArrayList<Point> diagonals = new ArrayList<Point>(Arrays.asList(MoveMath.getDiagonals(this.pos, this.iswhite)));
        for(Point pos: diagonals){
            straight.add(pos);
        }
        return straight;
    }
}
