package com.example.s198569.hangman.lib;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class HangmanDataSource {

    private SQLiteDatabase database;    //SQLite database
    private HangmanDatabase dbHelper;   //Own datasource
    private String[] allColumns = {HangmanDatabase.COLUMN_ID, HangmanDatabase.COLUMN_PNAME, HangmanDatabase.COLUMN_SCORE, HangmanDatabase.COLUMN_WON, HangmanDatabase.COLUMN_LOST};
    private String[] allPlayerNames = {HangmanDatabase.COLUMN_PNAME};

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

    /**
     * Fetches total score for a specific player by name.
     * @param playerName
     * @return
     */
    private int getTotalPlayerScore(String playerName){
        String query = "SELECT * FROM PLAYERS WHERE PNAME='"+playerName+"'";
        Cursor c = database.rawQuery(query, new String[]{});
        c.moveToFirst();
        return c.getInt(2);
    }

    /**
     * Fetches total stats for a specific player by name.
     * @param playerName
     * @return
     */
    private int[] getTotalPlayerStats(String playerName){
        String query = "SELECT * FROM PLAYERS WHERE PNAME='"+playerName+"'";
        Cursor c = database.rawQuery(query, new String[]{});
        c.moveToFirst();
        int[] pStats = new int[] {c.getInt(2), c.getInt(3), c.getInt(4)};
        return pStats;
    }

    /**
     * Updates the score for a specific player.
     * @param playerName
     * @param scoreToAdd
     */
    public void updateScore(String playerName, int scoreToAdd){
        int currentScore = getTotalPlayerScore(playerName);
        currentScore += scoreToAdd;

        ContentValues cv = new ContentValues();
        //cv.put(HangmanDatabase.COLUMN_PNAME, playerName);
        cv.put(HangmanDatabase.COLUMN_SCORE, currentScore);
        database.update(HangmanDatabase.TABLE_PLAYERS, cv, HangmanDatabase.COLUMN_PNAME + "='" + playerName + "'", null);

        //Alternative code to update the row with raw sql query. Not tested.
        //String updateSQL = "UPDATE PLAYERS SET SCORE = " + String.valueOf(currentScore) + " WHERE NAME = "+ playerName;
        //database.execSQL(updateSQL);
    }

    /**
     * Updates all stats for a specific player given the player database lookup name.
     * @param playerName
     * @param scoreToAdd
     * @param gamesWon
     * @param gamesLost
     */
    public void updateStats(String playerName, int scoreToAdd, int gamesWon, int gamesLost){
        int[] currentScore = getTotalPlayerStats(playerName);
        currentScore[0] += scoreToAdd;
        currentScore[1] += gamesWon;
        currentScore[2] += gamesLost;

        ContentValues cv = new ContentValues();
        cv.put(HangmanDatabase.COLUMN_SCORE, currentScore[0]);
        cv.put(HangmanDatabase.COLUMN_WON, currentScore[1]);
        cv.put(HangmanDatabase.COLUMN_LOST, currentScore[2]);

        database.update(HangmanDatabase.TABLE_PLAYERS, cv, HangmanDatabase.COLUMN_PNAME + "='" + playerName + "'", null);
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
        values.put(HangmanDatabase.COLUMN_PNAME, name);
        values.put(HangmanDatabase.COLUMN_SCORE, 0);
        values.put(HangmanDatabase.COLUMN_WON, 0);
        values.put(HangmanDatabase.COLUMN_LOST, 0);

        long insertId = database.insert(HangmanDatabase.TABLE_PLAYERS, null, values);

        if(insertId < 0){
            Log.d("HANGMAN", "Coludnt insert");
        }else{
            Log.d("HANGMAN", "Insertion succesfull");
        }

        Cursor cursor = database.query(HangmanDatabase.TABLE_PLAYERS,
                allColumns, HangmanDatabase.COLUMN_ID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();
        Player newPlayer = cursorToPlayer(cursor);
        cursor.close();

        return newPlayer;
    }

    /**
     * Returns an ArrayList of players stored in database. Is used to show the scores for all players. Results are sorted by the
     * total score for the user.
     * @return
     */
    public List<Player> getAllPlayers(){
        List<Player> players = new ArrayList<Player>();

        Cursor cursor = database.query(HangmanDatabase.TABLE_PLAYERS, allColumns, null, null, null, null, HangmanDatabase.COLUMN_SCORE + " DESC");
        if(cursor == null ) return null;
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

        //Log.w("HANGMAN", Arrays.toString(players.toArray(new String[players.size()])));

        return players.toArray(new String[players.size()]);
    }

    /**
     * Moves the SQLite cursor ti the current selected player.
     * @param cursor
     * @return
     */
    private Player cursorToPlayer(Cursor cursor){
        Player player = new Player();
//        Log.w("HANGMAN", "Antall rader: " + cursor.getCount());
//        Log.w("HANGMAN", "Antall kolonner: " + cursor.getColumnCount());
//        Log.w("HANGMAN", "cursor.getInt(2) " + cursor.getString(1));
//        Log.w("HANGMAN", "cursor.getInt(2) " + cursor.getInt(2));
//        Log.w("HANGMAN", "cursor.getInt(2) " + cursor.getInt(3));
//        Log.w("HANGMAN", "cursor.getInt(2) " + cursor.getInt(4));
        player.setName(cursor.getString(1));
        player.setScore(cursor.getInt(2));
        player.setWon(cursor.getInt(3));
        player.setLost(cursor.getInt(4));
        return player;
    }

}
