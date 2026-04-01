package edu.byuh.cis.cs300.hello.thems;

import android.graphics.Color;

public class DarkTheme implements Theme {
    @Override
    public int getBackgrounfColor() {
        return Color.rgb(34,34,34);
    }

    @Override
    public int getNutualColor() {
        return Color.GRAY;
    }

    @Override
    public int getTeamOneColor() {
        return Color.LTGRAY;
    }

    @Override
    public int getTeamtwoColor() {
        return Color.DKGRAY;
    }
}
