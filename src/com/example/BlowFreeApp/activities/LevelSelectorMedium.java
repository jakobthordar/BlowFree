package com.example.BlowFreeApp.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.example.BlowFreeApp.PackLevelFactory;
import com.example.BlowFreeApp.Puzzle;
import com.example.BlowFreeApp.R;
import com.example.BlowFreeApp.database.DbHelper;
import com.example.BlowFreeApp.database.GameStatusAdapter;

/**
 * Created by Kobblander on 9/22/2014.
 */
public class LevelSelectorMedium extends Activity{

    private GameStatusAdapter db = PackLevelFactory.getGameStatusAdapter();
    private Cursor cursor;
    private SimpleCursorAdapter cursorAdapter;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_medium);

        cursor = db.queryGameStatus(DbHelper.TableGameStatusMedium);
        gridView = (GridView) findViewById(R.id.gridLevelMedium);

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

    @Override
    protected void onResume() {
        super.onResume();
        String cols[] = DbHelper.TableGameStatusCols;
        String from[] = { cols[1], cols[2], cols[3] };
        int to[] = { android.R.id.text1 , android.R.id.text1, android.R.id.text1 };
        cursor = db.queryGameStatus(DbHelper.TableGameStatusMedium);
        cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, from, to);
        cursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                TextView tv;
                tv = (TextView) view;
                // Style
                tv.setBackgroundResource(R.drawable.cell_border);
                tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

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
    }

    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            // Reduce by one because onClick starts at 1
            startLevel((int)id - 1);
        }
    };

    public void startLevel(int id){
        Intent myIntent = new Intent(this, Game.class);
        Puzzle activeGame = PackLevelFactory.getMediumGame(id);
        PackLevelFactory.setActiveGame(activeGame);
        startActivity(myIntent);
    }

}
