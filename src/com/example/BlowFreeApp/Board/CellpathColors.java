package com.example.BlowFreeApp.Board;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;


public class CellpathColors {
    private int green = Color.GREEN;
    private int red = Color.RED;
    private int blue = Color.BLUE;
    private int yellow = Color.YELLOW;
    private int cyan = Color.CYAN;
    private int magenta = Color.MAGENTA;
    private List<Integer> colors = new ArrayList<Integer>();

    private static CellpathColors instance = new CellpathColors();

    private CellpathColors() {
        colors.add(green);
        colors.add(red);
        colors.add(blue);
        colors.add(yellow);
        colors.add(cyan);
        colors.add(magenta);
    }

    public static CellpathColors getInstance() {
        return instance;
    }

    public void setColorsForCellPaths(List<Cellpath> cellpaths) {
        for (int i = 0; i < cellpaths.size(); i++) {
            cellpaths.get(i).setColor(colors.get(i));
        }
    }
    public void setColorsTheme1(){

        List<Integer> i = new ArrayList<Integer>();

        int pastelGreen = Color.rgb(74,236,102);
        int pastelPurple = Color.rgb(109,77,127);
        int pastelYellow = Color.rgb(252,252,85);
        int pastelPink = Color.rgb(252,85,85);
        int pastelBlue = Color.rgb(76,79,175);
        int pastelOrange = Color.rgb(255,129,0);
        i.add(pastelBlue);
        i.add(pastelGreen);
        i.add(pastelOrange);
        i.add(pastelPink);
        i.add(pastelYellow);
        i.add(pastelPurple);
        colors = i;

    }
    public void restoreColorTheme()
    {
        List<Integer> i2 = new ArrayList<Integer>();

        i2.add(green);
        i2.add(red);
        i2.add(blue);
        i2.add(yellow);
        i2.add(cyan);
        i2.add(magenta);
        colors = i2;
    }


}
