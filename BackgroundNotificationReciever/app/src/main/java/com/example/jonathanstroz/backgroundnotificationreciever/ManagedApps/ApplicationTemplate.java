package com.example.jonathanstroz.backgroundnotificationreciever.ManagedApps;


import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.jonathanstroz.backgroundnotificationreciever.Activities.MainActivity;
import com.example.jonathanstroz.backgroundnotificationreciever.Database.DatabaseHelper;
import com.example.jonathanstroz.backgroundnotificationreciever.ListViewHelperClasses.FeatureListItem;

import java.util.ArrayList;

public abstract class ApplicationTemplate {

    protected int id;
    protected String name;
    private String featureQuery;
    private String initiatedCheck;

    public ApplicationTemplate(String appName, int appId){
        id = appId;
        name = appName;
        featureQuery = "SELECT * FROM "+name;
        initiatedCheck = "SELECT name FROM sqlite_master WHERE type='table' AND name='"+name+"}'";
    }

    /**
     * Creates a table in the database for the application
     * @param a
     * @return true if it creates a table, false othervise
     */
    public boolean initApplication(AppHolder a){
        SQLiteDatabase db = MainActivity.mDatabaseHelper.getReadableDatabase();
        if(!db.rawQuery(initiatedCheck,null).moveToFirst()){
            Log.e("APPCREATING",a.toCreateQuery());
            db.close();
            String query = a.toCreateQuery();
            db = MainActivity.mDatabaseHelper.getWritableDatabase();
            db.execSQL(query);

            db.close();
            return true;
        }
        db.close();
        return false;
    }

    public ArrayList<FeatureListItem> queryForFeatures(){
        SQLiteDatabase db = MainActivity.mDatabaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery(featureQuery,null);
        Log.e("APPLICATION TEMPLATE",DatabaseUtils.dumpCursorToString(c));
        if(c.moveToFirst()){
            do{
                //Log.e("DATABASE ROW","");
            }while(c.moveToNext());
        }
        db.close();
        return new ArrayList<FeatureListItem>();

    }
}
