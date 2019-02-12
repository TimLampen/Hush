package com.example.jonathanstroz.backgroundnotificationreciever;

import android.service.notification.StatusBarNotification;

public class DatabaseNotification {

    private int notification_code = 0;
    private String source;
    private String message;
    private long time = 0;
    private String title;
    private int id;
    private int cancelReason = 0;

    public DatabaseNotification(HushNotification copy){
        time = copy.getTime();
        message = copy.getMessage();
        title = copy.getTitle();
        source = copy.getSource();
        notification_code = copy.getNotifcationCode();
        id = copy.getId();
        cancelReason = copy.getCancelReason();
    }

    public long getTime(){return time;}
    public String getMessage(){return message;}
    public String getSource() {return source;}
    public String getTitle() {return title;}
    public int getNotification_code(){return notification_code;}
    public int getId() {return id;} //Id not unique can remove
    public int getCancelReason() { return cancelReason;}

}
