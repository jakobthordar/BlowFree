package com.example.BlowFreeApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class GameActivity extends Activity {

    private Global mGlobals = Global.getInstance();

        @Override
        protected void onCreate(Bundle savedInstanceState) {

        /* Board class is called here through the activity view */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();

    }


}
