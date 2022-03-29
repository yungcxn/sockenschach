package de.can.sockenschach.gui.render;

import de.can.sockenschach.Sockenschach;
import de.can.sockenschach.gui.sprite.Sprite;

import java.awt.image.BufferedImage;

public class Font {

    public Sprite fontImage;
    public int[] offsets;
    public int[] widths;

    public final int FONT_HEIGHT;

    int lettercount = 64;
    int firstletteroffset = 32;

    public Sprite[] letters;

    public Font(){
        fontImage = Sockenschach.instance.spriteHandler.getSpriteByName("font");

        FONT_HEIGHT = fontImage.height-1;

        offsets = new int[lettercount];
        widths = new int[lettercount];

        int uni = 0;

        for(int i = 0; i < fontImage.width; i++){
            if(fontImage.getPixelAt(i, 0) == 0xff0000ff){
                offsets[uni] = i;
            }

            if(fontImage.getPixelAt(i,0) == 0xffffff00){
                widths[uni] = i - offsets[uni];
                uni++;
            }
        }

        letters = new Sprite[lettercount];

        for(int i = 0; i<letters.length; i++){
            int letterwidth = widths[i];
            int letterheight = fontImage.height;
            int startoffset = offsets[i];



            BufferedImage buf = new BufferedImage(letterwidth, letterheight ,2);

            for(int x = startoffset; x< startoffset + letterwidth; x++){
                for(int y = 1; y<letterheight; y++){
                    int orgcolor = fontImage.getPixelAt(x,y);
                    if(orgcolor == 0xffffffff) {

                        buf.setRGB(x-startoffset, y, 0xffffffff);
                    }

                }
            }

            letters[i] = new Sprite(buf, Character.toString((char)(i+firstletteroffset)));

        }


        for(Sprite l: letters){
            Sockenschach.instance.spriteHandler.sprites.add(l);
        }

    }


}
