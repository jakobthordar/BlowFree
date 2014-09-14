package com.example.BlowFreeApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LevelActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        Intent intent = getIntent();

    }

    public void LevelOne(View view){
        Intent myIntent = new Intent(this, GameActivity.class);
        startActivity(myIntent);
    }

}
