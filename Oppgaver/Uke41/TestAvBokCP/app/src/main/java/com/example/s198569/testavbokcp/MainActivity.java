package com.example.s198569.testavbokcp;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    public final static String PROVIDER = "com.example.s198569.contentproviderbok";
    public static final Uri CONTENT_URI= Uri.parse("content://" + PROVIDER + "/bok");
    public static final String TITTEL = "Tittel";
    public static final String ID = "_id";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void leggTil(View v){
        EditText tittel = (EditText)findViewById(R.id.tittel);
        ContentValues values = new ContentValues();
        String inn = tittel.getText().toString();
        values.put(TITTEL, inn);
        getContentResolver().insert(CONTENT_URI, values);
        tittel.setText("");
    }
}
