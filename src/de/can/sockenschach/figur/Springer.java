package de.can.sockenschach.figur;

import de.can.sockenschach.util.MoveMath;
import de.can.sockenschach.util.Point;

import java.util.ArrayList;
import java.util.Arrays;

public class Springer extends Figur{

    public Springer(int id, Point pos, boolean iswhite) {
        super(id, "springer", "Springer", pos, iswhite);
    }

    @Override
    public ArrayList<Point> getNextMoves() {
        return new ArrayList<Point>(Arrays.asList(MoveMath.getHorseJumps(this.pos, this.iswhite)));
    }
}