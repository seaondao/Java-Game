package edu.byuh.cis.cs300.hello;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Chip {
    private static Paint round;
    private float chipX;
    private float chipY;
    private boolean kingChip;


    static {
        round = new Paint();
        round.setColor(Color.GREEN);
        round.setStyle(Paint.Style.FILL);
    }

    public Chip(float x, float y, boolean king){
        chipX = x;
        chipY = y;
        kingChip = king;
    }

    public void draw(Canvas c){

    }


}
