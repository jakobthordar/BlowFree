package com.example.BlowFreeApp;


import android.content.Context;
import android.graphics.Color;
import com.example.BlowFreeApp.Board.Cellpath;
import com.example.BlowFreeApp.Board.CellpathColors;
import com.example.BlowFreeApp.Board.Coordinate;
import com.example.BlowFreeApp.Board.PointButton;
import com.example.BlowFreeApp.activities.Pack;
import com.example.BlowFreeApp.database.GameStatusAdapter;
import com.example.BlowFreeApp.sound.SoundPlayer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PackLevelFactory {

    private static List<Pack> packs = new ArrayList<Pack>();
    private static List<Puzzle> maniaLevels = new ArrayList<Puzzle>();
    private static List<Puzzle> regularLevels = new ArrayList<Puzzle>();
    private static GameStatusAdapter gameStatusAdapter;
    private static Puzzle activeGame;
    private static Context context;
    private static SoundPlayer soundPlayer;


    public PackLevelFactory(Context c) {
        context = c;
        gameStatusAdapter = new GameStatusAdapter(c);
        soundPlayer = new SoundPlayer(c);
        readRegular();
        readMania();
        readPacks();
    }

    /**
     * Reads from regular.xml
     */
    private void readRegular() {
        try {
            List<Puzzle> packs = new ArrayList<Puzzle>();
            readPackLevelsXML(context.getAssets().open("packs/regular.xml"), packs);
            regularLevels = packs;
        } catch (IOException e) {
            System.out.println("Could not read regular file");
        }
    }

    private void readMania() {
        try {
            List<Puzzle> packsMania = new ArrayList<Puzzle>();
            readPackLevelsXML(context.getAssets().open("packs/mania.xml"), packsMania);
            maniaLevels = packsMania;
        } catch (IOException e) {
            System.out.println("Could not read mania file");
        }
    }

    private void readPacks() {
        try{
            List<Pack> p = new ArrayList<Pack>();
            readPacksXML(context.getAssets().open("packs/packs.xml"), p);
            packs = p;
        }
        catch ( Exception e){
            System.out.println("could not read pack");
        }
    }

    private void readPacksXML( InputStream is, List<Pack> packs) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse( is );
            NodeList nList = doc.getElementsByTagName( "pack" );
            for ( int c = 0; c < nList.getLength(); ++c ) {
                Node nNode = nList.item(c);
                if ( nNode.getNodeType() == Node.ELEMENT_NODE ) {
                    Element eNode = (Element) nNode;
                    String name = eNode.getElementsByTagName( "name" ).item(0).getFirstChild().getNodeValue();
                    String description = eNode.getElementsByTagName( "description" ).item(0).getFirstChild().getNodeValue();
                    String file = eNode.getElementsByTagName( "file" ).item(0).getFirstChild().getNodeValue();
                    packs.add( new Pack( name, description, file ) );
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readPackLevelsXML(InputStream is, List<Puzzle> packs) {
        String name;
        int challengeId;
        int puzzleId;
        int size;
        List<Cellpath> cellpaths;

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse( is );

            NodeList challengeList = doc.getElementsByTagName("challenge");
            for(int i = 0; i < challengeList.getLength(); ++i) {
                Node challangeNode = challengeList.item(i);
                Element challenge = (Element) challangeNode;

                name = challenge.getAttribute("name");
                challengeId = Integer.parseInt(challenge.getAttribute("id"));

                NodeList puzzleList = challangeNode.getChildNodes();
                for (int c = 0; c < puzzleList.getLength(); ++c) {
                    Node puzzleNode = puzzleList.item(c);
                    if (puzzleNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element puzzle = (Element) puzzleNode;

                        puzzleId = Integer.parseInt(puzzle.getAttribute("id"));
                        size = Integer.parseInt(puzzle.getElementsByTagName("size").item(0).getFirstChild().getNodeValue());
                        String temp = puzzle.getElementsByTagName("flows").item(0).getFirstChild().getNodeValue();
                        cellpaths = trimString(temp);
                        packs.add(new Puzzle(name, challengeId, puzzleId, size, cellpaths));
                        insertIntoTable(name, puzzleId, size);
                   }
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void insertIntoTable(String name, int puzzleId, int size) {
        // EASY
        if (size == 5) {
            gameStatusAdapter.insertGameStatusRegular(puzzleId, false, name);
        }
        // MEDIUM
        if (size == 6) {
            gameStatusAdapter.insertGameStatusRegular(puzzleId, false, name);

        }
        // HARD
        if (size == 7) {
            gameStatusAdapter.insertGameStatusMania(puzzleId, false, name);
        }
    }

    /**
     * This function trims the flow string received from the xml
     * and creates cellpaths according to that.
     */
    public List<Cellpath> trimString(String flows) {
        System.out.println("Inside trimString");
        List<Cellpath> cellPaths = new ArrayList<Cellpath>();

        List<PointButton> cellPoints = new ArrayList<PointButton>();
        String[] parts = flows.split(",");

        int X1, Y1, X2, Y2;
        String a, b, c, d;

        for (int i = 0; i < parts.length; i++) {

            String temp = parts[i];
            // regex take everything away but the numbers
            temp = temp.replaceAll("[^0-9]", "");

            System.out.println(temp + "''''''''''''''''");
            a = Character.toString(temp.charAt(0));
            b = Character.toString(temp.charAt(1));
            c = Character.toString(temp.charAt(2));
            d = Character.toString(temp.charAt(3));

            X1 = Integer.parseInt(a);
            Y1 = Integer.parseInt(b);
            X2 = Integer.parseInt(c);
            Y2 = Integer.parseInt(d);

            Coordinate coord1 = new Coordinate(X1, Y1);
            Coordinate coord2 = new Coordinate(X2, Y2);
            Cellpath cellPath = new Cellpath(new PointButton(coord1), new PointButton(coord2));
            cellPaths.add(cellPath);
        }

        CellpathColors colors = CellpathColors.getInstance();
        colors.setColorsForCellPaths(cellPaths);
        return cellPaths;
    }

    /**
     * This ID is not the same as gameID, simply the index of the list or the arrayList
     * @param id
     * @return
     */
    public static Puzzle getGameById(int id) {
        return regularLevels.get(id);
    }

    public static GameStatusAdapter getGameStatusAdapter() {
        return gameStatusAdapter;
    }

    public static SoundPlayer getSoundPlayer() {
        return soundPlayer;
    }

    public static List<Pack> getPacks() {
        return packs;
    }

    public static List<Puzzle> getManiaLevels() {
        return maniaLevels;
    }

    public static List<Puzzle> getRegularLevels() {
        return regularLevels;
    }

    public static Puzzle getActiveGame() {
        return activeGame;
    }

    public static Context getContext() {
        return context;
    }

    public static void setActiveGame(Puzzle a) {
        activeGame = a;
    }
}