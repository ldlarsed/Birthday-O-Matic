package com.example.s198569_mappe2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.s198569_mappe2.services.BDayOnBootService;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, BDayOnBootService.class));

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

    public void showPreferences(View view){
        Toast.makeText(getApplicationContext(), "Not yet implemented", Toast.LENGTH_SHORT).show();
    }





}
