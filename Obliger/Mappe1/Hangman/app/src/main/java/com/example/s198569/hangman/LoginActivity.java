package com.example.s198569.hangman;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s198569.hangman.lib.HangmanDataSource;
import com.example.s198569.hangman.lib.Player;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private HangmanDataSource datasource;
    private String playerName;

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
                    playerName = textView.getText().toString();
                    showContinueDialog();
                }
            });
        }
        /* End of Listener for existing players list view */
    }

    /**
     * Shows a continue dialog for the player. It gives posibility to revert the choosen playername in case of wrong click.
     */
    private void showContinueDialog(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent playGame = new Intent(LoginActivity.this, GamePlayActivity.class);
                        playGame.putExtra("pName", playerName);
                        startActivity(playGame);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Play as " + playerName + "?").setPositiveButton("OK", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener).show();
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
