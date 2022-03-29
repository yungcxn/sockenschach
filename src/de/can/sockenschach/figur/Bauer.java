package de.can.sockenschach.figur;

import de.can.sockenschach.game.GameBoard;
import de.can.sockenschach.game.GameField;
import de.can.sockenschach.Sockenschach;
import de.can.sockenschach.util.MoveMath;
import de.can.sockenschach.util.Point;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class Bauer extends Figur{

    public Bauer(int id, Point pos, boolean iswhite) {
        super(id, "bauer", "Bauer", pos, iswhite);
    }

    public boolean used2step = false;


    @Override
    public ArrayList<Point> getNextMoves() {
        if(Sockenschach.instance == null){
            return null;
        }
        ArrayList<Point> possibles = new ArrayList<>();
        GameBoard gb = Sockenschach.instance.gameBoard;
        Point frontp = MoveMath.getFrontPos(this.pos, this.iswhite);
        Point frontleftp = MoveMath.getFrontLeftPos(this.pos, this.iswhite);
        Point frontrightp = MoveMath.getFrontRightPos(this.pos, this.iswhite);
        Point frontx2p = MoveMath.getFront2Pos(this.pos, this.iswhite);

        GameField front = null;
        GameField frontleft = null;
        GameField frontright = null;
        GameField frontx2 = null;

        if(!frontp.isNeg() ){
            front = gb.getFieldByPos(frontp);
        }
        if(!frontleftp.isNeg() ){
            frontleft = gb.getFieldByPos(frontleftp);
        }
        if(!frontrightp.isNeg() ){
            frontright = gb.getFieldByPos(frontrightp);
        }
        if(!frontx2p.isNeg() ){
            frontx2 = gb.getFieldByPos(frontx2p);
        }


        if(front != null && frontx2 != null){
            if(!this.movedThisMatch && front.getHolder() == null && frontx2.getHolder() == null){
                possibles.add(frontx2p);
            }
        }

        if(front != null){
            if(front.getHolder() == null){
                    possibles.add(frontp);
            }
        }

        if(frontright != null) {
            if (frontright.getHolder() != null) {
                if (frontright.getHolder().iswhite != this.iswhite) {
                    possibles.add(frontrightp);
                }

            }
        }

        if(frontleft != null) {
            if (frontleft.getHolder() != null) {
                if (frontleft.getHolder().iswhite != this.iswhite) {
                    possibles.add(frontleftp);
                }

            }

        }

        //en passant
        Point leftp = MoveMath.getLeftPos(this.pos, this.iswhite);

        if(!leftp.isNeg()){
            if(gb.getFieldByPos(leftp).getHolder() != null){
                if(gb.getFieldByPos(frontleftp).getHolder() == null){
                    Figur f = gb.getFieldByPos(leftp).getHolder();
                    if(f instanceof Bauer){
                        Bauer fb = (Bauer) f;
                        if (fb.iswhite != iswhite && fb.used2step && fb.pos.isEqualTo(leftp)){
                            possibles.add(frontleftp);
                        }
                    }
                }
            }
        }

        Point rightp = MoveMath.getRightPos(this.pos, this.iswhite);

        if(!rightp.isNeg()){
            if(gb.getFieldByPos(rightp).getHolder() != null){
                if(gb.getFieldByPos(frontrightp).getHolder() == null){
                    Figur f = gb.getFieldByPos(rightp).getHolder();
                    if(f instanceof Bauer){
                        Bauer fb = (Bauer) f;
                        if (fb.iswhite != iswhite && fb.used2step && fb.pos.isEqualTo(rightp)){
                            possibles.add(frontrightp);
                        }
                    }
                }
            }
        }

        //strike out doubles;

        Set<Point> listWithoutDuplicates = new LinkedHashSet<Point>(possibles);

        possibles.clear();
        possibles.addAll(listWithoutDuplicates);

        return possibles;
    }

    @Override
    public void move(Point pos){
        if(!movedThisMatch){
            if(iswhite && pos.y == 4){
                used2step = true;
            }if(!iswhite && pos.y == 3){
                used2step = true;
            }
        }else{
            used2step = false;
        }
        super.move(pos);




    }
}
