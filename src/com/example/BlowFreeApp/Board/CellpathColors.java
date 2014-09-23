package com.example.BlowFreeApp.Board;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kobblander on 9/22/2014.
 */
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


}
