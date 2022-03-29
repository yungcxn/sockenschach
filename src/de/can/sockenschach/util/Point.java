package de.can.sockenschach.util;

public class Point {

    public int x;
    public int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public double getDistanceTo(Point vec){
        return Math.sqrt((vec.x - this.x)^2 +  (vec.y - this.y)^2);
    }

    public boolean isEqualTo(Point x){
        return this.x == x.x && this.y == x.y;
    }

    public String toString(){
        return "x:" + x + " & y:" + y;
    }

    public boolean isNeg(){
        return x < 0 && y < 0;
    }


}
