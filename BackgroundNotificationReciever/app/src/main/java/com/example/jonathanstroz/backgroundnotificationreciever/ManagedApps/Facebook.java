package com.example.jonathanstroz.backgroundnotificationreciever.ManagedApps;

import com.example.jonathanstroz.backgroundnotificationreciever.HushNotification;

import java.util.ArrayList;
import java.util.Arrays;

public class Facebook extends Application {

    private static final String[] featureNames = {"Events","Birthday"};
    private static final int[] featureImportances = {2,2};
    public static final String APPNAME = HushNotification.ApplicationNames.FACEBOOK_NAME;
    public static final int APPID = HushNotification.InterceptedNotificationCode.FACEBOOK_CODE;

    public static void init(){
        AppHolder a = new AppHolder(APPNAME, APPID, featureImportances, featureNames);

        initApplication(a);
    }
}
