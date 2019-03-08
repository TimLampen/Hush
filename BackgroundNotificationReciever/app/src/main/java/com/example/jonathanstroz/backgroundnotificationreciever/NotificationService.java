package com.example.jonathanstroz.backgroundnotificationreciever;

import android.app.Notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.app.NotificationChannel;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static android.app.Notification.Builder.recoverBuilder;
import static com.example.jonathanstroz.backgroundnotificationreciever.Hush.CHANNEL_1_ID;

import static com.example.jonathanstroz.backgroundnotificationreciever.Activities.MainActivity.bucket_channel;
import static com.example.jonathanstroz.backgroundnotificationreciever.Activities.MainActivity.high_channel;
import static com.example.jonathanstroz.backgroundnotificationreciever.Activities.MainActivity.low_channel;
import static com.example.jonathanstroz.backgroundnotificationreciever.Activities.MainActivity.mDatabaseHelper;
import static com.example.jonathanstroz.backgroundnotificationreciever.Activities.MainActivity.medium_channel;

import static com.example.jonathanstroz.backgroundnotificationreciever.Activities.MainActivity.mDatabaseHelper;


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
        HushNotification notif = new HushNotification(sbn);
        notif.setPriority(notif);
        double test = notif.getPriority();
        if(notif.getPriority() > 0.5 && notif.getPriority() < 4 ){
            notificationManager.cancel(sbn.getTag(), sbn.getId());
            cancelNotification(sbn.getKey());
            sendNotifcation(notif, sbn);
        }else if(notif.getPriority() < 0.5 && notif.getPriority() > 0.0){
            notificationManager.cancel(sbn.getTag(), sbn.getId());
            cancelNotification(sbn.getKey());
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
        Firebase ref = new Firebase("https://hush-808f8.firebaseio.com/");
        DatabaseNotification c_notif = new DatabaseNotification(notif);
        ref.child("Notifications").push().setValue(c_notif);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendNotifcation(HushNotification notif, StatusBarNotification sbn){
        Notification notification = notif.getNotification();

        notification.priority = Notification.PRIORITY_LOW;
        Notification notificationBuild = recoverBuilder(this, notification)
                .setChannelId("bucket")
                .build();
        int id = notif.getId();
        String tag = sbn.getTag();

        notificationManager.notify(tag, id, notificationBuild);


    }

}