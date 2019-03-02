package com.example.jonathanstroz.backgroundnotificationreciever.ManagedApps;

import com.example.jonathanstroz.backgroundnotificationreciever.HushNotification;

import java.util.ArrayList;
import java.util.Arrays;

public class Messenger extends Application  {

    private static final String[] featureNames = {"Message"};
    private static final int[] featureImportances = {3};
    public static final String APPNAME = HushNotification.ApplicationNames.FACEBOOK_MESSENGER_NAME;
    public static final int APPID = HushNotification.InterceptedNotificationCode.FACEBOOK_MESSENGER_CODE;

    public static boolean init(){
        AppHolder a = new AppHolder(APPNAME, APPID, featureImportances, featureNames);
        return initApplication(a);
    }
}
