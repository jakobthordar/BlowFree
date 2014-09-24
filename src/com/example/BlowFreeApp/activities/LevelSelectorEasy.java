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

public class LevelSelectorEasy extends Activity {

    private GameStatusAdapter db = PackLevelFactory.getGameStatusAdapter();
    private Cursor cursor;
    private SimpleCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_easy);

        GridView gridView = (GridView) findViewById(R.id.gridLevelEasy);

        cursor = db.queryGameStatus(DbHelper.TableGameStatusEasy);
        String cols[] = DbHelper.TableGameStatusCols;
        String from[] = { cols[1], cols[2], cols[3] };
        int to[] = { android.R.id.text1 , android.R.id.text1, android.R.id.text1 };
        cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, from, to);

        cursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                TextView tv;
                tv = (TextView) view;
                String columnName = cursor.getColumnName(columnIndex);
                //TODO: OFFBYONE
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
                    return true;
                }
                return false;
            }
        });



        gridView.setAdapter(cursorAdapter);
        gridView.setOnItemClickListener(mMessageClickedHandler);
    }

    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            // size is always 7 for mania
            startLevel((int)id);
        }
    };
    public void startLevel(int id){
        Intent myIntent = new Intent(this, Game.class);
        Puzzle activeGame = PackLevelFactory.getEasyGame(id);
        PackLevelFactory.setActiveGame(activeGame);
        startActivity(myIntent);
    }
}


