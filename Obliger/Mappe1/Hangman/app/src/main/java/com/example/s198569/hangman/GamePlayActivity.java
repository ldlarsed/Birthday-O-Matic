package com.example.s198569.hangman;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Handler;
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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s198569.hangman.lib.HangmanDataSource;

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
    private int lettersGuessed;
    private HangmanDataSource datasource;
    private int wonInSession, lostInSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        /* Datasource connection */
        datasource = new HangmanDataSource(this);
        try {
            datasource.open();
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, "Could not connect to the database.", Toast.LENGTH_SHORT);
            toast.show();
        }
        /* End of database conncetion */

        //Initializes intent the starts this activity
        Intent intent = getIntent();
        this.pName = intent.getStringExtra("pName");
        setPlayerName(pName);
        setKeyboard();
        newGame();
    }

    /**
     * This one is overridden with purpose to listen to orientation changes and re-draw the keyboard
     * layout if nescessary. Required changes are as well made inside the android manifest.
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_game_play);
        //setKeyboard();
    }

    /**
     * Sets the player name in the north west corner
     *
     * @param n
     */
    private void setPlayerName(String n) {
        playerName = (TextView) findViewById(R.id.playerName);
        playerName.setText(n);
    }

    /**
     * Starts or resets the game.
     */
    private void newGame() {
        score = 0;
        tryCount = 6;
        lettersGuessed = 0;
        setWord();
        resetTheKeyboard();
        scoreView = (TextView) findViewById(R.id.playerScore);
        scoreView.setText(String.valueOf(score));
        hangmanImage = (ImageView) findViewById(R.id.hangman_image);
        hangmanImage.setImageResource(R.drawable.hang0);
    }

    /**
     * Fetces a random word from arrays.xml and creates a new EditText component that represents each letter in the word.
     */
    private void setWord() {
        lettersCount = 0;
        edComponents = new ArrayList<>();

        //Loads the available word list
        //TODO: Find more effective way to get word directly from the xml array
        String[] words = getResources().getStringArray(R.array.words);
        Random rand = new Random();
        int chosen_index = rand.nextInt((words.length - 1));
        Log.w("HANGMAN", "chosen_index: " + chosen_index);
        Log.w("HANGMAN", words[chosen_index]);
        letters = words[chosen_index].toCharArray();

        wordsLayout = (LinearLayout) findViewById(R.id.word_layout);
        for (char c : letters) {
            final EditText et = new EditText(this);
            //et.setText(Character.toString(c));
            et.setEnabled(false);
            et.setTextColor(getResources().getColor(R.color.secondary_2_2));
            et.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
            et.setTextSize(15);
            et.setWidth(53);
            et.setHintTextColor(getResources().getColor(R.color.secondary_2_2));

            edComponents.add(et);
            wordsLayout.addView(et);
            lettersCount++;
        }
        Log.w("HANGMAN", "Antall EditText: " + lettersCount);
    }


    private void setKeyboard() {
        //Fetches the keyboard values for the default language
        String[] kb_values = getResources().getStringArray(R.array.alphabet);

        gameLayout = (RelativeLayout) findViewById(R.id.gameplay_layout);

        //TODO: Move to standalone static class
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;
        int screenWidth = displaymetrics.widthPixels;

        //Sets up the keyboard inside a gridview
        keyboard = (GridLayout) findViewById(R.id.keboard_layout);
        for (String kb : kb_values) {
            final Button b = new Button(this);
            b.setTextColor(getResources().getColor(R.color.secondary_2_1));
            b.setText(kb);
            b.setLayoutParams(new LinearLayout.LayoutParams(screenWidth / 8, screenWidth / 8));
            keyboard.addView(b);

            //Listeners for each button
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    char c = b.getText().charAt(0);
                    while (checkForLetter(c)) {
                        //When guessed correct letter
                        revealLetter(getLetterIndex(c), c);
                        score += 10;
                        lettersGuessed++;
                        scoreView.setText(Integer.toString(score));

                        //Guessed all of the letters
                        if (lettersGuessed == letters.length) {
                            datasource.updateScore(pName, score);
                            wordsLayout.removeAllViewsInLayout();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    String message = getResources().getString(R.string.game_success);
                                    showContinueDialog(message, score);
                                }
                            }, 200);
                        }
                    }

                    //Guessed wrong letter
                    tryCount--;
                    b.setEnabled(false);
                    updateHangmanImage();

                    //No tries left
                    if (tryCount == 0) {
                        datasource.updateScore(pName, score);
                        wordsLayout.removeAllViewsInLayout();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String message = getResources().getString(R.string.game_fail);
                                showContinueDialog(message, score);
                            }
                        }, 200);
                    }
                }
            });
        }
    }

    /**
     * Resets the buttons that was set to inactive due to the wrong guess.
     */
    private void resetTheKeyboard() {
        for (int i = 0; i < keyboard.getChildCount(); i++) {
            View v = keyboard.getChildAt(i);
            v.setEnabled(true);
        }
    }

    private void showContinueDialog(String message, int score) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        newGame();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        Intent startScreen = new Intent(GamePlayActivity.this, StartGameActivity.class);
                        startActivity(startScreen);
                        break;
                }
            }


        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(message + getResources().getString(R.string.game_play_again)).setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    /**
     * Depending on tries left for players sets corresponding hangman image.
     */
    private void updateHangmanImage() {
        switch (tryCount) {
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
     *
     * @param letter
     * @return
     */
    private boolean checkForLetter(char letter) {
        for (char c : letters)
            if (c == letter) return true;
        return false;
    }

    /**
     * Checks for count of occurencies of a specific letter.
     * Returns an integer array with indexes to this all of occurencies.
     *
     * @param letter
     * @return
     */
    private int[] getLetterOccurencies(char letter) {
        int[] idx = new int[letters.length];
        int k = 0;
        for (int i = 0; i < letters.length; i++)
            if (letters[i] == letter)
                idx[k++] = i;
        return idx;
    }

    /**
     * Returns index to the letter position. If not found returns -1.
     *
     * @param letter
     * @return
     */
    private int getLetterIndex(char letter) {
        for (int i = 0; i < letters.length; i++)
            if (letters[i] == letter) return i;
        return -1;
    }

    /**
     * Reveals a letter with current position to the player.
     *
     * @param idx
     */
    private void revealLetter(int idx, char letter) {
        if (idx > letters.length - 1) throw new IndexOutOfBoundsException();
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
