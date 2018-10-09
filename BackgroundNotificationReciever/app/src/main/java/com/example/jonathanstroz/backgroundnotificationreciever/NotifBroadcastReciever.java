package com.example.jonathanstroz.backgroundnotificationreciever;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;
import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.jonathanstroz.backgroundnotificationreciever.Hush.CHANNEL_1_ID;


public class NotifBroadcastReciever extends BroadcastReceiver {
    //Receive Notifications
    private NotificationManagerCompat notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        notificationManager = NotificationManagerCompat.from(context);
        cancelActiveNotifications(context);
    }

    private static void cancelActiveNotifications(Context context) {

        NotificationManager notifications = (NotificationManager)
                context.getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= 23) {
            try {
                StatusBarNotification[] activeNotifications = notifications.getActiveNotifications();

                for (StatusBarNotification activeNotification : activeNotifications) {
                        Toast.makeText(context, activeNotification.getNotification().getSettingsText(), Toast.LENGTH_SHORT).show();

                }
            } catch (Throwable e) {
                // XXX Appears to be a ROM bug, see #6043
                Log.w(TAG, e);
            }
        }
    }
}

/*
if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
           boolean noConnectivity = intent.getBooleanExtra(
                   ConnectivityManager.EXTRA_NO_CONNECTIVITY, false
           );

           if(noConnectivity) {
               Toast.makeText(context, "Disconnected", Toast.LENGTH_SHORT).show();
           }else{
               Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
           }
 */