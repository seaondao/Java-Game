package edu.byuh.cis.cs300.hello;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;

import java.util.logging.Handler;

public class Chip {
    private static final String TAG = "Chip";


    private static Paint fillColor;
    private static Paint border;

    //This will not be changed.
    private static final int[] colors = {Color.BLACK,Color.rgb(173,216,230), Color.rgb(0,100,0)};
    public int colorNum; //Light or Dark 0 or 1
    public boolean power;
    private Cell cell;
    private PointF center;


    private boolean selected;

    static Chip selectedChip;

    private PointF velocity; //int(x,y) direction that it moves
    private Cell destination;
    private float cellWidth;
    private float cellHeight;


    static {
        fillColor = new Paint();
        fillColor.setStyle(Paint.Style.FILL);

        border = new Paint();
        border.setStyle(Paint.Style.STROKE);
        border.setStrokeWidth(5);


    }

    public Cell getCell() {
        return cell;
    }

    private Chip(int colorNum, Cell cell, boolean power){
        this.colorNum = colorNum;//0 or 1
        this.cell = cell;
        this.center = cell.getPointF();
        this.power = power;
        this.velocity = new PointF(0,0);
        this.destination = null;
    }

    //We make 👆 private so belowe will call that
    public static Chip normal(int colorNum, Cell cell){
        return new Chip(colorNum,cell,false);
    }
    public static Chip power(int colorNum, Cell cell){
        return new Chip(colorNum,cell,true);
    }



    public void draw(Canvas c){

        cellWidth = cell.getRectF().width();
        cellHeight = cell.getRectF().height();

//        c.drawCircle(cellX,cellY,50,fillColor);
        fillColor.setColor(Color.RED);
        if(selected){//#1 draw the border of the selected Chip
            c.drawCircle(center.x, center.y,cellWidth*0.5f, fillColor);
        }

        fillColor.setColor(colors[colorNum]);
//        Log.d(TAG, "Chip pointF X : " + center.x + " and " + center.x);
        c.drawCircle(center.x, center.y,cellWidth*0.4f, fillColor);
        c.drawCircle(center.x, center.y,cellWidth*0.4f, border);

        if(power){
            fillColor.setColor(Color.rgb(202, 192, 6));
            c.drawCircle(center.x, center.y,cellWidth*0.2f, fillColor);

        }
        cell.setOccupied();
    }

    public Chip setSelected() {//Make sure player can't select more than 1 chip.
        if(selected||selectedChip!=null){//This chip is selected or other chip is selected
            //Un select it
            selectedChip.selected = false;
            selectedChip = null;
        }else{//There is no Chip selected so select this chip.
            selected = true;
            selectedChip = this;
        }
        return this; //TO get data from it
    }

    public void setDestination(Cell destination) {//Set Destination
        this.destination = destination;

        // Caluculate ABS (Destination X - Current X) /10 MOVE THIS AMOUT DO SAME FOR Y
        //

        float moveWidth =cellWidth;

        float moveHeight = cellHeight;


        //Check if the destination Direction.
        if(cell.getX()>destination.getX()){//Destination X is smaller
            velocity.x = -1*moveWidth;
            Log.d(TAG, "Velocity X : " +velocity.x );
        }else if(cell.getX()<destination.getX()){
            velocity.x = 1*moveWidth;
            Log.d(TAG, "Velocity X : " +velocity.x );
        }
        if(cell.getY()>destination.getY()){//Destination Y is smaller
            velocity.y = -1*moveHeight;
            Log.d(TAG, "Velocity Y : " +velocity.y );
        }else if(cell.getY()<destination.getY()){
            velocity.y = 1*moveHeight;
            Log.d(TAG, "Velocity Y : " +velocity.y );
        }

    }

    /*
            1. ANY selected chip.selected = false
            2. Make selectedChip Null
         */
    static void reset(){
        if(selectedChip!=null){ //for  Null pointer Thing
            selectedChip.selected = false;
            selectedChip = null;
        }

    }

    /*
        IF there is a destination
        offset(the current location for that chip move in the velocity angle)
        if it gets near by change the current cell to there.
     */
    public Chip animate(){// Make sure moving chip is still moving chhip untill moving is done.

        if(destination!= null){
            center.offset(velocity.x,velocity.y);
//            Log.d(TAG, "current (X, Y): ( " + center.x + ", " + destination.getCenterX());

            if (Math.abs(center.x-destination.getCenterX()) < cellWidth && Math.abs(center.y-destination.getCenterY()) < cellWidth){
                velocity.x = 0;
                velocity.y = 0;
                cell.setFree();
                cell = destination;
                center = cell.getPointF();
                destination = null;
                reset();
                return null;
            }else{
                return this;
            }
        }else {
            return this;
        }





    }


    public boolean contains(int x,int y){return cell.contains(x,y);}



}
