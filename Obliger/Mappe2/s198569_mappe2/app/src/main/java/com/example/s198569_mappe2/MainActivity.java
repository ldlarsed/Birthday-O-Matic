package com.example.s198569_mappe2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.s198569_mappe2.BOL.Person;
import com.example.s198569_mappe2.DAL.DBHandler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     /*   DBHandler db = new DBHandler(this);
        ArrayList<Person> pl = db.getAllBuddies();
        for(Person p : pl){
            Log.i("Person", p.toString());
        }*/
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

    public void addNew(View view){
        Intent addNewActivity = new Intent(MainActivity.this, RegisterPerson.class);
        addNewActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //This should prevent stacking the activities
        //this.getApplicationContext().startActivity(addNew);
        MainActivity.this.startActivity(addNewActivity);
    }

    public void showBuddyList(View view){
        Intent addNewActivity = new Intent(MainActivity.this, BuddyList.class);
        addNewActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //This should prevent stacking the activities
        //this.getApplicationContext().startActivity(addNew);
        MainActivity.this.startActivity(addNewActivity);
    }
}
