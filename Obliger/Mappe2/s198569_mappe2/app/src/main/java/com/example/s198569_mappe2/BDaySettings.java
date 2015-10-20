package com.example.s198569_mappe2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.s198569_mappe2.LIB.Constants;
import com.example.s198569_mappe2.fragments.TimePickerFragment;
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
                    Toast.makeText(getActivity(), "Preference item is clicked", Toast.LENGTH_SHORT).show();
                    /*TimePickerFragment timePicker = new TimePickerFragment();
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.add(timePicker, "time_picker");
                    ft.commit();*/

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
                //Toast.makeText(getActivity(), "Service preference changed", Toast.LENGTH_SHORT).show();

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
