package com.example.BlowFreeApp.Board;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;

/**
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

    public void setStart(Coordinate coordinate) {
        if (point_a.getCoordinate().getCol() == coordinate.getCol() &&
                point_a.getCoordinate().getRow() == coordinate.getRow()) {
            point_a.setStart(true);
        }

        if (point_b.getCoordinate().getCol() == coordinate.getCol() &&
                point_b.getCoordinate().getRow() == coordinate.getRow()) {
            point_b.setStart(true);
        }
    }

    public void setEnd(Coordinate coordinate) {
        if (point_a.getCoordinate().getCol() == coordinate.getCol() &&
                point_a.getCoordinate().getRow() == coordinate.getRow()) {
            point_a.setStart(true);
        }

        if (point_b.getCoordinate().getCol() == coordinate.getCol() &&
                point_b.getCoordinate().getRow() == coordinate.getRow()) {
            point_b.setStart(true);
        }
    }

    public void setFinished(boolean isFinished) {
        this.finished = isFinished;
        point_a.setStart(false);
        point_b.setStart(false);
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
