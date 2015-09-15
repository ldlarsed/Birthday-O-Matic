package com.example.s198569.aktiviteter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class Hoved extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoved);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hoved, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
          //  return true;
        //}

        switch (item.getItemId()){
            case R.id.aktivitet2:
                Intent i = new Intent(this, Aktivitet2.class);
                i.putExtra("utskrift", ((EditText) findViewById(R.id.hovedtekst)).getText().toString());
                startActivity(i);
                return true;
            case R.id.aktivitet3:
                Intent j = new Intent(this, Aktivitet3.class);
                startActivityForResult(j, 555);
                return true;
            default: super.onOptionsItemSelected(item);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d("HOVED", "ON START");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("HOVED", "ON RESUME");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("HOVED", "ON DESTROY");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("HOVED", "ON STOP");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 555){
            if(resultCode == RESULT_OK){
                String resultat = data.getStringExtra("fra_3");
                EditText et = (EditText) findViewById(R.id.hovedtekst);
                et.setText(resultat);
            }
            if(resultCode == RESULT_CANCELED){

            }
        }
    }
}
