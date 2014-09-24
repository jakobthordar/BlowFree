package com.example.BlowFreeApp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.BlowFreeApp.Board.Canvas;
import com.example.BlowFreeApp.PackLevelFactory;
import com.example.BlowFreeApp.Puzzle;
import com.example.BlowFreeApp.R;
public class Game extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Puzzle stats = PackLevelFactory.getActiveGame();
        PackLevelFactory.setGameActivity(this);

        // Set Init game viw
        initGameView(stats);

        // Register callbacks for buttons
        Button navButton = (Button) findViewById(R.id.back_button);
        navButton.setOnClickListener(new View.OnClickListener() { public void onClick(View v) { navBack(v); }});
        navButton = (Button) findViewById(R.id.forward_button);
        navButton.setOnClickListener(new View.OnClickListener() { public void onClick(View v) { navForward(v); }});
        navButton = (Button) findViewById(R.id.reset);
        navButton.setOnClickListener(new View.OnClickListener() { public void onClick(View v) { navRest(v); }});
    }

    private void initGameView (Puzzle stats) {
        // Set Lvl
        setText(R.id.lvl, "Level " + stats.getPuzzleId());

        // Set Size
        setText(R.id.boardSize, stats.getSize() +  "x" + stats.getSize());

        // Init Flows counter
        setText(R.id.connectCount, "0/" + Integer.toString(stats.getCellPaths().size()));
    }

    private void navBack(View v) {
        Puzzle game = PackLevelFactory.getActiveGame();
        int id = game.getPuzzleId();
        Puzzle newGame = PackLevelFactory.getGameById(id - 1);

        PackLevelFactory.setActiveGame(newGame);

        Canvas canvas = (Canvas) findViewById(R.id.board);
        canvas.create();
    }

    private void navForward(View v) {

    }

    private void navRest(View v) {
        Puzzle game = PackLevelFactory.getActiveGame();
        Canvas canvas = (Canvas) findViewById(R.id.board);
        canvas.reset();

    }

    public void setText(int id, String text) {
        TextView element = (TextView) findViewById(id);
        element.setText(text);
    }
}
