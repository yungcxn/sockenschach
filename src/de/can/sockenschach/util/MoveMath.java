package de.can.sockenschach.util;

import de.can.sockenschach.figur.Koenig;
import de.can.sockenschach.figur.Turm;
import de.can.sockenschach.game.GameBoard;
import de.can.sockenschach.Sockenschach;
import de.can.sockenschach.figur.Figur;

import java.util.ArrayList;

//static class for utils to calculate fields of figures
public class MoveMath {

    private static boolean isInBoundaries(Point pos){
        if(pos == null){
            return false;
        }
        if(pos.x > 7 || pos.y > 7 || pos.x < 0 || pos.y < 0){
            return false;
        }
        return true;
    }

    public static Point getFrontPos(Point figurpos, boolean iswhite){
        Point temp = null;

        if(iswhite){
            temp = new Point(figurpos.x, figurpos.y-1);
        }else{
            temp = new Point(figurpos.x, figurpos.y+1);
        }

        if(!isInBoundaries(temp)){
            temp = new Point(-1,-1);
        }

        return temp;

    }

    public static Point getFront2Pos(Point figurpos, boolean iswhite){
        Point temp = null;

        if(iswhite){
            temp = new Point(figurpos.x, figurpos.y-2);
        }else{
            temp = new Point(figurpos.x, figurpos.y+2);
        }

        if(!isInBoundaries(temp)){
            temp = new Point(-1,-1);
        }

        return temp;

    }

    public static Point getBackPos(Point figurpos, boolean iswhite){
        Point temp = null;

        if(iswhite){
            temp = new Point(figurpos.x, figurpos.y+1);
        }else{
            temp = new Point(figurpos.x, figurpos.y-1);
        }

        if(!isInBoundaries(temp)){
            temp = new Point(-1,-1);
        }

        return temp;

    }
    public static Point getFrontRightPos(Point figurpos, boolean iswhite){
        Point temp = null;

        if(iswhite){
            temp = new Point(figurpos.x+1, figurpos.y-1);
        }else{
            temp = new Point(figurpos.x-1, figurpos.y+1);
        }

        if(!isInBoundaries(temp)){
            temp = new Point(-1,-1);
        }

        return temp;

    }
    public static Point getFrontLeftPos(Point figurpos, boolean iswhite){
        Point temp = null;

        if(iswhite){
            temp = new Point(figurpos.x-1, figurpos.y-1);
        }else{
            temp = new Point(figurpos.x+1, figurpos.y+1);
        }

        if(!isInBoundaries(temp)){
            temp = new Point(-1,-1);
        }

        return temp;

    }
    public static Point getBackRightPos(Point figurpos, boolean iswhite){
        Point temp = null;

        if(iswhite){
            temp = new Point(figurpos.x+1, figurpos.y+1);
        }else{
            temp = new Point(figurpos.x-1, figurpos.y-1);
        }

        if(!isInBoundaries(temp)){
            temp = new Point(-1,-1);
        }

        return temp;

    }
    public static Point getBackLeftPos(Point figurpos, boolean iswhite){
        Point temp = null;

        if(iswhite){
            temp = new Point(figurpos.x-1, figurpos.y+1);
        }else{
            temp = new Point(figurpos.x+1, figurpos.y-1);
        }

        if(!isInBoundaries(temp)){
            temp = new Point(-1,-1);
        }

        return temp;

    }

    public static Point getRightPos(Point figurpos, boolean iswhite){
        Point temp = null;

        if(iswhite){
            temp = new Point(figurpos.x+1, figurpos.y);
        }else{
            temp = new Point(figurpos.x-1, figurpos.y);
        }

        if(!isInBoundaries(temp)){
            temp = new Point(-1,-1);
        }

        return temp;

    }
    public static Point getLeftPos(Point figurpos, boolean iswhite){
        Point temp = null;

        if(iswhite){
            temp = new Point(figurpos.x-1, figurpos.y);
        }else{
            temp = new Point(figurpos.x+1, figurpos.y);
        }

        if(!isInBoundaries(temp)){
            temp = new Point(-1,-1);
        }

        return temp;

    }

    public static Point[] getHorseJumps(Point figurpos, boolean iswhite){

        if(Sockenschach.instance == null){
            return null;
        }

        GameBoard board = Sockenschach.instance.gameBoard;

        Point[] arr = {
            new Point(figurpos.x + 1, figurpos.y + 2),
            new Point(figurpos.x - 1 , figurpos.y + 2),
            new Point(figurpos.x + 1, figurpos.y - 2),
            new Point(figurpos.x - 1, figurpos.y - 2),
            new Point(figurpos.x + 2, figurpos.y + 1),
            new Point(figurpos.x - 2, figurpos.y - 1),
            new Point(figurpos.x + 2, figurpos.y - 1),
            new Point(figurpos.x - 2, figurpos.y + 1)
        };

        for(int i = 0; i < arr.length; i++){

            if(isInBoundaries(arr[i])){
                if(board.getFieldByPos(arr[i]).getHolder() != null){
                    if(board.getFieldByPos(arr[i]).getHolder().iswhite == iswhite){
                        arr[i] = new Point(-1, -1);
                    }
                }
            }else{
                arr[i] = new Point(-1, -1);
            }

        }

        return arr;

    }


    public static Point[] getDiagonals(Point figurpos, boolean iswhite){
        ArrayList<Point> possibles = new ArrayList<>();
        if(Sockenschach.instance != null){
            GameBoard board = Sockenschach.instance.gameBoard;
            for(int i = 0; i<4; i++){
                boolean hitEnemy = false;
                Point newPos = figurpos;
                boolean running = true;
                while(running && !hitEnemy){
                    switch(i){
                        case 0: //top right
                            newPos  = getFrontRightPos(newPos, true);
                            break;
                        case 1: //top left
                            newPos  = getFrontLeftPos(newPos, true);
                            break;
                        case 2: //bottom right
                            newPos  = getBackRightPos(newPos, true);
                            break;
                        case 3: //bottom left
                            newPos  = getBackLeftPos(newPos, true);
                            break;
                    }



                    if(!newPos.isNeg()){
                        if(board.getFieldByPos(newPos).getHolder() == null){
                            possibles.add(newPos);

                        }else{
                            if(board.getFieldByPos(newPos).getHolder().iswhite != iswhite){
                                possibles.add(newPos);
                                hitEnemy = true;
                            }else{
                                running = false;
                            }
                        }
                    }else{
                        running = false;
                    }

                }
            }
        }
        return possibles.toArray(new Point[possibles.size()]);
    }
    public static Point[] getStraights(Point figurpos, boolean iswhite){
        ArrayList<Point> possibles = new ArrayList<>();
        if(Sockenschach.instance != null){
            GameBoard board = Sockenschach.instance.gameBoard;
            Point newPos;
            for(int i = 0; i<4; i++){
                boolean hitEnemy = false;
                newPos = figurpos;
                boolean running = true;
                while(running && !hitEnemy){
                    switch(i){
                        case 0: //top
                            newPos  = getFrontPos(newPos, true);
                            break;
                        case 1: //left
                            newPos  = getLeftPos(newPos, true);
                            break;
                        case 2: //right
                            newPos  = getRightPos(newPos, true);
                            break;
                        case 3: //bottom
                            newPos  = getBackPos(newPos, true);
                            break;
                    }


                    if(!newPos.isNeg()){
                        if(board.getFieldByPos(newPos).getHolder() == null){
                            possibles.add(newPos);

                        }else{
                            if(board.getFieldByPos(newPos).getHolder().iswhite != iswhite){
                                possibles.add(newPos);
                                hitEnemy = true;
                            }else{
                                running = false;
                            }
                        }
                    }else{
                        running = false;
                    }





                }
            }
        }
        return possibles.toArray(new Point[possibles.size()]);
    }

    public static ArrayList<Point> getVertFieldsBetween(Point a, Point b){

        ArrayList<Point> possibles = new ArrayList<>();

        if(a.x == b.x && a.y != b.y){
            int higher = a.y > b.y ? a.y : b.y;
            int lower = a.y < b.y ? a.y : b.y;
            for(int i = higher - 1; i > lower; i--){
                possibles.add(new Point(a.x, i));
            }
        }

        return possibles;

    }

    public static ArrayList<Point> getHoriFieldsBetween(Point a, Point b){
        ArrayList<Point> possibles = new ArrayList<>();

        if(a.x != b.x && a.y == b.y){
            int higher = a.x > b.x ? a.x : b.x;
            int lower = a.x < b.x ? a.x : b.x;
            for(int i = higher - 1; i > lower; i--){
                possibles.add(new Point(i, a.y));
            }
        }

        return possibles;

    }


    public static Point[] isLeftRochadePossible(boolean iswhite){
        GameBoard gb = Sockenschach.instance.gameBoard;

        ArrayList<Point> dangerousFields = new ArrayList<>();
        if(iswhite){
            for(Figur f: Sockenschach.instance.currentMatch.blackPlayer.team){
                dangerousFields.addAll(f.getNextMoves());
            }
        }else{
            for(Figur f: Sockenschach.instance.currentMatch.whitePlayer.team){
                dangerousFields.addAll(f.getNextMoves());
            }
        }

        Point kingpos = null;
        Point turmpos = null;
        Point kingrochdest = null;
        Point turmrochdest = null;
        if(iswhite){
            kingpos = new Point(4,7);
            turmpos = new Point(0,7);
            kingrochdest = new Point(kingpos.x-2, kingpos.y);
            turmrochdest = new Point(turmpos.x+3, turmpos.y);

        }else{
            kingpos = new Point(4,0);
            turmpos = new Point(7,0);
            kingrochdest = new Point(kingpos.x+2, kingpos.y);
            turmrochdest = new Point(turmpos.x-2, turmpos.y); //short
        }

        if(gb.getFieldByPos(kingpos).getHolder() != null){
            Koenig king = (Koenig)gb.getFieldByPos(kingpos).getHolder();
            if(gb.getFieldByPos(turmpos).getHolder() != null){
                Turm turm = (Turm)gb.getFieldByPos(turmpos).getHolder();
                if(!king.movedThisMatch){
                    ArrayList<Point> betweenKingRoch = getHoriFieldsBetween(kingpos, kingrochdest);
                    ArrayList<Point> between = new ArrayList(betweenKingRoch);
                    if(iswhite){
                        between.add(new Point(1,7));
                    }
                    boolean isbetween = false;
                    for(Point p : between){
                        if(gb.getFieldByPos(p).getHolder() != null){
                            isbetween = true;
                        }
                    }
                    if(!isbetween){
                        boolean isfree = true;
                        for(Point dp: dangerousFields){

                            for(Point kp: betweenKingRoch){
                                if(kp.isEqualTo(dp)){
                                    isfree = false;
                                }
                            }

                        }
                        if(isfree){
                            return new Point[] {
                                    kingpos, turmpos, kingrochdest, turmrochdest
                            };
                        }
                    }
                }
            }

        }
        return null;

    }




    public static Point[] isRightRochadePossible(boolean iswhite){
        GameBoard gb = Sockenschach.instance.gameBoard;

        ArrayList<Point> dangerousFields = new ArrayList<>();
        if(iswhite){
            for(Figur f: Sockenschach.instance.currentMatch.blackPlayer.team){
                dangerousFields.addAll(f.getNextMoves());
            }
        }else{
            for(Figur f: Sockenschach.instance.currentMatch.whitePlayer.team){
                dangerousFields.addAll(f.getNextMoves());
            }
        }

        Point kingpos = null;
        Point turmpos = null;
        Point kingrochdest = null;
        Point turmrochdest = null;
        if(iswhite){
            kingpos = new Point(4,7);
            turmpos = new Point(7,7);
            kingrochdest = new Point(kingpos.x+2, kingpos.y);
            turmrochdest = new Point(turmpos.x-2, turmpos.y);

        }else{
            kingpos = new Point(4,0);
            turmpos = new Point(0,0);
            kingrochdest = new Point(kingpos.x-2, kingpos.y);
            turmrochdest = new Point(turmpos.x+3, turmpos.y); //short
        }

        if(gb.getFieldByPos(kingpos).getHolder() != null){
            Koenig king = (Koenig)gb.getFieldByPos(kingpos).getHolder();
            if(gb.getFieldByPos(turmpos).getHolder() != null){
                Turm turm = (Turm)gb.getFieldByPos(turmpos).getHolder();
                if(!king.movedThisMatch){
                    ArrayList<Point> betweenKingRoch = getHoriFieldsBetween(kingpos, kingrochdest);
                    ArrayList<Point> between = new ArrayList(betweenKingRoch);
                    if(!iswhite){
                        between.add(new Point(1,0));
                    }
                    boolean isbetween = false;
                    for(Point p : between){
                        if(gb.getFieldByPos(p).getHolder() != null){
                            isbetween = true;
                        }
                    }
                    if(!isbetween){
                        boolean isfree = true;
                        for(Point dp: dangerousFields){

                            for(Point kp: betweenKingRoch){
                                if(kp.isEqualTo(dp)){
                                    isfree = false;
                                }
                            }

                        }
                        if(isfree){
                            return new Point[] {
                                    kingpos, turmpos, kingrochdest, turmrochdest
                            };
                        }
                    }
                }
            }

        }
        return null;

    }



}
