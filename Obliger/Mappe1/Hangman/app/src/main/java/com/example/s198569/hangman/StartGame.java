package com.example.s198569.hangman;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class StartGame extends ActionBarActivity {

    private ImageButton button_startGame;
    final Context context = this;

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

        ArrayAdapter<String> mainMenuAdapter = new ArrayAdapter<String>(this, R.layout.main_menu_item, menuItems);
        mainMenu.setAdapter(mainMenuAdapter);


        //Listeners for Main Menu
        mainMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id){
                TextView textView = (TextView) itemClicked;
                String strText = textView.getText().toString();
                if(strText.equalsIgnoreCase(getResources().getString(R.string.menu_play))){
                    startActivity(new Intent(StartGame.this, GamePlay.class));
                }else if(strText.equalsIgnoreCase(getResources().getString(R.string.menu_scores))){
                    //Toast toast = Toast.makeText(context, "Not implemented", Toast.LENGTH_SHORT);
                    //toast.show();
                    startActivity(new Intent(StartGame.this, Scores.class));
                }else if(strText.equalsIgnoreCase(getResources().getString(R.string.menu_settings))){
                    Toast toast = Toast.makeText(context, "Not implemented", Toast.LENGTH_SHORT);
                    toast.show();
                }else if(strText.equalsIgnoreCase(getResources().getString(R.string.menu_help))){
                    Toast toast = Toast.makeText(context, "Not implemented", Toast.LENGTH_SHORT);
                    toast.show();
                }else if(strText.equalsIgnoreCase(getResources().getString(R.string.menu_quit))){
                    finish();
                }
            }
        });

        /* End of Main Menu */

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





}
