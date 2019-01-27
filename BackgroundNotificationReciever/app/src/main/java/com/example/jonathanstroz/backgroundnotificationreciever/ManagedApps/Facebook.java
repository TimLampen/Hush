package com.example.jonathanstroz.backgroundnotificationreciever.ManagedApps;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;

import com.example.jonathanstroz.backgroundnotificationreciever.HushNotification;
import com.example.jonathanstroz.backgroundnotificationreciever.ListViewHelperClasses.FeatureListItem;
import com.example.jonathanstroz.backgroundnotificationreciever.NotificationService;

import java.util.ArrayList;
import java.util.Arrays;

public class Facebook extends ApplicationTemplate {

    private static final String[] featureNames = {"Events","Birthday"};
    private static final Integer[] featureImportances = {2,2};
    public static final String APPNAME = HushNotification.ApplicationNames.FACEBOOK_NAME;
    public static final int APPID = HushNotification.InterceptedNotificationCode.FACEBOOK_CODE;

    public Facebook(){
        super(APPNAME,APPID);
        init();
    }

    public void init(){
        AppHolder a = new AppHolder(APPNAME, APPID, new ArrayList<Integer>(Arrays.asList(featureImportances)), new ArrayList<String>(Arrays.asList(featureNames)));

        initApplication(a);
    }
}
