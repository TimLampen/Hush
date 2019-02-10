package com.example.jonathanstroz.backgroundnotificationreciever;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

/*
 * To-Do
 * 1. Add Notification Bucket
 * 2. Test with different notifications
 * 3. Machine Learning Algorithm
 * 4. Read Priorities from DB
 * 4. Turn into background service
 *
 * To-Do
 * 1. UI
 * 2. Priorities
 */
public class Hush extends Application {
    public static final String CHANNEL_1_ID = "HUSH_HIGH";
    public static final String CHANNEL_2_ID = "HUSH_LOW";
    public static final String CHANNEL_3_ID = "HUSH_MEDIUM";
    public static final String CHANNEL_4_ID = "bucket";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotifcationChannels();
    }

    private void createNotifcationChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "High_Priority",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("High Priority Notification Route");

            NotificationChannel channel3 = new NotificationChannel(
                    CHANNEL_3_ID,
                    "Low_Priority",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel3.setDescription("Low Priority Notification Route");

            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Medium_Priority",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel3.setDescription("Medium Priority Notification Route");

            NotificationChannel channel4 = new NotificationChannel(
                    CHANNEL_4_ID,
                    "Bucket",
                    NotificationManager.IMPORTANCE_LOW //FIX
            );
            channel3.setDescription("Bucket Notification Route");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
            manager.createNotificationChannel(channel3);
            manager.createNotificationChannel(channel4);
        }
    }
}
