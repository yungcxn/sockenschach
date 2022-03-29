package de.can.sockenschach.figur;

import de.can.sockenschach.util.MoveMath;
import de.can.sockenschach.util.Point;

import java.util.ArrayList;

import static java.util.Arrays.*;

public class Laeufer extends Figur{

    public Laeufer(int id, Point pos, boolean iswhite) {
        super(id, "laeufer", "Laeufer", pos, iswhite);
    }

    @Override
    public ArrayList<Point> getNextMoves() {
        return new ArrayList<Point>(asList(MoveMath.getDiagonals(this.pos, this.iswhite)));
    }
}