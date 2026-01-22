package edu.byuh.cis.cs300.hello;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Trace;
import android.view.View;
import android.widget.Toast;

public class TView extends View {

    //**Variables for the back groud, and lines*/
    private Paint bg;
    private Paint lGb;
    private Paint rGb;
    private Paint lines;
    private Toast john;
    private Boolean firstDraw = true;

    public TView(Context c) {
        super(c);
        //**setting the color for back groud and two rectangle  and lines*/
        bg = new Paint();
        bg.setColor(Color.rgb(210, 180, 140));/*Back groud for thr bord*/
        lGb = new Paint();
        lGb.setColor(Color.rgb(160, 110, 50));/*Left small box BG*/
        rGb = new Paint();
        rGb.setColor(Color.rgb(139, 94, 60));/*right side small box BG*/
        lines = new Paint();
        lines.setColor(Color.BLACK);
        lines.setStyle(Paint.Style.STROKE);//**We did this in class*/

        //**Class thing for the text that show up*/
        john = Toast.makeText(c,"CS 300 is my favorite class!", Toast.LENGTH_LONG);
        john.show();
        firstDraw = true;

    }


    @Override//**Main drawing part*/
    public void onDraw(Canvas c){
        c.drawColor(Color.GREEN);
        float w = getWidth();
        float h = getHeight();
        //**Only when it loads for the first time*/
        if(firstDraw){
            lines.setStrokeWidth(w/100);
            firstDraw = false;
        }
        float bgLeft = 0;
        float bgTop = 0;
        float bgBottom = (float)(h*0.8);

        //**back groud*/
        c.drawRect(bgLeft,bgTop, w,bgBottom,bg);


        //**Variables for -----XY lines----*/
        float startX = 0;
        float startY = 0;
        float stopY = 0;
        float yWidth = bgBottom/10;
        float xWidth = w/9;

        //**Box Rect*/
        c.drawRect(xWidth*6,bgTop,w,yWidth*3,rGb);
        c.drawRect(startX,yWidth*7,xWidth*3,bgBottom,lGb);

        //**For both of them the first line will
        // be invisible since it starts at 0 its +1 times*/
        for (int i = 0;i<11;i++){//Draw Y lines. 11 times because 10 + the last line for the bottom
            //**Horizontal Lines*/
            c.drawLine(startX,startY+(i*yWidth),w,stopY+(i*yWidth),lines);
        }
        for (int i = 0; i<9;i++){  //**Draw the X lines*/
            c.drawLine(startX+(i*xWidth),startY,startX+(i*xWidth),bgBottom,lines);

        }

    }
}
