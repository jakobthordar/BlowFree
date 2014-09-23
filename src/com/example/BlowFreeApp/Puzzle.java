package com.example.BlowFreeApp;



import com.example.BlowFreeApp.Board.Cellpath;
import com.example.BlowFreeApp.Board.Coordinate;

import java.util.List;

public class Puzzle {
    private String name;
    private int challengeId;
    private int puzzleId;
    private int size;
    private List<Cellpath> cellPaths;


    public Puzzle(String name, int challangeId, int puzzleId, int size,
                  List<Cellpath> cellPaths) {
        this.name = name;
        this.challengeId = challangeId;
        this.puzzleId = puzzleId;
        this.size = size;
        this.cellPaths = cellPaths;
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
}
