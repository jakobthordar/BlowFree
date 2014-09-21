package com.example.BlowFreeApp.Board;

import android.graphics.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains information about a particular cellpath.
 * Created by yngvi on 5.9.2014.
 */
public class Cellpath {
    // Paths of coordinates
    private ArrayList<Coordinate> m_path = new ArrayList<Coordinate>();
    private ArrayList<Coordinate> pathIntersected = new ArrayList<Coordinate>();

    // End points
    private PointButton point_a;
    private PointButton point_b;

    // Paint Objects
    private Paint paintPath  = new Paint();
    private Paint paintHighlight = new Paint();
    private Path drawPath = new Path();

    // Path and Point color
    private int color;

    // Boolean representing path state
    private boolean finished;
    private boolean active;
    private boolean isIntersected;

    public Cellpath(PointButton A, PointButton B, int color) {
        this.point_a = A;
        this.point_b = B;
        this.color = color;

        // Set Color
        paintPath.setColor(color);
        paintHighlight.setColor(color);

        // Cell path paint settings
        paintPath.setStyle(Paint.Style.STROKE);
        paintPath.setStrokeWidth(32);

        paintPath.setStrokeCap(Paint.Cap.ROUND);
        paintPath.setStrokeJoin(Paint.Join.ROUND);
        paintPath.setAntiAlias(true);

        // Set Cell Highlight paint
        paintHighlight.setAlpha(80);
    }

    /**
     * Draws the cellpath
     * @param canvas require to draw on board
     */
    public void draw(android.graphics.Canvas canvas) {
        Point center;
        ArrayList<Coordinate> path = (isIntersected) ? pathIntersected : m_path;
        Coordinate cell = path.get(0);

        // Get and set the paths starting point
        center = Grid.getCellCenter(cell.getCol(), cell.getRow());
        drawPath.moveTo(center.x, center.y);

        for(int i = 1; i < path.size(); ++i) {
            cell = m_path.get(i);
            center = Grid.getCellCenter(cell.getCol(), cell.getRow());

            drawPath.lineTo(center.x, center.y);
        }

        canvas.drawPath(drawPath, paintPath);
    }

    /**
     * Highlight the current path
     * @param canvas require to draw on board
     */
    public void drawHighlight(android.graphics.Canvas canvas) {
        Rect cellRect;
        Coordinate cell;

        for(int i = 0; i < m_path.size(); ++i) {
            cell = m_path.get(i);
            cellRect = Grid.getCellRect(cell.getCol(), cell.getRow());

            canvas.drawRect(cellRect, paintHighlight);
        }
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
    public boolean isPathActive(Coordinate coordinate) {
        Coordinate lastPoint;

        // Check point A and B
        if(isPoint(coordinate))
            return true;

        // If the path is empty we are done
        if(m_path.isEmpty()) return false;

        lastPoint = m_path.get(m_path.size() - 1);

        // Check last point in path
        if(lastPoint.getCol() == coordinate.getCol() &&
           lastPoint.getRow() == coordinate.getRow()) {

            return true;
        }

        return false;
    }

    public boolean isIntersected() { return isIntersected; }

    public void setIntersection(boolean intersection) {
        this.isIntersected = intersection;
    }

    public void setIntersectionPath() {
        this.m_path = pathIntersected;
    }

    public boolean isIntersection(Cellpath path) {
        Coordinate intersectionPoint = null;

        for (Coordinate cell : path.m_path) {
            if (m_path.contains(cell)) {
                intersectionPoint = cell;
            }
        }

        // if intersection point is found
        if (intersectionPoint != null) {
            pathIntersected = getIntersectionPath(intersectionPoint);
            return true;
        }

        return false;
    }

    private ArrayList<Coordinate> getIntersectionPath(Coordinate intersect) {
        ArrayList<Coordinate> sectionPath = (ArrayList<Coordinate>) m_path.clone();
        Coordinate cell;

        for(int i = 0; i < m_path.size(); i++) {
            cell = sectionPath.get(sectionPath.size() - 1);
            sectionPath.remove(sectionPath.size() - 1);

            if(cell.equals(intersect)) {
                break;
            }
        }

        return sectionPath;
    }

    /**
     * This function checks if checks if either point a or point b are
     * located at the point of coordinate.
     * @param coordinate Takes in a given coordinate.
     * @return
     */
    public boolean isPoint(Coordinate coordinate) {
        if(point_a.getCoordinate().getCol() == coordinate.getCol() &&
            point_a.getCoordinate().getRow() == coordinate.getRow()) {

            return true;
        }

        if(point_b.getCoordinate().getCol() == coordinate.getCol() &&
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
            this.finished = false;
        }

        if (point_b.getCoordinate().getCol() == coordinate.getCol() &&
            point_b.getCoordinate().getRow() == coordinate.getRow()) {

            point_b.setStart(true);
            point_a.setStart(false);
            this.finished = false;
        }
    }

    public void setFinished(boolean isFinished) {
        this.finished = isFinished;

        this.point_a.setStart(false);
        this.point_b.setStart(false);
    }

    public boolean isFinished() {
        return finished;
    }

    public int getColor() {
        return color;
    }

    public Path getThisPath() {
      return drawPath;
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
        ArrayList<Coordinate> path = (isIntersected) ? pathIntersected : m_path;

        return path.size() <= 1;
    }
}
