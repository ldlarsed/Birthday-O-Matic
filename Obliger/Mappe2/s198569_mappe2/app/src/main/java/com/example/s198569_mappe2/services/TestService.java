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
public class TestService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(getApplicationContext(), "This is from testservice", Toast.LENGTH_SHORT).show();
        Log.i("Service", "TestService is running");


        return super.onStartCommand(intent, flags, startId);
    }
}
