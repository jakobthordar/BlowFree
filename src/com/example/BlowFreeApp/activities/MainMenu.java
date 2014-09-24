package com.example.BlowFreeApp.activities;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;
import com.example.BlowFreeApp.PackLevelFactory;
import com.example.BlowFreeApp.R;
import com.example.BlowFreeApp.settings.GameSettings;
import java.util.*;

public class MainMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PackLevelFactory packLevelFactory = new PackLevelFactory(this);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        if(pref.getBoolean("sound_Theme_sound", true  )){
            PackLevelFactory.getSoundPlayer().playTheme();
        }

    }

    public void newGame(View view){
        Intent myIntent = new Intent(this, DifficultySelector.class);
        startActivity(myIntent);
    }

    public void continueGame(View view){
        Intent myIntent = new Intent(this, GameInstances.class);
        startActivity(myIntent);
    }
    public void gameSettings(View view){
        Intent myIntent = new Intent(this, GameSettings.class);
        startActivity(myIntent);
    }


}
