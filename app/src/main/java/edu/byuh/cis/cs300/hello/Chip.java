package edu.byuh.cis.cs300.hello;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Chip {
    private static Paint fillColor;
    private static Paint border;

    //This will not be changed.
    private static final int[] colors = {Color.BLACK,Color.rgb(173,216,230), Color.rgb(0,100,0)};
    public int colorNum; //Light or Dark 1 or 2
    public boolean power;
    private Cell cell;

    private boolean selected;

    static Chip selectedChip;

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
        this.power = power;
    }

    //We make 👆 private so belowe will call that
    public static Chip normal(int colorNum, Cell cell){
        return new Chip(colorNum,cell,false);
    }
    public static Chip power(int colorNum, Cell cell){
        return new Chip(colorNum,cell,true);
    }



    public void draw(Canvas c){
//        c.drawCircle(cellX,cellY,50,fillColor);
        fillColor.setColor(Color.YELLOW);
        if(selected){
            c.drawCircle(cell.getCenterX(), cell.getCenterY(),cell.getRectF().width()*0.5f, fillColor);
        }

        fillColor.setColor(colors[colorNum]);
        c.drawCircle(cell.getCenterX(), cell.getCenterY(),cell.getRectF().width()*0.4f, fillColor);
        c.drawCircle(cell.getCenterX(), cell.getCenterY(),cell.getRectF().width()*0.4f, border);

        if(power){
            fillColor.setColor(Color.rgb(202, 192, 6));
            c.drawCircle(cell.getCenterX(), cell.getCenterY(),cell.getRectF().width()*0.2f, fillColor);

        }
        cell.setOccupied();
    }

    public Chip setSelected() {//Make sure player can't select more than 1 chip.
        if(selected||selectedChip!=null){
            selectedChip.selected = false;
            selectedChip = null;
        }else{
            selected = true;
            selectedChip = this;
        }
        return this; //TO get data from it
    }




    public boolean contains(int x,int y){return cell.contains(x,y);}

}
