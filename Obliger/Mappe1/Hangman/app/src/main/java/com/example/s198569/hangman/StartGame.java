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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;


public class StartGame extends ActionBarActivity {

    private ImageButton button_startGame;
    private EditText userName;
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

        /* The sign in dialog that allows user to enter it's name. */
        //Fetching the start game button
        button_startGame = (ImageButton) findViewById(R.id.btn_startGame);
        userName = (EditText) findViewById(R.id.user_Name);

        button_startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View promtView = layoutInflater.inflate(R.layout.dialog_user_name, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promtView);

                final EditText input = (EditText) promtView.findViewById(R.id.user_Name);

                //Set up for the user name dialog window
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }

                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog dialog_userInputName = alertDialogBuilder.create();
                dialog_userInputName.show();
            }
        });
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

    /**
     * Configures the immersive state of an application where both navigation and status bar are hidden and require user to swipe from upper or lower side of screen to activate.
     * @param hasFocus
     */
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


    /*
    Following are the methods to start screen of application.
     */

    /**
     * Since the OS on android handles the application exit this will basically put application to standby and exit the home screen.
     * @param v
     */
    public void quitApp(View v){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



}
