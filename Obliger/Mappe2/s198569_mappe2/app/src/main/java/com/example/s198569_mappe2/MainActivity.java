package com.example.s198569_mappe2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.s198569_mappe2.LIB.Constants;
import com.example.s198569_mappe2.services.BDayOnBootService;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Calling up the shared preferences
        runPreferenceActions();

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
    private void runPreferenceActions() {
        SharedPreferences prefs = getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
        boolean bDayServiceisActive = prefs.getBoolean(Constants.SHARED_PREFS_SERVICE_ACTIVE, true);
        int messageHour = prefs.getInt(Constants.SHARED_PREFS_SERVICE_HOUR, 12);
        int messageMinute = prefs.getInt(Constants.SHARED_PREFS_SERVICE_MINUTE, 0);

        Log.i(Constants.SHARED_PREFS, "Service key in main is " + bDayServiceisActive);

        if(bDayServiceisActive) {
            //Starting the message service
            startService(new Intent(this, BDayOnBootService.class));
        }
        else{
            //Stoppping the message service
            stopService(new Intent(this, BDayOnBootService.class));
        }

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
        addNewActivity.putExtra(Constants.IS_EDIT_SESSION, false);
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
