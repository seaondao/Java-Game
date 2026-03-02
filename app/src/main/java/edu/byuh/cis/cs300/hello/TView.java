package edu.byuh.cis.cs300.hello;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Message;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

//For debugging
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class TView extends View {

    //**Variables for the back groud, and lines*/
    private static final String TAG = "TView";
    //field declarations
    private final Paint bg;
    private final Paint lGb;
    private final Paint rGb;
    private final Paint lines;
    private final Paint redball;
    private final Paint textP;

    private Boolean firstDraw = true;

    //Below here is test variables


    private ArrayList<Chip> allChips;

    private Cell[][] cells;

    private RectF rectF;
    ArrayList<Cell> legalMoves = new ArrayList<>();

    boolean power = false;//The chip movingChip is a power or not.

    Chip movingChip = null;//This is the Chip that is selected.
    public boolean moving = false;

    boolean unclick = true;

    private Timer timer;

    //IMAGE
    private Bitmap undoImg;
    private RectF undoRect;

    private Bitmap duckImg;
    private RectF duckRect;


    //Stack
    private Stack<Move> undoStack = new Stack<>();
    Toast undoMsg;
    private boolean undoed = false;//Check if undo happened or not.
    private int currentPlayer;

    private int winner;
    private boolean animated = false;

    public class Timer extends Handler{
        public Timer(){sendMessageDelayed(obtainMessage(),10);}

        @Override
        public void handleMessage(Message m){
            if(movingChip != null){
                animated = movingChip.animate();
            }
            invalidate();
            if(animated){
                checkForWinner();
                animated = false;
            }
            sendMessageDelayed(obtainMessage(),10);
        }
    }

    public boolean undoLastMove(){
        /*
            When Undo is pressed move a little to the bottom just for fun
       *Everytime some where that dose not have a chip was clicked it will unselect the chip so
       didn't add that here.
            1. Check if undoStack is empty or not if not empty, pop and save that as variable and go next
            2. movingChip will be the chip that is in newest undoStack destination
            3. That chip's destination becomes where it was before.
            4. Animate that.
         */

        undoRect.offset(0,undoRect.height()*0.1f);
        if (!undoStack.empty()){
            Move m = undoStack.pop();
            movingChip = m.destination.chip;
            movingChip.setDestination(m.before);
            movingChip.animate();
            currentPlayer *= -1;//Swich back the current team
        }else{//When undoStack is empty
            undoMsg.show();
        }
        return true;
    }


    public TView(Context c) {
        super(c);
        timer = new Timer();
        //Creating undo img
        undoImg = BitmapFactory.decodeResource(getResources(), R.drawable.undo);
        duckImg = BitmapFactory.decodeResource(getResources(), R.drawable.duck);

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
        redball = new Paint();
        redball.setColor(Color.RED);
        textP = new Paint();
        textP.setColor(Color.BLACK);
        textP.setTextSize(90);
        textP.setTextAlign(Paint.Align.CENTER);
        textP.setStyle(Paint.Style.FILL_AND_STROKE);

        //**Class thing for the text that show up*/ Toast example
        undoMsg = Toast.makeText(c,"No more UNDO to do ", Toast.LENGTH_LONG);
        firstDraw = true;


    }

    @Override
    public boolean onTouchEvent(MotionEvent m){


        if(m.getAction()==MotionEvent.ACTION_DOWN){
            int x = (int) m.getX();
            int y = (int) m.getY();

            /*
            This Part check IF the Click was made in the Chip or Not
                1. If the click is in any of the Chip "unclick" = false
                2. If unclick is true until end, it goes to $if(unclick)
             */
            unclick = true;//Unclick Means cell was clicked and not the Chip

            for(Chip chip : allChips){// Loop throw to find if the click happend in the Chip.
                //DEBIUG HERE FOR WINNING IF
                if(chip.contains(x,y)){//&&chip.colorNum == currentPlayer
                    movingChip = chip.setSelected(); // set new moving chip
                    power = movingChip.power;
                    unclick = false;
                }
            }

            /*
                $if(unclick)
                    Reset all(all fields that used for Moving Chip)
                If Chip was clicked
                    Check Moveable Position and save it to
             */

            boolean skip = false; //It will start moving if legal cell is clicked(true).
            if(unclick){//Empty Cell was clicked Reset all.
                if(movingChip != null){//Chip is selected and a cell was cliked

                    for (Cell cell:legalMoves){//Loop throw the legal moves.
                        if(cell.contains(x,y)){//Legal cell was selected.
                            //add Move of (before, after) of where the chip moved.
                            undoStack.push(new Move(movingChip.getCell(), cell));
                            movingChip.setDestination(cell);
                            skip = true;
                            currentPlayer*= -1;
                        }
                    }
                }
                if(!skip){
                    movingChip = null;
                    Chip.reset();
                }


            }else{
                //Main Idea! check move able spots
                checkMoveable(movingChip, power);
            }

            if(undoRect.contains(x,y)){
                undoLastMove();
                undoed = true;//this is just to make the undo botton move.
            }
            if(duckRect.contains(x,y)){
                cheatCommand();
            }

            //Here is when it draw the Legal moves.


        }
        if(m.getAction()==MotionEvent.ACTION_UP&&undoed){//this is just to make the undo botton move.
            //Undo happened and mouse up == make the undo botton location back.
            undoRect.offset(0,-undoRect.height()*0.1f);
            undoed = false;
        }
        invalidate();
        //Lets get which sell it belongs too.

//        checkForWinner();
        if(winner!=0){
            var alert = new AlertDialog.Builder(getContext());
            alert.setTitle("Congratuation Winner is : " + Team.getName(winner))
                    .setCancelable(false)
                    .setPositiveButton("Restart", (dialogInterface, i) -> firstDraw = true)
                    .setNegativeButton("Quit", (dialogInterface, i) -> ((Activity)getContext()).finish());
            AlertDialog box = alert.create();
            box.show();
        }
        return true;

    }

    private void checkForWinner() {
        int light = 0;
        int dark =0;

        for (Chip c : allChips) {//If the team(cell that fills firsts,s opposite team is the winner
            if(c.colorNum==1){
                light+=c.getCell().getColor();

            }else if(c.colorNum == -1){
                dark += c.getCell().getColor();
            }
        }
       if(light==-9){//Blue(Team Light win)
           Log.d(TAG, "Winner is Team Blue: ");
           winner = 1;

       } else if (dark == 9) {
           Log.d(TAG, "Winner is Team Green: ");
           winner = -1;
       }


    }

    /*
    *** Only When Chip is clicked call this
    * 1. legalMoves Array list get Reset Everytime
    * 2. Both Power and Non Power chip will Check Vertically and Horizontally
    *   Power will save all possible candidate, Non P will only save the last one.
    * 3. If Power Check Diagnol too
    *
     */
    public void checkMoveable(Chip movingChip, boolean power){


        legalMoves.clear();//Clear everytime before looking for a moveable spot
        int selectedCellX = movingChip.getCell().getX();
        int selectedCellY = movingChip.getCell().getY();


        Cell candidate = null;



        for(int i = selectedCellX+1; i< 9;i++){//Check Right
            if (cells[i][selectedCellY].isLegalMove(movingChip)) {
                if (!power){
                    candidate = cells[i][selectedCellY];
                }else{//If Power chip add all leagal moves
                    legalMoves.add(cells[i][selectedCellY]);
                }

            }else{
                break;
            }
        }
        if(candidate != null){
            legalMoves.add(candidate);//Most right side that is able to go.
        }

        //Left
        candidate = null;
        for(int i = selectedCellX-1; i>=0;i--){
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
            legalMoves.add(candidate);
        }

        //Down
        candidate = null;
        for(int i = selectedCellY+1; i< 10;i++){
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
            legalMoves.add(candidate);
        }
        //Top
        candidate = null;
        for(int i = selectedCellY-1; i>=0;i--){
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
            legalMoves.add(candidate);
        }

        if(power){//Check diagonal //Right UP
            int i = selectedCellX+1;
            int j = selectedCellY-1;
            while (i<9 && j>=0 ){
                if(cells[i][j].isLegalMove(movingChip)){
                    legalMoves.add(cells[i][j]);
                }else{
                    break;
                }
                i++;
                j--;
            }//Left Down
            i = selectedCellX-1;
            j = selectedCellY+1;
            while (i>=0 && j<=9 ){
                if(cells[i][j].isLegalMove(movingChip)){
                    legalMoves.add(cells[i][j]);
                }else{
                    break;
                }
                i--;
                j++;
            }
            //Left Top
            i = selectedCellX-1;
            j = selectedCellY-1;
            while (i>=0 && j>=0 ){
                if(cells[i][j].isLegalMove(movingChip)){
                    legalMoves.add(cells[i][j]);
                }else{
                    break;
                }
                i--;
                j--;
            }
            //Right Down
            i = selectedCellX+1;
            j = selectedCellY+1;
            while (i<9 && j<=9 ){
                if(cells[i][j].isLegalMove(movingChip)){
                    legalMoves.add(cells[i][j]);
                }else{
                    break;
                }
                i++;
                j++;
            }

        }
    }

    public void cheatCommand(){
        //Make one of the team win;
        Log.d(TAG, "cheatCommand: ");
        allChips.forEach(s -> { s.setDestination(cells[0][0]);
        s.animate();});
        invalidate();
    }

    @Override//**Main drawing part*/
    public void onDraw(Canvas c) {
        c.drawColor(Color.GREEN);
        float w = getWidth();
        float h = getHeight();

        //**Only when it loads for the first time*/
        if (firstDraw) {
            lines.setStrokeWidth(w / 100);
            firstDraw = false;
            /*Making invisible cells.*/
            float xWidth = w / 9f; //Imagine 20px
            float yWidth = (h * 0.8f) / 10f; //30px

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

                    float[] lightX = {7, 8, 9};
                    int color;
                    if (x > 5 && y < 3) {//right top
                        color = Team.DARK;
                    } else if (x < 3 && y > 6) {//left bottom
                        color = Team.LIGHT;
                    } else {//others
                        color = Team.NEUTRAL;
                    }

                    cells[x][y] = new Cell(x, y, rectF, color);
                }
            }


            //Making and drawing Chips
            allChips = new ArrayList<>();

            int x=0;
            //Making all chips array with first 8 is black and the rest is light
            for (int i = 0; i < 18; i++){

                if(i<9){
                    if(i==4){
                        allChips.add(Chip.power(Team.DARK, cells[i][i]));
                    }else{
                        allChips.add(Chip.normal(Team.DARK, cells[i][i]));
                    }
                }else{
                    if(x==4){
                        allChips.add(Chip.power(Team.LIGHT, cells[x][x + 1]));
                    }else{
                        allChips.add(Chip.normal(Team.LIGHT, cells[x][x + 1]));
                    }
                    x++;
                }
            }
            //UNDO IMG
            int undoSize = (int) (w * 0.15f);
            undoImg = Bitmap.createScaledBitmap(undoImg, undoSize, undoSize, true);
            undoRect = new RectF(w * 0.8f, h * 0.85f,w * 0.8f+w * 0.15f,h * 0.85f +w * 0.15f);

            duckImg = Bitmap.createScaledBitmap(duckImg, undoSize, undoSize, true);
            duckRect = new RectF(w * 0.05f, h * 0.85f,w * 0.05f+w * 0.15f,h * 0.85f +w * 0.15f);


            //End game
            currentPlayer = Team.getRandomTeam();
            Log.d(TAG, "onDraw: "+ currentPlayer);
            winner = 0;

        }//FInish first only

        //**back groud*/
        float bgLeft = 0;
        float bgTop = 0;
        float bgBottom = (float) (h * 0.8);

        c.drawRect(bgLeft, bgTop, w, bgBottom, bg);
        //**Variables for -----XY lines----*/
        float startX = 0;
        float startY = 0;
        float stopY = 0;
        float yWidth = bgBottom / 10;
        float xWidth = w / 9;

        //**Box Rect*/
        c.drawRect(xWidth * 6, bgTop, w, yWidth * 3, rGb);
        c.drawRect(startX, yWidth * 7, xWidth * 3, bgBottom, lGb);

        /*For both of them the first line will
         * be invisible since it starts at 0 its +1 times*/
        for (int i = 0; i < 11; i++) {//Draw Y lines. 11 times because 10 + the last line for the bottom
            //**Horizontal Lines*/
            c.drawLine(startX, startY + (i * yWidth), w, stopY + (i * yWidth), lines);
        }
        for (int i = 0; i < 10; i++) {  //**Draw the X lines*/
            c.drawLine(startX + (i * xWidth), startY, startX + (i * xWidth), bgBottom, lines);

        }

        for(Chip chip : allChips){
            chip.draw(c);
        }

        //Loop cell that is leagal move and draw on that cell
        for (Cell cell : legalMoves) {
            cell.draw(c);
        }

        c.drawRect(undoRect, lines);
        c.drawBitmap(undoImg, undoRect.left, undoRect.top, null);

        c.drawRect(duckRect,lines);
        c.drawBitmap(duckImg, duckRect.left, duckRect.top, null);


        //End game
        c.drawText( Team.getName(currentPlayer) + "Team's turn.", w/2,h*0.85f,textP);

    }






    }

