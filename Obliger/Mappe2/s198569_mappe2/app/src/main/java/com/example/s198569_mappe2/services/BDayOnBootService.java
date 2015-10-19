package com.example.s198569_mappe2.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

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

        Toast.makeText(getApplicationContext(), "BDayService is running", Toast.LENGTH_LONG).show();
        Log.i("Service", "BDayService is running");

        //Hver 30 sekund
/*        Calendar cal = Calendar.getInstance();
        Intent i = new Intent(this, TestService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                10 * 1000, pintent);*/

        //Starter ved spesifikk tid
     /*   Calendar calendar = Calendar.getInstance();

        Context context = getApplicationContext();
        calendar.set(Calendar.HOUR_OF_DAY, 22); // For 1 PM or 2 PM
        calendar.set(Calendar.MINUTE, 31);
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pi = PendingIntent.getService(context, 0,
                new Intent(context, TestService.class),PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pi);*/


        return super.onStartCommand(intent, flags, startId);
    }

    /*@Override
    public void onCreate() {
        super.onCreate();

        //Kall opp det som skal gjøres her etter start
        Toast.makeText(getApplicationContext(), "BDayService is running", Toast.LENGTH_LONG).show();
        Log.i("Service", "BDayService is running");

    }*/

    /*
    Her skal det startes en service samme tid hver dag (det blir normalt avhengig av instillinger som seinere gjøres av brukeren).
    Denne service kommer til å gå igjennom alle registrerte brukere som har en bursdag som overensstemmer med dagens dato.
    Dersom slike personer finnes kommer man til å sende epost til alle brukere avhengig av hvilken tekst de har.
     */
    @Override
    public void onCreate() {
        super.onCreate();

        Calendar calendar = Calendar.getInstance();
        Context context = getApplicationContext();
        calendar.set(Calendar.HOUR_OF_DAY, 22); // For 1 PM or 2 PM
        calendar.set(Calendar.MINUTE, 36);
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pi = PendingIntent.getService(context, 0,
                new Intent(context, TestService.class),PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pi);
    }
}
