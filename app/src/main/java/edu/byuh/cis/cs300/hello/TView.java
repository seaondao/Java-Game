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

import java.util.ArrayList;
import java.util.List;


public class TView extends View {

    //**Variables for the back groud, and lines*/
    private static final String TAG = "TView";
    //field declarations
    private Paint bg;
    private Paint lGb;
    private Paint rGb;
    private Paint lines;
    private Toast john;
    private Boolean firstDraw = true;

    //Below here is test variables

//    private Chip chip1;

    private Chip[] darkChips;
    private Chip[] lightChips;

    private Chip[] allChips;
    private Cell[][] cells;

    private float bgBottom;
    private float xWidth;
    private float yWidth;

    private RectF rectF;
    ArrayList<Cell> legalMoves = new ArrayList<>();


    public TView(Context c) {
        super(c);
        //**setting the color for back groud and two rectangle  and lines*/
        bg = new Paint();
        bg.setColor(Color.rgb(210, 180, 140));/*Back groud for thr bord*/
        lGb = new Paint();
        lGb.setColor(Color.rgb(110, 70, 35));/*Left small box BG*/
        rGb = new Paint();
        rGb.setColor(Color.rgb(175, 125, 85));/*right side small box BG*/
        lines = new Paint();
        lines.setColor(Color.BLACK);
        lines.setStyle(Paint.Style.STROKE);//**We did this in class*/

        //**Class thing for the text that show up*/
//        john = Toast.makeText(c,"CS 300 is my favorite class!", Toast.LENGTH_LONG);
//        john.show();
        firstDraw = true;

    }


    @Override
    public boolean onTouchEvent(MotionEvent m){
        boolean power = false;
        if(m.getAction()==MotionEvent.ACTION_DOWN){
            Chip movingChip = null;

            int x = (int) m.getX();
            int y = (int) m.getY();

            for(Chip chip : darkChips){
                if(chip.contains(x,y)){
                    movingChip = chip.setSelected();
                    power = movingChip.power;
                }
            }
            for(Chip chip : lightChips){
                if(chip.contains(x,y)){
                    movingChip = chip.setSelected();
                    power = movingChip.power;
                }
            }

            //CHECK WHICH SELL WAS CLIOCKED For LATER Maybe
//            Cell clickedCell = null;
//            for(int i = 0; i<9 ;i++){
//                for(int j = 0; j<10 ; j++){
//                    if (cells[i][j].contains(x,y)){
//                        clickedCell = cells[i][j];
//                    }
//                }
//            }
//            if (clickedCell != null){ // I think I need this later
//                Log.i(TAG, "Clicked Cell X : " + clickedCell.getX() + " and Y :"+clickedCell.getY());
//
//            }



            //Only If  chip is selected
            if(movingChip != null){
                legalMoves.clear();//Clear everytime before looking for a moveable spot
                int selectedCellX = movingChip.getCell().getX();
                int selectedCellY = movingChip.getCell().getY();

                //Which Cell(with Chip is selected)
                Log.i(TAG, "X is : " +selectedCellX +"Y is "+ selectedCellY);


                //Right
                Cell candidate = null;
                for(int i = selectedCellX+1; i< 9;i++){//Check Right
                    if (cells[i][selectedCellY].isLegalMove(movingChip)) {
                        if (!power){
                            candidate = cells[i][selectedCellY];
                        }else{
                            legalMoves.add(cells[i][selectedCellY]);
                        }

                    }else{
                        break;
                    }
                }if(candidate != null){
                        legalMoves.add(candidate);//Most right side that is able to go.
                    }

                //Left
                candidate = null;
                for(int i = selectedCellX-1; i>=0;i--){//Check Right
                    if (cells[i][selectedCellY].isLegalMove(movingChip)) {
                        if (!power){
                            candidate = cells[i][selectedCellY];
                        }else{
                            legalMoves.add(cells[i][selectedCellY]);
                        }
                    }else{
                        break;
                    }
                }if(candidate != null){
                    legalMoves.add(candidate);//Most right side that is able to go.
                }

                //Down
                candidate = null;
                for(int i = selectedCellY+1; i< 10;i++){//Check Dwn
                    if (cells[selectedCellX][i].isLegalMove(movingChip)) {
                        if (!power){
                            candidate = cells[selectedCellX][i];
                        }else{
                            legalMoves.add(cells[selectedCellX][i]);
                        }

                    }else{
                        break;
                    }
                }if(candidate != null){
                    legalMoves.add(candidate);//Most right side that is able to go.
                }
                //Top
                candidate = null;
                for(int i = selectedCellY-1; i>=0;i--){//Check Top
                    if (cells[selectedCellX][i].isLegalMove(movingChip)) {
                        if (!power){
                            candidate = cells[selectedCellX][i];
                        }else{
                            legalMoves.add(cells[selectedCellX][i]);
                        }
                    }else{
                        break;
                    }
                }if(candidate != null){
                    legalMoves.add(candidate);//Most right side that is able to go.
                }

//                if(power){//Check diagonal //Right UP first
//                    int i = selectedCellX+1;
//                    int j = selectedCellY-1;
//                    while (i<9 && j>0 ){
//                        if(cells[i][j].isLegalMove(movingChip)){
//                            legalMoves.add(cells[selectedCellX][i]);
//                        }else{
//                            break;
//                        }
//                        i++;
//                        j--;
//                    }
//
//                }

            }

            invalidate();
        }
        //Lets get which sell it belongs too.
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

            /*Making invisible cells.*/
            xWidth = w/9f; //Imagine 20px
            yWidth = (h*0.8f)/10f; //30px

            cells = new Cell[9][10]; //9 X and 10Y.

            for (int x = 0; x < 9; x++) {//9 column
                //Going throw Column
                for (int y = 0; y < 10; y++) {//10rows
                    //ALl of the rows, so next
                    float left = x * xWidth;
                    float top = y * yWidth;
                    float right = left + xWidth;
                    float bottom = top + yWidth;

                    RectF rectF = new RectF(left, top, right, bottom);

                    float[] lightX = {7,8,9};
                    int color;
                    if (x>5&& y<3) {//right top
                        color = Team.DARK;
                    } else if (x<3&& y>6) {//left bottom
                        color = Team.LIGHT;
                    }else{//others
                        color = Team.NEUTRAL;
                    }

                    cells[x][y] = new Cell(x, y, rectF, color);
                }
            }


            //Making and drawing Chips
            darkChips = new Chip[9];
            lightChips =new Chip[9];

            for (int i = 0; i<9;i++){
                if(i==4){
                    /*The 5th one is power chip
                     *No need new because its calling method.
                     * 'Simple Factory pattern'*/
                    lightChips[i] = Chip.power(1,cells[i][i+1]);
                    darkChips[i] = Chip.power(2,cells[i][i]);


                }else{
                    lightChips[i] = Chip.normal(1,cells[i][i+1]);
                    darkChips[i] = Chip.normal(2,cells[i][i]);

                }


            }

        }//FInish first only

        //**back groud*/
        float bgLeft = 0;
        float bgTop = 0;
        bgBottom = (float)(h*0.8);

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

        /*For both of them the first line will
        * be invisible since it starts at 0 its +1 times*/
        for (int i = 0;i<11;i++){//Draw Y lines. 11 times because 10 + the last line for the bottom
            //**Horizontal Lines*/
            c.drawLine(startX,startY+(i*yWidth),w,stopY+(i*yWidth),lines);
        }
        for (int i = 0; i<10;i++){  //**Draw the X lines*/
            c.drawLine(startX+(i*xWidth),startY,startX+(i*xWidth),bgBottom,lines);

        }

        for (int i = 0 ; i<9;  i++){
            darkChips[i].draw(c);
            lightChips[i].draw(c);
        }

        for(Cell cell : legalMoves){
            cell.draw(c);
        }
    }
}
