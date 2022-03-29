package de.can.sockenschach.gui.sprite;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SpriteHandler {

    public ArrayList<Sprite> sprites;

    public SpriteHandler(){
        sprites = new ArrayList<>();

        registerSprites();
    }

    private void registerSprites() {
        this.sprites.add(new Sprite("assets/bauer.png", "bauer"));
        this.sprites.add(new Sprite("assets/dame.png", "dame"));
        this.sprites.add(new Sprite("assets/koenig.png", "koenig"));
        this.sprites.add(new Sprite("assets/laeufer.png", "laeufer"));
        this.sprites.add(new Sprite("assets/springer.png", "springer"));
        this.sprites.add(new Sprite("assets/turm.png", "turm"));
        this.sprites.add(new Sprite("assets/font.png", "font"));
    }

    public Sprite getSpriteByName(String name){
        for(Sprite s: sprites){
            if(name.equalsIgnoreCase(s.name)){
                return s;
            }
        }
        return null;
    }

}
