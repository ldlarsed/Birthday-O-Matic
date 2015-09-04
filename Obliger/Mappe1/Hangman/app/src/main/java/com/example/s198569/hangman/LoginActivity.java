package com.example.s198569.hangman;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.s198569.hangman.lib.HangmanDataSource;
import com.example.s198569.hangman.lib.HangmanDatabase;
import com.example.s198569.hangman.lib.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private HangmanDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* Connecting to the SQLite database */

        datasource = new HangmanDataSource(this);
        datasource.open();
        Player newPlayer = datasource.createPlayer("Luke");
        Toast p = Toast.makeText(this, newPlayer.toString() + " created", Toast.LENGTH_SHORT);
        p.show();


/*        try {

            SQLiteOpenHelper hangmanDatabase = new HangmanDatabase(this);
            db = hangmanDatabase.getReadableDatabase();

            //Fetching list of existing players
            cursor = db.query("PLAYERS", new String[] {"NAME"}, null, null, null, null, null);

            CursorAdapter listAdapter = new SimpleCursorAdapter(this,
                                            android.R.layout.simple_list_item_1,
                                            cursor,
                                            new String[]{"NAME", "SCORE"},
                                            new int[] {android.R.id.text1},
                                            0);

            existingPlayersList.setAdapter(listAdapter);

        }catch(SQLiteException e){
            Log.d("HANGMAN", "Cannot read database: " + e.getMessage());
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }*/
        /* End of SQLite database connection */


        /* Existing Players List*/
        ListView existingPlayersList = (ListView) findViewById(R.id.existing_players_list);
        ArrayList<Player> registeredPlayers = (ArrayList<Player>) datasource.getAllPlayers();
        String[] playerNames = datasource.getAllPlayerNames();
        ArrayAdapter<String> playersAdapter = new ArrayAdapter<String>(this, R.layout.listview_item_existing_players, playerNames);
        existingPlayersList.setAdapter(playersAdapter);


        /* End of existing players list */


    }


    @Override
    protected void onResume(){
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause(){
        datasource.close();
        super.onPause();
    }

    @Override
    protected void onDestroy(){
        datasource.close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Starts a game with a new player and registers this new player in a SQLlite database
     * @param view
     */
    public void onPlayGameButtonClick(View view){

        EditText inputNewUserName = (EditText) findViewById(R.id.edit_text_new_player);
        String newUserName = inputNewUserName.getText().toString();

        //Local check of regex
        Toast toast;
        if(newUserName.matches(getString(R.string.regex_all_alphanumeric))){
            toast = Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT);

        }else {
            toast = Toast.makeText(getApplicationContext(), "Enter valid user name", Toast.LENGTH_SHORT);
        }
        toast.show();
    }


}
