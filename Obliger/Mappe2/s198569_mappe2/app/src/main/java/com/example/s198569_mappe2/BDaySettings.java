package com.example.s198569_mappe2;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TimePicker;

import com.example.s198569_mappe2.LIB.Constants;
import com.example.s198569_mappe2.services.BDayOnBootService;

/**
 * Created by luke on 10/20/15.
 */
public class BDaySettings extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar(); //v7 action bar prevents nullpointer
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new BDaySettingsFragment())
                .commit();
    }



    public static class BDaySettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.bday_settings);

            final Preference setMessageTime = findPreference("bdayServiceMessageTime");
            setMessageTime.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    final SharedPreferences prefs = getActivity().getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
                    int bmHour = prefs.getInt(Constants.SHARED_PREFS_SERVICE_HOUR, 12);
                    int bmMinute = prefs.getInt(Constants.SHARED_PREFS_SERVICE_MINUTE, 0);

                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            //eReminderTime.setText( selectedHour + ":" + selectedMinute);
                            prefs.edit().putInt(Constants.SHARED_PREFS_SERVICE_HOUR, selectedHour).apply();
                            prefs.edit().putInt(Constants.SHARED_PREFS_SERVICE_MINUTE, selectedMinute).apply();
                            Log.i(Constants.SHARED_PREFS, "New selected time is " + selectedHour + ":" + selectedMinute);
                        }
                    }, bmHour, bmMinute, true);
                    mTimePicker.setTitle("Select messaging time");
                    mTimePicker.show();

                    return true;
                }
            });
        }

        @Override
        public void onResume() {
            super.onResume();
            // Set up a listener whenever a key changes
            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            // Unregister the listener whenever a key changes
            getPreferenceScreen().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if(key.equals(Constants.SHARED_PREFS_SERVICE_ACTIVE)){
                boolean isServiceOn = sharedPreferences.getBoolean(Constants.SHARED_PREFS_SERVICE_ACTIVE, true);
                Log.i(Constants.SHARED_PREFS, "Message service is " + (isServiceOn ? "ON" : "OFF"));
                if(isServiceOn) {
                    //Starting the message service
                    getActivity().startService(new Intent(getActivity(), BDayOnBootService.class));
                }
                else{
                    //Stoppping the message service
                    getActivity().stopService(new Intent(getActivity(), BDayOnBootService.class));
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_bday_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }




}
