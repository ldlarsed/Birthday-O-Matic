package com.example.s198569.hangman.lib;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class HangmanDataSource {

    private SQLiteDatabase database;
    private HangmanDatabase dbHelper;
    private String[] allColumns = {HangmanDatabase.COLUMN_ID, HangmanDatabase.COLUMN_NAME, HangmanDatabase.COLUMN_SCORE};
    private String[] allPlayerNames = {HangmanDatabase.COLUMN_NAME};

    public HangmanDataSource(Context context){

        dbHelper = new HangmanDatabase(context);
    }

    /**
     * Opens a writable database
     * @throws SQLiteException
     */
    public void open() throws SQLiteException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * Creates new player entry in the database.
     * @param name
     * @return
     */
    public Player createPlayer(String name){
        ContentValues values = new ContentValues();
        values.put(HangmanDatabase.COLUMN_NAME, name);
        values.put(HangmanDatabase.COLUMN_SCORE, 0);


        if(database.insert(HangmanDatabase.TABLE_PLAYERS, null, values) < 0){
            Log.d("HANGMAN", "Coludnt insert");
        }else{
            Log.d("HANGMAN", "Insertion succesfull");
        }
        long insertId = database.insert(HangmanDatabase.TABLE_PLAYERS, null, values);
        Cursor cursor = database.query(HangmanDatabase.TABLE_PLAYERS,
                allColumns, HangmanDatabase.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Player newPlayer = cursorToPlayer(cursor);
        cursor.close();
        return newPlayer;
    }

    /**
     * Returns an ArrayList of players stored in database.
     * @return
     */
    public List<Player> getAllPlayers(){
        List<Player> players = new ArrayList<Player>();

        Cursor cursor = database.query(HangmanDatabase.TABLE_PLAYERS, allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Player player = cursorToPlayer(cursor);
            players.add(player);
            cursor.moveToNext();
        }

        cursor.close();
        return players;
    }

    /**
     * Extracts player names and returns as string array.
     * @return
     */
    public String[] getAllPlayerNames(){
        List<String> players = new ArrayList<String>();

        Cursor cursor = database.query(HangmanDatabase.TABLE_PLAYERS, allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Player player = cursorToPlayer(cursor);
            players.add(player.getName());
            cursor.moveToNext();
        }
        cursor.close();

        String[] navn = players.toArray(new String[players.size()]);
        Log.d("HANGMAN", navn.toString());

        return players.toArray(new String[players.size()]);
    }

    /**
     * Moves the SQLite cursor ti the current selected player.
     * @param cursor
     * @return
     */
    private Player cursorToPlayer(Cursor cursor){
        Player player = new Player();
        player.setName(cursor.getString(1));
        player.setScore(cursor.getInt(2));
        return player;
    }

}
