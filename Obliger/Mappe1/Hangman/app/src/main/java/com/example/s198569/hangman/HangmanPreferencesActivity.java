package com.example.s198569.hangman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Locale;


public class HangmanPreferencesActivity extends PreferenceActivity {

    private SharedPreferences.OnSharedPreferenceChangeListener prefListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

                    if(j.equals("1"))
                        locale_language = "en_US";
                    else
                        locale_language = "nb_NO";

                    Log.w("HANGMAN", "Switching language to " + locale_language);
                    changeLanguage(locale_language);
                    //change_language(locale_language);
                }
            }
        };

        prefs.registerOnSharedPreferenceChangeListener(prefListener);

    }

    /**
     * Works once but it doesn't change the language for the whole system.
     * @param locale_language
     */
    private void changeLanguage(String locale_language){
        Locale locale = new Locale(locale_language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, null);

        //finish();
        //startActivity(new Intent(HangmanPreferencesActivity.this, StartGameActivity.class));

        //finish();
        //startActivity(getIntent());

        //recreate();

        //restartSelf();

        //Tryin to restart the main activity
        StartGameActivity.ENABLE_RESTART = true;
        Intent i = new Intent(this, StartGameActivity.class);
        i.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
    }

/*    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // refresh your views here
        super.onConfigurationChanged(newConfig);
    }*/

   /* private void change_language(String locale_language){
        String languageToLoad  = locale_language;
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        this.setContentView(R.id.sett);
    }*/


/*    private void change_language(String locale_language){
        Locale myLocale = new Locale(locale_language);
        Resources res = getBaseContext().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;

        res.updateConfiguration(conf, dm);
        getBaseContext().getResources().updateConfiguration(
                getBaseContext().getResources().getConfiguration(),
                getBaseContext().getResources().getDisplayMetrics());
    }*/

   /* private void restartSelf() {
        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis() + 1000, // one second
                PendingIntent.getActivity(this, 0, getIntent(), PendingIntent.FLAG_ONE_SHOT
                        | PendingIntent.FLAG_CANCEL_CURRENT));
        finish();
    }*/
}
