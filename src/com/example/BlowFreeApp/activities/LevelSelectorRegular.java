package com.example.BlowFreeApp.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.BlowFreeApp.PackLevelFactory;
import com.example.BlowFreeApp.Puzzle;
import com.example.BlowFreeApp.R;
import com.example.BlowFreeApp.database.GameStatusAdapter;

import java.util.List;

public class LevelSelectorRegular extends Activity {

    private GameStatusAdapter adapter = new GameStatusAdapter(this);
    private int sizeOfBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selector);
        //Intent intent = getIntent();


        ArrayAdapter<Puzzle> adapt = new ArrayAdapter<Puzzle>(this,
                android.R.layout.simple_list_item_1, PackLevelFactory.getRegularLevels());

        List<Puzzle> regularLevels = PackLevelFactory.getRegularLevels();
        ListView listView = (ListView) findViewById(R.id.listLevel);
        listView.setAdapter(adapt);

        listView.setOnItemClickListener(mMessageClickedHandler);
    }

    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            // Sets the correct game id of the game.
            if (id <= 9) {
                sizeOfBoard = 5;
            }
            if (id > 9) {
                sizeOfBoard = 6;
            }
            startLevel(id);
        }
    };

    public void startLevel(long id){
        Intent myIntent = new Intent(this, Game.class);
        int gameId;
        gameId = (int) id;

        Puzzle activeGame = PackLevelFactory.getGameById(gameId);
        PackLevelFactory.setActiveGame(activeGame);

        startActivity(myIntent);
    }


}


