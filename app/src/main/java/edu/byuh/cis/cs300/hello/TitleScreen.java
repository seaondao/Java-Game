package edu.byuh.cis.cs300.fun;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

import edu.byuh.cis.cs300.hello.MainActivity;
import edu.byuh.cis.cs300.hello.R;


public class TitleScreen extends Activity {
    private ImageView iv;
    private ImageView ivStart;
    private ImageView ivSetting;
    private ImageView ivQuestion;


    @Override
    public void onCreate(Bundle b){
        super.onCreate(b);
        iv = new ImageView(this);
        iv.setImageResource(R.drawable.splash);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);

        ivStart = new ImageView(this);
        ivStart.setImageResource(R.drawable.splash_s);
        ivStart.setScaleType(ImageView.ScaleType.FIT_XY);

        ivQuestion = new ImageView(this);
        ivQuestion.setImageResource(R.drawable.splash_q);
        ivQuestion.setScaleType(ImageView.ScaleType.FIT_XY);

        ivSetting = new ImageView(this);
        ivSetting.setImageResource(R.drawable.splash_set);
        ivSetting.setScaleType(ImageView.ScaleType.FIT_XY);

        setContentView(iv);
    }
    @Override
    public boolean onTouchEvent(MotionEvent m){

        float w = iv.getWidth();
        float h = iv.getHeight();
        float x = m.getX();
        float y = m.getY();
        if(m.getAction() == MotionEvent.ACTION_UP){
            setContentView(iv);
            //User tapped top 20% part
            if(y<h*0.2f){ //Question Or Setting
                if(x<w*0.5f){
                    //open alert
                    AlertDialog.Builder ab = new AlertDialog.Builder(this);
                    ab.setTitle("About Smile tap")
                            .setMessage("From the instruction sheet:\n" +
                                    "\"EQUIPMENT: 1 playing board with 90 squares, including two corners of 9 squares each. 18 chips (9 dark and 9 light), one chip on each side has a dot on it, called the \"power chip\".\n" +
                                    "\n" +
                                    "OBJECT: To be the first to slide all nine of your chips into your own corner of the board.\"\n" +
                                    "\n" +
                                    "The chips are setup in the middle of the board at start. Each player plays alternately one chip in one direction only. a regular chip may move horizontally or vertically only. A power chip may move also diagonally. A regular chip must slide as far as it can go. Stopped only by reaching the edge of the board, another chip or the opponent's corner. A power chip can stop whenever it wants, but must stop for the same reasons as a regular chip.\n" +
                                    "\n" +
                                    "Once inside its own corner, no chip can move back into the playing area. A regular chip must move horizontally or vertically as far as it can within the corner, a power chip may move any number of squares, within the corner.\n" +
                                    "\n" +
                                    "Similar to Aztec Conquest, but in those versions, pieces can only move horizontally or vertically. No power chips to allow diagonal movement or stopping early. Aztec Conquest also allows you to move in and out of your corner freely, while Outwit has a rule that you may not leave your corner once you enter it.")
                            .setNeutralButton("OK",null);
                    AlertDialog box = ab.create();
                    box.show();
                }else{//Open setting

                }
            }else if(y > h*0.67f){
                //TODO open main activity
                Intent matt = new Intent(this, MainActivity.class);
                startActivity(matt);
                finish();
            }
        } else if (m.getAction() == MotionEvent.ACTION_DOWN) {//Click ABle
            if(y<h*0.2f){//Question Or Setting
                if(x<w*0.5f){//Question
                    setContentView(ivQuestion);
                }else{//Open setting
                    setContentView(ivSetting);
                }
            }else if(y > h*0.67f){//TODO open main activity
                setContentView(ivStart);
            }
        }

        return true;

    }

}
