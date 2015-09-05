package com.example.s198569.hangman;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s198569.hangman.lib.HangmanDataSource;
import com.example.s198569.hangman.lib.HangmanDatabase;
import com.example.s198569.hangman.lib.Player;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
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
        try {
            datasource.open();
        }catch (Exception e){
            Toast toast = Toast.makeText(this, "Could not connect to the database.", Toast.LENGTH_SHORT);
            toast.show();
        }


        /* Existing Players List*/
        ListView existingPlayersList = (ListView) findViewById(R.id.existing_players_list);
        ArrayList<Player> registeredPlayers = (ArrayList<Player>) datasource.getAllPlayers();
        if(registeredPlayers == null){
            String[] playerNames = {"No registered players"};
        }else {

            String[] playerNames = datasource.getAllPlayerNames();
            ArrayAdapter<String> playersAdapter = new ArrayAdapter<String>(this, R.layout.listview_item_existing_players, playerNames);
            existingPlayersList.setAdapter(playersAdapter);

        /* End of existing players list */


        /* Listener for existing players list view */
            existingPlayersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                    TextView textView = (TextView) itemClicked;
                    String playerName = textView.getText().toString();
                    Intent playGame = new Intent(LoginActivity.this, GamePlayActivity.class);
                    playGame.putExtra("pName", playerName);
                    startActivity(playGame);
                }
            });
        }
        /* End of Listener for existing players list view */
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
            //toast = Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT);
            EditText playerInput = (EditText) findViewById(R.id.edit_text_new_player);
            //Saves the name to the database and retrives it to verify that everything worked ok
            Player newPlayer = datasource.createPlayer(playerInput.getText().toString());

            toast = Toast.makeText(this, "Player " + newPlayer.getName() + " created", Toast.LENGTH_SHORT);
        }else {
            toast = Toast.makeText(getApplicationContext(), "Enter valid user name", Toast.LENGTH_SHORT);
        }
        toast.show();
    }


}
