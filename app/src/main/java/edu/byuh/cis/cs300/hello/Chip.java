package edu.byuh.cis.cs300.hello;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Chip {
    private static Paint fillColor;
    private static Paint border;
    private int[] colors = {Color.WHITE, Color.GRAY};
    private int colorNum; //Light or Dark 0 or 1
    private boolean power;
    private Cell cell;



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

    public Chip(int colorNum, Cell cell, boolean power){
        this.colorNum = colorNum;//0 or 1
        this.cell = cell;
        this.power = power;

    }

    public void draw(Canvas c){
//        c.drawCircle(cellX,cellY,50,fillColor);
        fillColor.setColor(colors[colorNum]);
        c.drawCircle(cell.getCenterX(), cell.getCenterY(),cell.getRectF().width()*0.4f, fillColor);
        c.drawCircle(cell.getCenterX(), cell.getCenterY(),cell.getRectF().width()*0.4f, border);

        if(power){
            fillColor.setColor(Color.YELLOW);
            c.drawCircle(cell.getCenterX(), cell.getCenterY(),cell.getRectF().width()*0.2f, fillColor);

        }
    }


}
