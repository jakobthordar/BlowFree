package com.example.BlowFreeApp.Board;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains information about a particular cellpath.
 * Created by yngvi on 5.9.2014.
 */
public class Cellpath {

    private ArrayList<Coordinate> m_path = new ArrayList<Coordinate>();
    private PointButton point_a;
    private PointButton point_b;
    private Paint paintPath  = new Paint();
    private Path thisPath = new Path();
    private int color;
    private boolean finished;
    private boolean active;
    private boolean inUse;

    public Cellpath() {
    }

    public Cellpath(PointButton point_a, PointButton point_b) {
        this.point_a = point_a;
        this.point_b = point_b;
        paintPath.setStyle(Paint.Style.STROKE);
        paintPath.setStrokeWidth(32);
        paintPath.setStrokeCap(Paint.Cap.ROUND);
        paintPath.setStrokeJoin(Paint.Join.ROUND);
        paintPath.setAntiAlias(true);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Sets the cellpath at these coordinates as active.
     * @param coordinate
     */
    public void setActive(Coordinate coordinate) {
        if (point_a.getCoordinate().getCol() == coordinate.getCol() &&
                point_a.getCoordinate().getRow() == coordinate.getRow()) {
            this.active = true;
        }

        if (point_b.getCoordinate().getCol() == coordinate.getCol() &&
                point_b.getCoordinate().getRow() == coordinate.getRow()) {
            this.active = true;
        }
    }

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    /**
     * This function checks if checks if either point a or point b are
     * located at the point of coordinate.
     * @param coordinate Takes in a given coordinate.
     * @return
     */
    public boolean checkPointButtons(Coordinate coordinate) {
        if (point_a.getCoordinate().getCol() == coordinate.getCol() &&
                point_a.getCoordinate().getRow() == coordinate.getRow()) {
            return true;
        }

        if (point_b.getCoordinate().getCol() == coordinate.getCol() &&
                point_b.getCoordinate().getRow() == coordinate.getRow()) {
            return true;
        }

        return false;
    }

    /**
     * Checks if the point you are on is the end goal of the path, the coordinates
     * need to be valid to this cellpath and the PointButton at the given coordinates
     * must not also be the start pointButton.
     * @param coordinate Takes in a given coordinate.
     * @return
     */
    public boolean checkIfEnd(Coordinate coordinate) {
        if (point_a.getCoordinate().getCol() == coordinate.getCol() &&
                point_a.getCoordinate().getRow() == coordinate.getRow() &&
                !point_a.isStart()) {
            return true;
        }

        if (point_b.getCoordinate().getCol() == coordinate.getCol() &&
                point_b.getCoordinate().getRow() == coordinate.getRow() &&
                !point_b.isStart()) {
            return true;
        }

        return false;
    }

    public Paint getPaintPath() {
        return paintPath;
    }

    /**
     * Sets the PointButton at the given coordinates as start. This function
     * also needs to reset the boolean variables of point_b and point_a. This
     * function lastly needs to set this path as not finised.
     * @param coordinate Takes in a given coordinate.
     */
    public void setStart(Coordinate coordinate) {
        if (point_a.getCoordinate().getCol() == coordinate.getCol() &&
                point_a.getCoordinate().getRow() == coordinate.getRow()) {
            point_a.setStart(true);
            point_b.setStart(false);
            point_b.setEnd(false);
            this.finished = false;
        }

        if (point_b.getCoordinate().getCol() == coordinate.getCol() &&
                point_b.getCoordinate().getRow() == coordinate.getRow()) {
            point_b.setStart(true);
            point_a.setStart(false);
            point_a.setEnd(false);
            this.finished = false;
        }
    }

    public void setEnd(Coordinate coordinate) {
        if (point_a.getCoordinate().getCol() == coordinate.getCol() &&
                point_a.getCoordinate().getRow() == coordinate.getRow()) {
            point_a.setEnd(true);
        }

        if (point_b.getCoordinate().getCol() == coordinate.getCol() &&
                point_b.getCoordinate().getRow() == coordinate.getRow()) {
            point_b.setEnd(true);
        }
    }

    public void setFinished(boolean isFinished) {
        this.finished = isFinished;
        this.point_a.setStart(false);
        this.point_a.setEnd(false);
        this.point_b.setStart(false);
        this.point_b.setEnd(false);
    }

    public boolean isFinished() {
        return finished;
    }

    public int getColor() {
        return color;
    }

    public void setPaintPath(Paint paintPath) {
        this.paintPath = paintPath;
    }

    public Path getThisPath() {
        return thisPath;
    }

    public void setThisPath(Path thisPath) {
        this.thisPath = thisPath;
    }

    public void setColor(int color) {
        paintPath.setColor(color);
        this.color = color;
    }

    public void append(Coordinate co) {
        int idx = m_path.indexOf(co);
        if (idx >= 0) {
            for (int i = m_path.size()-1; i > idx; --i) {
                m_path.remove(i);
            }
        }

        else {
            m_path.add(co);
        }
    }

    public PointButton getPoint_a() {
        return point_a;
    }

    public PointButton getPoint_b() {
        return point_b;
    }

    public List<Coordinate> getCoordinates() {
        return m_path;
    }

    public void reset() {
        m_path.clear();
    }

    public boolean isEmpty() {
        return m_path.isEmpty();
    }

}
