package com.example.BlowFreeApp.Board;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Board extends View {

    private final int NUM_CELLS = 5;
    private int m_cellWidth;
    private int m_cellHeight;

    private Rect m_rect = new Rect();
    private Paint m_paintGrid  = new Paint();
    private Paint m_paintPath  = new Paint();
    private Paint m_paintShape  = new Paint();
    private Path m_path = new Path();
    private ShapeDrawable m_shape = new ShapeDrawable(new OvalShape());

    private List<Cellpath> m_cellPaths = new ArrayList<Cellpath>();
    private Cellpath m_cellPath = new Cellpath(new PointButton(new Coordinate(0, 0)), new PointButton(new Coordinate(1, 2)));


    private int xToCol( int x ) {
        return (x - getPaddingLeft()) / m_cellWidth;
    }

    private int yToRow( int y ) {
        return (y - getPaddingTop()) / m_cellHeight;
    }

    private int colToX( int col ) {
        return col * m_cellWidth + getPaddingLeft() ;
    }

    private int rowToY( int row ) {
        return row * m_cellHeight + getPaddingTop() ;
    }

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);

        m_paintGrid.setStyle( Paint.Style.STROKE );
        m_paintGrid.setColor( Color.GRAY );

        m_paintPath.setStyle( Paint.Style.STROKE );
        m_paintPath.setStrokeWidth(32);
        m_paintPath.setStrokeCap( Paint.Cap.ROUND );
        m_paintPath.setStrokeJoin( Paint.Join.ROUND );
        m_paintPath.setAntiAlias( true );

        PointButton point_a = new PointButton(new Coordinate(0, 0));
        PointButton point_b = new PointButton(new Coordinate(1, 2));
        Cellpath cellPath_a = new Cellpath(point_a, point_b);
        cellPath_a.setColor(Color.RED);


        PointButton point_c = new PointButton(new Coordinate(4, 0));
        PointButton point_d = new PointButton(new Coordinate(3, 2));
        Cellpath cellPath_b = new Cellpath(point_c, point_d);
        cellPath_b.setColor(Color.BLUE);

        PointButton point_e = new PointButton(new Coordinate(0, 4));
        PointButton point_f = new PointButton(new Coordinate(4, 3));
        Cellpath cellPath_c = new Cellpath(point_e, point_f);
        cellPath_c.setColor(Color.GREEN);

        m_cellPaths.add(cellPath_a);
        m_cellPaths.add(cellPath_b);
        m_cellPaths.add(cellPath_c);
    }

    @Override
    protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec ) {
        super.onMeasure( widthMeasureSpec, heightMeasureSpec );
        int width  = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        int size = Math.min(width, height);
        setMeasuredDimension( size + getPaddingLeft() + getPaddingRight(),
                size + getPaddingTop() + getPaddingBottom() );
    }

    @Override
    protected void onSizeChanged( int xNew, int yNew, int xOld, int yOld ) {
        int sw = Math.max(1, (int) m_paintGrid.getStrokeWidth());
        m_cellWidth  = (xNew - getPaddingLeft() - getPaddingRight() - sw) / NUM_CELLS;
        m_cellHeight = (yNew - getPaddingTop() - getPaddingBottom() - sw) / NUM_CELLS;
    }

    @Override
    protected void onDraw( Canvas canvas ) {

        for (int r = 0; r<NUM_CELLS; ++r) {
            for (int c = 0; c < NUM_CELLS; ++c) {
                int x = colToX(c);
                int y = rowToY(r);
                m_rect.set(x, y, x + m_cellWidth, y + m_cellHeight);
                canvas.drawRect(m_rect, m_paintGrid);

                for (Cellpath cp : m_cellPaths) {
                    if (cp.getPoint_a().getCoordinate().getCol() == c &&
                            cp.getPoint_a().getCoordinate().getRow() == r) {
                        m_shape.setBounds(m_rect);
                        m_shape.getPaint().setColor(cp.getColor());
                        m_shape.draw(canvas);
                    }
                    if (cp.getPoint_b().getCoordinate().getCol() == c &&
                            cp.getPoint_b().getCoordinate().getRow() == r) {
                        m_shape.setBounds(m_rect);
                        m_shape.getPaint().setColor(cp.getColor());
                        m_shape.draw(canvas);
                    }
                }
            }
        }

        // TODO: Check if PointButton
        // TODO: If PointButton draw from it and match it to the correct Cellpath
        for (Cellpath cp : m_cellPaths) {
            if(!cp.isFinished()) {
                cp.getThisPath().reset();
            }

            if (!cp.isEmpty()) {
                List<Coordinate> colist = cp.getCoordinates();
                Coordinate co = colist.get(0);

                cp.getThisPath().moveTo(colToX(co.getCol()) + m_cellWidth / 2,
                        rowToY(co.getRow()) + m_cellHeight / 2 );

                for ( int i=1; i<colist.size(); ++i ) {
                    co = colist.get(i);
                    cp.getThisPath().lineTo(colToX(co.getCol()) + m_cellWidth / 2,
                            rowToY(co.getRow()) + m_cellHeight / 2);
                }
            }

            canvas.drawPath(cp.getThisPath(), cp.getPaintPath());

        }
    }

    private boolean areNeighbours( int c1, int r1, int c2, int r2 ) {
        return Math.abs(c1-c2) + Math.abs(r1-r2) == 1;
    }

    @Override
    public boolean onTouchEvent( MotionEvent event ) {

        int x = (int) event.getX();         // NOTE: event.getHistorical... might be needed.
        int y = (int) event.getY();
        int c = xToCol( x );
        int r = yToRow( y );

        if ( c >= NUM_CELLS || r >= NUM_CELLS ) {
            return true;
        }

        Cellpath tempCellPath;

        // TODO: When already finished with a path, you should be able to reset it
        // TODO: You should not be able to draw a path over another pats POINT
        // TODO: When a path intersects another path cut off the other path
        // TODO: Win state

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (Cellpath cp : m_cellPaths) {
                if (!cp.isFinished()) {
                    cp.reset();
                    Coordinate coordinate = new Coordinate(c, r);
                    if (cp.checkPointButtons(coordinate)) {
                        cp.setStart(coordinate);
                        cp.append(coordinate);
                        cp.setActive(true);
                    }
                }
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            for (Cellpath cp : m_cellPaths) {
                if (cp.isActive()) {
                    if (!cp.isFinished()) {
                        if (!cp.isEmpty()) {
                            Coordinate coordinate = new Coordinate(c, r);
                            if (cp.checkIfEnd(coordinate)) {
                                cp.setEnd(coordinate);
                                cp.setFinished(true);
                                System.out.println("Finished!");
                            }

                            List<Coordinate> coordinateList = cp.getCoordinates();
                            Coordinate last = coordinateList.get(coordinateList.size() - 1);
                            if (areNeighbours(last.getCol(), last.getRow(), c, r)) {
                                cp.append(new Coordinate(c, r));
                                invalidate();
                            }
                        }
                    }
                }
            }
        }
        else if(event.getAction() == MotionEvent.ACTION_UP) {
            for (Cellpath cp : m_cellPaths) {
                if (cp.isActive()) {
                    cp.setActive(false);
                }
            }
        }

        return true;
    }

    public void setColor( int color ) {
        m_paintPath.setColor( color );
        invalidate();
    }
}
