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
import com.example.BlowFreeApp.GameInfo;
import com.example.BlowFreeApp.Global;
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
    private final int NUM_CELLS = 5;

    private Grid grid;
    private List<Cellpath> m_cellPaths = new ArrayList<Cellpath>();

    /* changed for activities */
    int sizeOfBoard;
    int idForBoard;
    List<GameInfo> g = new ArrayList<GameInfo>();

    private Global mGlobals = Global.getInstance();

    String boardCoordinates;

    /**
     * Constructor for the Board context.
     * @param context I don't know what this is.
     * @param attrs I don't know what this is.
     */
    public Canvas(Context context, AttributeSet attrs) {
        super(context, attrs);

        grid = new Grid(NUM_CELLS, NUM_CELLS);
        grid.setPadding(getPaddingTop(), getPaddingBottom(), getPaddingRight(), getPaddingLeft());

        PointButton point_a = new PointButton(new Coordinate(0, 0));
        PointButton point_b = new PointButton(new Coordinate(1, 2));

        Cellpath cellPath_a = new Cellpath(point_a, point_b, Color.RED);

        PointButton point_c = new PointButton(new Coordinate(4, 0));
        PointButton point_d = new PointButton(new Coordinate(3, 2));
        Cellpath cellPath_b = new Cellpath(point_c, point_d, Color.BLUE);

        PointButton point_e = new PointButton(new Coordinate(0, 4));
        PointButton point_f = new PointButton(new Coordinate(4, 3));
        Cellpath cellPath_c = new Cellpath(point_e, point_f, Color.GREEN);

        m_cellPaths.add(cellPath_a);
        m_cellPaths.add(cellPath_b);
        m_cellPaths.add(cellPath_c);

        // get info from globals to make board */

        g = mGlobals.mGameInfo;
        GameInfo game = g.get(g.size() - 1);
        sizeOfBoard = game.getSize();
        idForBoard = game.getId();

        System.out.println(sizeOfBoard + "******");
        // next call a function here and extract the info needed to
        // draw the board

        if(sizeOfBoard <= 6) {
            try {

                getInfoForBoardDrawing(getContext().getAssets().open("packs/regular.xml"), sizeOfBoard, idForBoard);

            } catch (Exception e) {
                System.out.println("could not read fle regular.xml");
            }
        }
        if(sizeOfBoard == 7){
            try {

                getInfoForBoardDrawing(getContext().getAssets().open("packs/mania.xml"), sizeOfBoard, idForBoard);

            } catch (Exception e) {
                System.out.println("could not read fle mania.xml");
            }
        }
    }

    // read board info from xml
    public void getInfoForBoardDrawing(InputStream is,int size,int idForBoard){

        // fix for reading xml file
        // and finding 6 by 6 boards
        if(idForBoard  > 9 ){
            idForBoard = idForBoard - 10;
        }

        int tempId;
        int tempSize;

        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse( is );
            NodeList cList = doc.getElementsByTagName( "challenge" );

            // TO DO get the points for the board and size

            for(int i = 0; i< cList.getLength(); ++i) {
                Node cNode = cList.item(i);
                Element challenge = (Element) cNode;

                NodeList n = cNode.getChildNodes();

                for (int c = 0; c < n.getLength(); ++c) {

                    Node nNode = n.item(c);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        // eNode is puzzle
                        Element eNode = (Element) nNode;

                        String temp = eNode.getAttribute("id");
                        tempId = Integer.parseInt(temp) -1;

                        // cast to be able to compare
                        String s = eNode.getElementsByTagName( "size" ).item(0).getFirstChild().getNodeValue();
                        tempSize = Integer.parseInt(s);

                        // compare size also &&
                        if( tempId == idForBoard && tempSize == sizeOfBoard){
                            // TO DO GET THE board from elment flows
                            boardCoordinates = eNode.getElementsByTagName( "flows" ).item(0).getFirstChild().getNodeValue();
                        }
                    }
                }
            }
        }
        catch ( Exception e ) {
            System.out.println("Could not read points for board in Board.java");
        }
       // trimString(boardCoordinates);
    }

   /* public List<Coordinate> trimString(String coordinates){

        List<Coordinate> coordinatesToDraw = new ArrayList<Coordinate>();

        String[] parts = coordinates.split(",");

        for(String s:parts){

            String x = s.substring(0);
            String y = s.substring(1);

            if(!(x == "(") || !(x ==")") || !(y == "(") || !(y ==")") ){

                int tempX = Integer.parseInt(x);
                int tempY = Integer.parseInt(y);

                Coordinate c = new Coordinate(tempX,tempY);
                coordinatesToDraw.add(c);
            }

            // remove the coordinate we have already put in the list
            s = s.substring(2, s.length() - 2);
        }

        for(Coordinate c: coordinatesToDraw){
            System.out.println(c.getCol()+ "xxxxxxxxxxxxx");
            System.out.println(c.getRow() + "yyyyyyyyyyyy");
        }

        return coordinatesToDraw;
    } */

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
     * A helper function which simply draws all the board lines and the
     * dots onto the screen. I don't expect this to change at all.
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
            boundRect.set(grid.getCellRect(x, y));

            // Draw Point A
            circle.setBounds(boundRect);
            circle.getPaint().setColor(cp.getColor());
            circle.draw(canvas);

            // Get Point B
            x = cp.getPoint_b().getCoordinate().getCol();
            y = cp.getPoint_b().getCoordinate().getRow();

            // Set bounding Rect
            boundRect.set(grid.getCellRect(x, y));

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
        // TODO: Check if PointButton
        // TODO: If PointButton draw from it and match it to the correct Cellpath

        // Loop through all cellpaths
        for (Cellpath cp : m_cellPaths) {
            // Reset the path this cellpath has.
            cp.getThisPath().reset();

            // Only start drawing if the cellpath coordinate list is not empty.
            if(cp.isEmpty()) continue;

            cp.draw(canvas);

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

    private boolean isAnotherPathsPoint(Coordinate coordinate, Cellpath cellpath) {
        for (Cellpath cp : m_cellPaths) {
            if (!cp.equals(cellpath)) {
                if(cp.checkPointButtons(coordinate)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean checkWin(List<Cellpath> cellPath){
        for(Cellpath c : cellPath){
            if(!c.isFinished())
                return false;
        }
        return true;
    }

    private boolean areNeighbours( int c1, int r1, int c2, int r2 ) {
        return Math.abs(c1-c2) + Math.abs(r1-r2) == 1;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Get the current coordinate the finger touched
        Coordinate coordinate = grid.getCellIndex((int) event.getX(), (int) event.getY()).coordinate;

        if (coordinate.getCol() >= NUM_CELLS || coordinate.getRow() >= NUM_CELLS ) {
            return true;
        }

        // TODO: When already finished with a path, you should be able to reset it
        // TODO: You should not be able to draw a path over another pats POINT
        // TODO: When a path intersects another path cut off the other path
        // TODO: Win state

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (Cellpath cp : m_cellPaths) {
                // For each path, reset it if it isnt finished.
                // Sets the cellPath as active if it has a point at the coordinates
                cp.setActive(coordinate);

                if (cp.isActive()) {
                    cp.setStart(coordinate);

                    if (!cp.isFinished()) {
                        cp.reset();
                        System.out.println("Not finished");
                        cp.append(coordinate);
                    }
                    else {
                        System.out.println("Already finished");
                        cp.reset();
                        cp.setFinished(false);
                        cp.append(coordinate);
                    }
                }
            }
            invalidate();
        }
        else if (event.getAction() == MotionEvent.ACTION_MOVE) {

            for (Cellpath cp : m_cellPaths) {
                if (cp.isActive() && !cp.isFinished() && !cp.isEmpty() && !isAnotherPathsPoint(coordinate, cp)) {
                    if (cp.checkIfEnd(coordinate)) {
                        cp.setFinished(true);
                        if(checkWin(m_cellPaths)){
                            displayWinner();
                        }
                        System.out.println("Finished!");
                    }

                    List<Coordinate> coordinateList = cp.getCoordinates();
                    Coordinate last = coordinateList.get(coordinateList.size() - 1);
                    if (areNeighbours(last.getCol(), last.getRow(), coordinate.getCol(), coordinate.getRow())) {
                        cp.append(new Coordinate(coordinate.getCol(), coordinate.getRow()));
                        invalidate();
                    }

                    // TODO: Here we should check if this path intersects another path.
                }
            }
        }
        else if(event.getAction() == MotionEvent.ACTION_UP) {
            // TODO: Bad place is here
            for (Cellpath cp : m_cellPaths) {
                if (cp.isActive()) {
                    cp.setActive(false);
                }
            }
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
