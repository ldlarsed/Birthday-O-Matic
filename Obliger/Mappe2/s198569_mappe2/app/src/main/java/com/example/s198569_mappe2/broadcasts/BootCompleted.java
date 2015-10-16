package com.example.s198569_mappe2.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.s198569_mappe2.services.BDayOnBootService;

/**
 * Created by luke on 10/16/15.
 */
public class BootCompleted extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)){
            //Starting the service after the boot is completed
            Intent serviceIntent = new Intent(context, BDayOnBootService.class);
            context.startService(serviceIntent);
        }
    }
}
