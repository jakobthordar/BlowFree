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

    public long insertGameStatusRegular( int gid, boolean finished, String name) {
        String[] cols = DbHelper.TableGameStatusCols;
        ContentValues contentValues = new ContentValues();
        contentValues.put( cols[1], ((Integer)gid).toString() );
        contentValues.put( cols[2], finished ? "1" : "0" );
        contentValues.put( cols[3], name);
        openToWrite();
        long value = db.insert(DbHelper.TableGameStatusRegular, null, contentValues );
        close();
        return value;
    }
    public long insertGameStatusMania( int gid, boolean finished, String name) {
        String[] cols = DbHelper.TableGameStatusCols;
        ContentValues contentValues = new ContentValues();
        contentValues.put( cols[1], ((Integer)gid).toString() );
        contentValues.put( cols[2], finished ? "1" : "0" );
        contentValues.put( cols[3], name);
        openToWrite();
        long value = db.insert(DbHelper.TableGameStatusMania, null, contentValues );
        close();
        return value;
    }

    public long updateGameStatusRegular( int gid, boolean finished, int type ) {
        String[] cols = DbHelper.TableGameStatusCols;
        ContentValues contentValues = new ContentValues();
        contentValues.put( cols[1], ((Integer)gid).toString() );
        contentValues.put( cols[2], finished ? "1" : "0" );
        contentValues.put( cols[3],  ((Integer)type).toString());
        openToWrite();
        long value = db.update(DbHelper.TableGameStatusRegular, contentValues, cols[1] + gid, null );
        close();
        return value;
    }

    public long updateGameStatusMania( int gid, boolean finished, int type ) {
        String[] cols = DbHelper.TableGameStatusCols;
        ContentValues contentValues = new ContentValues();
        contentValues.put( cols[1], ((Integer)gid).toString() );
        contentValues.put( cols[2], finished ? "1" : "0" );
        contentValues.put( cols[3],  ((Integer)type).toString());
        openToWrite();
        long value = db.update(DbHelper.TableGameStatusMania, contentValues, cols[1] + gid, null );
        close();
        return value;
    }

    public Cursor queryGameStatusRegular() {
        openToRead();
        Cursor cursor = db.query( DbHelper.TableGameStatusRegular,
                DbHelper.TableGameStatusCols, null, null, null, null, null);
        return cursor;
    }

    public Cursor queryGameStatusMania() {
        openToRead();
        Cursor cursor = db.query( DbHelper.TableGameStatusMania,
                DbHelper.TableGameStatusCols, null, null, null, null, null);
        return cursor;
    }
}