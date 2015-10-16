package com.example.s198569.serviceandbroadcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Calendar;

/**
 * Created by luke on 10/16/15.
 */
public class PeriodicService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Calendar cal = Calendar.getInstance();
        Intent i = new Intent(this, MyService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, i, 0);

        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                10 * 1000, pendingIntent);

        return super.onStartCommand(intent, flags, startId);
    }
}
