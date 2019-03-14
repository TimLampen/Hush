package com.example.jonathanstroz.backgroundnotificationreciever;
import android.app.Notification;
import android.service.notification.StatusBarNotification;

import com.example.jonathanstroz.backgroundnotificationreciever.Activities.MainActivity;
import com.example.jonathanstroz.backgroundnotificationreciever.ListViewHelperClasses.AppFeaturesHolder;
import com.example.jonathanstroz.backgroundnotificationreciever.ListViewHelperClasses.AppFeaturesHolder;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Random;

import static com.example.jonathanstroz.backgroundnotificationreciever.Database.DatabaseHelper.getRowCount;
import static com.example.jonathanstroz.backgroundnotificationreciever.Hush.CHANNEL_1_ID;

public class HushNotification {
    @Exclude
    private Notification notification = new Notification();

    private int notification_code = 0;
    private String source;
    private double priority = 0;
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
        status_notif = sbn; //
        time = sbn.getPostTime();
        notification = sbn.getNotification();
        message = notification.extras.getCharSequence(Notification.EXTRA_TEXT).toString();
        title = notification.extras.getCharSequence(Notification.EXTRA_TITLE).toString();
        source = sbn.getPackageName();
        notification_code = getAppCode(source);
        id = sbn.getId();
        code = getAppCode(source);
    }

    public static final class ApplicationPackageNames {
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
        public static final int FACEBOOK_MESSENGER_CODE = 6;
        public static final int OTHER_NOTIFICATIONS_CODE = 0;

        public static int[] getNotificationCodes(){
            int[] temp = {OTHER_NOTIFICATIONS_CODE, FACEBOOK_CODE, WHATSAPP_CODE, INSTAGRAM_CODE, SNAPCHAT_CODE, HUSH_CODE, FACEBOOK_MESSENGER_CODE};
            return temp;
        }
    }

    public static final class ApplicationNames {
        public static final String FACEBOOK_NAME = "Facebook";
        public static final String WHATSAPP_NAME = "Whatsapp";
        public static final String INSTAGRAM_NAME = "Instagram";
        public static final String SNAPCHAT_NAME = "Snapchat";
        public static final String HUSH_NAME = "Hush";
        public static final String FACEBOOK_MESSENGER_NAME = "Messenger";
        public static final String OTHER_NOTIFICATIONS_NAME = "Other";

        public static String[] getApplicationNames(){
            String[] temp = {OTHER_NOTIFICATIONS_NAME, FACEBOOK_NAME, WHATSAPP_NAME, INSTAGRAM_NAME, SNAPCHAT_NAME, HUSH_NAME, FACEBOOK_MESSENGER_NAME};
            return temp;
        }
    }

    public static final class Modes {
        public static final int HUSH_MODE = 0;
        public static final int WORK_MODE = 1;
        public static final int MEETING_MODE = 2;
        public static final int SOCIAL_MODE = 3;

        public static final String[] MODE_NAMES = {"Hush", "Work", "Meeting", "Social"};
    }

    public static final class ApplicationImages {
        public static final int FACEBOOK_IMAGE_EXTRA_SMALL = R.drawable.facebook_logo_extra_small;
        public static final int FACEBOOK_IMAGE_SMALL = R.drawable.facebook_logo_small;
        public static final int INSTAGRAM_IMAGE_EXTRA_SMALL = R.drawable.instagram_logo_extra_small;
        public static final int INSTAGRAM_IMAGE_SMALL = R.drawable.instagram_logo_small;
        public static final int DEFAULT_IMAGE = R.drawable.hush_logo_full_no_background;

        public static int[] getApplicationImages() {
            int[] temp = {DEFAULT_IMAGE, FACEBOOK_IMAGE_EXTRA_SMALL, DEFAULT_IMAGE, INSTAGRAM_IMAGE_EXTRA_SMALL, DEFAULT_IMAGE, DEFAULT_IMAGE, DEFAULT_IMAGE};
            return temp;
        }
    }

    private int getAppCode(String source){
        if(source.equals(ApplicationPackageNames.FACEBOOK_PACK_NAME)){
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

        }else if(source.equals(ApplicationPackageNames.FACEBOOK_MESSENGER_PACK_NAME)){
            return(InterceptedNotificationCode.FACEBOOK_MESSENGER_CODE);
        }
        else{
            return(InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE);
        }
    }

    public double getPriority(){
        return priority;
    }

    public int getRow(){
        return rows;
    }

    public void setPriority(HushNotification notification){
        //make sure it is one of the apps that is set up with the user
        AppFeaturesHolder appHolder = MainActivity.mDatabaseHelper.getAppFeatures(notification_code);
        ArrayList<String> features = appHolder.getFeatureNames();
        ArrayList<Integer> feature_importance = appHolder.getFeatureImportances();
        int feature_chosen_importance = -1;
        String title = notification.getTitle();
        String text = notification.getMessage();

        for (int i = 1; i < features.size();i++){
            if(title.contains(features.get(i)) ||  text.contains(features.get(i))){
                feature_chosen_importance = feature_importance.get(i);
            }
        }

        int app_importance = feature_importance.get(0);

        if ( feature_chosen_importance == -1){
            feature_chosen_importance = app_importance;
        }

        // talk about possibility of modes

        //App importance set to id 1

        priority = ((feature_chosen_importance + app_importance)/10) * 3;

        if(notification_code == InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE){
            priority = -1;
        }
    }

    public String getSource(){return source;}
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

    @Exclude public Notification getNotification() {return notification;} // Exclude Notification Feild

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
