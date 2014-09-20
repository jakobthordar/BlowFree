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
    private boolean isEnd;

    public PointButton(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.isStart = false;
        this.isEnd = false;
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

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }
}
