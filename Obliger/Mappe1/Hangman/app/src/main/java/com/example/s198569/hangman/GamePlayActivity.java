package com.example.s198569.hangman;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.Image;
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
import android.widget.ImageView;
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
    private int tryCount;
    private TextView scoreView;
    private ImageView hangmanImage;
    private String pName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        //Initializes intent the starts this activity
        Intent intent = getIntent();
        this.pName = intent.getStringExtra("pName");
        setPlayerName(pName);
        setKeyboard();
        newGame();
    }

    /**
     * Sets the player name in the north west corner
     * @param n
     */
    private void setPlayerName(String n){
        playerName = (TextView) findViewById(R.id.playerName);
        playerName.setText(n);
    }

    private void newGame(){
        score = 0;
        tryCount = 6;
        setWord();
        scoreView = (TextView) findViewById(R.id.playerScore);
        scoreView.setText(String.valueOf(score));
        hangmanImage = (ImageView) findViewById(R.id.hangman_image);
        hangmanImage.setImageResource(R.drawable.hang0);
    }

    /**
     * Fetces a random word from arrays.xml and creates a new EditText component that represents each letter in the word.
     */
    private void setWord(){
        lettersCount = 0;
        edComponents = new ArrayList<>();

        //Loads the available word list
        //TODO: Find more effective way to get word directly from the xml array
        String[] words = getResources().getStringArray(R.array.words);
        Random rand = new Random();
        int chosen_index = rand.nextInt((words.length - 1));
        Log.w("HANGMAN", "chosen_index: " + chosen_index);
        //Log.w("HANGMAN", words[chosen_index]);
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
            b.setTextColor(getResources().getColor(R.color.secondary_2_1));
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
                        //When guessed correct letter
                        revealLetter(getLetterIndex(c), c);
                        score+=10;
                        scoreView.setText(Integer.toString(score));
                    }else{
                        //Guessed wrong letter
                        tryCount--;
                        updateHangmanImage();
//                        if(tryCount == 0) {
//                            wordsLayout.removeAllViewsInLayout();
//                            newGame();
//                        }
                    }
                }
            });
        }
    }

    /**
     * Depending on tries left for players sets corresponding hangman image.
     */
    private void updateHangmanImage(){
        switch(tryCount){
            case 5:
                hangmanImage.setImageResource(R.drawable.hang1);
                break;
            case 4:
                hangmanImage.setImageResource(R.drawable.hang2);
                break;
            case 3:
                hangmanImage.setImageResource(R.drawable.hang3);
                break;
            case 2:
                hangmanImage.setImageResource(R.drawable.hang4);
                break;
            case 1:
                hangmanImage.setImageResource(R.drawable.hang5);
                break;
            case 0:
                hangmanImage.setImageResource(R.drawable.hang6);
                break;
            default:
                hangmanImage.setImageResource(R.drawable.hang0);
                break;
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
        letters[idx] = 0; //removes letter if guessed correctly
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
