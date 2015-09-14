package com.example.s198569.hangman;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class HangmanPreferencesActivity extends PreferenceActivity {

    public static final String LANGUAGE = "LANGUAGE";
    private SharedPreferences.OnSharedPreferenceChangeListener prefListener;
    public SharedPreferences prefs;
    private String locale_language;
    private boolean changeLingo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);


        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {

                if (key.equals("soundOnOff")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Not implemented", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (key.equals("languageChooser")) {
                    languageWarning();
                }
            }
        };
        prefs.registerOnSharedPreferenceChangeListener(prefListener);
    }

    private void confirmChange(){
        String j = prefs.getString("languageChooser", "1");
        if (j.equals("1"))
            locale_language = "en_US";
        else
            locale_language = "nb_NO";
        changeLanguage(locale_language);
    }

    private void languageWarning() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        changeLingo = true;
                        confirmChange();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(getResources().getString(R.string.warning_language)).setPositiveButton("OK", dialogClickListener).show();
    }



    /**
     * Works once but it doesn't change the language for the whole system.
     *
     * @param locale_language
     */
    private void changeLanguage(String locale_language) {
        Locale locale = new Locale(locale_language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, null);

        //Saving the chosen language to open it up inside the main activity
        SharedPreferences.Editor spEdit = prefs.edit();
        spEdit.putString(LANGUAGE, locale_language);
        spEdit.apply();

        //Tryin to restart the main activity
        StartGameActivity.ENABLE_RESTART = true;
        Intent i = new Intent(this, StartGameActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
