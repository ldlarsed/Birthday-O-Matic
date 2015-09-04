package com.example.s198569.hangman.lib;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.Log;
import android.widget.Toast;

public class HangmanDatabase extends SQLiteOpenHelper {

    public static final String TABLE_PLAYERS = "PLAYERS";
    public static final String COLUMN_ID = "_ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_SCORE = "SCORE";

    private static final String DB_NAME = "hangmanDB";
    private static final int DB_VERSION = 2;


    //Creation of the database
    private static final String DATABASE_CREATE = "create table " + TABLE_PLAYERS + "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_NAME + " text not null, " +
            COLUMN_SCORE + " integer);";


    public HangmanDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
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
