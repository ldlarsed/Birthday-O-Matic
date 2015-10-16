package com.example.s198569_mappe2.fragments;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.s198569_mappe2.R;

/**
 * Created by luke on 10/16/15.
 */
public class BDayPreferenceFragment extends PreferenceFragment {

    private SwitchPreference bdayReminderSwitch, smsReminderSwitch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        bdayReminderSwitch = (SwitchPreference)  getPreferenceManager().findPreference("bdayServiceSwitch");
        bdayReminderSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                // insert custom code
                return false;
            }
        });

        return inflater.inflate(R.layout.activity_main, container, false);
    }
}
