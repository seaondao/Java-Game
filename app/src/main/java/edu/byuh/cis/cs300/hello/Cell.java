package edu.byuh.cis.cs300.hello;

import android.graphics.RectF;

public class Cell {
    private int x;/*2D array location of X and Y [9][10]*/
    private int y;

    /*Contains the box 4 location */
    private RectF rectF;

    //Team color from Team CLASS 0,1,2
    private int color;


    //Constructor
    public Cell(int x, int y, RectF rectF, int color){
        this.x = x;
        this.y = y;
        this.rectF = rectF;
        this.color = color;

    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public RectF getRectF() {
        return rectF;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    //Check if touched place is in the cell ???
    public boolean contains(float touchX, float touchY){
        return rectF.contains(touchX,touchY);
    }

    public float getCenterX(){
        return rectF.centerX();
    }
    public float getCenterY(){
        return rectF.centerY();
    }


}
