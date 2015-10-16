package com.example.s198569_mappe2.broadcasts;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by luke on 10/16/15.
 */
public class BDayReceiver extends BroadcastReceiver{

    //Starts every 30 seconds
    private static final long REPEAT_TIME = 1000 * 30;

    @Override
    public void onReceive(Context context, Intent intent) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, BDayStartServiceReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        context, 0, i,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );

        Calendar cal = Calendar.getInstance();
        //Offset 30 seconds after boot
        cal.add(Calendar.SECOND, 30);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                cal.getTimeInMillis(), REPEAT_TIME, pendingIntent);
    }
}
