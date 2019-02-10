package com.example.jonathanstroz.backgroundnotificationreciever;
import android.app.Notification;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;

import java.util.Random;

import static com.example.jonathanstroz.backgroundnotificationreciever.DatabaseHelper.getRowCount;
import static com.example.jonathanstroz.backgroundnotificationreciever.Hush.CHANNEL_1_ID;

public class HushNotification {
    private Notification notification = new Notification();
    private int notification_code = 0;
    private String source;
    private int priority;
    private String message;
    private long time = 0;
    private String title;
    private int id;
    private StatusBarNotification status_notif;
    private int cancelReason = 0;
    private int code = 0;
    private int rows = 0;

    //Constructor
    public HushNotification(StatusBarNotification sbn){
        status_notif = sbn;
        time = sbn.getPostTime();
        notification = sbn.getNotification();
        message = notification.extras.getCharSequence(Notification.EXTRA_TEXT).toString();
        title = notification.extras.getCharSequence(Notification.EXTRA_TITLE).toString();
        source = sbn.getPackageName();
        notification_code = getAppCode(source);
        priority = setPriority(notification);
        id = sbn.getId();
        code = getAppCode(source);
        rows = getRowCount();
    }

    private static final class ApplicationPackageNames {
        public static final String FACEBOOK_PACK_NAME = "com.facebook.katana";
        public static final String FACEBOOK_MESSENGER_PACK_NAME =  "com.facebook.orca";
        public static final String WHATSAPP_PACK_NAME = "com.whatsapp";
        public static final String INSTAGRAM_PACK_NAME = "com.instagram.android";
        public static final String SNAPCHAT_PACK_NAME = "com.snapchat.android";
        public static final String HUSH_PACK_NAME = "com.example.jonathanstroz.backgroundnotificationreciever";
    }

    public static final class InterceptedNotificationCode {
        public static final int FACEBOOK_CODE = 1;
        public static final int WHATSAPP_CODE = 2;
        public static final int INSTAGRAM_CODE = 3;
        public static final int SNAPCHAT_CODE = 4;
        public static final int HUSH_CODE = 5;
        public static final int OTHER_NOTIFICATIONS_CODE = 0;
    }

    private int getAppCode(String source){
        if(source.equals(ApplicationPackageNames.FACEBOOK_PACK_NAME)
                || source.equals(ApplicationPackageNames.FACEBOOK_MESSENGER_PACK_NAME)){
            return(InterceptedNotificationCode.FACEBOOK_CODE);
        }
        else if(source.equals(ApplicationPackageNames.INSTAGRAM_PACK_NAME)){
            return(InterceptedNotificationCode.INSTAGRAM_CODE);
        }
        else if(source.equals(ApplicationPackageNames.WHATSAPP_PACK_NAME)){
            return(InterceptedNotificationCode.WHATSAPP_CODE);
        }
        else if(source.equals(ApplicationPackageNames.SNAPCHAT_PACK_NAME)){
            return(InterceptedNotificationCode.SNAPCHAT_CODE);

        }else if(source.equals(ApplicationPackageNames.HUSH_PACK_NAME)){
            return(InterceptedNotificationCode.HUSH_CODE);
        }
        else{
            return(InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE);
        }
    }

    public int getPriority(){
        return priority;
    }

    public int getRow(){
        return rows;
    }
    private int setPriority(Notification notification){
        //Get Priorities | 60%
        //Get ML Algorithm | 40%
        // 1 = high
        // 2 = medium
        // 3 = low
        // 4 = bucket
        // 5 = ignore
        Random rand = new Random();
        int randomNum = rand.nextInt((5 - 1) + 1) + 1;

        return 1;
    }

    public long getTime(){
        return time;
    }
    public String getMessage(){
        return message;
    }
    public int getNotifcationCode(){
        return notification_code;
    }
    public int getCancelReason(){
        return cancelReason;
    }
    public String getTitle(){
        return title;
    }
    public int getId() { return id;}
    public Notification getNotification() {return notification;}
    public StatusBarNotification getStatus_notif() {return status_notif;}

    public void setCancelReason(int reason){
        if(reason == 1 || reason == 2 || reason == 3){
            cancelReason = reason;
        }else{
            cancelReason = 0;
        }
        // REASON_CLICK: User clicks on the app (1)
        // REASON_CANCEL: User swipes (2)
        // REASON_CANCEL_ALL: USer cancels all notfications (3)
    }
}
