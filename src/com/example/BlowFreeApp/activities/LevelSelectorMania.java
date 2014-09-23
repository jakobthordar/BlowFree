package com.example.BlowFreeApp.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.BlowFreeApp.GameInfo;
import com.example.BlowFreeApp.PackLevelFactory;
import com.example.BlowFreeApp.Puzzle;
import com.example.BlowFreeApp.R;
import com.example.BlowFreeApp.database.DbHelper;
import com.example.BlowFreeApp.database.GameStatusAdapter;
import java.util.ArrayList;
import java.util.List;


public class LevelSelectorMania extends ListActivity {

    List<Puzzle> mPacksMania = new ArrayList<Puzzle>();
    List<GameInfo> mGameI = new ArrayList<GameInfo>();
    private GameStatusAdapter adapter = new GameStatusAdapter(this);
    private SimpleCursorAdapter mCA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_level_mania);
        //Intent intent = getIntent();

        Cursor cursor = adapter.queryGameStatus();
        String cols[] = DbHelper.TableGameStatusCols;
        String from[] = { cols[1], cols[2], cols[3] };
        int to[] = { R.id.listLevel};

        startManagingCursor( cursor );
        mCA = new SimpleCursorAdapter(this, R.layout.activity_level_mania, cursor, from, to );

        mCA.setViewBinder( new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if ( columnIndex == 2 ) {
                    ((ImageView)view).setImageResource(
                            (cursor.getInt(columnIndex) == 0) ?
                                    R.drawable.emo_im_sad : R.drawable.emo_im_cool );
                    return true;
                }
                return false;
            }
        });
        setListAdapter( mCA );

    }
    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
           // size is always 7 for mania
           startLevelMania(id);
        }
    };

    public void startLevelMania(long id){

        Intent intent = new Intent(this, Game.class);
        int levelId;
        levelId = (int) id;

        Puzzle activeGame = PackLevelFactory.getGameById(levelId);
        PackLevelFactory.setActiveGame(activeGame);
        startActivity(intent);
    }
}
