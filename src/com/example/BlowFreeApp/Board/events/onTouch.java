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
            if(cp.isIntersected())
                cp.setIntersectionPath();

            cp.setActive(false);
            cp.setIntersection(false);
        }

        view.invalidate();
    }

    public void touchMove(Coordinate cell, List<Cellpath> paths) {
        List<Coordinate> coordinateList;
        Coordinate last;

        for (Cellpath cp : paths) {
            if(isValidMove(cp, paths, cell)) {

                if (cp.checkIfEnd(cell))
                    cp.setFinished(true);

                coordinateList = cp.getCoordinates();
                last = coordinateList.get(coordinateList.size() - 1);

                if (isNeighbours(last, cell)) {
                    cp.append(new Coordinate(cell.getCol(), cell.getRow()));
                    view.invalidate();
                }

                setIntersection(cp, paths);
            }
        }
    }

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
               !isInPath(move, path, paths);     // 4. Touched cell is path
    }

    /**
     * Checks is the two points are adjacent
     * @param cellA Cell A
     * @param cellBl B
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