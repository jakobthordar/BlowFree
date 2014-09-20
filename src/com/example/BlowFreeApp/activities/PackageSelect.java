package com.example.BlowFreeApp.activities;


import android.app.Activity;
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

public class PackageSelect extends Activity {

    List<PackLevels> mPacks = new ArrayList<PackLevels>();
    private Global mGlobals = Global.getInstance();
    List<GameInfo> mGameI = new ArrayList<GameInfo>();

    public int sizeOfBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selector);
        Intent intent = getIntent();

        try {
            readPackFoLevels(getAssets().open("packs/regular.xml"), mPacks);
            List<PackLevels> packs = new ArrayList<PackLevels>();
            readPackFoLevels(getAssets().open("packs/regular.xml"), packs);
            mGlobals.mPacksLevels = packs;
        } catch (Exception e) {
            System.out.println("could not read fle regular.xml");
        }


        ArrayAdapter<PackLevels> adapt = new ArrayAdapter<PackLevels>(this,
                android.R.layout.simple_list_item_1, mPacks);


        ListView listView = (ListView) findViewById(R.id.listLevel);
        listView.setAdapter(adapt);

        listView.setOnItemClickListener(mMessageClickedHandler);
    }

    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {


            // levels numberes from 0 - 9 are 5 by 5
            if(id <= 9){
                sizeOfBoard = 5;
            }
            // levels numbered above 9 are 6 by 6
            if(id > 9  && id <= 11){
                sizeOfBoard = 6;
            }

            startLevel(id);
        }
    };

    public void startLevel(long id){
        Intent myIntent = new Intent(this, Game.class);
        //pass to game activity

        // got in trouble passing long with intents
        int lId;
        lId = (int) id;

        // set gameinstance info so the Board can get
        // info on what board it should draw
        GameInfo g = new GameInfo(sizeOfBoard,lId);
        mGameI.add(g);
        mGlobals.mGameInfo = mGameI;
        startActivity(myIntent);
    }

    private void readPackFoLevels(InputStream is, List<PackLevels> packs) {

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


