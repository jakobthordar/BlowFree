package com.example.BlowFreeApp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.example.BlowFreeApp.R;

public class Settings extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Intent intent = getIntent();
    }
}
