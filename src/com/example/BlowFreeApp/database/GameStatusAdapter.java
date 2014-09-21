package com.example.BlowFreeApp.database;
import android.database.sqlite.SQLiteDatabase;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * Created by yngvi on 15.9.2014.
 */
public class GameStatusAdapter {

    SQLiteDatabase db;
    DbHelper dbHelper;
    Context context;

    public GameStatusAdapter( Context c ) {
        context = c;
    }

    public GameStatusAdapter openToRead() {
        dbHelper = new DbHelper( context );
        db = dbHelper.getReadableDatabase();
        return this;
    }

    public GameStatusAdapter openToWrite() {
        dbHelper = new DbHelper( context );
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public long insertGameStatus( int gid, boolean finished, int type) {
        String[] cols = DbHelper.TableGamestatusCols;
        ContentValues contentValues = new ContentValues();
        contentValues.put( cols[1], ((Integer)gid).toString() );
        contentValues.put( cols[2], finished ? "1" : "0" );
        contentValues.put( cols[3],  ((Integer)type).toString());
        openToWrite();
        long value = db.insert(DbHelper.TableGameStatus, null, contentValues );
        close();
        return value;
    }

    public long updateGameStatus( int gid, boolean finished, int type ) {
        String[] cols = DbHelper.TableGamestatusCols;
        ContentValues contentValues = new ContentValues();
        contentValues.put( cols[1], ((Integer)gid).toString() );
        contentValues.put( cols[2], finished ? "1" : "0" );
        contentValues.put( cols[3],  ((Integer)type).toString());
        openToWrite();
        long value = db.update(DbHelper.TableGameStatus, contentValues, cols[1] + gid, null );
        close();
        return value;
    }

    public Cursor queryStudents() {
        openToRead();
        Cursor cursor = db.query( DbHelper.TableGameStatus,
                DbHelper.TableGamestatusCols, null, null, null, null, null);
        return cursor;
    }

    public Cursor queryStudent( int sid) {
        openToRead();
        String[] cols = DbHelper.TableGamestatusCols;
        Cursor cursor = db.query( DbHelper.TableGameStatus,
                cols, cols[1] + "" + sid, null, null, null, null);
        return cursor;
    }

}