package com.example.s198569.hangman;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s198569.hangman.lib.HangmanDataSource;
import com.example.s198569.hangman.lib.WordsProvider;

import java.util.ArrayList;
import java.util.Arrays;

public class GamePlayActivity extends AppCompatActivity {

    //Layouts
    private RelativeLayout gameLayout;
    private LinearLayout wordsLayout;
    private GridLayout keyboard;

    //GUI components
    private TextView playerName;
    private TextView gameScoreView, sessionScoreView;
    private TextView gamesWonView, gamesLostView;
    private ImageView hangmanImage;
    private ArrayList<EditText> edComponents; //Current collection of letter placeholders

    //Game variables
    private int lettersCount;
    private char[] letters;
    private int gameScore, sessionScore;
    private int tryCount;
    private String pName;
    private int lettersGuessed;
    private int gamesWon, gamesLost;

    //Game objects
    private HangmanDataSource datasource;
    private WordsProvider wordsProvider;


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
        /* End of database connection */

        //Initializes intent the starts this activity
        Intent intent = getIntent();
        this.pName = intent.getStringExtra("pName");
        wordsProvider = new WordsProvider(this);
        setPlayerName(pName);
        setKeyboard();
        newGame();
        sessionScoreView = (TextView) findViewById(R.id.sessionScore);
        gamesWonView = (TextView) findViewById(R.id.gamesWon);
        gamesLostView = (TextView) findViewById(R.id.gamesLost);

        Log.w("HANGMAN", "ORIENTATION: "+String.valueOf(getResources().getConfiguration().orientation));
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
        gameScore = 0;
        tryCount = 6;
        lettersGuessed = 0;
        setWord();
        resetTheKeyboard();

        gameScoreView = (TextView) findViewById(R.id.playerScore);
        gameScoreView.setText(String.valueOf(gameScore));

        hangmanImage = (ImageView) findViewById(R.id.hangman_image);
        hangmanImage.setImageResource(R.drawable.hang0);
    }

    /**
     * Fetches a random word from arrays.xml and creates a new EditText component that represents each letter in the word.
     */
    private void setWord() {
        lettersCount = 0;
        edComponents = new ArrayList<>();

        //Loads the available word list
        /*String[] words = getResources().getStringArray(R.array.words);
        Random rand = new Random();
        int chosen_index = rand.nextInt((words.length - 1));
        Log.w("HANGMAN", "chosen_index: " + chosen_index);
        Log.w("HANGMAN", words[chosen_index]);
        letters = words[chosen_index].toCharArray();*/

        letters = wordsProvider.getNextWord();
        Log.w("HANGMAN", Arrays.toString(letters));
        if (letters[0] == '0') {
            Toast.makeText(this, "All words have been used up", Toast.LENGTH_SHORT).show();
            Log.w("HANGMAN", "All words are used up");
        }

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


    private void setButtonOrientation(Button b){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;
        int screenWidth = displaymetrics.widthPixels;

        if(getResources().getConfiguration().orientation == 1)
            b.setLayoutParams(new LinearLayout.LayoutParams(screenWidth / (keyboard.getColumnCount() +1), screenWidth / (keyboard.getColumnCount() +1)));
        else
            b.setLayoutParams(new LinearLayout.LayoutParams((screenWidth/2) / (keyboard.getColumnCount() - 1), (screenHeight/2) / (keyboard.getRowCount()-1)));
    }

    private void setKeyboard() {
        //Fetches the keyboard values for the default language
        String[] kb_values = getResources().getStringArray(R.array.alphabet);

        gameLayout = (RelativeLayout) findViewById(R.id.gameplay_layout);


        //Sets up the keyboard inside a gridview
        keyboard = (GridLayout) findViewById(R.id.keboard_layout);
        for (String kb : kb_values) {
            final Button b = new Button(this);
            b.setTextColor(getResources().getColor(R.color.secondary_2_1));
            b.setText(kb);
            //b.setLayoutParams(new LinearLayout.LayoutParams(screenWidth / (keyboard.getColumnCount() +1), screenWidth / (keyboard.getColumnCount() +1)));
            //b.setLayoutParams(new LinearLayout.LayoutParams((screenWidth/2) / (keyboard.getColumnCount() - 1), (screenHeight/2) / (keyboard.getRowCount()-1)));
            setButtonOrientation(b);
            keyboard.addView(b);

            //Listeners for each button
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    char c = b.getText().charAt(0);
                    if (checkForLetter(c)) {
                        while (checkForLetter(c)) {
                            //When guessed correct letter
                            revealLetter(getLetterIndex(c), c);
                            gameScore++;
                            sessionScore++;
                            lettersGuessed++;
                            gameScoreView.setText(Integer.toString(gameScore));
                            sessionScoreView.setText(Integer.toString(sessionScore));

                            //Guessed all of the letters
                            if (lettersGuessed == letters.length) {
                                gamesWon++;
                                //datasource.updateScore(pName, gameScore); //depriciated
                                datasource.updateStats(pName, gameScore, gamesWon, gamesLost);
                                gamesWonView.setText(Integer.toString(gamesWon));
                                wordsLayout.removeAllViewsInLayout();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        String message = getResources().getString(R.string.game_success);
                                        showContinueDialog(message, gameScore);
                                    }
                                }, 200);
                            }
                        }
                    } else {
                        //Guessed wrong letter
                        tryCount--;
                        b.setEnabled(false);
                        updateHangmanImage();

                        //No tries left
                        if (tryCount == 0) {
                            gamesLost++;
                            //datasource.updateScore(pName, gameScore); //depriciated
                            datasource.updateStats(pName, gameScore, gamesWon, gamesLost);
                            gamesLostView.setText(Integer.toString(gamesLost));
                            wordsLayout.removeAllViewsInLayout();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    String message = getResources().getString(R.string.game_fail);
                                    showContinueDialog(message, gameScore);
                                }
                            }, 200);
                        }
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
