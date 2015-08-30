package com.example.s198569.hangman;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class GamePlay extends AppCompatActivity {

    private RelativeLayout gameLayout;
    private TextView playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        //Initializes intent the starts this activity
        Intent intent = getIntent();
        String playerNameString = intent.getStringExtra("userName");

        //Sets the player name in the north west corner
        playerName = (TextView) findViewById(R.id.playerName);
        playerName.setText(playerNameString);

        //Fetches the keyboard values for the default language
        String[] kb_values = getResources().getStringArray(R.array.alphabet);

        gameLayout = (RelativeLayout) findViewById(R.id.gameplay_layout);

        //Sets up the keyboard inside a gridview
        GridLayout keyboard = (GridLayout) findViewById(R.id.keboard_layout);
        for(String kb : kb_values){
            Button b = new Button(this);
            //b.setBackgroundColor(getResources().getColor(R.color.secondary_1_1));
            b.setTextColor(getResources().getColor(R.color.primary_4));
            b.setText(kb);
            b.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            keyboard.addView(b);
        }

        //Loads the available word list
        //TODO: Find more effective way to get word directly from the xml array
        String[] words = getResources().getStringArray(R.array.words);
        Random rand = new Random();
        int chosen_index = rand.nextInt((words.length - 0) + 1) + 0;
        char[] letters = words[chosen_index].toCharArray();
        //Sluttet her. Her skal hver bokstav som vi har legges i en textedit som i sin tur skal legges en etter den linear layout som alerede er lagt til.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_play, menu);
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        View decorView = getWindow().getDecorView();

        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }
}
