package com.example.s198569.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by luke on 9/22/15.
 */
public class MinBroadcastReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "I BroadcastReceiver", Toast.LENGTH_SHORT).show();

        //Denne vil starte MinService en gang. Trenger ikke lengre.
        Intent i = new Intent(context, MinService.class);
        context.startService(i);

        //Denne kommer til å start periodisk service som kommer til å kjøre hele tiden.
        //Kommer til å repeteres hver 10:e sekund.
        Intent j = new Intent(context, SettPeriodiskService.class);
        context.startService(j);
    }
}
