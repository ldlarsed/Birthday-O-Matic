package com.example.s198569.hangman.lib;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class HangmanDatabase extends SQLiteOpenHelper {

    public static final String TABLE_PLAYERS = "PLAYERS";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PNAME = "PNAME";
    public static final String COLUMN_SCORE = "SCORE";
    public static final String COLUMN_WON = "WON";
    public static final String COLUMN_LOST = "LOST";

    private static final String DB_NAME = "hangmanDB";
    private static final int DB_VERSION = 1;


    //Creation of the database
    private static final String DATABASE_CREATE =
            "create table " + TABLE_PLAYERS + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_PNAME + " text not null, " +
                    COLUMN_SCORE + " integer, " +
                    COLUMN_WON + " integer, " +
                    COLUMN_LOST + " integer" +
                    ");";


    public HangmanDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(DATABASE_CREATE);
        } catch (Exception e) {
            Log.d("HANGMAN", "Database cannot be created");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(HangmanDatabase.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
        onCreate(db);
    }

}
