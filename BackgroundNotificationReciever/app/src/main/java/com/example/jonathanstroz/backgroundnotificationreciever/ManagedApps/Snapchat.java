package com.example.jonathanstroz.backgroundnotificationreciever.ManagedApps;

import com.example.jonathanstroz.backgroundnotificationreciever.HushNotification;

public class Snapchat extends Application  {

    private static final String[] featureNames = {"Message", "Screenshot", "Typing"};
    private static final int[] featureImportances = {3,3,1};
    public static final String APPNAME = HushNotification.ApplicationNames.SNAPCHAT_NAME;
    public static final int APPID = HushNotification.InterceptedNotificationCode.SNAPCHAT_CODE;

    public static boolean init(){
        AppHolder a = new AppHolder(APPNAME, APPID, featureImportances, featureNames);
        return initApplication(a);
    }
}
