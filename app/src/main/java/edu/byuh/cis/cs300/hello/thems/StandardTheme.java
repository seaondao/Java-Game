package edu.byuh.cis.cs300.hello.thems;

import android.graphics.Color;

public class StandardTheme implements Theme {
    @Override
    public int getBackgrounfColor() {
        return Color.rgb(181,101,29);
    }

    @Override
    public int getNutualColor() {
        return Color.rgb(210, 180, 140);
    }

    @Override
    public int getTeamOneColor() {
        return Color.rgb(110, 70, 35);
    }


    @Override
    public int getTeamtwoColor() {
        return Color.rgb(175, 125, 85);
    }

}
