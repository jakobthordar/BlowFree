package com.example.BlowFreeApp.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Kobblander on 9/22/2014.
 */
public class DifficultySelector extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_level);

        ArrayList<String> array = new ArrayList<String>();
        array.add("Easy");
        array.add("Medium");
        array.add("Hard");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array);

        setListAdapter(adapter);
        getListView().setOnItemClickListener(mMessageClickedHandler);

    }

    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            if (id == 0) {
                easyPack(v);
            }
            if (id == 1) {
                mediumPack(v);
            }
            if (id == 2) {
                hardPack(v);
            }
        }
    };
    public void easyPack(View v) {
        Intent intent = new Intent(this, LevelSelectorEasy.class);
        startActivity(intent);
    }

    public void mediumPack(View view) {
        Intent intent = new Intent(this, LevelSelectorMedium.class);
        startActivity(intent);
    }

    public void hardPack(View view) {
        Intent intent = new Intent(this, LevelSelectorHard.class);
        startActivity(intent);
    }
}
