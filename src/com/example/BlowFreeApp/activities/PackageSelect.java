package com.example.BlowFreeApp.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.BlowFreeApp.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PackageSelect extends ListActivity {

    List<Pack> mPacks = new ArrayList<Pack>();
    private Global mGlobals = Global.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        Intent intent = getIntent();

        try{
            readPack(getAssets().open("packs/packs.xml"), mPacks);
            List<Pack> packs = new ArrayList<Pack>();
            readPack(getAssets().open("packs/packs.xml"), packs);
            mGlobals.mPacks = packs;
        }
        catch ( Exception e){
            System.out.println("could not read pack");
        }

        ArrayList<String> array = new ArrayList<String>();

        // TO DO put descritpoin in list also
        for(Pack p: mGlobals.mPacks){
            array.add(p.getName());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, array);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(mMessageClickedHandler);

    }

    /**
     * Create a message handling object as an anonymous class.
     */
    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            if(id == 0){
                regularPack(v);
            }
            if(id == 1){
                maniaPack(v);
            }
        }
    };

    public void maniaPack(View v){
        Intent intent = new Intent(this, LevelSelectorMania.class);
        startActivity(intent);
    }



    public void regularPack(View view){
        Intent intent = new Intent(this, LevelSelectorRegular.class);
        startActivity(intent);
    }

    private void readPack( InputStream is, List<Pack> packs) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse( is );
            NodeList nList = doc.getElementsByTagName( "pack" );
            for ( int c=0; c<nList.getLength(); ++c ) {
                Node nNode = nList.item(c);
                if ( nNode.getNodeType() == Node.ELEMENT_NODE ) {
                    Element eNode = (Element) nNode;
                    String name = eNode.getElementsByTagName( "name" ).item(0).getFirstChild().getNodeValue();
                    String description = eNode.getElementsByTagName( "description" ).item(0).getFirstChild().getNodeValue();
                    String file = eNode.getElementsByTagName( "file" ).item(0).getFirstChild().getNodeValue();
                    packs.add( new Pack( name, description, file ) );

                }
            }
        }
        catch ( Exception e ) {
            System.out.println("Could not read the pack, in readPack()");
        }
    }
}