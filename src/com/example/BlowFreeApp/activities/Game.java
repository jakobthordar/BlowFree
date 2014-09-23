package com.example.BlowFreeApp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.example.BlowFreeApp.PackLevelFactory;
import com.example.BlowFreeApp.R;


public class Game extends Activity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {

        /* Board class is called here through the activity view */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();

    }


}
