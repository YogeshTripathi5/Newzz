package terribleappsdevs.com.newzz.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;

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
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);

        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(context)
                .setContentTitle("Fresh Feeds")
                .setContentText("New Newzz Feeds Added...").setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent).build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);

    }
}
