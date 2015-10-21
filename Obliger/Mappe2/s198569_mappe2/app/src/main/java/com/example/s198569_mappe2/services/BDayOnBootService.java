package com.example.s198569_mappe2.services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.s198569_mappe2.LIB.Constants;
import com.example.s198569_mappe2.R;
import com.example.s198569_mappe2.RegisterPerson;

import java.util.Calendar;

/**
 * Created by luke on 10/16/15.
 */
public class BDayOnBootService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //TODO: Show different logs depending on if service did run prevously or is being started for the first time
        //Toast.makeText(getApplicationContext(), Constants.B_DAY_SERVICE_STARTED, Toast.LENGTH_LONG).show();
        Log.i(Constants.SERVICE, Constants.B_DAY_SERVICE_STARTED);


        //Fetching shared preferences
        final SharedPreferences prefs = getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
        int bmHour = prefs.getInt(Constants.SHARED_PREFS_SERVICE_HOUR, 12);
        int bmMinute = prefs.getInt(Constants.SHARED_PREFS_SERVICE_MINUTE, 0);

        Calendar calendar = Calendar.getInstance();
        Context context = getApplicationContext();
        calendar.set(Calendar.HOUR_OF_DAY, bmHour);
        calendar.set(Calendar.MINUTE, bmMinute);
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pi = PendingIntent.getService(context, 0,
                new Intent(context, DailyMessageService.class),PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pi);


        Notification noti = new Notification.Builder(this)
                .setContentTitle(getString(R.string.application_name))
                .setContentText(getString(R.string.bday_service_running))
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pi).build();
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, noti);



        return Service.START_STICKY; //This one should run as soon system resources for it are available
        //return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Toast.makeText(getApplicationContext(), Constants.B_DAY_SERVICE_STOPPED, Toast.LENGTH_LONG).show();
        Log.i(Constants.SERVICE, Constants.B_DAY_SERVICE_STOPPED);
    }
}
