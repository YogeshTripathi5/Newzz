package terribleappsdevs.com.newzz.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import terribleappsdevs.com.newzz.Login.CoreLoginScreen;
import terribleappsdevs.com.newzz.R;
import terribleappsdevs.com.newzz.material.MainActivity;

/**
 * Created by user on 4/18/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {


        createNotification(context);

    }

    public void createNotification(Context context) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "1")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("ds")
                .setContentText("dsa")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(Integer.parseInt("1"), mBuilder.build());


    }
}
