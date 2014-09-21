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
    private int NUM_CELLS;

    private Grid grid;
    private onTouch touchListner;
    private List<Cellpath> m_cellPaths = new ArrayList<Cellpath>();

    // Board style finals
    private static final int POINT_PADDING = 25;

    /* changed for activities */
    int sizeOfBoard;
    int idForBoard;
    List<GameInfo> g = new ArrayList<GameInfo>();

    private Global mGlobals = Global.getInstance();

    /**
     * Constructor for the Board context.
     * @param context I don't know what this is.
     * @param attrs I don't know what this is.
     */
    public Canvas(Context context, AttributeSet attrs) {
        super(context, attrs);

        // get info from globals to make board
        g = mGlobals.mGameInfo;
        GameInfo game = g.get(g.size() - 1);
        sizeOfBoard = game.getSize();
        idForBoard = game.getId();
        NUM_CELLS = sizeOfBoard;

        // Setup Grid
        grid = new Grid(NUM_CELLS, NUM_CELLS);
        grid.setPadding(getPaddingTop(), getPaddingBottom(), getPaddingRight(), getPaddingLeft());

        // Setup listener
        touchListner = new onTouch(this);

        System.out.println("Size of board: " + sizeOfBoard);
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

    /**
     * This function reads the XML to be able to aquire the correct board to draw
     * and that boards flows.
     */
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
                            String boardCoordinates;
                            boardCoordinates = eNode.getElementsByTagName( "flows" ).item(0).getFirstChild().getNodeValue();
                            trimString(boardCoordinates);
                        }
                    }
                }
            }
        }
        catch ( Exception e ) {
            System.out.println("Could not read points for board in Board.java");
        }

    }

    /**
     * This function trims the flow string received from the xml
     * and creates cellpaths according to that.
     * @param coordinates
     */
    public void trimString(String coordinates){
        System.out.println("Inside trimString");

        List<PointButton> coordinatesToDraw = new ArrayList<PointButton>();
        String[] parts = coordinates.split(",");

        int X1, Y1,X2,Y2 ;
        String a,b,c,d;

        for(int i = 0; i < parts.length; i++){

            String temp = parts[i];
            // regex take everything away but the numbers
            temp = temp.replaceAll("[^0-9]","");

            System.out.println(temp + "''''''''''''''''");
            a  = Character.toString(temp.charAt(0));
            b  = Character.toString(temp.charAt(1));
            c  = Character.toString(temp.charAt(2));
            d  = Character.toString(temp.charAt(3));

            X1 = Integer.parseInt(a);
            Y1 = Integer.parseInt(b);
            X2 = Integer.parseInt(c);
            Y2 = Integer.parseInt(d);

            Coordinate coord1 = new Coordinate(X1,Y1);
            Coordinate coord2 = new Coordinate(X2,Y2);
            coordinatesToDraw.add(new PointButton(coord1));
            coordinatesToDraw.add(new PointButton(coord2));
        }


        if (coordinatesToDraw.get(0) != null) {
            Cellpath cellPath = new Cellpath(coordinatesToDraw.get(0), coordinatesToDraw.get(1), Color.GREEN);
            m_cellPaths.add(cellPath);
        }
        if (coordinatesToDraw.get(2) != null) {
            Cellpath cellPath = new Cellpath(coordinatesToDraw.get(2), coordinatesToDraw.get(3), Color.RED);
            m_cellPaths.add(cellPath);
        }
        if (coordinatesToDraw.get(4) != null) {
            Cellpath cellPath = new Cellpath(coordinatesToDraw.get(4), coordinatesToDraw.get(5), Color.BLUE);
            m_cellPaths.add(cellPath);
        }
        if (coordinatesToDraw.get(6) != null) {
            Cellpath cellPath = new Cellpath(coordinatesToDraw.get(6), coordinatesToDraw.get(7), Color.YELLOW);
            m_cellPaths.add(cellPath);
        }
        if (coordinatesToDraw.get(8) != null) {
            Cellpath cellPath = new Cellpath(coordinatesToDraw.get(8), coordinatesToDraw.get(9), Color.CYAN);
            m_cellPaths.add(cellPath);
        }
        if (coordinatesToDraw.get(10) != null) {
            Cellpath cellPath = new Cellpath(coordinatesToDraw.get(10), coordinatesToDraw.get(11), Color.MAGENTA);
            m_cellPaths.add(cellPath);
        }
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
            if(isWin(m_cellPaths))
                displayWinner();
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
