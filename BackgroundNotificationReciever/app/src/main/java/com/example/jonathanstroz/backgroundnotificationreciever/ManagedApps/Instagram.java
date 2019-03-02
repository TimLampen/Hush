package com.example.jonathanstroz.backgroundnotificationreciever.ManagedApps;

import com.example.jonathanstroz.backgroundnotificationreciever.HushNotification;

import java.util.ArrayList;
import java.util.Arrays;

public class Instagram extends Application {
    private static final String[] featureNames = {"Like","Comment"};
    private static final int[] featureImportances = {2,2};
    public static final String APPNAME = HushNotification.ApplicationNames.INSTAGRAM_NAME;
    public static final int APPID = HushNotification.InterceptedNotificationCode.INSTAGRAM_CODE;

    public static void init() {
        AppHolder a = new AppHolder(APPNAME, APPID, featureImportances, featureNames);

        initApplication(a);
    }
}
