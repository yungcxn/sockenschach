package de.can.sockenschach.game;

import de.can.sockenschach.Sockenschach;
import de.can.sockenschach.figur.Figur;
import de.can.sockenschach.gui.subgui.MatchEndSubGui;
import de.can.sockenschach.net.packet.MovePacket;
import de.can.sockenschach.net.packet.RochadePacket;
import de.can.sockenschach.player.Player;
import de.can.sockenschach.util.Point;

import java.util.ArrayList;

public class Match {

    public Player whitePlayer;
    public Player blackPlayer;

    public boolean whitesTurn;

    public ArrayList<String> log = new ArrayList<>();

    public Winner winner = Winner.NONE;

    public int isInCheck;


    public Match(){
        this.isInCheck = -1;




    }

    public void flipTurn(){
        this.whitesTurn = !this.whitesTurn;
    }

    private void callMove(boolean iswhite, int figid, Point dest){

        if(Sockenschach.instance != null){
            if(iswhite){
                this.whitePlayer.act(
                        this.whitePlayer.getFigurById(figid), dest);
            }
            else{

                this.blackPlayer.act(
                        this.blackPlayer.getFigurById(figid), dest);
            }
            flipTurn();
        }

    }

    private void callMove_Rochade(boolean iswhite, int kingid, int turmid, Point kingdest, Point turmdest){

        if(Sockenschach.instance != null){
            if(iswhite){
                this.whitePlayer.act_rochade(
                        this.whitePlayer.getFigurById(kingid),
                        this.whitePlayer.getFigurById(turmid),
                        kingdest, turmdest);
            }
            else{

                this.blackPlayer.act_rochade(
                        this.blackPlayer.getFigurById(kingid),
                        this.blackPlayer.getFigurById(turmid),
                        kingdest, turmdest);
            }
            flipTurn();
        }

    }


    private void callMove_BauerChange(boolean iswhite, int figid, Point dest, int changeindex){

        if(Sockenschach.instance != null){
            if(iswhite){
                this.whitePlayer.act_BauerChange(
                        this.whitePlayer.getFigurById(figid), dest, changeindex);
                this.whitePlayer.tryChangingBauer(this.whitePlayer.getFigurById(figid), changeindex);
            }
            else{

                this.blackPlayer.act_BauerChange(
                        this.blackPlayer.getFigurById(figid), dest, changeindex);
                this.blackPlayer.tryChangingBauer(this.blackPlayer.getFigurById(figid), changeindex);
            }
            flipTurn();
        }

    }


    public void killFigure(boolean iswhite, int figid){
        if(Sockenschach.instance != null){
            if(iswhite){
                this.whitePlayer.moveToDeads(figid);
            }
            else{
                this.blackPlayer.moveToDeads(figid);
            }
        }
    }

    public void callMoveSP_Rochade(int kingid, int turmid, Point kingdest, Point turmdest){
        Sockenschach.instance.netHandler.sendFormattedString(RochadePacket.CODE,
                String.valueOf(kingid), String.valueOf(turmid), String.valueOf(kingdest.x),String.valueOf(kingdest.y),
                String.valueOf(turmdest.x),String.valueOf(turmdest.y));
        callMove_Rochade(Sockenschach.instance.thePlayer.iswhite, kingid, turmid, kingdest, turmdest);
    }

    public void callMoveSP_BauerChange(int figid, Point dest, int changeindex){
        Sockenschach.instance.netHandler.sendFormattedString(
                MovePacket.CODE,String.valueOf(figid),String.valueOf(dest.x),String.valueOf(dest.y), String.valueOf(changeindex));
        callMove_BauerChange(Sockenschach.instance.thePlayer.iswhite, figid, dest, changeindex);
    }

    public void callMoveSP(int figid, Point dest){
        Sockenschach.instance.netHandler.sendFormattedString(
                MovePacket.CODE,String.valueOf(figid),String.valueOf(dest.x),String.valueOf(dest.y));
        callMove(Sockenschach.instance.thePlayer.iswhite, figid, dest);
    }

    public void callMoveMP_Rochade(int kingid, int turmid, Point kingdest, Point turmdest){
        callMove_Rochade(Sockenschach.instance.otherPlayer.iswhite, kingid, turmid, kingdest, turmdest);
    }

    public void callMoveMP_BauerChange(int figid, Point dest, int changeindex){
        callMove_BauerChange(Sockenschach.instance.otherPlayer.iswhite, figid, dest, changeindex);
    }

    public void callMoveMP(int figid, Point dest){
        callMove(Sockenschach.instance.otherPlayer.iswhite, figid, dest);
    }

    public Match(Player whiteplayer, Player blackplayer){
        whitePlayer = whiteplayer;
        blackPlayer = blackplayer;
    }

    private void initTeams(){
        whitePlayer.initTeam();
        blackPlayer.initTeam();
    }

    public void startMatch(){
        initTeams();
        Sockenschach.instance.gameBoard.updateBoard();
        whitesTurn = true;

    }


    public void onUpdate(){
        this.isInCheck = isCheck();
        this.winner = getWinner();


        if(this.winner != Winner.NONE && !oncecalledonendmatch){
            onEndMatch();
        }


    }

    private boolean oncecalledonendmatch = false;
    public void onEndMatch(){//TODO animation
        oncecalledonendmatch = true;
        MatchEndSubGui matchEndSubGui = new MatchEndSubGui(this.winner);
        Sockenschach.instance.renderHandler.addToQueue(matchEndSubGui);
    }

    public static enum Winner{
        BLACK,WHITE,NONE
    }

    private Winner getWinner(){ //TODO more rules
        if(this.whitePlayer.team.isEmpty() ||
                (isInCheck == 1 && !whitesTurn) ){
            return Winner.BLACK;
        }
        if(this.blackPlayer.team.isEmpty()||
                (isInCheck == 0 && whitesTurn) ){
            return Winner.WHITE;
        }
        return Winner.NONE;
    }


    //returns -1 if none, 0 for black, 1 for white
    private int isCheck(){
        Point blackKingPos = blackPlayer.getFigurByName("Koenig").pos;
        Point whiteKingPos = whitePlayer.getFigurByName("Koenig").pos;
        for(GameField[] fa: Sockenschach.instance.gameBoard.fields){
            for(GameField f: fa){
                if(f != null){
                    Figur tempf = f.getHolder();
                    if(tempf != null){
                        ArrayList<Point> poslist = tempf.getNextMoves();
                        if(tempf.iswhite){
                            for(Point p: poslist){
                                if(p.isEqualTo(blackKingPos)){
                                    return 0;
                                }
                            }
                        }else{
                            for(Point p: poslist){
                                if(p.isEqualTo(whiteKingPos)){
                                    return 1;
                                }
                            }
                        }
                    }
                }
            }
        }
        return -1;
    }


}
