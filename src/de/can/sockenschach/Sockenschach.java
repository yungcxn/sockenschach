package de.can.sockenschach;

import de.can.sockenschach.game.GameBoard;
import de.can.sockenschach.gui.GameFrame;
import de.can.sockenschach.game.Match;
import de.can.sockenschach.gui.render.Font;
import de.can.sockenschach.gui.render.RenderHandler;
import de.can.sockenschach.gui.sprite.SpriteHandler;
import de.can.sockenschach.net.NetHandler;
import de.can.sockenschach.player.Player;
import de.can.sockenschach.player.PlayerMP;
import de.can.sockenschach.player.PlayerSP;
import de.can.sockenschach.util.Logger;

public class Sockenschach {

    public static final boolean PROTOCOL_SWITCH = false; //legt fest ob canprotocol oder klassische ip eingabe

    public final String name = "Sockenschach";
    public final float version = 0.1f;
    public final String compName = name + " " + version;
    public final String author = "can";

    public boolean running;

    public static Sockenschach instance;

    public GameBoard gameBoard;
    public GameFrame gameFrame;

    public RenderHandler renderHandler;
    public SpriteHandler spriteHandler;
    public NetHandler netHandler;

    public Match currentMatch;

    final int FPS = 60;

    public Font font;

    public PlayerSP thePlayer;
    public PlayerMP otherPlayer;



    public Sockenschach(){
        this.instance = this;
        this.running = true;
        this.gameBoard = new GameBoard();
        this.gameFrame = new GameFrame(compName);
    }

    public void postInit(){
        spriteHandler = new SpriteHandler();
        font = new Font();



        renderHandler = new RenderHandler();

        gameFrame.run();

    }

    public void main(){
        //fpslock specific

        final int SKIP_TICKS = 1000 / FPS;

        long startTime = System.currentTimeMillis();
        long sleep_time = 0;

        while(running){
            onupdate();
            onrender();

            startTime += SKIP_TICKS;
            sleep_time = startTime - System.currentTimeMillis();
            if(sleep_time >= 0){
                try {
                    Thread.sleep(sleep_time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                Logger.log("Machine is too slow!");
            }
        }
    }

    private void onupdate(){

        if(this.netHandler != null){
            this.netHandler.onUpdate();
        }
        if(this.currentMatch != null){
            if(this.gameBoard != null){
                this.gameBoard.updateBoard();
            }
            this.currentMatch.onUpdate();

        }

    }

    private void onrender(){
        this.gameFrame.gamePane.repaint();
    }


}
