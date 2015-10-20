package com.example.s198569_mappe2.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import com.example.s198569_mappe2.LIB.Constants;
import com.example.s198569_mappe2.LIB.DialogYesNoListener;
import com.example.s198569_mappe2.R;

import java.util.logging.Handler;

/**
 * Created by luke on 10/20/15.
 */
public class TimePickerFragment extends DialogFragment {

    Handler mHandler;
    int mHour, mMinute;




    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);

       /* Bundle b = getArguments();
        mHour = b.getInt("set_hour");
        mMinute = b.getInt("set_minute");*/

        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                int mHour = hourOfDay;
                int mMinute = minute;

                /** Creating a bundle object to pass currently set time to the fragment */
                Bundle b = new Bundle();

                /** Adding currently set hour to bundle object */
                b.putInt("set_hour", mHour);

                /** Adding currently set minute to bundle object */
                b.putInt("set_minute", mMinute);

                /** Adding Current time in a string to bundle object */
                b.putString("set_time", "Set Time : " + Integer.toString(mHour) + " : " + Integer.toString(mMinute));
            }
        };
        return new TimePickerDialog(getActivity(), listener, mHour, mMinute, false);
    }
}
