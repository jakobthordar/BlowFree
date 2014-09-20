package com.example.BlowFreeApp.Board;

/**
 * <p></p>
 *
 * @author jakob
 *         Created on 14.9.2014.
 */
public class PointButton {
    public Coordinate coordinate;
    private boolean isStart;

    public PointButton(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.isStart = false;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean isStart) {
        this.isStart = isStart;
    }
}
