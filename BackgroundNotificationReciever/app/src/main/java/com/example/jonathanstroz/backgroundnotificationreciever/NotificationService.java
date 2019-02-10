package com.example.jonathanstroz.backgroundnotificationreciever;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.app.NotificationChannel;

import java.util.Random;

import static android.app.Notification.Builder.recoverBuilder;
import static com.example.jonathanstroz.backgroundnotificationreciever.Hush.CHANNEL_1_ID;
import static com.example.jonathanstroz.backgroundnotificationreciever.MainActivity.bucket_channel;
import static com.example.jonathanstroz.backgroundnotificationreciever.MainActivity.high_channel;
import static com.example.jonathanstroz.backgroundnotificationreciever.MainActivity.low_channel;
import static com.example.jonathanstroz.backgroundnotificationreciever.MainActivity.mDatabaseHelper;
import static com.example.jonathanstroz.backgroundnotificationreciever.MainActivity.medium_channel;

public class NotificationService extends NotificationListenerService {
    private static int SUMMARY_ID = 0;
    public NotificationManager notificationManager;
    @Override
    public IBinder onBind(Intent intent) {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn){
        String channel = sbn.getNotification().getChannelId();
        HushNotification notif = new HushNotification(sbn);

        if (notif.getNotifcationCode() != 5) {

            notificationManager.cancel(sbn.getTag(), sbn.getId());
            cancelNotification(sbn.getKey());
            sendNotifcation(notif, sbn);
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn,NotificationListenerService.RankingMap rankingMap, int reason){
        //https://developer.android.com/reference/android/service/notification/NotificationListenerService.html#REASON_LISTENER_CANCEL
        Log.e("CHECK","We reached here");
        HushNotification notif = new HushNotification(sbn);
        notif.setCancelReason(reason);

        if(notif.getCancelReason() != 0) {
            addToDataset(notif);
        }
    }

    public void addToDataset(HushNotification notif){
        //add the noification to the dataset with all of its informationa as well as its reason
        AddData(Integer.toString(notif.getNotifcationCode()), notif.getTitle(), notif.getMessage(), notif.getTime(), Integer.toString(notif.getCancelReason()));
    }

    public void AddData(String source, String title, String message, long time, String cancelReason){
        //Check if successfully entered into the DB
        Long insertData = mDatabaseHelper.addData(source, title, message, time, cancelReason);
        Log.d(" **** Inserted: ", Long.toString(insertData));
    }

    public static void cancelNotification(Context ctx, int notifyId) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
        nMgr.cancel(notifyId);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendNotifcation(HushNotification notif, StatusBarNotification sbn){
        Notification notification = notif.getNotification();
        if (notif.getPriority() == 1){
            notification.priority = Notification.PRIORITY_HIGH;
            Notification notificationBuild = recoverBuilder(this, notification)
                    .setChannelId(high_channel)
                    .build();
            notificationManager.notify(sbn.getTag(), sbn.getId(), notificationBuild);
        }else if (notif.getPriority() == 2){
            notification.priority = Notification.PRIORITY_DEFAULT;
            Notification notificationBuild = recoverBuilder(this, notification)
                    .setChannelId(medium_channel)
                    .build();

            notificationManager.notify(sbn.getTag(), sbn.getId(), notificationBuild);
        }else if (notif.getPriority() == 3){
            notification.priority = Notification.PRIORITY_LOW;
            Notification notificationBuild = recoverBuilder(this, notification)
                    .setChannelId(low_channel)
                    .setPriority(Notification.PRIORITY_LOW)
                    .build();
            notificationManager.notify(sbn.getTag(), sbn.getId(), notificationBuild);

        }else if (notif.getPriority() == 4){
            notification.priority = Notification.PRIORITY_LOW;

            Notification notificationBuild = recoverBuilder(this, notification)
                    .setChannelId("bucket")
                    .build();

            notificationManager.notify(sbn.getTag(), notif.getId(), notificationBuild);
        }
    }
}