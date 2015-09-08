package com.example.s198569.hangman;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;


public class HangmanPreferencesActivity extends PreferenceActivity {

    private SharedPreferences.OnSharedPreferenceChangeListener prefListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);



        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {

                if(key.equals("soundOnOff"))
                    Log.w("HANGMAN", "Sound settings changed");
                else if(key.equals("languageChooser")){
                    String j = prefs.getString("languageChooser", "1");
                    Log.w("HANGMAN", "Language settings choosen: " + j);

                }
            }
        };

        prefs.registerOnSharedPreferenceChangeListener(prefListener);

    }



}
