package com.example.BlowFreeApp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.BlowFreeApp.R;


public class MainMenu extends Activity {

    public String EXTRA_MESSAGE = "level 1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void newGame(View view){
        Intent myIntent = new Intent(this, Level.class);
        startActivity(myIntent);
    }

    public void continueGame(View view){
        Intent myIntent = new Intent(this, GameInstances.class);
        startActivity(myIntent);
    }
    public void gameSettings(View view){
        Intent myIntent = new Intent(this, Settings.class);
        startActivity(myIntent);
    }
}