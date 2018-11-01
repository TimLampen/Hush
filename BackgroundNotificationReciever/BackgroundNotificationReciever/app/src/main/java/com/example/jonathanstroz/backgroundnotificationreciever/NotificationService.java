package com.example.jonathanstroz.backgroundnotificationreciever;

import android.app.Notification;
import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import static com.example.jonathanstroz.backgroundnotificationreciever.MainActivity.mDatabaseHelper;
import static com.example.jonathanstroz.backgroundnotificationreciever.MainActivity.notificationManager;

public class NotificationService extends NotificationListenerService {

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn){
        String channel = sbn.getNotification().getChannelId();
        HushNotification notif = new HushNotification(sbn);
        if (notif.getNotifcationCode() != 5) {
            sendNotifcation(notif);
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn,NotificationListenerService.RankingMap rankingMap, int reason){
        //https://developer.android.com/reference/android/service/notification/NotificationListenerService.html#REASON_LISTENER_CANCEL
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

    private void sendNotifcation(HushNotification notif){
        Notification notification = notif.getNotification();
        if (notif.getPriority() == 1){
            notification.priority = Notification.PRIORITY_HIGH;
            notificationManager.notify(notif.getId(),notification);
        }else if (notif.getPriority() == 2){
            notification.priority = Notification.PRIORITY_DEFAULT;
            notificationManager.notify(notif.getId(),notification);
        }else if (notif.getPriority() == 3){
            notification.priority = Notification.PRIORITY_LOW;
            notificationManager.notify(notif.getId(),notification);
        }else if (notif.getPriority() == 4){
            notification.priority = Notification.PRIORITY_LOW;
            //Save into notification bucket
            notificationManager.notify(notif.getId(),notification);
        }
    }
}