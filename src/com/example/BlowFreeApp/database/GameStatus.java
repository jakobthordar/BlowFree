package com.example.BlowFreeApp.database;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import com.example.BlowFreeApp.R;

/**
 * Created by yngvi on 15.9.2014.
 */
public class GameStatus extends ListActivity {

    private GameStatusAdapter mSA = new GameStatusAdapter( this );
    private SimpleCursorAdapter mCA;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Cursor cursor = mSA.queryStudents();
        String cols[] = DbHelper.TableGamestatusCols;
        String from[] = { cols[1], cols[2], cols[3] };
        //int to[] = { R.id.s_sid, R.id.s_name, R.id.s_cool };
        startManagingCursor( cursor );
        //mCA = new SimpleCursorAdapter(this, R.layout.row, cursor, from, to );

        mCA.setViewBinder( new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if ( columnIndex == 3 ) {
                    /*((ImageView)view).setImageResource(
                            (cursor.getInt(columnIndex) == 0) ?
                                    R.drawable.emo_im_sad : R.drawable.emo_im_cool );*/
                    return true;
                }
                return false;
            }
        });
        setListAdapter( mCA );
    }
}