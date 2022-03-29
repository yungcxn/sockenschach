package de.can.sockenschach.gui.subgui;

import de.can.sockenschach.Sockenschach;
import de.can.sockenschach.figur.Bauer;
import de.can.sockenschach.figur.Figur;
import de.can.sockenschach.figur.Koenig;
import de.can.sockenschach.game.GameBoard;
import de.can.sockenschach.game.GameField;
import de.can.sockenschach.gui.sprite.Sprite;
import de.can.sockenschach.gui.subgui.key.MouseAndKeyListener;
import de.can.sockenschach.util.MoveMath;
import de.can.sockenschach.util.Point;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class FigureSubGui extends SubGui{

    Point startingTilePos;
    boolean iswhite;

    private Point selectedFigureTilePos;
    private Point hoveredFigureTilePos;
    private boolean selectedMode;

    Point[] leftRochadeDest;
    Point[] rightRochadeDest;

    private ArrayList<Point> selectedTileFigureNextMoves = new ArrayList<>();

    private int hoverColor = 0xaae5dd4c;
    private int renderShadowColor = 0xaa000000;
    private int nextMoveTileColor = 0xff4ce56d;


    public FigureSubGui(boolean iswhite, Point startingTilePos){
        super();
        this.iswhite = iswhite;
        this.startingTilePos = startingTilePos;
        this.selectedMode = false;
        postInit();


    }

    private void postInit(){ //TODO!!!!!
        this.mklistener = new MouseAndKeyListener(){

            private Point getHoveredTilePos(int screenmousex, int screenmousey){
                Point toreturn = null;
                int boardWidth = Sockenschach.instance.gameBoard.fields.length * tilesize;
                int boardWidthInTiles = boardWidth / tilesize;
                Point p = new Point(startingTilePos.x * tilesize, startingTilePos.y * tilesize);
                if(isBetween(screenmousex, screenmousey,p.x*scale, p.y*scale,
                        (p.x+boardWidth)*scale, (p.y+boardWidth)*scale)){

                    for(int i = 0; i < boardWidthInTiles; i++){
                        for(int j = 0; j < boardWidthInTiles; j++){
                            if(isBetween(screenmousex, screenmousey,
                                    p.x*scale + j*tilesize*scale, p.y*scale + i*tilesize*scale,
                                    p.x*scale + j*tilesize*scale + tilesize*scale,
                                    p.y*scale + i*tilesize*scale + tilesize*scale)){
                                toreturn = new Point(j,i);
                            }

                        }
                    }

                }
                return toreturn;
            }


            @Override
            public void mouseMoved(MouseEvent e) {
                Point shiftedMouse = this.getShiftedMousePos(e, renderPoint);

                hoveredFigureTilePos = getHoveredTilePos(shiftedMouse.x, shiftedMouse.y);
            }

            @Override
            public void mouseReleased(MouseEvent e){

                if(hoveredFigureTilePos == null){//outside of board
                    selectedMode = false;
                    selectedFigureTilePos = null;
                    selectedTileFigureNextMoves = new ArrayList<>();
                }

                if(hoveredFigureTilePos != null && !selectedMode){

                    Point translatedHoveredPos = hoveredFigureTilePos;

                    if(!iswhite){
                        translatedHoveredPos = new Point(7-hoveredFigureTilePos.x, 7- hoveredFigureTilePos.y);
                    }


                    GameField gf = Sockenschach.instance.gameBoard.getFieldByPos(translatedHoveredPos);
                    if(gf.getHolder() != null){
                        if(gf.getHolder().iswhite == Sockenschach.instance.thePlayer.iswhite){
                            if(Sockenschach.instance.thePlayer.iswhite == Sockenschach.instance.currentMatch.whitesTurn){
                                selectedMode = true;
                                selectedFigureTilePos = hoveredFigureTilePos;
                            }

                        }
                    }

                }

                if(selectedMode && hoveredFigureTilePos != null){
                    boolean clickedInPossibleArea = false;
                    if(!selectedTileFigureNextMoves.isEmpty()){

                        for(Point p: selectedTileFigureNextMoves){
                            Point flipped = new Point(p.x,p.y);
                            if(!iswhite){
                                flipped = new Point(7-p.x,7-p.y);
                            }
                            if(hoveredFigureTilePos.isEqualTo(flipped)){
                                clickedInPossibleArea = true;

                                if(iswhite){
                                    executeMove(selectedFigureTilePos,hoveredFigureTilePos );
                                }else{
                                    executeMove(
                                            new Point(7-selectedFigureTilePos.x, 7-selectedFigureTilePos.y),
                                            new Point(7-hoveredFigureTilePos.x, 7-hoveredFigureTilePos.y));
                                }

                                selectedFigureTilePos = null;
                                selectedMode = false;
                                selectedTileFigureNextMoves = new ArrayList<>();

                            }
                        }


                    }
                    if(!clickedInPossibleArea){

                        Point translatedHoveredPos = hoveredFigureTilePos;

                        if(!iswhite){
                            translatedHoveredPos = new Point(7-hoveredFigureTilePos.x, 7- hoveredFigureTilePos.y);
                        }

                        GameField gf = Sockenschach.instance.gameBoard.getFieldByPos(translatedHoveredPos);
                        if(gf.getHolder() != null){
                            if(gf.getHolder().iswhite == Sockenschach.instance.thePlayer.iswhite){

                                selectedFigureTilePos = hoveredFigureTilePos;
                            }
                        }else{
                            selectedFigureTilePos = null;
                            selectedMode = false;
                            selectedTileFigureNextMoves = new ArrayList<>();
                        }

                    }

                }
            }

            @Override
            public void keyReleased(KeyEvent k){
                if(selectedMode && k.getKeyCode() == k.VK_ESCAPE){
                    selectedMode = false;
                    selectedFigureTilePos = null;
                    selectedTileFigureNextMoves = new ArrayList<>();
                }
                if(selectedMode && k.getKeyCode()== k.VK_LEFT){
                    Point translatedHoveredPos = selectedFigureTilePos;

                    if(!iswhite){
                        translatedHoveredPos = new Point(7-selectedFigureTilePos.x, 7- selectedFigureTilePos.y);
                    }

                    GameField gf = Sockenschach.instance.gameBoard.getFieldByPos(translatedHoveredPos);
                    if(gf.getHolder() != null){
                        if(gf.getHolder() instanceof Koenig){
                            if (leftRochadeDest != null){
                                executeLeftRochade();
                                selectedFigureTilePos = null;
                                selectedMode = false;
                                selectedTileFigureNextMoves = new ArrayList<>();
                            }
                        }

                    }


                }
                if(selectedMode && k.getKeyCode()== k.VK_RIGHT){
                    Point translatedHoveredPos = selectedFigureTilePos;

                    if(!iswhite){
                        translatedHoveredPos = new Point(7-selectedFigureTilePos.x, 7- selectedFigureTilePos.y);
                    }

                    GameField gf = Sockenschach.instance.gameBoard.getFieldByPos(translatedHoveredPos);
                    if(gf.getHolder() != null){
                        if(gf.getHolder() instanceof Koenig){
                            if (rightRochadeDest != null){
                                executeRightRochade();
                                selectedFigureTilePos = null;
                                selectedMode = false;
                                selectedTileFigureNextMoves = new ArrayList<>();
                            }
                        }

                    }


                }
            }



        };
    }

    public void executeMove(Point from, Point to){

        if(Sockenschach.instance.gameBoard.getFieldByPos(from).getHolder() instanceof Bauer){
            Bauer b = (Bauer) Sockenschach.instance.gameBoard.getFieldByPos(from).getHolder();
            if((b.iswhite && to.y == 0) || (!b.iswhite && to.y == 7)){

                //init BauerChangeSubGui;
                BauerChangeSubGui bauerChangeSubGui = new BauerChangeSubGui(this.iswhite, from, to);
                Sockenschach.instance.renderHandler.addToQueue(bauerChangeSubGui);

            }else{
                Sockenschach.instance.currentMatch.callMoveSP(
                        Sockenschach.instance.gameBoard.getFieldByPos(from).getHolder().id, to);
            }
        }else{
            Sockenschach.instance.currentMatch.callMoveSP(
                    Sockenschach.instance.gameBoard.getFieldByPos(from).getHolder().id, to);
        }




    }

    public void executeRightRochade(){
        Sockenschach.instance.currentMatch.callMoveSP_Rochade(
                Sockenschach.instance.gameBoard.getFieldByPos(rightRochadeDest[0]).getHolder().id,
                Sockenschach.instance.gameBoard.getFieldByPos(rightRochadeDest[1]).getHolder().id,
                rightRochadeDest[2],rightRochadeDest[3]);

    }

    public void executeLeftRochade(){
        Sockenschach.instance.currentMatch.callMoveSP_Rochade(
                Sockenschach.instance.gameBoard.getFieldByPos(leftRochadeDest[0]).getHolder().id,
                Sockenschach.instance.gameBoard.getFieldByPos(leftRochadeDest[1]).getHolder().id,
                leftRochadeDest[2],leftRochadeDest[3]);
    }

    @Override
    public void render(Graphics2D g2){

        super.render(g2);

        leftRochadeDest = MoveMath.isLeftRochadePossible(iswhite);
        rightRochadeDest = MoveMath.isRightRochadePossible(iswhite);

        boolean drawArrowLeft = false;
        boolean drawArrowRight = false;


        //if(true){
            if(leftRochadeDest != null){
                drawArrowLeft = true;
            }
            if(rightRochadeDest != null){
                drawArrowRight = true;
            }
        //}else{

        if(drawArrowLeft){
            drawText("<-", startingTilePos.x * tilesize, startingTilePos.y * tilesize + 10*tilesize,
                    0xff222222,  1, g2);
        }
        if(drawArrowRight){
            drawText("->", startingTilePos.x * tilesize + 8*16 - getTextWidth("->"),
                    startingTilePos.y * tilesize + 10*tilesize, 0xff222222,  1, g2);
        }

        renderFigures(g2);

        if(selectedMode && selectedFigureTilePos != null){
            renderSelectedMode(g2);
            Point translatedHoveredPos = selectedFigureTilePos;
            if(!iswhite){
                translatedHoveredPos = new Point(7-selectedFigureTilePos.x, 7- selectedFigureTilePos.y);
            }
            Figur selectedFigure = Sockenschach.instance.gameBoard.getFieldByPos(translatedHoveredPos).getHolder();
            if(selectedFigure != null){
                selectedTileFigureNextMoves = selectedFigure.getNextMoves();

                renderPossibleNextMoveTiles(selectedTileFigureNextMoves, g2);

            }

        }

        if(hoveredFigureTilePos != null){

            renderHovered(g2);
        }

        if(selectedMode){

            rerenderSpecialTargetedFigurs(selectedTileFigureNextMoves,g2);

        }

    }

    private void renderSelectedMode(Graphics2D g2) {
        //darken everything
        fillRect(0,0,screenwidth,screenheight,renderShadowColor, g2);

    }


    public void renderFigures(Graphics2D g2){
        GameBoard board = Sockenschach.instance.gameBoard;
        GameField[][] fields = board.fields;

        for(int i = 0; i < fields.length; i++){
            for(int j = 0; j<fields[i].length; j++){
                if(fields[j][i].getHolder() != null){

                    Figur f = fields[j][i].getHolder();
                    Sprite sp = f.getSprite();
                    int tilei = iswhite? i: 7-i;
                    int tilej = iswhite? j: 7-j;
                    Tile t = new Tile(sp, this.tilesize * startingTilePos.x + this.tilesize * tilej, this.tilesize* tilei+ startingTilePos.y * this.tilesize);
                    drawImage(t,g2);
                }
            }
        }

    }



    public void renderHovered(Graphics2D g2){
        renderBoardTile(hoveredFigureTilePos, hoverColor, g2);

    }

    public void renderBoardTile(Point pos, int color, Graphics2D g2){
        renderOffsetedBoardTile(pos,0, color, g2);
    }


    public void renderOffsetedBoardTile(Point pos, int offset, int color, Graphics2D g2){


        int imgsize = tilesize-offset*2;
        BufferedImage img = new BufferedImage(imgsize, imgsize, 2);
        for(int i = 0; i<imgsize;i++){
            for(int j = 0;j<imgsize;j++){
                img.setRGB(j,i,color);
            }
        }

        Sprite sp = new Sprite(img, "tempsprite");
        Tile t = new Tile(sp, startingTilePos.x*tilesize + tilesize*pos.x + offset,
                startingTilePos.y*tilesize + tilesize*pos.y + offset);
        drawImage(t,g2);
    }

    public void renderPossibleNextMoveTiles(ArrayList<Point> points, Graphics2D g2){
        for(Point p: points){
            if(!p.isNeg()){

                Point flipped = new Point(p.x,p.y);
                if(!iswhite){
                    flipped = new Point(7-p.x,7-p.y);
                }

                renderOffsetedBoardTile(flipped, 1 ,nextMoveTileColor, g2);
            }

        }
    }


    public void rerenderSpecialTargetedFigurs(ArrayList<Point> points, Graphics2D g2){

        Point translatedSelectedPos = selectedFigureTilePos;
        if(!iswhite){
            translatedSelectedPos = new Point(7-selectedFigureTilePos.x, 7- selectedFigureTilePos.y);
        }

        Figur f = Sockenschach.instance.gameBoard.getFieldByPos(translatedSelectedPos).getHolder();
        if(f != null){

            Sprite sp = f.getSprite();

            Tile t = new Tile(sp, this.tilesize * startingTilePos.x + this.tilesize * selectedFigureTilePos.x, this.tilesize* selectedFigureTilePos.y+ startingTilePos.y * this.tilesize);
            drawImage(t,g2);
        }

        if(!points.isEmpty()){
            for(Point targetp : points){
                if(!targetp.isNeg()){
                    Figur f2 = Sockenschach.instance.gameBoard.getFieldByPos(targetp).getHolder();
                    if(f2 != null){

                        Sprite sp = f2.getSprite();
                        int tilei = iswhite? targetp.y: 7-targetp.y;
                        int tilej = iswhite? targetp.x: 7-targetp.x;
                        Tile t = new Tile(sp, this.tilesize * startingTilePos.x + this.tilesize * tilej, this.tilesize* tilei+ startingTilePos.y * this.tilesize);
                        drawImage(t,g2);
                    }
                }

            }
        }




    }

}
