package com.example.s198569_mappe2.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by luke on 10/16/15.
 */
public class BDayService extends Service {

    private final IBinder mBinder = new MyBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        //return null;
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(getApplicationContext(), "BDayService started", Toast.LENGTH_SHORT).show();



        //return super.onStartCommand(intent, flags, startId);
        return Service.START_NOT_STICKY;
    }

    public class MyBinder extends Binder {
        public BDayService getService() {
            return BDayService.this;
        }
    }
}
