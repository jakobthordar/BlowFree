package com.example.BlowFreeApp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yngvi on 15.9.2014.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "GAMESTATUS_DB";
    public static final int DB_VERSION = 5;


    public static final String TableGameStatusEasy = "gameStatusEasy";
    public static final String TableGameStatusMedium = "gameStatusMedium";
    public static final String TableGameStatusHard = "gameStatusHard";

    /**
     * The cols are identical in the tables
     */
    public static final String[] TableGameStatusCols = { "_id", "gid", "finished", "name" };


    private static final String sqlCreateTableGameStatusEasy =
            "CREATE TABLE gameStatusEasy(" +
                    " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " gid INTEGER NOT NULL," +
                    " finished INTEGER NOT NULL," +
                    " name TEXT" +
                    ");";

    private static final String sqlCreateTableGameStatusMedium =
            "CREATE TABLE gameStatusMedium(" +
                    " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " gid INTEGER NOT NULL," +
                    " finished INTEGER NOT NULL," +
                    " name TEXT" +
                    ");";

    private static final String sqlCreateTableGameStatusHard =
            "CREATE TABLE gameStatusHard(" +
                    " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " gid INTEGER NOT NULL," +
                    " finished INTEGER NOT NULL," +
                    " name TEXT" +
                    ");";

    private static final String sqlDropTableGameStatusEasy =
            "DROP TABLE IF EXISTS gameStatusEasy;";

    private static final String sqlDropTableGameStatusMedium =
            "DROP TABLE IF EXISTS gameStatusMedium;";

    private static final String sqlDropTableGameStatusHard =
            "DROP TABLE IF EXISTS gameStatusHard;";

    public DbHelper( Context context ) {
        super( context, DB_NAME, null, DB_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( sqlCreateTableGameStatusEasy );
        db.execSQL( sqlCreateTableGameStatusMedium );
        db.execSQL( sqlCreateTableGameStatusHard );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( sqlDropTableGameStatusEasy );
        db.execSQL( sqlDropTableGameStatusMedium );
        db.execSQL( sqlDropTableGameStatusHard );
        onCreate( db );
    }
}