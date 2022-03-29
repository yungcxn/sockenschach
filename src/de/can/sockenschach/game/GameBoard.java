package de.can.sockenschach.game;

import de.can.sockenschach.Sockenschach;
import de.can.sockenschach.figur.Figur;
import de.can.sockenschach.util.Point;

public class GameBoard {

    public GameField[][] fields = new GameField[8][8];

    public GameField getFieldByPos(Point pos){
        return fields[pos.x][pos.y];
    }
    public GameField getField(int x, int y){
        return fields[x][y];
    }

    public GameBoard(){

        reinitfields();
    }

    private void reinitfields(){
        String[] names = new String[]{
                "A", "B", "C", "D", "E", "F", "G", "H"
        };

        String[] numerals = new String[]{
                "8", "7", "6", "4", "5", "3", "2", "1"
        };

        for(int i = 0; i<fields.length; i++){
            for(int j = 0; j<fields[i].length; j++){
                fields[j][i] = new GameField(names[j] + numerals[i], new Point(j, i));
            }
        }
    }

    public void updateBoard(){
        reinitfields();
        for(Figur f: Sockenschach.instance.currentMatch.whitePlayer.team){
            fields[f.pos.x][f.pos.y].setHolder(f);
        }
        for(Figur f: Sockenschach.instance.currentMatch.blackPlayer.team){
            fields[f.pos.x][f.pos.y].setHolder(f);
        }

    }

}
