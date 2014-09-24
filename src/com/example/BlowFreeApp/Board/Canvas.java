package com.example.BlowFreeApp.Board;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.example.BlowFreeApp.Board.events.onTouch;
import com.example.BlowFreeApp.PackLevelFactory;
import com.example.BlowFreeApp.Puzzle;
import com.example.BlowFreeApp.activities.Game;
import com.example.BlowFreeApp.database.GameStatusAdapter;

import java.util.ArrayList;
import java.util.List;

public class Canvas extends View {
    private int NUM_CELLS;

    private Grid grid;
    private onTouch touchListener;
    private GameStatusAdapter db = new GameStatusAdapter(getContext());
    private List<Cellpath> m_cellPaths;
    private Puzzle stats;

    private boolean openWinWindow = false;

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

        // Canvas init
        create();
    }

    /**
     * This function reads the XML to be able to acquire the correct board to draw
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
     * Create the canvas
     */

    public void create() {
        CellpathColors colors = CellpathColors.getInstance();
        // get info from globals to make board
        stats = PackLevelFactory.getActiveGame();

        m_cellPaths = new ArrayList<Cellpath>(stats.getCellPaths());
        NUM_CELLS = stats.getSize();

        // Setup Grid
        grid = new Grid(NUM_CELLS, NUM_CELLS);
        grid.setPadding(getPaddingTop(), getPaddingBottom(), getPaddingRight(), getPaddingLeft());

        // Setup listener
        touchListener = new onTouch(this);
    }

    /**
     * Reset the canvas
     */
    public void reset() {
        // Rest Paths
        for(Cellpath cp : m_cellPaths) {
            cp.setFinished(false);
            cp.reset();
        }

        // Rest stats
        stats.reset();

        // Redraw canvas
        invalidate();
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
     * @param canvas We need the canvas for a context to be able to draw.
     */
    public void drawPaths(android.graphics.Canvas canvas) {
        Cellpath activePath = null;

        for (Cellpath cp : m_cellPaths) {
            // Reset the path this cellpath has.
            cp.getThisPath().reset();

            if(cp.isActive()) {
                activePath = cp;
                continue;
            }

            // If the path is not active or finished we highlight it
            if(!cp.isActive() || cp.isFinished())
                cp.drawHighlight(canvas);


            // Only start drawing if the cellpath coordinate list is not empty.
            if(!cp.isEmpty())
                cp.draw(canvas);
        }

        if(activePath != null)
            activePath.draw(canvas);
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
        Coordinate cell = grid.getCellIndex((int) event.getX(), (int) event.getY()).coordinate;

        // Check if the coordinate is on the board
        if(!inCanvas(cell))
            return true;

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP :
                touchListener.touchUp(m_cellPaths);

                // Add Move
                stats.addMoves();

                // Set flows
                stats.setFlow(pathsConnected());
                break;
            case MotionEvent.ACTION_DOWN :
                touchListener.touchDown(cell, m_cellPaths);
                break;
            case MotionEvent.ACTION_MOVE :
                touchListener.touchMove(cell, m_cellPaths);

                // Check for Win
                if(isWin(m_cellPaths)) {
                    Cursor cursor = db.queryGameStatusId(thisGame.getTableGameStatus(), thisGame.getPuzzleId());
                    String finished = "";
                    displayWinner();
                    if(cursor.moveToFirst()) {
                        finished = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2)));
                        db.updateGameStatus(thisGame.getTableGameStatus(), thisGame.getPuzzleId(), true, 2);
                        db.close();
                        finished = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2)));
                        finished = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2)));
                    }
                }

            }

        return true;
    }

    /**
     * Counts how many path are connected
     * @return how many paths are connected
     */
    private int pathsConnected() {
        int flows = 0;

        for(Cellpath cp : m_cellPaths)
            if(cp.isFinished())
                flows ++;

        return flows;
    }

    /**
     * Checks if the cell is on canvas
     * @param cell clicked on cell
     * @return is cell in canvas
     */
    private boolean inCanvas(Coordinate cell) {
        return cell.getCol()  < NUM_CELLS &&
                cell.getRow() < NUM_CELLS &&
                cell.getCol() >= 0         &&
                cell.getRow() >= 0;
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
        if(openWinWindow) return;

        openWinWindow = true;

        new AlertDialog.Builder(getContext())
                .setTitle("OMG YOU WON")
                .setMessage("You must have an IQ above 145, at least!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Game main = PackLevelFactory.getGameActivity();
                        main.setGameLevel(true);
                        openWinWindow = false;
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        PackLevelFactory.getSoundPlayer().playWin();
    }

}
