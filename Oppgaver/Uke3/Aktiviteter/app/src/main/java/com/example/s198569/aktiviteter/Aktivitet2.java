package com.example.s198569.aktiviteter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by luke on 9/1/15.
 */
public class Aktivitet2 extends AppCompatActivity{

    TextView utskrift;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.aktivitet2);
        utskrift = (TextView) findViewById(R.id.tekst2);
        Intent i = getIntent();
        String fraAkt1 = i.getStringExtra("utskrift");
        utskrift.setText(fraAkt1);
    }

}
