package com.example.s198569.tripptrapptresko;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class GameActivity extends ActionBarActivity {

    private GameLogic game = new GameLogic();
    private boolean o, moveRes;
    private int moves = 0;

    private TextView player1Name, player2Name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        String player1 = intent.getStringExtra("player1");
        String player2 = intent.getStringExtra("player2");

        player1Name = (TextView) findViewById(R.id.textViewPlayerName1);
        player2Name = (TextView) findViewById(R.id.textViewPlayerName2);

        player1Name.setText(player1);
        player2Name.setText(player2);
    }


    public void playerMove(View v){

        switch(v.getId()){
            case R.id.button1:
                paintButton((Button) findViewById(R.id.button1), 0, 0);
                break;
            case R.id.button2:
                paintButton((Button) findViewById(R.id.button2), 1, 0);
                break;
            case R.id.button3:
                paintButton((Button) findViewById(R.id.button3), 2, 0);
                break;
            case R.id.button4:
                paintButton((Button) findViewById(R.id.button4), 0, 1);
                break;
            case R.id.button5:
                paintButton((Button) findViewById(R.id.button5), 1, 1);
                break;
            case R.id.button6:
                paintButton((Button) findViewById(R.id.button6), 2, 1);
                break;
            case R.id.button7:
                paintButton((Button) findViewById(R.id.button7), 0, 2);
                break;
            case R.id.button8:
                paintButton((Button) findViewById(R.id.button8), 1, 2);
                break;
            case R.id.button9:
                paintButton((Button) findViewById(R.id.button9), 2, 2);
                break;
        }
    }

    private void switchPlayer(){

        Animation a = AnimationUtils.loadAnimation(this, R.anim.text_resize2);

        if(!o) {
            a.reset();
            player1Name.setTextColor(Color.RED);
            player2Name.setTextColor(Color.GRAY);

            player1Name.clearAnimation();
            player1Name.startAnimation(a);
        } else {
            a.reset();
            player2Name.setTextColor(Color.RED);
            player1Name.setTextColor(Color.GRAY);

            player2Name.clearAnimation();
            player2Name.startAnimation(a);
        }
    }

    private void showToastInMiddle(String text){
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, -100);
        toast.show();
    }

    private void paintButton(Button b, int x, int y){

        if (!o) {
            b.setBackground(this.getResources().getDrawable(R.drawable.cross));

            //showToastInMiddle(x + " " + y + " " + State.X.toString());

            if(game.move(x,y, GameLogic.State.X)){
                showToastInMiddle(player1Name.getText().toString() + " WINS!");
            }
            ++moves;
            o = true;
            switchPlayer();
        } else {
            b.setBackground(this.getResources().getDrawable(R.drawable.circle));

            //showToastInMiddle(x + " " + y + " " + State.O.toString());

            if(game.move(x,y, GameLogic.State.O)){
                showToastInMiddle(player2Name.getText().toString() + " WINS!");
            }
            ++moves;
            o = false;
            switchPlayer();
        }

        if(moves == 9) {
            showToastInMiddle(getResources().getString(R.string.finished));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
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
