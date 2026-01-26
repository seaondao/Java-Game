package edu.byuh.cis.cs300.hello;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Chip {
    private static Paint fillColor;
    private int[] colors = {Color.WHITE, Color.BLACK};
    private int colorNum; //Light or Dark 0 or 1
    private boolean power;
    private Cell cell;

    private float size;


    static {
        fillColor = new Paint();
        fillColor.setStyle(Paint.Style.FILL);
    }

    public Chip(int colorNum, Cell cell, boolean power){
        this.colorNum = colorNum;
        this.cell = cell;
        this.power = power;

    }

    public void draw(Canvas c){
//        c.drawCircle(cellX,cellY,50,fillColor);
        fillColor.setColor(colors[colorNum]);
        c.drawCircle(cell.getCenterX(), cell.getCenterY(),cell.getRectF().width()*0.4f, fillColor);
    }


}
