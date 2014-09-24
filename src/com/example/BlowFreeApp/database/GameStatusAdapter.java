package com.example.BlowFreeApp.database;
import android.database.sqlite.SQLiteDatabase;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * Created by yngvi on 15.9.2014.
 */
public class GameStatusAdapter {

    private SQLiteDatabase db;
    private DbHelper dbHelper;
    private Context context;
    private String[] cols = DbHelper.TableGameStatusCols;

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

    /**
     * Updates the database with given values
     * @param tableGameStatus Each Puzzle has an instance of table game status
     */
    public long insertGameStatus(String tableGameStatus, int gid, boolean finished, String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put( cols[1], ((Integer)gid).toString() );
        contentValues.put( cols[2], finished ? "1" : "0" );
        contentValues.put( cols[3], name);
        openToWrite();
        long value = db.insert(tableGameStatus, null, contentValues );
        close();
        return value;
    }

    public long insertActiveGame(int gid) {
        ContentValues contentValues = new ContentValues();
        contentValues.put( "gid", ((Integer)gid).toString() );
        openToWrite();
        long value = db.insert(DbHelper.TableActiveGame, null, contentValues );
        close();
        return value;
    }

    /**
     * Updates the database with given values
     * @param tableGameStatus Each Puzzle has an instance of table game status
     */
    public long updateGameStatus(String tableGameStatus, int gid, boolean finished, int type) {
        ContentValues contentValues = new ContentValues();
        contentValues.put( cols[1], ((Integer)gid).toString() );
        contentValues.put( cols[2], finished ? "1" : "0" );
        contentValues.put( cols[3],  ((Integer)type).toString());
        openToWrite();
        long value = db.update(tableGameStatus, contentValues, cols[1] + "=" + gid, null);
        close();
        return value;
    }

    public long updateActiveGame(int gid) {
        ContentValues contentValues = new ContentValues();
        contentValues.put( "gid", ((Integer)gid).toString() );
        openToWrite();
        long value = db.update(DbHelper.TableActiveGame, contentValues, "gid" + "=" + gid, null);
        close();
        return value;
    }

    public Cursor queryActiveGame() {
        openToRead();
        Cursor cursor = db.query( DbHelper.TableActiveGame,
                cols, null, null, null, null, null);
        return cursor;
    }

    public Cursor queryGameStatus(String tableGameStatus) {
        openToRead();
        Cursor cursor = db.query( tableGameStatus,
                cols, null, null, null, null, null);
        return cursor;
    }

    public Cursor queryGameStatusId(String tableGameStatus, int id) {
        openToRead();
        Cursor cursor = db.query( tableGameStatus,
                cols, cols[1] + "=" + id, null, null, null, null, null);
        return cursor;
    }
}