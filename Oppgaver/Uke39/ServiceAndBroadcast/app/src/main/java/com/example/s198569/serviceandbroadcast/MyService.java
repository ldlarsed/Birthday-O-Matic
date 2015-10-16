package com.example.s198569.serviceandbroadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by luke on 10/16/15.
 */
public class MyService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "Message from the service"
        , Toast.LENGTH_SHORT).show();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //Intent i = new Intent(NOTIFICATION_SERVICE);
        Intent i = new Intent(this, Result.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("Tittel")
                .setContentText("Tekst")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent).build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);

        return super.onStartCommand(intent, flags, startId);
    }
}
