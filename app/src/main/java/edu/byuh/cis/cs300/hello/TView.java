package edu.byuh.cis.cs300.hello;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

//For debugging
import android.util.Log;


public class TView extends View {

    //**Variables for the back groud, and lines*/
    private Paint bg;
    private Paint lGb;
    private Paint rGb;
    private Paint lines;
    private Toast john;
    private Boolean firstDraw = true;

    //Below here is test variables

    private Chip chip1;
    private Cell[][] cells;

    private float bgBottom;
    private float xWidth;
    private float yWidth;

    private RectF rectF;

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


    @Override
    public boolean onTouchEvent(MotionEvent m){
        if(m.getAction()==MotionEvent.ACTION_DOWN){
            float x = m.getX();
            float y = m.getY();
            //It will tell you which row and column its been clicked.
//            Toast t = Toast.makeText(getContext(),"Tapped at Column: " + (int)(x/xWidth) + " and Row: " + (int)(y/yWidth), Toast.LENGTH_SHORT);
//            t.show();

         Toast g = Toast.makeText(getContext()," Y " + y, Toast.LENGTH_SHORT);
            g.show();

        }

        return true;
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

            //Making invisible cells.

            xWidth = w/9f; //Imagine 20px
            yWidth = (h*0.8f)/10f; //30px

            //DO i even needs these cells?
            cells = new Cell[9][10]; // 9 10 or 10 9 ?

            for (int x = 0; x < 9; x++) {
                //Going throw EACH ROW
                for (int y = 0; y < 10; y++) {
                    //Column in the each row.
                    float left = x * xWidth;
                    float top = y * yWidth;
                    float right = left + xWidth;
                    float bottom = top + yWidth;

                    RectF rectF = new RectF(left, top, right, bottom);

                    float[] lightX = {7,8,9};
                    int color;
                    if (x>5&& y<3) {//right top
                        color = Team.LIGHT;
                    } else if (x<3&& y>6) {//left bottom
                        color = Team.DARK;
                    }else{//others
                        color = Team.NEUTRAL;
                    }

                    cells[x][y] = new Cell(x, y, rectF, color);
                }
            }
            chip1 = new Chip(0,cells[0][0],false);


        }

        float bgLeft = 0;
        float bgTop = 0;
        bgBottom = (float)(h*0.8);
//        Log.d("TView", "Cell TEST X"+ cells[2][5].getCenterX() +" Y ; "+ cells[2][5].getCenterY());



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
        for (int i = 0; i<10;i++){  //**Draw the X lines*/
            c.drawLine(startX+(i*xWidth),startY,startX+(i*xWidth),bgBottom,lines);

        }

        chip1.draw(c);


    }
}
