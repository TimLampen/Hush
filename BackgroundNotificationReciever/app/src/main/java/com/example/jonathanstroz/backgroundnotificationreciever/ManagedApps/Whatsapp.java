package com.example.jonathanstroz.backgroundnotificationreciever.ManagedApps;

import com.example.jonathanstroz.backgroundnotificationreciever.HushNotification;

public class Whatsapp extends Application  {

    private static final String[] featureNames = {"Message"};
    private static final int[] featureImportances = {3};
    public static final String APPNAME = HushNotification.ApplicationNames.WHATSAPP_NAME;
    public static final int APPID = HushNotification.InterceptedNotificationCode.WHATSAPP_CODE;

    public static boolean init(){
        AppHolder a = new AppHolder(APPNAME, APPID, featureImportances, featureNames);
        return initApplication(a);
    }
}
