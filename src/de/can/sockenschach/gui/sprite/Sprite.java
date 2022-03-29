package de.can.sockenschach.gui.sprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Sprite {

    private BufferedImage image;

    public String name;
    public int width;
    public int height;

    public Sprite(BufferedImage img, String name){
        this.image = img;
        this.name = name;
        this.width = this.image.getWidth();
        this.height = this.image.getHeight();
    }

    public Sprite(String path, String name){
        try {
            this.image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.name = name;
        this.width = this.image.getWidth();
        this.height = this.image.getHeight();
    }



    public BufferedImage getBufferedImage(){
        return this.image;
    }

    public int getPixelAt(int x, int y){
        return this.image.getRGB(x,y);
    }

    public Sprite getChangedColorVersion(int from, int to){
        BufferedImage buf = new BufferedImage(image.getWidth(), image.getHeight(), 2);
        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                if(new Color(image.getRGB(x,y), true).getAlpha() != 0){
                    if(image.getRGB(x,y) == from){
                        buf.setRGB(x,y,to);
                    }else{
                        buf.setRGB(x,y,from);
                    }
                }
            }
        }
        return new Sprite(buf, this.name);


    }

    public Sprite getDarkerVersion(int num){
        BufferedImage buf = new BufferedImage(image.getWidth(), image.getHeight(), 2);
        for(int y = 0; y < buf.getHeight(); y++){
            for(int x = 0; x < buf.getWidth(); x++){
                int rgba = image.getRGB(x,y);
                Color newcolor = new Color(rgba, true);
                int r = newcolor.getRed() - num < 0? 0 : newcolor.getRed() - num;
                int g = newcolor.getGreen() - num < 0? 0 : newcolor.getGreen() - num;
                int b = newcolor.getBlue() - num< 0? 0 : newcolor.getBlue() - num ;
                int a = newcolor.getAlpha();

                buf.setRGB(x,y, new Color(r,g,b,a).getRGB());



            }
        }

        return new Sprite(buf, this.name);

    }

}
