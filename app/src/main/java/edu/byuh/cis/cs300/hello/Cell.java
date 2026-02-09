package edu.byuh.cis.cs300.hello;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Canvas;


public class Cell {
    private int x;/*2D array location of X and Y [9][10]*/
    private int y;

    /*Contains the box 4 location */
    private RectF rectF;

    //Team color from Team CLASS 0,1,2
    private int color;

    private boolean occupied;

    private Paint fillColor = new Paint();



    //Constructor
    public Cell(int x, int y, RectF rectF, int color){
        this.x = x;
        this.y = y;
        this.rectF = rectF;
        this.color = color;
        this.occupied = false;
    }
    public RectF getRectF() {
        return rectF;
    }

    //No usage for now but recomanded in the instruction

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

    public boolean isLegalMove(Chip c){
        return (!occupied&&color!=c.colorNum);//&& need some more conditions
    }


    public Cell setOccupied() {
        this.occupied = true;
        return this;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void draw(Canvas c){
        if(Chip.selectedChip!= null){
            fillColor.setStyle(Paint.Style.FILL);
            fillColor.setColor(Color.RED);
            c.drawCircle(getCenterX(),getCenterY(),getRectF().width()*0.3f,fillColor);
        }
    }
}
