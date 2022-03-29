package de.can.sockenschach.player;

import de.can.sockenschach.Sockenschach;
import de.can.sockenschach.figur.*;
import de.can.sockenschach.game.GameBoard;
import de.can.sockenschach.game.GameField;
import de.can.sockenschach.util.Logger;
import de.can.sockenschach.util.MoveMath;
import de.can.sockenschach.util.Point;

import java.util.ArrayList;

public class Player {

    public String name;

    public ArrayList<Figur> team;
    public ArrayList<Figur> deadteam;

    public boolean iswhite;


    public Player(String name, boolean iswhite){
        this.name = name;
        this.iswhite = iswhite;

    }

    public void initTeam(){

        int startid = iswhite? 0:50;


        int y0 = iswhite ?  7 : 0;
        int y1 = iswhite ?  6 : 1;

        deadteam = new ArrayList<Figur>();
        team = new ArrayList<Figur>();
        for(int i = 0; i<8; i++){
            team.add(new Bauer(startid++, new Point(i, y1),iswhite));
        }


        team.add(new Turm(startid++, new Point(0, y0),iswhite));
        team.add(new Springer(startid++, new Point(1, y0),iswhite));
        team.add(new Laeufer(startid++, new Point(2, y0),iswhite));
        team.add(new Dame(startid++, new Point(3, y0),iswhite));
        team.add(new Koenig(startid++, new Point(4, y0),iswhite));
        team.add(new Laeufer(startid++, new Point(5, y0),iswhite));
        team.add(new Springer(startid++, new Point(6, y0),iswhite));
        team.add(new Turm(startid++, new Point(7, y0),iswhite));


    }

    public Figur getFigurById(int id){

        for(Figur f: team){

            if(f.id == id){

                return f;
            }
        }
        return null;
    }

    public Figur getFigurByName(String str){
        for(Figur f: team){
            if(f.name.equalsIgnoreCase(str)){
                return f;
            }
        }
        return null;
    }

    public int getTeamIndexByFigur(Figur fig){
        int i = 0;
        for(Figur f: team){
            if(f.id == fig.id){
                return i;
            }
            i++;
        }
        return -1;
    }

    public Figur getFigurByPos(Point pos){
        for(Figur f: team){
            if(f.pos.x == pos.x  && f.pos.y == f.pos.y){
                return f;
            }
        }
        return null;
    }




    public void act(Figur fig, Point dest){
        boolean epdone = false;
        Point eptargetpos = null;
        GameField gf = Sockenschach.instance.gameBoard.getFieldByPos(dest);
        char figchar = fig.name.charAt(0);
        String toAdd = ((Sockenschach.instance.currentMatch.log.size() + 2)/2) + ". " + (fig.iswhite? "W: " : "S: ") + (figchar == 'B'? "" : figchar);
        if(figchar == 'S'){
            GameField x = Sockenschach.instance.gameBoard.getFieldByPos(fig.pos);
            toAdd += x.name.charAt(0);
        }

        if(figchar == 'T'){
            GameField x = Sockenschach.instance.gameBoard.getFieldByPos(fig.pos);
            toAdd += x.name.charAt(1);
        }


        if(gf != null){
            if(gf.getHolder() != null){

                if(figchar == 'B'){
                    GameField x = Sockenschach.instance.gameBoard.getFieldByPos(fig.pos);
                    toAdd += x.name.charAt(0);
                }
                toAdd += "x";




                if(gf.getHolder().iswhite != fig.iswhite){

                    Sockenschach.instance.currentMatch.killFigure(!fig.iswhite, gf.getHolder().id);
                }


            }else{
                if(fig instanceof Bauer){ //not passing specialized ...
                    if(Sockenschach.instance.gameBoard.getFieldByPos(dest).getHolder() == null){
                        eptargetpos = MoveMath.getBackPos(dest, fig.iswhite);
                        if(Sockenschach.instance.gameBoard.getFieldByPos(eptargetpos).getHolder() != null){
                            if(Sockenschach.instance.gameBoard.getFieldByPos(eptargetpos).getHolder() instanceof Bauer){
                                Bauer targetf = (Bauer)Sockenschach.instance.gameBoard.getFieldByPos(eptargetpos).getHolder();
                                if(targetf.used2step && targetf.iswhite != fig.iswhite){
                                    epdone = true;
                                    Logger.gameLog("performing en passant");

                                    Sockenschach.instance.currentMatch.killFigure(targetf.iswhite, targetf.id);
                                }
                            }
                        }

                    }

                }
            }
        }

        toAdd += gf.name.toLowerCase();

        if(epdone){
            toAdd += "x";

            toAdd += Sockenschach.instance.gameBoard.getFieldByPos(eptargetpos).name;

            toAdd += " e. p.";
        }





        int checknum = fig.iswhite ? 1 : 0;
        if(Sockenschach.instance.currentMatch.isInCheck == checknum){
            toAdd += "+";
        }

        Sockenschach.instance.currentMatch.log.add(toAdd);

        fig.move(dest);

        //tryChangingBauer();

    }




    public void act_rochade(Figur king, Figur tower, Point kingdest, Point turmdest){
        String toAdd = ((Sockenschach.instance.currentMatch.log.size() + 2)/2) + ". " + (king.iswhite? "W: " : "S: ");
        if(king.iswhite){
            if(king.pos.x > kingdest.x){
                toAdd += "0-0-0";
            }else{
                toAdd += "0-0";
            }
        }else{
            if(king.pos.x > kingdest.x){
                toAdd += "0-0";
            }else{
                toAdd += "0-0-0";
            }
        }

        Sockenschach.instance.currentMatch.log.add(toAdd);

        king.move(kingdest);
        tower.move(turmdest);

    }




    public void act_BauerChange(Figur fig, Point dest, int changeindex){
        GameField gf = Sockenschach.instance.gameBoard.getFieldByPos(dest);
        char figchar = fig.name.charAt(0);
        String toAdd = ((Sockenschach.instance.currentMatch.log.size() + 2)/2) + ". " + (fig.iswhite? "W: " : "S: ") + figchar;


        if(gf != null){
            if(gf.getHolder() != null){
                GameField x = Sockenschach.instance.gameBoard.getFieldByPos(fig.pos);
                toAdd += x.name.charAt(0);
                toAdd += "x";


                if(gf.getHolder().iswhite != fig.iswhite){

                    Sockenschach.instance.currentMatch.killFigure(!fig.iswhite, gf.getHolder().id);
                }
            }
        }

        toAdd += gf.name.toLowerCase();

        Bauer b = (Bauer) fig;
        if(changeindex == 0){
            toAdd += "D";
        }else if(changeindex == 1){
            toAdd += "S";
        }else if(changeindex == 2){
            toAdd += "L";
        }else if(changeindex == 3){
            toAdd += "T";
        }


        int checknum = fig.iswhite ? 1 : 0;
        if(Sockenschach.instance.currentMatch.isInCheck == checknum){
            toAdd += "+";
        }

        Sockenschach.instance.currentMatch.log.add(toAdd);

        fig.move(dest);

        //tryChangingBauer();

    }

    public void moveToDeads(int id){
        for(int i = 0; i<team.size();i++){
            if(team.get(i).id == id){
                deadteam.add(team.get(i));
                team.remove(i);
            }
        }
    }

    public <T extends Figur> void tryChangingBauer(Figur f, int changeindex){
        moveToDeads(f.id);
        if(changeindex == 0){
            team.add(new Dame(f.id, f.pos,f.iswhite));
        }else if(changeindex == 1){
            team.add(new Springer(f.id, f.pos,f.iswhite));
        }else if(changeindex == 2){
            team.add(new Laeufer(f.id, f.pos,f.iswhite));
        }else if(changeindex == 3){
            team.add(new Turm(f.id, f.pos,f.iswhite));
        }

    }

    public void giveUp(){
        this.team = new ArrayList<>();
    }



}
