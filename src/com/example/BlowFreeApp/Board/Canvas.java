package com.example.BlowFreeApp.Board;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.example.BlowFreeApp.Board.events.onTouch;
import com.example.BlowFreeApp.GameInfo;
import com.example.BlowFreeApp.PackLevelFactory;
import com.example.BlowFreeApp.Puzzle;
import com.example.BlowFreeApp.database.GameStatusAdapter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Canvas extends View {
    private int NUM_CELLS;

    private int type;
    private Grid grid;
    private onTouch touchListner;
    private List<Cellpath> m_cellPaths = new ArrayList<Cellpath>();
    private Puzzle thisGame;
    private GameStatusAdapter adapter = new GameStatusAdapter(getContext());

    // Board style finals
    private static final int POINT_PADDING = 25;

    /* changed for activities */

    /**
     * Constructor for the Board context.
     * @param context I don't know what this is.
     * @param attrs I don't know what this is.
     */
    public Canvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        CellpathColors colors = CellpathColors.getInstance();
        // get info from globals to make board
        thisGame = PackLevelFactory.getActiveGame();
        m_cellPaths = thisGame.getCellPaths();

        /*if(idForBoard  > 9 ){
            idForBoard = idForBoard - 10;
        }*/
        if (thisGame.getSize() == 5)
            type = 0;
        if (thisGame.getSize() == 6)
            type = 1;
        if (thisGame.getSize() == 7)
            type = 2;

        NUM_CELLS = thisGame.getSize();

        // Setup Grid
        grid = new Grid(NUM_CELLS, NUM_CELLS);
        grid.setPadding(getPaddingTop(), getPaddingBottom(), getPaddingRight(), getPaddingLeft());

        // Setup listener
        touchListner = new onTouch(this);

        // next call a function here and extract the info needed to
        // draw the board
    }

    /**
     * This function reads the XML to be able to aquire the correct board to draw
     * and that boards flows.
     */
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
        grid.setSize(xNew, yNew);
    }

    /**
     * Draws the board points in to the canvas
     * @param canvas Needs the canvas to be able to draw
     */
    public void drawPoints(android.graphics.Canvas canvas) {
        int x, y;

        ShapeDrawable circle = new ShapeDrawable(new OvalShape());
        Rect boundRect = new Rect();

        for (Cellpath cp : m_cellPaths) {
            // Get Point A
            x = cp.getPoint_a().getCoordinate().getCol();
            y = cp.getPoint_a().getCoordinate().getRow();

            // Set bounding Rect
            boundRect.set(grid.getCellPoint(x, y, POINT_PADDING));

            // Draw Point A
            circle.setBounds(boundRect);
            circle.getPaint().setColor(cp.getColor());
            circle.draw(canvas);

            // Get Point B
            x = cp.getPoint_b().getCoordinate().getCol();
            y = cp.getPoint_b().getCoordinate().getRow();

            // Set bounding Rect
            boundRect.set(grid.getCellPoint(x, y, POINT_PADDING));

            // Draw Point B
            circle.setBounds(boundRect);
            circle.getPaint().setColor(cp.getColor());
            circle.draw(canvas);
        }
    }

    /**
     * Draws board paths.
     * Highlights paths that are finished
     * @param canvas
     */
    public void drawPaths(android.graphics.Canvas canvas) {
        for (Cellpath cp : m_cellPaths) {
            // Reset the path this cellpath has.
            cp.getThisPath().reset();

            // Only start drawing if the cellpath coordinate list is not empty.
            if(cp.isEmpty()) continue;

            cp.draw(canvas);

            // If the path is finished we highlight it
            if(cp.isFinished())
                cp.drawHighlight(canvas);
        }
    }

    /**
     * This function is called at the start of the game and each time
     * the canvas is validated.
     * @param canvas We need the canvas for a context to be able to draw.
     */
    @Override
    protected void onDraw(android.graphics.Canvas canvas) {
        // Draw the background grid
        grid.draw(canvas);

        // Draw the board points
        drawPoints(canvas);

        // Draw the board paths
        drawPaths(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Get the current coordinate the finger touched
        Coordinate coordinate = grid.getCellIndex((int) event.getX(), (int) event.getY()).coordinate;

        // Check if the coordinate is on the board
        if (coordinate.getCol() >= NUM_CELLS || coordinate.getRow() >= NUM_CELLS )
            return true;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touchListner.touchDown(coordinate, m_cellPaths);
        }
        else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            touchListner.touchMove(coordinate, m_cellPaths);

            // Check for Win
            if(isWin(m_cellPaths)) {
                displayWinner();
                //adapter.insertGameStatus(this.idForBoard, true, this.type);
            }
        }
        else if(event.getAction() == MotionEvent.ACTION_UP) {
            touchListner.touchUp(m_cellPaths);
        }

        return true;
    }

    /**
     * Check if all paths have been connected
     * @return if win
     */
    private boolean isWin(List<Cellpath> paths) {
        for(Cellpath c : paths) {
            if(!c.isFinished())
                return false;
        }

        return true;
    }

    public void displayWinner() {
        new AlertDialog.Builder(getContext())
                .setTitle("OMG YOU WON")
                .setMessage("You must have an IQ above 145, at least!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
