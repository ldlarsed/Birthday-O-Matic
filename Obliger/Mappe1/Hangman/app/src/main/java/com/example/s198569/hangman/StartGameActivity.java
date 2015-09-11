package com.example.s198569.hangman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class StartGameActivity extends ActionBarActivity {

    //Constants
    public static final String LANGUAGE = "SAVED_LANGUAGE";

    //Instance variables
    private ImageButton button_startGame;
    final Context context = this;
    private String language;
    public static boolean ENABLE_RESTART = false;

    //Objects
    SharedPreferences langPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        //Starting the title animation for the title and hangman figure
        ImageView title_image = (ImageView) findViewById(R.id.title_image);
        Animation titleAnimation = AnimationUtils.loadAnimation(this, R.anim.title_anim);
        ImageView hangman_title__image = (ImageView) findViewById(R.id.hangman_title_image);
        Animation hangmanTitleAnimation = AnimationUtils.loadAnimation(this, R.anim.hangman_anim);
        title_image.startAnimation(titleAnimation);
        hangman_title__image.startAnimation(hangmanTitleAnimation);

        /*  Main Menu */
        ListView mainMenu = (ListView) findViewById(R.id.main_menu);
        //TODO: Try to fill it from a string array later
        String[] menuItems = {
                getResources().getString(R.string.menu_play),
                getResources().getString(R.string.menu_scores),
                getResources().getString(R.string.menu_settings),
                getResources().getString(R.string.menu_help),
                getResources().getString(R.string.menu_quit)
        };

        ArrayAdapter<String> mainMenuAdapter = new ArrayAdapter<String>(this, R.layout.listview_item_main_menu, menuItems);
        mainMenu.setAdapter(mainMenuAdapter);


        //Listeners for Main Menu
        mainMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                TextView textView = (TextView) itemClicked;
                String strText = textView.getText().toString();
                if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_play))) {
                    //startActivity(new Intent(StartGameActivity.this, GamePlayActivity.class));
                    startActivity(new Intent(StartGameActivity.this, LoginActivity.class));
                } else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_scores))) {
                    //Toast toast = Toast.makeText(context, "Not implemented", Toast.LENGTH_SHORT);
                    //toast.show();
                    startActivity(new Intent(StartGameActivity.this, ScoresActivity.class));
                } else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_settings))) {
                    //Toast toast = Toast.makeText(context, "Not implemented", Toast.LENGTH_SHORT);
                    //toast.show();
                    startActivity(new Intent(StartGameActivity.this, HangmanPreferencesActivity.class));
                } else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_help))) {
                    Toast toast = Toast.makeText(context, "Not implemented", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_quit))) {
                    finish();
                }
            }
        });

        //Prevents the listview from using scrollbar
        mainMenu.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        /* End of Main Menu */

        //Test if I can change language of main activity at boot
        //checkLanguage();

        //ENABLE_RESTART = true;
        //restartMain();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_game, menu);
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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //restartMain();

        langPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        language = langPrefs.getString("LANGUAGE", "");
        Log.w("START GAME", "Detected language: " + language);

        if (ENABLE_RESTART)
            restartMain();
    }

    private void checkLanguage() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String lang = sp.getString("languageChooser", "1");
        Log.w("START GAME", "Detected language: " + lang);
    }

    private void changeLanguage(String localeString) {
        Locale locale = new Locale(localeString);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, null);

        finish();
        startActivity(getIntent());

        //recreate();
    }

    public void restartMain() {
        if (ENABLE_RESTART == true) {
            Intent mainIntent = new Intent(this, StartGameActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);
            finish();
        } else {
            finish();
        }
        ENABLE_RESTART = false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        langPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        language = langPrefs.getString("LANGUAGE", "");
        outState.putString(LANGUAGE, language);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        language = savedInstanceState.getString(LANGUAGE);
    }
}
