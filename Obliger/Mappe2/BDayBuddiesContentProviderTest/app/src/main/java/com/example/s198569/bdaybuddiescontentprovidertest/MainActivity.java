package com.example.s198569.bdaybuddiescontentprovidertest;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    static final String PROVIDER_NAME = "com.example.s198569_mappe2.BDayOMatic";
    static final Uri URL = Uri.parse("content://" + PROVIDER_NAME + "/BDayBuddies");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cursor c = getContentResolver().query(URL, null, null, null, null, null);
        //c.moveToFirst();
        while (c.moveToNext()){
            Log.i("Test", c.getString(1) + " " + c.getString(2));
        }
    }
}
