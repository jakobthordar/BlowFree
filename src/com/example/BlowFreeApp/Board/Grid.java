package com.example.BlowFreeApp.Board;

import android.graphics.*;

/**
 * Created with IntelliJ IDEA
 * User : Oli
 * Date : 9/19/2014
 * Time : 22:28
 */
public class Grid {
    private static Paint paint  = new Paint();
    private Rect cell = new Rect();

    // Board width x height
    private static int width, height;

    // Cell size in pixels
    private static int cellWidth, cellHeight;

    // Padding
    private static int padTop, padBottom, padRight, padLeft;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;

        // Border
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GRAY);
    }

    /**
     * Draws the grid
     * @param canvas required to draw
     */
    public void draw(android.graphics.Canvas canvas){
        for (int w = 0; w < width; w++) {
            for(int h = 0; h < height; h++) {
                cell.set(w * cellWidth, h * cellHeight, (w + 1) * cellWidth, (h + 1) * cellHeight);

                canvas.drawRect(cell, paint);
            }
        }
    }

    /**
     * Sets the grid size
     * @param w new board width
     * @param h new board height
     */
    public void setSize(int w, int h) {
        int strokeWidth = Math.max(1, (int) paint.getStrokeWidth()) * 4;

        cellWidth = (w - padLeft - padRight + strokeWidth) / width;
        cellHeight = (h - padTop - padBottom + strokeWidth) / height;
    }

    /**
     * Sets the grid padding
     * @param top Top padding
     * @param bottom Bottom padding
     * @param right Right padding
     * @param left Left padding
     */
    public void setPadding(int top, int bottom, int right, int left) {
        // Set top and bottom padding
        padTop = top;
        padBottom = bottom;

        // Set left and right padding
        padRight = right;
        padLeft = left;
    }

    /**
     * Returns cell center point
     * @param x cell number index
     * @param y row number index
     * @return Point
     */
    public static Point getCellCenter(int x, int y) {
        return new Point(getColumnPos(x) + cellWidth / 2, getRowPos(y) + cellHeight / 2);
    }

    /**
     * Returns cell rect in pixels according to point size
     * @param x cell number index
     * @param y row number index
     * @return returns the cell as rectangle
     */
    public Rect getCellPoint(int x, int y, int padding) {
        Rect cell = getCellRect(x, y);

        cell.set(cell.left + padding, cell.top + padding, cell.right - padding, cell.bottom - padding);

        return cell;
    }

    /**
     * Returns cell rect in pixels according to point size
     * @param x cell number index
     * @param y row number index
     * @return returns the cell as rectangle
     */
    public static Rect getCellRect(int x, int y) {
        Rect cell = new Rect();

        cell.set(getColumnPos(x), getRowPos(y), getColumnPos(x + 1), getRowPos(y + 1));

        return cell;
    }

    /**
     * Get x index in pixels
     * @param x index
     * @return x in pixel format
     */
    private static int getColumnPos(int x) {
        return x * cellWidth + padLeft;
    }

    /**
     * Get y index in pixels
     * @param y index
     * @return y in pixel format
     */
    private static int getRowPos(int y) {
        return y * cellHeight + padRight;
    }

    /**
     * Returns a cell index (col , row)
     * @param x in pixels
     * @param y in pixels
     * @return returns point representing the column and row index
     */
    public PointButton getCellIndex(int x, int y) {
        Coordinate point = new Coordinate(getColumnIndex(x), getRowIndex(y));

        return new PointButton(point);
    }

    /**
     * Calculates the cell pos
     * @param x in pixels
     * @return column index
     */
    private int getColumnIndex(int x) {
        return (x - padLeft) / cellWidth;
    }

    /**
     * Calculates the row pos
     * @param y in pixels
     * @return row index
     */
    private int getRowIndex(int y) {
        return (y - padTop) / cellHeight;
    }
}
