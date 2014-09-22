package com.example.BlowFreeApp.Board;

public class Coordinate {

    private int m_col;
    private int m_row;

    public Coordinate() {
    }

    public  Coordinate( int col, int row ) {
        m_col = col;
        m_row = row;
    }

    public int getCol() {
        return m_col;
    }

    public int getRow() {
        return m_row;
    }

    public boolean equalPos(Coordinate cell) {
        return cell.getCol() == m_col && cell.getRow() == m_row;
    }

    @Override
    public String toString() {
        return "(" + m_col + ", " + m_row + ")";
    }

    @Override
    public boolean equals( Object other ) {
        if ( !(other instanceof Coordinate) ) {
            return false;
        }
        Coordinate otherCo = (Coordinate) other;
        return otherCo.getCol() == this.getCol()&& otherCo.getRow() == this.getRow();
    }
}
