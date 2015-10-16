package com.example.s198569.serviceandbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by luke on 10/16/15.
 */
public class MyBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Messege from BroadcastReceiver",
                Toast.LENGTH_SHORT).show();

        Intent i = new Intent(context, PeriodicService.class);
        context.startService(i);
    }
}
