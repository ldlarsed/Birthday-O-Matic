package com.example.s198569.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Dette er kun en enkel service som viser en toast.
 */
public class MinService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(getApplicationContext(), "I MinService", Toast.LENGTH_SHORT).show();


        //Legger til NotificationManager
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent i = new Intent(this, Resultat.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);

        Notification noti = new Notification.Builder(this)
                .setContentTitle("Titel")
                .setContentText("Tekst")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pintent).build();

        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, noti);


        return super.onStartCommand(intent, flags, startId);
    }
}
