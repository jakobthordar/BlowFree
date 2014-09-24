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
        setGameLevel(false);
    }

    private void navForward(View v) {
        setGameLevel(true);
    }

    public void setGameLevel(boolean forward) {
        // Set New Active Game
        int id = PackLevelFactory.getActiveGame().getPuzzleId();

        // Forward or backwards
        id = (forward) ? id  : id - 2;

        Puzzle newGame = getGame(id);

        // If game was not found we do nothing
        if(newGame == null) return;

        // Set the new game as active
        PackLevelFactory.setActiveGame(newGame);

        // Create and then rest the canvas
        Canvas canvas = (Canvas) findViewById(R.id.board);
        canvas.create();
        canvas.reset();

        // Update layout text
        initGameView(newGame);

    }

    private Puzzle getGame(int id) {
        String type = PackLevelFactory.getActiveGame().getTableGameStatus();

        // Get Easy game
        if(type == "gameStatusEasy")
            return PackLevelFactory.getEasyGame(id);

        // Get Medium game
        if(type == "gameStatusMedium")
            return PackLevelFactory.getMediumGame(id);

        // Get Hard game
        if(type == "gameStatusHard")
            return PackLevelFactory.getHardGame(id);

        return null;
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
