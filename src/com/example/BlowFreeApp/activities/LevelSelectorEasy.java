package com.example.BlowFreeApp.activities;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.BlowFreeApp.PackLevelFactory;
import com.example.BlowFreeApp.Puzzle;
import com.example.BlowFreeApp.R;
import com.example.BlowFreeApp.database.DbHelper;
import com.example.BlowFreeApp.database.GameStatusAdapter;

import java.util.ArrayList;
import java.util.List;

public class LevelSelectorEasy extends Activity {

    private GameStatusAdapter db = PackLevelFactory.getGameStatusAdapter();
    private Cursor cursor;
    private SimpleCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_easy);
        List<String> gid = new ArrayList<String>();
        List<String> finished = new ArrayList<String>();
        List<String> name = new ArrayList<String>();

        cursor = db.queryGameStatus(DbHelper.TableGameStatusEasy);
        int i = 0;

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            String gidS = cursor.getString(1);
            String finishedS = cursor.getString(2);
            String nameS = cursor.getString(3);
            gid.add(gidS);
            gid.add(finishedS);
            gid.add(nameS);
            i++;
            cursor.moveToNext();
            System.out.println("Gid: " + gidS + ", Finished: " + finishedS + ", Name: " + nameS);
        }


        GridView gridView = (GridView) findViewById(R.id.gridLevelEasy);


        String cols[] = DbHelper.TableGameStatusCols;
        String from[] = { cols[1], cols[2], cols[3] };
        int to[] = { android.R.id.text1 , android.R.id.text1, android.R.id.text1 };
        cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, from, to);

        cursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                TextView tv;
                tv = (TextView) view;

                if (columnIndex == 1) {
                    Integer i = cursor.getInt(1) + 1;
                    tv.setText(i.toString());
                }
                if (columnIndex == 2) {
                    int finished = cursor.getInt(columnIndex);
                    if (finished == 0) {
                        tv.setTextColor(Color.WHITE);
                    }
                    else {
                        tv.setTextColor(Color.GREEN);
                    }
                }
                return true;
            }
        });



        gridView.setAdapter(cursorAdapter);
        gridView.setOnItemClickListener(mMessageClickedHandler);
    }

    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            // size is always 7 for mania
            startLevel((int)id - 1);
        }
    };
    public void startLevel(int id){
        Intent myIntent = new Intent(this, Game.class);
        Puzzle activeGame = PackLevelFactory.getEasyGame(id);
        PackLevelFactory.setActiveGame(activeGame);
        startActivity(myIntent);
    }
}


