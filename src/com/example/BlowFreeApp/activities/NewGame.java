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

/**
 * NOT USED
 * Created by oen on 11.9.2014.
 */
public class NewGame extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        Intent intent = getIntent();
    }


}