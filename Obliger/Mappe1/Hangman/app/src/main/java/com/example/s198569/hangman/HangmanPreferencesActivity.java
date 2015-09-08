package com.example.s198569.hangman;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;


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

                    String locale_language;
                    if(j.equals("1"))locale_language = "en_US";
                    else locale_language="nb_NO";

                    Locale locale = new Locale(locale_language);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getApplicationContext().getResources().updateConfiguration(config, null);
                    startActivity(new Intent(HangmanPreferencesActivity.this, StartGameActivity.class));
                }
            }
        };

        prefs.registerOnSharedPreferenceChangeListener(prefListener);

    }



}
