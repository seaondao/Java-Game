package edu.byuh.cis.cs300.hello;

import java.lang.reflect.Array;
import java.util.ArrayList;

public enum Team {
    NEUTRAL,
    LIGHT,
    DARK;

//    public static final int NEUTRAL =0;
//    public static final int LIGHT = 1;
//    public static final int DARK = -1;

//    public static int[] teams = {LIGHT,DARK};

//    public static int getRandomTeam(){
//        return teams[((int) ((Math.random() * 2)))];
//    }

    public static String getName(int i){
        if(i==1){
            return "Light";
        }else{
            return "Dark";
        }
    }

}

