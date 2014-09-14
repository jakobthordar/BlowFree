package com.example.BlowFreeApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
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