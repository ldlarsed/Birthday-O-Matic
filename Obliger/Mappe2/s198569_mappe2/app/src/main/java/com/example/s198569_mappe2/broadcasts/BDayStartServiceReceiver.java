package com.example.s198569_mappe2.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.s198569_mappe2.services.BDayService;

/**
 * Created by luke on 10/16/15.
 */
public class BDayStartServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent bDayService = new Intent(context, BDayService.class);
        context.startService(bDayService);
    }
}
