package de.can.sockenschach.figur;

import de.can.sockenschach.util.MoveMath;
import de.can.sockenschach.util.Point;
import de.can.sockenschach.gui.sprite.Sprite;

import java.util.ArrayList;
import java.util.Arrays;

public class Turm extends Figur{

    public Turm(int id, Point pos, boolean iswhite) {
        super(id, "turm", "Turm", pos, iswhite);
    }

    @Override
    public ArrayList<Point> getNextMoves() {
        return new ArrayList<Point>(Arrays.asList(MoveMath.getStraights(this.pos, this.iswhite)));
    }
}
