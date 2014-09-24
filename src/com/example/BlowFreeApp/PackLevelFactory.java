package com.example.BlowFreeApp;


import android.content.Context;
import android.database.Cursor;
import com.example.BlowFreeApp.Board.Cellpath;
import com.example.BlowFreeApp.Board.CellpathColors;
import com.example.BlowFreeApp.Board.Coordinate;
import com.example.BlowFreeApp.Board.PointButton;
import com.example.BlowFreeApp.activities.Game;
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

    private static List<Puzzle> hardLevels = new ArrayList<Puzzle>();
    private static List<Puzzle> mediumLevels = new ArrayList<Puzzle>();
    private static List<Puzzle> easyLevels = new ArrayList<Puzzle>();
    private static GameStatusAdapter gameStatusAdapter;
    private static Puzzle activeGame;
    private static Context context;
    private static SoundPlayer soundPlayer;
    private static Game gameActivity;

    public PackLevelFactory(Context c) {
        context = c;
        gameStatusAdapter = new GameStatusAdapter(c);
        soundPlayer = new SoundPlayer(c);
        activeGame = null;
        easyLevels = readFile("packs/easy.xml");
        mediumLevels = readFile("packs/medium.xml");
        hardLevels = readFile("packs/hard.xml");
        gameStatusAdapter.insertActiveGame(-1);
    }

    private List<Puzzle> readFile(String name) {
        List<Puzzle> list = new ArrayList<Puzzle>();
        try {
            readPackLevelsXML(context.getAssets().open(name), list);
        } catch (IOException e) {
            System.out.println("Could not read " + name + " file. " + e.getMessage());
        }
        return list;
    }

    private void readPackLevelsXML(InputStream is, List<Puzzle> puzzles) {
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
                        Puzzle p = new Puzzle(name, challengeId, puzzleId, size, cellpaths);
                        puzzles.add(p);
                        Cursor cursor = gameStatusAdapter.queryGameStatusId(p.getTableGameStatus(), puzzleId);
                        int gid = -1;
                        if(cursor.moveToFirst()) {
                            gid = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(1)));
                        }
                        if (!(gid == puzzleId)) {
                            insertIntoTable(name, puzzleId, p.getTableGameStatus());
                        }
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

    private static void insertIntoTable(String name, int puzzleId, String tableGameStatus) {
        gameStatusAdapter.insertGameStatus(tableGameStatus, puzzleId, false, name);
        gameStatusAdapter.close();
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

        return cellPaths;
    }



    /**
     * This ID is not the same as gameID, simply the index of the list or the arrayList
     * @param id
     * @return
     */
    public static Puzzle getEasyGame(int id) {
        if(easyLevels.size() - 1 < id || id < 0) return null;

        return easyLevels.get(id);
    }

    public static Puzzle getMediumGame(int id) {
        if(mediumLevels.size() - 1 < id || id < 0) return null;
        return mediumLevels.get(id);
    }

    public static Puzzle getHardGame(int id) {
        if(hardLevels.size() - 1 < id || id < 0) return null;
        return hardLevels.get(id);
    }

    public static GameStatusAdapter getGameStatusAdapter() {
        return gameStatusAdapter;
    }

    public static SoundPlayer getSoundPlayer() {
        return soundPlayer;
    }

    public static List<Puzzle> getEasyLevels() {
        return easyLevels;
    }

    public static List<Puzzle> getMediumLevels() {
        return mediumLevels;
    }

    public static List<Puzzle> getHardLevels() {
        return hardLevels;
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

    public static void setGameActivity(Game activity) {
        gameActivity = activity;
    }

    public static Game getGameActivity() { return gameActivity; }
}