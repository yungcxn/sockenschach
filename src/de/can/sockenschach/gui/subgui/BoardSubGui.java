package de.can.sockenschach.gui.subgui;

import de.can.sockenschach.Sockenschach;
import de.can.sockenschach.util.Point;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

public class BoardSubGui extends SubGui{

    public final int boardcolor1 = 0xffcccccc;
    public final int boardcolor2 = 0xff111111;
    public final int framecolor = 0xffbe813c;
    public final int labelareacolor = boardcolor1;
    public final int labelfontcolor = boardcolor2;
    public final int labelfontsize = 1;
    public final int boardMargin = 2;
    public final int framewidth = 2;
    public final int labelareawidth = 10;

    public boolean iswhite;
    private int boardwidthintiles;

    public BoardSubGui(boolean iswhite){
        super();
        this.boardwidthintiles = Sockenschach.instance.gameBoard.fields.length;

        this.iswhite = iswhite;

    }

    @Override
    public void render(Graphics2D g2){
        super.render(g2);
        //int i = 0;
        /*for(Sprite s: Sockenschach.instance.font.letters){

            g2.drawImage(s.getBufferedImage(), 10*i, 10*i, null);
            i++;
        }*/

        //drawTotallyCenteredText("can\nist der king!", 0xffff0000,3, g2);


        int counter = 0;
        for(int i = 0; i< boardwidthintiles; i++){
            for(int j = 0; j< boardwidthintiles; j++){
                int realx = j+boardMargin;
                int realy = i+boardMargin;
                drawSquareAtTileVec(new Point(realx, realy), ((counter % 2) == 0 ? boardcolor1 : boardcolor2), g2);
                counter++;
            }
            counter--;
        }

        int squareDistance = boardMargin * this.tilesize;
        Point chessBoardStart = new Point(squareDistance, squareDistance);

        drawFrame(chessBoardStart, labelareacolor, framewidth+labelareawidth, g2);
        drawFrame(chessBoardStart, framecolor, framewidth, g2);


        drawLabels(chessBoardStart, labelareawidth, framewidth, labelfontsize, labelfontcolor, g2);
    }

    private void drawSquareAtTileVec(Point p, int color, Graphics2D g2){
        //p points to tile at 0-16x and 0-12x
        this.fillRect(p.x * this.tilesize, p.y * this.tilesize, this.tilesize, this.tilesize, color, g2);
    }

    private void drawFrame(Point chessBoardStart, int color, int width, Graphics2D g2){
        int chessBoardTileWidth = 8;
        int boardSize = chessBoardTileWidth * tilesize;
        Point edge1 = new Point(chessBoardStart.x - width, chessBoardStart.y-width);
        Point edge2 = new Point(chessBoardStart.x + boardSize + width, chessBoardStart.y - width);
        Point edge3 = new Point(chessBoardStart.x - width, chessBoardStart.y + width + boardSize);

        this.fillRect(edge1.x, edge1.y,  boardSize + 2*width, width, color, g2);
        this.fillRect(edge2.x-width, edge2.y+width, width, boardSize + width, color, g2);
        this.fillRect(edge1.x, edge1.y+width, width, boardSize + width, color, g2);
        this.fillRect(edge3.x, edge3.y - width, boardSize + 2*width, width, color, g2);
    }

    private void drawLabels(Point chessBoardStart, int labelAreaDistanceToBoard, int framewidth,
                            int fontsize, int fontcolor, Graphics2D g2){
        int chessBoardTileWidth = 8;
        int boardSize = chessBoardTileWidth * this.tilesize;

        Point areaStartToDraw = new Point(chessBoardStart.x, chessBoardStart.y-labelAreaDistanceToBoard);

        String[] horis = new String[]{
                "A", "B", "C", "D", "E", "F", "G", "H"
        };

        String[] verts = new String[]{
                "8", "7", "6", "5", "4", "3", "2", "1"
        };

        if(!iswhite){
            Collections.reverse(Arrays.asList(horis));Collections.reverse(Arrays.asList(verts));

        }

        for(int i = 0; i<boardwidthintiles; i++){
            int horizontalCenter = areaStartToDraw.x + (this.tilesize * i) + (this.tilesize/2);
            int verticalCoordToDraw = areaStartToDraw.y + ((labelAreaDistanceToBoard - framewidth) / 2);

            //fillRect(horizontalCenter, verticalCoordToDraw, 1, 1, 0xff000000, g2); debug

            drawText(horis[i], horizontalCenter - (fontsize* getTextWidth(horis[i])) / 2,
                    verticalCoordToDraw - (int) ((fontsize* getTextHeight(horis[i]))/ 1.5), fontcolor, fontsize, g2);

            int verticalCoordToDraw2 = chessBoardStart.y + boardSize + framewidth + ((labelAreaDistanceToBoard - framewidth) / 2);

            drawText(horis[i], horizontalCenter -(fontsize* getTextWidth(horis[i])) / 2,
                    verticalCoordToDraw2 - (int) ((fontsize* getTextHeight(horis[i]))/ 1.5), fontcolor, fontsize, g2);

        }

        areaStartToDraw = new Point(chessBoardStart.x - labelAreaDistanceToBoard, chessBoardStart.y);

        for(int i = 0; i<boardwidthintiles; i++){
            int horizontalCenter = areaStartToDraw.x + ((labelAreaDistanceToBoard - framewidth) / 2);
            int verticalCoordToDraw = areaStartToDraw.y + (this.tilesize * i) + (this.tilesize/2);

            drawText(verts[i], horizontalCenter - (fontsize * getTextWidth(verts[i])) / 2,
                    verticalCoordToDraw - (int) ((fontsize * getTextHeight(verts[i])) / 1.5), fontcolor, fontsize, g2);

            int horizontalCenter2 = chessBoardStart.x + boardSize + framewidth + ((labelAreaDistanceToBoard - framewidth) / 2);

            drawText(verts[i], horizontalCenter2 - (fontsize * getTextWidth(verts[i])) / 2,
                    verticalCoordToDraw - (int) ((fontsize * getTextHeight(verts[i])) / 1.5), fontcolor, fontsize, g2);

        }



    }
}
