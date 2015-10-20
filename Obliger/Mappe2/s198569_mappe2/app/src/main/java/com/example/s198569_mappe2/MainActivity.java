package com.example.s198569_mappe2;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.s198569_mappe2.BLL.BirthdayController;
import com.example.s198569_mappe2.BOL.Person;
import com.example.s198569_mappe2.LIB.Constants;
import com.example.s198569_mappe2.services.BDayOnBootService;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Calling up the shared preferences
        getSharedPreferences();

        android.support.v7.app.ActionBar actionBar = getSupportActionBar(); //v7 action bar prevents nullpointer
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        setContentView(R.layout.activity_main);
        //startService(new Intent(this, BDayOnBootService.class));
    }

    /**
     * Set the shared preferences if not set before.
     * Mostly important for the first time app runs.
     * Using the default value if nothing is found.
     */
    private void getSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
        boolean bDayServiceisActive = prefs.getBoolean(Constants.SHARED_PREFS_SERVICE_ACTIVE, true);
        int messageHour = prefs.getInt(Constants.SHARED_PREFS_SERVICE_HOUR, 12);
        int messageMinute = prefs.getInt(Constants.SHARED_PREFS_SERVICE_MINUTE, 0);
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

        switch (id) {
            case R.id.main_add_new:
                addNew(getWindow().getDecorView());
                break;
            case R.id.main_upcoming:
                showBuddyList(getWindow().getDecorView());
                break;
            case R.id.main_settings:
                showPreferences(getWindow().getDecorView());
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addNew(View view) {
        Intent addNewActivity = new Intent(MainActivity.this, RegisterPerson.class);
        addNewActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //This should prevent stacking the activities
        //this.getApplicationContext().startActivity(addNew);
        MainActivity.this.startActivity(addNewActivity);
    }

    public void showBuddyList(View view) {
        Intent addNewActivity = new Intent(MainActivity.this, BuddyList.class);
        addNewActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //This should prevent stacking the activities
        //this.getApplicationContext().startActivity(addNew);
        MainActivity.this.startActivity(addNewActivity);
    }

    public void showPreferences(View view) {
        Intent settings = new Intent(MainActivity.this, BDaySettings.class);
        settings.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        MainActivity.this.startActivity(settings);
    }


}
