package com.example.BlowFreeApp;


import com.example.BlowFreeApp.Board.Cellpath;
import com.example.BlowFreeApp.activities.Game;

import java.util.List;

public class Puzzle {
    private String name;
    private int challengeId;
    private int puzzleId;
    private int size;
    private int moves, bestMove;
    private List<Cellpath> cellPaths;


    public Puzzle(String name, int challangeId, int puzzleId, int size,
                  List<Cellpath> cellPaths) {
        this.name = name;
        this.challengeId = challangeId;
        this.puzzleId = puzzleId;
        this.size = size;
        this.cellPaths = cellPaths;
        this.moves = 0;
        this.bestMove = Integer.MAX_VALUE;
    }

    public String getName() {
        return name;
    }

    public int getPuzzleId() {
        return puzzleId;
    }

    public int getSize() {
        return size;
    }

    public List<Cellpath> getCellPaths() {
        return cellPaths;
    }

    @Override
    public String toString() {
        return "Level: " + this.name + ", Puzzle: " + this.puzzleId;
    }

    public int getChallengeId() {
        return challengeId;
    }

    public void addMoves() {
        Game activeGame = PackLevelFactory.getGameActivity();

        moves++;

        activeGame.setText(R.id.moveCount, Integer.toString(moves));
    }

    public void setFlow(int conntections) {
        Game activeGame = PackLevelFactory.getGameActivity();

        activeGame.setText(R.id.connectCount, Integer.toString(conntections) + "/" + Integer.toString(cellPaths.size()));
    }

    public void reset() {
        Game activeGame = PackLevelFactory.getGameActivity();

        // Init connections
        setFlow(0);

        // Init moves
        moves = 0;
        activeGame.setText(R.id.moveCount, "0");
    }
}
