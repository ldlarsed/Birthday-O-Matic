package com.example.s198569_mappe2.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

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
    public void onCreate() {
        super.onCreate();

        //Kall opp det som skal gjøres her etter start
        Toast.makeText(getApplicationContext(), "BDayService is running", Toast.LENGTH_LONG).show();
        Log.i("Service", "BDayService is running");

        }
    }
