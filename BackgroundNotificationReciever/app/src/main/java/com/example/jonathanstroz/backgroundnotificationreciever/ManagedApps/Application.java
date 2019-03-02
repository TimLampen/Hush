package com.example.jonathanstroz.backgroundnotificationreciever.ManagedApps;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.jonathanstroz.backgroundnotificationreciever.Activities.MainActivity;
import com.example.jonathanstroz.backgroundnotificationreciever.listViewHelperClasses.FeatureListItem;

import java.util.ArrayList;

public abstract class Application {

//    protected int id;
//    protected String name;
//    private String featureQuery;
//    private String initiatedCheck;
//
//    public Application(String appName, int appId){
//        id = appId;
//        name = appName;
//        featureQuery = "SELECT * FROM "+name;
//        initiatedCheck = "SELECT name FROM sqlite_master WHERE type='table' AND name='"+name+"}'";
//    }

    /**
     * Creates a table in the database for the application
     * @param a
     * @return true if it creates a table, false othervise
     */
    protected static boolean initApplication(AppHolder a){
        SQLiteDatabase db = MainActivity.mDatabaseHelper.getWritableDatabase();
        String query = a.toCreateQuery();
        db.execSQL(query);
        insertFeatures(a.getName(), a.getInsertValues(), db);
        db.close();
        return false;
    }

    private static void insertFeatures(String tableName, ArrayList<ContentValues> cvs, SQLiteDatabase db){
        for(int i=0; i<cvs.size(); i++){
            db.insert(tableName, null, cvs.get(i));
        }
    }

//    public ArrayList<FeatureListItem> queryForFeatures(){
//        SQLiteDatabase db = MainActivity.mDatabaseHelper.getReadableDatabase();
//        Cursor c = db.rawQuery(featureQuery,null);
//        Log.e("APPLICATION TEMPLATE",DatabaseUtils.dumpCursorToString(c));
//        if(c.moveToFirst()){
//            do{
//                //Log.e("DATABASE ROW","");
//            }while(c.moveToNext());
//        }
//        db.close();
//        return new ArrayList<FeatureListItem>();
//
//    }
}
