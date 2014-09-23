package com.example.BlowFreeApp.activities;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.BlowFreeApp.PackLevelFactory;
import com.example.BlowFreeApp.R;

import java.util.List;


public class GameInstances extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_instances);
        Intent intent = getIntent();
    }


}
