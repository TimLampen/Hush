package com.example.jonathanstroz.backgroundnotificationreciever.ManagedApps;

import com.example.jonathanstroz.backgroundnotificationreciever.HushNotification;
import com.example.jonathanstroz.backgroundnotificationreciever.ListViewHelperClasses.FeatureListItem;

import java.util.ArrayList;
import java.util.Arrays;

public class Instagram extends ApplicationTemplate {
    private static final String[] featureNames = {"Like","Comment"};
    private static final Integer[] featureImportances = {2,2};
    public static final String APPNAME = HushNotification.ApplicationNames.INSTAGRAM_NAME;
    public static final int APPID = HushNotification.InterceptedNotificationCode.INSTAGRAM_CODE;

    public Instagram(){
        super(APPNAME,APPID);
        init();
    }

    public void init() {
        AppHolder a = new AppHolder(APPNAME, APPID, new ArrayList<Integer>(Arrays.asList(featureImportances)), new ArrayList<String>(Arrays.asList(featureNames)));

        initApplication(a);
    }
}
