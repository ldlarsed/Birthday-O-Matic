package com.example.s198569.bdaybuddiescontentprovidertest;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    static final String PROVIDER_NAME = "com.example.s198569_mappe2.BDayOMatic";
    static final Uri URL = Uri.parse("content://" + PROVIDER_NAME + "/BDayBuddies");

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.listView);
        List<String> buddies = new ArrayList<String>();


        Cursor c = getContentResolver().query(URL, null, null, null, null, null);
        //c.moveToFirst();
        while (c.moveToNext()){
            Log.i("Test", c.getString(1) + " " + c.getString(2));
            buddies.add(c.getInt(0) + " " + c.getString(1) + " " + c.getString(2));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                buddies
        );

        lv.setAdapter(arrayAdapter);





    }
}
