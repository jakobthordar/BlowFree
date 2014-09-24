package com.example.BlowFreeApp.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import com.example.BlowFreeApp.PackLevelFactory;
import com.example.BlowFreeApp.Puzzle;
import com.example.BlowFreeApp.R;

public class LevelSelectorEasy extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_easy);
        ArrayAdapter<Puzzle> adapt = new ArrayAdapter<Puzzle>(this, android.R.layout.simple_list_item_1, PackLevelFactory.getEasyLevels());

        GridView gridView = (GridView) findViewById(R.id.listLevelEasy);
        gridView.setAdapter(adapt);

        gridView.setOnItemClickListener(mMessageClickedHandler);
    }

    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            startLevel(id);
        }
    };

    public void startLevel(long id){
        Intent myIntent = new Intent(this, Game.class);
        int gameId;
        gameId = (int) id;

        Puzzle activeGame = PackLevelFactory.getEasyGame(gameId);
        PackLevelFactory.setActiveGame(activeGame);

        startActivity(myIntent);
    }
}


