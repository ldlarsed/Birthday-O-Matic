package com.example.s198569.hangman;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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

/**
 * GamePlayActivity class. Controls the whole game play logic and visual components.
 */
public class GamePlayActivity extends AppCompatActivity {

    //Layouts
    private RelativeLayout gameLayout;  //Overall activity layout
    private GridLayout layoutInfo;      //Statistics layout
    private LinearLayout wordsLayout;   //Layout for the word to guess
    private GridLayout keyboard;        //Layout for the keyboard buttons

    //GUI components
    private TextView playerName;        //Player name
    private TextView gameScoreView, sessionScoreView;       //View for game score and overall session score
    private TextView gamesWonView, gamesLostView;           //View for the games won and lost in session
    private ImageView hangmanImage;                         //View for the hangman image
    private ArrayList<EditText> edComponents;               //Current collection of letter placeholders. Used as references.

    //Game variables
    private int lettersCount;   //Counts the letters in current game word
    String[] kb_values;         //Keyboard letter values. Used to store current locale alphabet.
    private boolean[] keybUsed; //Boolean indicators for used keyboard letters. Used to recreate the state of the keyboard after rotation.
    private char[] letters, lettersOriginal;        //Containers for all chars of the word that is being guessed. Each right guessed letter is being removed from letters, lettersOriginal is used to recreate the letters array after the screen rotation.
    private int gameScore, sessionScore;        //Self explanatory
    private int tryCount;       //Self explanatory
    private String pName;       //Self explanatory
    private int lettersGuessed;     //Self explanatory
    private int gamesWon, gamesLost;        //Self explanatory
    private String outMessage;      //Class variable for different messages that are shown to user after a completed round.

    //Game objects
    private HangmanDataSource datasource;       //DAO for the SQLite connection. Used to update user score in database after each completed game round.
    private WordsProvider wordsProvider;        //Words provider that provides unique words from arrays.xml


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        /* Datasource connection */
        datasource = new HangmanDataSource(this);
        try {
            datasource.open();
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, getResources().getString(R.string.err_no_database_connection), Toast.LENGTH_SHORT);
            toast.show();
        }
        /* End of database connection */


        //Initializes intent the starts this activity
        Intent intent = getIntent();
        this.pName = intent.getStringExtra("pName");
        kb_values = getResources().getStringArray(R.array.alphabet);
        initializesGUIComponents();
        wordsProvider = new WordsProvider(this);
        setPlayerName(pName);

        newGame();

        //Log.w(getResources().getString(R.string.exception), "ORIENTATION: " + String.valueOf(getResources().getConfiguration().orientation)); //Used for debugging
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        datasource.close();
        super.onDestroy();
        finishAffinity();
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

        //Re-initializes all components
        initializesGUIComponents();
        updateHangmanImage();


        //Re-populating all stats with saved state data
        playerName.setText(pName);
        gameScoreView.setText(String.valueOf(gameScore));
        sessionScoreView.setText(String.valueOf(sessionScore));
        gamesWonView.setText(String.valueOf(gamesWon));
        gamesLostView.setText(String.valueOf(gamesLost));

        edComponents.clear();
        wordsLayout.removeAllViews();

        createLettersToGuess();

        for (int i = 0; i < lettersOriginal.length; i++) {
            if (letters[i] == 0)
                revealLetter(i, lettersOriginal[i]);
        }

        setUpKeyboardAndListen();
        //Toast.makeText(this, Arrays.toString(keybUsed), Toast.LENGTH_SHORT).show(); //For debugging shows all boolean contents of keyboard references
        markUsedKeys();

    }

    /**
     * Disables previously used keys. Use after screen rotation is completed.
     */
    private void markUsedKeys() {
        if (keyboard != null) {
            for (int i = 0; i < keyboard.getChildCount(); i++) {
                if (keybUsed[i])
                    keyboard.getChildAt(i).setEnabled(false);
            }
        }
    }

    /**
     * Returns child index of specific button.
     *
     * @param c
     * @return
     */
    private int getKeyIDX(char c) {
        for (int i = 0; i < keyboard.getChildCount(); i++) {
            if (((Button) keyboard.getChildAt(i)).getText().charAt(0) == c)
                return i;
        }
        return -1;
    }


    /**
     * This method initializes all the components hence android 13 onConfigurationChanged
     * must be used. Therefore after change of screen orientation all changes will be lost
     * and must be re-initialized and re-populated to the previous state on screen rotation.
     */
    private void initializesGUIComponents() {
        gameLayout = (RelativeLayout) findViewById(R.id.gameplay_layout);
        layoutInfo = (GridLayout) findViewById(R.id.layout_info);
        playerName = (TextView) findViewById(R.id.playerName);
        gameScoreView = (TextView) findViewById(R.id.playerScore);
        hangmanImage = (ImageView) findViewById(R.id.hangman_image);
        wordsLayout = (LinearLayout) findViewById(R.id.word_layout);
        keyboard = (GridLayout) findViewById(R.id.keboard_layout);
        sessionScoreView = (TextView) findViewById(R.id.sessionScore);
        gamesWonView = (TextView) findViewById(R.id.gamesWon);
        gamesLostView = (TextView) findViewById(R.id.gamesLost);
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

        cleanupKeyboard();
        //Fetches the keyboard values for the default language
        keybUsed = new boolean[kb_values.length];
        setUpKeyboardAndListen();
        setWord();
        gameScoreView.setText(String.valueOf(gameScore));
        hangmanImage.setImageResource(R.drawable.hang0);
    }


    /**
     * Fetches a random word from arrays.xml and creates a new EditText component that represents each letter in the word.
     */
    private void setWord() {
        lettersCount = 0;
        edComponents = new ArrayList<>();

        letters = wordsProvider.getNextWord();
        Log.w(getResources().getString(R.string.exception), Arrays.toString(letters)); //Show new word in log view
        if (letters[0] == '0') {
            Toast.makeText(this, getResources().getString(R.string.info_no_more_letters), Toast.LENGTH_SHORT).show();
            Log.w(getResources().getString(R.string.exception), getResources().getString(R.string.info_no_more_letters));
        }

        lettersOriginal = letters.clone();

        createLettersToGuess();

    }

    private void createLettersToGuess(){
        for (char c : letters) {
            final EditText et = new EditText(this);
            et.setEnabled(false);
            et.setTextColor(getResources().getColor(R.color.secondary_2_2));
            et.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
            et.setTextSize(15);
            et.setWidth(53);
            et.setHintTextColor(getResources().getColor(R.color.secondary_2_1));
            et.setBackgroundResource(R.drawable.edit_text_background);

            edComponents.add(et);
            wordsLayout.addView(et);
            lettersCount++;
        }
    }

    /**
     * Sets up the keyboard dimensions depending on the rotation of the screen. Since the
     * keyboard is dynamically set up it has to be re-created on each screen rotation.
     * @param b
     */
    private void setKeyboardDimension(Button b) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;
        int screenWidth = displaymetrics.widthPixels;

        if (getResources().getConfiguration().orientation == 1)
            b.setLayoutParams(new LinearLayout.LayoutParams(screenWidth / (keyboard.getColumnCount() + 1), screenWidth / (keyboard.getColumnCount() + 1)));
        else
            b.setLayoutParams(new LinearLayout.LayoutParams((screenWidth / 2) / (keyboard.getColumnCount() - 1), (screenHeight / 2) / (keyboard.getRowCount() - 1)));
    }


    /**
     * Sets up the keyboard and listeners for each button.
     */
    private void setUpKeyboardAndListen() {
        for (String kb : kb_values) {
            final Button b = new Button(this);
            b.setTextColor(getResources().getColor(R.color.secondary_2_1));
            b.setText(kb);
            setKeyboardDimension(b);

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
                            b.setEnabled(false);
                            keybUsed[getKeyIDX(c)] = true;
                            gameScore++;
                            sessionScore++;
                            lettersGuessed++;
                            gameScoreView.setText(Integer.toString(gameScore));
                            sessionScoreView.setText(Integer.toString(sessionScore));

                            //Guessed all of the letters. Game won.
                            if (lettersGuessed == letters.length) {
                                gamesWon++;
                                datasource.updateStats(pName, gameScore, gamesWon, gamesLost);
                                gamesWonView.setText(Integer.toString(gamesWon));

                                clearKeyboardButtonReferences();

                                //Sets the correct message to the user and pops up the continue dialog
                                outMessage = getResources().getString(R.string.game_success);
                                delayAction(200);
                            }
                        }
                    } else {
                        //Guessed wrong letter
                        tryCount--;
                        b.setEnabled(false);
                        keybUsed[getKeyIDX(c)] = true;
                        //Toast.makeText(getApplicationContext(), Arrays.toString(keybUsed), Toast.LENGTH_SHORT).show(); //Used for debugging purposes

                        updateHangmanImage();

                        //No tries left. Game lost.
                        if (tryCount == 0) {
                            gamesLost++;
                            datasource.updateStats(pName, gameScore, gamesWon, gamesLost);
                            gamesLostView.setText(Integer.toString(gamesLost));

                            clearKeyboardButtonReferences();

                            //Sets the correct message to the user and pops up the continue dialog
                            outMessage = getResources().getString(R.string.game_fail);
                            delayAction(200);
                        }
                    }
                }
            });
        }
    }

    /**
     * Clears the references array list of all the buttons it contains.
     */
    private void clearKeyboardButtonReferences() {
        edComponents.clear();
    }

    /**
     * Cleans up and resets the keyboard in between sessions.
     */
    private void cleanupKeyboard() {
        wordsLayout.removeAllViews();
        keyboard.removeAllViews();
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

    /**
     * After the game is won or lost this one is popped up and shown to the user.
     * @param message
     * @param score
     */
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
     * Checks for count of occurrences of a specific letter.
     * Returns an integer array with indexes to this all of occurrences.
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

    /**
     * Delays an action with extra thread. When user fails on identifying the word it the last leg
     * of hangman is drown. This the delays the lost game dialog for few milliseconds in order to
     * make it possible for the user to the updated hangman image.
     * @param msec
     */
    private void delayAction(int msec){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //String message = getResources().getString(R.string.game_success);
                showContinueDialog(outMessage, gameScore);
            }
        }, msec);
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
