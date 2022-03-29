package de.can.sockenschach.gui.subgui;

import de.can.sockenschach.Sockenschach;
import de.can.sockenschach.util.Point;

public class GameFullGui extends FullGui{//used for log displaying, names, dead figures, maybe time

    BoardSubGui boardSubGui;
    FigureSubGui figureSubGui;
    MatchInfoSubGui matchInfoSubGui;

    boolean iswhite;

    public GameFullGui(){
        super();
        this.bgColor = 0xffbead99;
        this.iswhite = Sockenschach.instance.thePlayer.iswhite;

        boardSubGui = new BoardSubGui(iswhite);
        int off = boardSubGui.boardMargin*tilesize - boardSubGui.labelareawidth;
        matchInfoSubGui = new MatchInfoSubGui(iswhite, new Point(
                off,
                off));
        figureSubGui = new FigureSubGui(iswhite, new Point(boardSubGui.boardMargin, boardSubGui.boardMargin));

        this.subguis.add(boardSubGui);
        this.subguis.add(matchInfoSubGui);
        this.subguis.add(figureSubGui);
    }
}
