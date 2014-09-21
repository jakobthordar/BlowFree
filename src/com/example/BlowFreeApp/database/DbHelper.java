package com.example.BlowFreeApp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yngvi on 15.9.2014.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "GAMESTATUS_DB";
    public static final int DB_VERSION = 1;

    public static final String TableGameStatus = "gameStatus";
    public static final String[] TableGamestatusCols = { "_id", "gid", "finished", "type" };

    private static final String sqlCreateTableGameStatus =
            "CREATE TABLE students(" +
                    " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " gid INTEGER NOT NULL," +
                    " finished INTEGER NOT NULL," +
                    " type INTEGER NOT NULL" +
                    ");";

    private static final String sqlDropTableGameStatus =
            "DROP TABLE IF EXISTS gameStatus;";

    public DbHelper( Context context ) {
        super( context, DB_NAME, null, DB_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( sqlCreateTableGameStatus );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( sqlDropTableGameStatus );
        onCreate( db );
    }
}