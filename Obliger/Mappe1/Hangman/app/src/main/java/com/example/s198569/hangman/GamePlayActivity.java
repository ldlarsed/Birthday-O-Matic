package com.example.s198569.hangman;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GamePlayActivity extends AppCompatActivity {

    private RelativeLayout gameLayout;
    private TextView playerName;
    private char[] letters;
    private LinearLayout wordsLayout;
    private GridLayout keyboard;
    private int lettersCount;
    private ArrayList<EditText> edComponents; //Current collection of letter placeholders
    private int score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        //Initializes intent the starts this activity
        Intent intent = getIntent();
        String playerNameString = intent.getStringExtra("pName");

        setPlayerName(playerNameString);
        score = 0;
        setKeyboard();
        setWord();
    }

    /**
     * Sets the player name in the north west corner
     * @param n
     */
    private void setPlayerName(String n){
        playerName = (TextView) findViewById(R.id.playerName);
        playerName.setText(n);
    }

    private void setWord(){
        lettersCount = 0;
        edComponents = new ArrayList<>();

        //Loads the available word list
        //TODO: Find more effective way to get word directly from the xml array
        String[] words = getResources().getStringArray(R.array.words);
        Random rand = new Random();
        int chosen_index = rand.nextInt((words.length - 0) + 1) + 0;
        Log.w("HANGMAN", words[chosen_index]);
        letters = words[chosen_index].toCharArray();

        wordsLayout = (LinearLayout) findViewById(R.id.word_layout);
        for(char c : letters){
            final EditText et = new EditText(this);
            //et.setText(Character.toString(c));
            et.setEnabled(false);
            edComponents.add(et);
            wordsLayout.addView(et);
            lettersCount++;
        }
        Log.w("HANGMAN", "Antall EditText: " + lettersCount);
    }

    private void setKeyboard(){
        //Fetches the keyboard values for the default language
        String[] kb_values = getResources().getStringArray(R.array.alphabet);

        gameLayout = (RelativeLayout) findViewById(R.id.gameplay_layout);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;
        int screenWidth = displaymetrics.widthPixels;

        //Sets up the keyboard inside a gridview
        keyboard = (GridLayout) findViewById(R.id.keboard_layout);
        for(String kb : kb_values){
            final Button b = new Button(this);
            b.setTextColor(getResources().getColor(R.color.primary_4));
            b.setText(kb);
            b.setLayoutParams(new LinearLayout.LayoutParams(screenWidth/8, screenWidth/8));
            keyboard.addView(b);

            //Listeners for each button
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast t = Toast.makeText(getApplicationContext(), b.getText().toString(), Toast.LENGTH_SHORT);
                    char c = b.getText().charAt(0);
                    //Toast t = Toast.makeText(getApplicationContext(), String.valueOf(checkForLetter(c)), Toast.LENGTH_SHORT);
                    //t.show();
                    if(checkForLetter(c)){
                        revealLetter(getLetterIndex(c), c);
                        score+=10;
                    }
                }
            });
        }
    }

    /**
     * Checks if letter exists i array.
     * @param letter
     * @return
     */
    private boolean checkForLetter(char letter){
        for(char c : letters)
            if(c == letter) return true;
        return false;
    }

    /**
     * Returns index to the letter position. If not found returns -1.
     * @param letter
     * @return
     */
    private int getLetterIndex(char letter){
        for(int i = 0; i < letters.length; i++)
            if(letters[i] == letter) return i;
        return -1;
    }

    /**
     * Reveals a letter with current position to the player.
     * @param idx
     */
    private void revealLetter(int idx, char letter){
        if(idx > letters.length -1) throw new IndexOutOfBoundsException();
        edComponents.get(idx).setText(Character.toString(letter));
        letters[idx] = 0;
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


}
