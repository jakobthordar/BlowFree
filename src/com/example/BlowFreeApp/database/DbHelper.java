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


    public static final String TableGameStatusRegular = "gameStatusRegular";
    public static final String TableGameStatusMania = "gameStatusMania";
    /**
     * The cols are identical in the tables
     */
    public static final String[] TableGameStatusCols = { "_id", "gid", "finished", "name" };


    private static final String sqlCreateTableGameStatusRegular =
            "CREATE TABLE gameStatusRegular(" +
                    " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " gid INTEGER NOT NULL," +
                    " finished INTEGER NOT NULL," +
                    " name TEXT" +
                    ");";

    private static final String sqlCreateTableGameStatusMania =
            "CREATE TABLE gameStatusMania(" +
                    " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " gid INTEGER NOT NULL," +
                    " finished INTEGER NOT NULL," +
                    " name TEXT" +
                    ");";

    private static final String sqlDropTableGameStatusRegular =
            "DROP TABLE IF EXISTS gameStatusRegular;";

    private static final String sqlDropTableGameStatusMania =
            "DROP TABLE IF EXISTS gameStatusMania;";

    public DbHelper( Context context ) {
        super( context, DB_NAME, null, DB_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( sqlCreateTableGameStatusRegular );
        db.execSQL( sqlCreateTableGameStatusMania );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( sqlDropTableGameStatusRegular );
        db.execSQL( sqlDropTableGameStatusMania );
        onCreate( db );
    }
}