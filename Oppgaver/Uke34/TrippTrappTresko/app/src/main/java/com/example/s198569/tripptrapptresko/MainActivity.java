package com.example.s198569.tripptrapptresko;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText player1, player2;

        player1 = (EditText) findViewById(R.id.editTextPlayer1);
        player2 = (EditText) findViewById(R.id.editTextPlayer2);

        player1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    player1.setText("", TextView.BufferType.EDITABLE);
                }
            }
        });

        player2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    player2.setText("", TextView.BufferType.EDITABLE);
                }
            }
        });

    }

    public void startGame(View v){
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);

        String player1, player2;

        player1 = ((EditText) findViewById(R.id.editTextPlayer1)).getText().toString();
        player2 = ((EditText) findViewById(R.id.editTextPlayer2)).getText().toString();

        intent.putExtra("player1", player1);
        intent.putExtra("player2", player2);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
