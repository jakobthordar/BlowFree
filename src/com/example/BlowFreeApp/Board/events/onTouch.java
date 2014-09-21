package com.example.BlowFreeApp.Board.events;

import android.view.View;
import com.example.BlowFreeApp.Board.Cellpath;
import com.example.BlowFreeApp.Board.Coordinate;

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

    public void touchUp(List<Cellpath> paths) {

        // Remove active on touchUp
        for (Cellpath cp : paths) {
            if (cp.isActive()) {
                cp.setActive(false);
            }
        }

    }

    public void touchMove(Coordinate cell, List<Cellpath> paths) {
        List<Coordinate> coordinateList;
        Coordinate last;

        for (Cellpath cp : paths) {
            if (isValidMove(cp, paths, cell)) {

                if (cp.checkIfEnd(cell))
                    cp.setFinished(true);

                coordinateList = cp.getCoordinates();
                last = coordinateList.get(coordinateList.size() - 1);

                if (isNeighbours(last.getCol(), last.getRow(), cell.getCol(), cell.getRow())) {
                    cp.append(new Coordinate(cell.getCol(), cell.getRow()));
                    view.invalidate();
                }
            }
        }
    }

    private boolean isValidMove(Cellpath path, List<Cellpath> paths, Coordinate cell) {
                                                // Move is Valid if :
        return path.isActive()               && // 1. Path is active
               !path.isFinished()            && // 2. Path is not finished
               !path.isEmpty()               && // 3. Path is not empty
               !isInPath(cell, path, paths);    // 4. Touched cell is path
    }

    /**
     * Checks is the two points are adjacent
     * @param c1 Point C x
     * @param r1 Point R x
     * @param c2 Point C y
     * @param r2 Point R y
     * @return if points are neighbours
     */
    private boolean isNeighbours(int c1, int r1, int c2, int r2) {
        return Math.abs(c1-c2) + Math.abs(r1-r2) == 1;
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
