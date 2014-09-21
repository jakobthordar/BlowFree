package com.example.BlowFreeApp.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.BlowFreeApp.GameInfo;
import com.example.BlowFreeApp.Global;
import com.example.BlowFreeApp.PackLevels;
import com.example.BlowFreeApp.R;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class LevelSelectorMania extends ListActivity {

    List<PackLevels> mPacksMania = new ArrayList<PackLevels>();
    private Global mGlobals = Global.getInstance();
    List<GameInfo> mGameI = new ArrayList<GameInfo>();

    int sizeOfBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_mania);
        Intent intent = getIntent();

        try {
            readPackFoLevelsMania(getAssets().open("packs/mania.xml"), mPacksMania);
            List<PackLevels> packsMania = new ArrayList<PackLevels>();
            readPackFoLevelsMania(getAssets().open("packs/mania.xml"), packsMania);
            mGlobals.mPacksLevels = packsMania;
        } catch (Exception e) {
            System.out.println("could not read fle regular.xml");
        }

        ArrayAdapter<PackLevels> adapt = new ArrayAdapter<PackLevels>(this,
                android.R.layout.simple_list_item_1, mPacksMania);


        ListView listView = (ListView) findViewById(R.id.listLevelmania);
        listView.setAdapter(adapt);

        listView.setOnItemClickListener(mMessageClickedHandler);

    }
    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
           // size is always 7 for mania
           sizeOfBoard = 7;
           startLevelMania(id);
        }
    };

    public void startLevelMania(long id){

        Intent intent = new Intent(this, Game.class);
        int levelId;
        levelId = (int) id;

        GameInfo g = new GameInfo(sizeOfBoard,levelId);
        mGameI.add(g);
        mGlobals.mGameInfo = mGameI;
        startActivity(intent);


    }

    private void readPackFoLevelsMania(InputStream is, List<PackLevels> packs) {

        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse( is );
            NodeList cList = doc.getElementsByTagName( "challenge" );

            for(int i = 0; i< cList.getLength(); ++i) {
                Node cNode = cList.item(i);
                Element challenge = (Element) cNode;
                String name = challenge.getAttribute("name");

                NodeList n = cNode.getChildNodes();

                for (int c = 0; c < n.getLength(); ++c) {
                    Node nNode = n.item(c);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eNode = (Element) nNode;
                        String id = eNode.getAttribute("id");

                        packs.add(new PackLevels(id,name));
                    }
                }
            }
        }
        catch ( Exception e ) {
            System.out.println("Could not read the pack, in readPack()");
        }
    }

}
