package edu.byuh.cis.cs300.hello;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
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
    private Paint border = new Paint();


    static Boolean selectable = true;
    static Cell selectedCell = null;



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

    /*
        Check Leagal Moves
        1. if this cell is not occupied
        2. if this cell Color is not same as the chip team
            SO it can'ty go in to the same team area.

        Tell, Don’t Ask (Allen Holub) ??? ?? ??
            1 Meaning full question is ok ?
     */
    public boolean isLegalMove(Chip c){
        return (!occupied&&color!=c.colorNum);
    }


    public Cell setOccupied() {
        this.occupied = true;
        return this;
    }
    public Cell setFree(){
        this.occupied = false;
        return this;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public PointF getPointF(){
        return new PointF(getCenterX(),getCenterY());

    }

    /*
    This draw is only for showing Moveable cell *Dot or something
     */
    public void draw(Canvas c){
        if(Chip.selectedChip!= null){
            //Circle
            fillColor.setStyle(Paint.Style.FILL);
            fillColor.setColor(Color.rgb(255,20,147));
            c.drawCircle(getCenterX(),getCenterY(),getRectF().width()*0.3f,fillColor);

            //Border lines
            border.setStyle(Paint.Style.STROKE);
            border.setColor(Color.RED);
            border.setStrokeWidth(rectF.width()*0.05f);
            c.drawRect(rectF,border );
        }
    }

//    public Cell setSelected() {
//        if(selectable && selectedCell == null){
//            selectedCell = this;
//        }else{
//            selectable = false;
//            selectedCell  = null;
//        }
//        return this; //TO get data from it
//    }
}
