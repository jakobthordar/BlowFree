package com.example.BlowFreeApp.Board.events;

import android.view.View;
import com.example.BlowFreeApp.Board.Cellpath;
import com.example.BlowFreeApp.Board.Coordinate;
import com.example.BlowFreeApp.PackLevelFactory;
import com.example.BlowFreeApp.sound.SoundPlayer;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * User : Oli
 * Date : 9/21/2014
 * Time : 02:35
 */
public class onTouch {
    private View view;
    
    public onTouch(View board) {
        this.view = board;
    }

    /**
     * Fires on touch down
     * @param cell Cell touched down
     * @param paths All paths on the board
     */
    public void touchDown(Coordinate cell, List<Cellpath> paths) {
        for (Cellpath cp : paths) {
            cp.setActive(cp.isPathActive(cell));

            // Point A or B was touched
            if (cp.isPoint(cell)) {
                // Set path as unfinished
                cp.setFinished(false);

                // Rest and add new cell
                cp.reset();
                cp.append(cell);

                // Set point A or B as start
                cp.setStart(cell);
            }
        }

        // Force update the canvas
        view.invalidate();
    }

    /**
     * Fires on touchUp
     * @param paths All paths on the board
     */
    public void touchUp(List<Cellpath> paths) {

        // Remove active on touchUp
        for (Cellpath cp : paths) {
            if(cp.isIntersected())
                cp.setIntersectionPath();

            cp.setActive(false);
            cp.setIntersection(false);
        }

        view.invalidate();
    }

    /**
     * Fires on touchMove
     * @param cell Cell touched on
     * @param paths All paths on the board
     */
    public void touchMove(Coordinate cell, List<Cellpath> paths) {
        List<Coordinate> coordinateList;
        Coordinate last;

        for (Cellpath cp : paths) {
            if(isValidMove(cp, paths, cell)) {
                coordinateList = cp.getCoordinates();
                last = coordinateList.get(coordinateList.size() - 1);

                if (isNeighbours(last, cell)) {
                    cp.append(new Coordinate(cell.getCol(), cell.getRow()));

                    if (cp.checkIfEnd(cell)) {
                        cp.setFinished(true);
                        // there where sound issues of we dont new sound class each time
                        SoundPlayer p = new SoundPlayer(view.getContext());
                        p.playConnect();
                    }

                    setIntersection(cp, paths);
                    view.invalidate();
                }
            }
        }
    }

    /**
     * Set the path intersection
     * @param path Current path being moved
     * @param paths All board paths
     */
    private void setIntersection(Cellpath path, List<Cellpath> paths) {
        for(Cellpath cp : paths) {
            // Ignore path that is active
            if(cp.equals(path)) continue;

            cp.setIntersection(cp.isIntersection(path));
        }
    }

    /**
     * Checks if next move is valid
     * @param path Path begin moved
     * @param paths All paths on the board
     * @param move Move being validate
     * @return if move is valid
     */
    private boolean isValidMove(Cellpath path, List<Cellpath> paths, Coordinate move) {
                                                 // Move is Valid if :
        return  path.isActive()               && // 1. Path is active
               !path.isFinished()             && // 2. Path is not finished
               !isInPath(move, path, paths);     // 3. Touched cell is path
    }

    /**
     * Checks is the two points are adjacent
     * @param cellA Cell A
     * @param cellB Cell B
     * @return if cells are neighbours
     */
    private boolean isNeighbours(Coordinate cellA, Coordinate cellB) {
        return Math.abs(cellA.getCol() - cellB.getCol()) + Math.abs(cellA.getRow() - cellB.getRow()) == 1;
    }

    /**
     * Checks if cell path exists in paths
     * @param cell Current cell
     * @param path Current path
     * @param paths All cellpath
     * @return if in path
     */
    private boolean isInPath(Coordinate cell, Cellpath path, List<Cellpath> paths) {
        for (Cellpath cp : paths) {
            if (!cp.equals(path)) {
                if(cp.isPoint(cell)) {
                    return true;
                }
            }
        }

        return false;
    }
}