package com.example.s198569.aktiviteter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * Created by luke on 9/1/15.
 */
public class Aktivitet3 extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aktivitet3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_aktivitet3, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch(item.getItemId()){
            case R.id.fra_akt3_til_hoved:
                EditText et = (EditText) findViewById(R.id.tekst_i_3);
                String resultat = et.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("fra_3", resultat);
                Intent mIntent = new Intent();
                mIntent.putExtras(bundle);
                setResult(RESULT_OK, mIntent);
                finish();
                return true;
        }

        return true;
    }



}
