package com.example.BlowFreeApp.Board;

import android.view.View;
import android.widget.TextView;
import com.example.BlowFreeApp.Puzzle;
import com.example.BlowFreeApp.R;

/**
 * Created with IntelliJ IDEA
 * User : Oli
 * Date : 9/23/2014
 * Time : 21:36
 */
public class ViewController {
    private View view;


    public ViewController(View view) {
        this.view = view;

    }

    public void setGameInfo(Puzzle stats) {
        // Set Lvl
        setText(R.id.lvl, "Test!");
    }

    private void setText(int id, String text) {
        TextView lvl = (TextView) view.findViewById(id);
        lvl.setText(text);
    }
}
