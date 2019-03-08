package com.example.jonathanstroz.backgroundnotificationreciever.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;
import android.util.Log;

import com.example.jonathanstroz.backgroundnotificationreciever.Activities.MainActivity;
import com.example.jonathanstroz.backgroundnotificationreciever.HushNotification;
import com.example.jonathanstroz.backgroundnotificationreciever.ManagedApps.Facebook;
import com.example.jonathanstroz.backgroundnotificationreciever.ManagedApps.Instagram;
import com.example.jonathanstroz.backgroundnotificationreciever.ManagedApps.Messenger;
import com.example.jonathanstroz.backgroundnotificationreciever.ManagedApps.Snapchat;
import com.example.jonathanstroz.backgroundnotificationreciever.ManagedApps.Whatsapp;
import com.example.jonathanstroz.backgroundnotificationreciever.R;
import com.example.jonathanstroz.backgroundnotificationreciever.ListViewHelperClasses.AppFeaturesHolder;
import com.example.jonathanstroz.backgroundnotificationreciever.ListViewHelperClasses.FeatureListItem;
import com.example.jonathanstroz.backgroundnotificationreciever.ListViewHelperClasses.MainListItem;

import java.util.ArrayList;

import static com.example.jonathanstroz.backgroundnotificationreciever.Activities.MainActivity.mDatabaseHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
/*  Tables: Notifications, UserInfo, Activated Applications, Facebook, Instagram etc...

        Applications:
        id  Name    Activated
        1   FB      1
        etc...

        Table created for each application eg:
        Facebook:
        Feature Name    Importance  etc...
        Event           4
        Birthday        3

        Notifications:
        source  title   message time    dismissed

*/

    private Context dbhContext;

    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "hushDB";

    private static final String TABLE_NOTIFICATION = "notifications";
    private static final String KEY_SRC = "source";
    private static final String KEY_TITLE = "title";
    private static final String KEY_MSG = "message";
    private static final String KEY_TIME = "time";
    private static final String KEY_DISMISS = "dismissed";

    private static final String TABLE_USERINFO = "userInfo";
    private static final String KEY_INIT = "init";
    private static final String KEY_DEVICEID = "deviceID";

    private static final String TABLE_APPLICATIONS = "applications";
    private static final String KEY_ID = "ID";
    private static final String KEY_NAME = "name";
    private static final String KEY_ACTIVATED = "activated";
    private static final String KEY_FEATURES = "features";

    public static final String KEY_FEATUREID = "FeatureID";
    public static final String COL_FEATURENAME = "FeatureName";
    public static final String COL_IMPORTANCE = "Importance";


    //@TODO Add more user tables
    private static final String CREATE_TABLE_NOTIFICATION = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTIFICATION + " ("
            + KEY_SRC + " TEXT NOT NULL, "
            + KEY_TITLE + " TEXT NOT NULL, "
            + KEY_MSG + " TEXT NOT NULL, "
            + KEY_TIME + " TEXT NOT NULL, "
            + KEY_DISMISS + " TEXT);";

    private static final String CREATE_TABLE_USERDATA = "CREATE TABLE IF NOT EXISTS " + TABLE_USERINFO + " ("
            + KEY_INIT + " INTEGER NOT NULL, "
            + KEY_DEVICEID + " INTEGER NOT NULL);";

    private static final String CREATE_TABLE_APPLICATIONS = "CREATE TABLE IF NOT EXISTS " + TABLE_APPLICATIONS + " ("
            + KEY_ID + " INTEGER, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_ACTIVATED + " INTEGER); ";

    private static final String GET_ACTIVE_APPLICATIONS = "SELECT * FROM " + TABLE_APPLICATIONS + " WHERE "
            + KEY_ACTIVATED + " = 1";

    private static final String GET_INACTIVE_APPLICATIONS = "SELECT * FROM " + TABLE_APPLICATIONS + " WHERE "
            + KEY_ACTIVATED + " = 0";

    private static final String GET_ALL_APPLICATIONS = "SELECT * FROM " + TABLE_APPLICATIONS + ";";

    private static final String GET_INIT = "SELECT "+KEY_INIT+" FROM " + TABLE_USERINFO+";";

    private static final String[] NOTIFICATION_COLS = {KEY_SRC, KEY_TITLE, KEY_MSG, KEY_TIME, KEY_DISMISS};

    // Each app ID will correspond to it's location in the array eg: facebook is id 1
    private static final int[] APPLICATION_IMAGES = HushNotification.ApplicationImages.getApplicationImages();

    private static final String[] APPLICATION_NAMES = HushNotification.ApplicationNames.getApplicationNames();

    private static final int[] APPLICATION_IDS = HushNotification.InterceptedNotificationCode.getNotificationCodes();

    private static final int NUMBER_OF_APPLICATIONS = APPLICATION_IDS.length;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        dbhContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_USERDATA);
            db.execSQL(CREATE_TABLE_NOTIFICATION);
            db.execSQL(CREATE_TABLE_APPLICATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPLICATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERINFO);
        onCreate(db);
    }

    public void reset(){
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPLICATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERINFO);
        db.execSQL("DROP TABLE IF EXISTS " + HushNotification.ApplicationNames.FACEBOOK_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + HushNotification.ApplicationNames.INSTAGRAM_NAME);
    }

    public long addData(String source, String title, String message, Long time, String reason){
        //Fix adding to DB only adds one line
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_SRC, source);
        contentValues.put(KEY_TITLE, title);
        contentValues.put(KEY_MSG,  message);
        contentValues.put(KEY_TIME, time);
        contentValues.put(KEY_DISMISS, reason);
        long rowId = db.insert(TABLE_NOTIFICATION, null, contentValues);
        return rowId;
    }

    public boolean isInitialized(){
        SQLiteDatabase db = this.getReadableDatabase();

        if(db == null || !checkIfTableCreated(TABLE_USERINFO)){
            return false;
        }
        else{
            //  @TODO put if statement to see if fully initiated
            Cursor c = db.rawQuery(GET_INIT,null);
            if(c.moveToFirst()) {
                int initiated = c.getInt(c.getColumnIndex(KEY_INIT));
                if (initiated > 0) {
                    db.close();
                    return true;
                }
            }
            else{
                SQLiteDatabase writeDB = this.getWritableDatabase();

                ContentValues userInfoCV = new ContentValues();
                userInfoCV.put(KEY_INIT, 0);
                userInfoCV.put(KEY_DEVICEID, Settings.Secure.getString(dbhContext.getContentResolver(), Settings.Secure.ANDROID_ID)); // TODO

                Log.e("USERINFOUPDATE", userInfoCV.toString());
                writeDB.insert(TABLE_USERINFO,null,userInfoCV);
                writeDB.close();
            }
            db.close();
            return false;
        }
    }

    public boolean checkIfTableCreated(String tableName){
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='"+tableName+"';",null);
        return c.moveToFirst();
    }

    public void initializeApplicationTable(){ // @TODO synchronize loop with the notification code
        SQLiteDatabase dbWrite = mDatabaseHelper.getWritableDatabase();

        if(!checkIfTableCreated(TABLE_APPLICATIONS)) {
            dbWrite.execSQL(CREATE_TABLE_APPLICATIONS);
        }
        SQLiteDatabase dbRead = mDatabaseHelper.getReadableDatabase();
        Cursor c = dbRead.rawQuery("SELECT * FROM "+TABLE_APPLICATIONS,null);
        if(!c.moveToFirst()) {
            ContentValues values = new ContentValues();

            for (int i = 0; i < NUMBER_OF_APPLICATIONS; i++) {
                values.put(KEY_ID, APPLICATION_IDS[i]);
                values.put(KEY_NAME, APPLICATION_NAMES[i]);
                values.put(KEY_ACTIVATED, 0);
                dbWrite.insert(TABLE_APPLICATIONS, null, values);
            }
            dbRead.close();
            dbWrite.close();
        }

    }

    // @TODO check with johnny to see if we still need this
    public static String getDb(){

        try {
        String selectQuery = "SELECT  * FROM " + TABLE_NOTIFICATION;

        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String list = "";
        if (cursor.moveToFirst()){
            while(cursor.moveToNext()){
                String source = cursor.getString(cursor.getColumnIndex(KEY_SRC));
                String title = cursor.getString(cursor.getColumnIndex(KEY_TITLE));
                String message = cursor.getString(cursor.getColumnIndex(KEY_MSG));
                String time = cursor.getString(cursor.getColumnIndex(KEY_TIME));
                String reason = cursor.getString(cursor.getColumnIndex(KEY_DISMISS));
                list += source + "\t" + title + "\t" + message + "\t" + time + "\t" + reason + "\n";
            }
        }
        cursor.close();
        // close db connection
        db.close();

        // return notes list
        return list;
        }catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public ArrayList<Integer> getActivatedAppIds(){
        ArrayList<Integer> apps = new ArrayList<Integer>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(GET_ACTIVE_APPLICATIONS, null);

        if(c.moveToFirst()){
            int idIndex = c.getColumnIndex(KEY_ID);
            do{
                int appID = c.getInt(idIndex);
                apps.add(appID);
            }while(c.moveToNext());
        }

        c.close();
        db.close();

        return apps;
    }

    public ArrayList<MainListItem> getActiveApps(){
        return getApps(GET_ACTIVE_APPLICATIONS);
    }

    public ArrayList<MainListItem> getInActiveApps(){
        return getApps(GET_INACTIVE_APPLICATIONS);
    }

    public ArrayList<MainListItem> getAllApps(){
        return getApps(GET_ALL_APPLICATIONS);
    }

    private ArrayList<MainListItem> getApps(String query){
        ArrayList<MainListItem> apps = new ArrayList<MainListItem>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        int nameIndex = c.getColumnIndex(KEY_NAME);
        int idIndex = c.getColumnIndex(KEY_ID);

        Log.e("QUERY",query);
        Log.e("CURSOR OUTPUT",DatabaseUtils.dumpCursorToString(c));

        if(c.moveToFirst()){
            do{
                int appID = c.getInt(idIndex);
                int imageID;
                if(appID <= NUMBER_OF_APPLICATIONS ) {
                    imageID = APPLICATION_IMAGES[appID];
                }
                else{
                    imageID = R.drawable.hush_logo_full_no_background;
                }
                apps.add(new MainListItem(c.getString(nameIndex), imageID, appID));
            }while(c.moveToNext());
        }



        c.close();
        db.close();

        return apps;
    }

    public void activateApps(ArrayList<Integer> appsToAdd){

        String condition = "";

        ContentValues applicationCV = new ContentValues();
        applicationCV.put(KEY_ACTIVATED,1);

        ContentValues userInfoCV = new ContentValues();
        userInfoCV.put(KEY_INIT, 1);

        for(int i=0;i<appsToAdd.size(); i++){

            if(i == appsToAdd.size()-1){
                condition += KEY_ID+" = "+appsToAdd.get(i);
            }
            else{
                condition += KEY_ID+" = "+appsToAdd.get(i)+" OR ";
            }
            initializeTable(appsToAdd.get(i));
        }

        if(!this.isInitialized()){
            SQLiteDatabase db = this.getWritableDatabase();
            db.update(TABLE_USERINFO, userInfoCV, null,null);
        }
        SQLiteDatabase db = this.getWritableDatabase();

        db.update(TABLE_APPLICATIONS, applicationCV, condition,null);

        db.close();
    }

    private void initializeTable(int id){
        switch(id) {
            case HushNotification.InterceptedNotificationCode.FACEBOOK_CODE:
                Facebook.init();
                break;
            case HushNotification.InterceptedNotificationCode.INSTAGRAM_CODE:
                Instagram.init();
                break;
            case HushNotification.InterceptedNotificationCode.FACEBOOK_MESSENGER_CODE:
                Messenger.init();
                break;
            case HushNotification.InterceptedNotificationCode.SNAPCHAT_CODE:
                Snapchat.init();
                break;
            case HushNotification.InterceptedNotificationCode.WHATSAPP_CODE:
                Whatsapp.init();
                break;
            // Add other apps when supported
        }
    }

    public Cursor getRawApp(int id){
        String query = "Select * From "+APPLICATION_NAMES[id];
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        return c;
    }

    public void closeDB(){
        this.getWritableDatabase().close();
    }

    public AppFeaturesHolder getAppFeatures(int id){
        Cursor c = getRawApp(id);
        ArrayList<Integer> featureIDs = new ArrayList<Integer>();
        ArrayList<Integer> importances = new ArrayList<Integer>();
        ArrayList<String> names = new ArrayList<String>();

        if(c.moveToFirst()){
            do{
                int featureID = c.getInt(c.getColumnIndex(KEY_FEATUREID));
                String featureName = c.getString(c.getColumnIndex(COL_FEATURENAME));
                int featureImportance = c.getInt(c.getColumnIndex(COL_IMPORTANCE));
                importances.add(featureImportance);
                names.add(featureName);
                featureIDs.add(featureID);

            }while(c.moveToNext());
        }

        AppFeaturesHolder appHolder = new AppFeaturesHolder(id,featureIDs,names,importances);
        closeDB();
        return appHolder;
    }

    public ArrayList<FeatureListItem> getFeatureList(int id){
        return getAppFeatures(id).getFeatureListItems();
    }

    public boolean updateFeature(int appId, int featureId, int importance, String featureName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_IMPORTANCE, importance);
        cv.put(KEY_FEATUREID, featureId);
        cv.put(COL_FEATURENAME, featureName);
        db.update(APPLICATION_NAMES[appId], cv,KEY_FEATUREID +" = "+featureId, null);
        Log.e("FEATURE UPDATED", "FEATURE ID: "+featureId+" APPID: "+appId+" IMPORTANCE: "+importance);
        db.close();
        return false;
    }

    public static int getRowCount(){
        //SELECT Count(*) FROM tblName
        String countQuery = "SELECT  * FROM " + TABLE_NOTIFICATION;
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}
