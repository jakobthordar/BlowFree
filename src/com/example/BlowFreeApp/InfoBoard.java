package com.example.BlowFreeApp;


import com.example.BlowFreeApp.Board.Coordinate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

public class InfoBoard {

    // read board info from xml
    public void getInfoForBoardDrawing(InputStream is,int size,int idForBoard,int sizeOfBoard,String boardCoordinates){

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


}
