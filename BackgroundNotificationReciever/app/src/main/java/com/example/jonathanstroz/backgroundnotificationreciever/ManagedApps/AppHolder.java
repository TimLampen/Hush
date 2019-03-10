package com.example.jonathanstroz.backgroundnotificationreciever.ManagedApps;

import android.content.ContentValues;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.jonathanstroz.backgroundnotificationreciever.Database.DatabaseHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Used for the creation of each app table
 */
public class AppHolder {

    private int id;
    private String name;
    private int[] featureImportances;
    private String[] featureNames;

    public AppHolder(String tempName, int tempID, int[] fImportances, String[] fNames){
        if(fImportances.length == fNames.length) {
            id = tempID;
            name = tempName;
            featureImportances = fImportances;
            featureNames = fNames;
        }
        else{
            Log.e("ERROR IN APPHOLDER","Arrays not same size");
        }
    }

    public int getID() {
        return id;
    }

    public String getName(){
        return name;
    }

    public int[] getFeatureImportances() {
        return featureImportances;
    }

    public String[] getFeatureNames() {
        return featureNames;
    }

    public String toCreateQuery(){
        String query = "CREATE TABLE IF NOT EXISTS "+name+" ( "+ DatabaseHelper.KEY_FEATUREID +" Integer PRIMARY KEY, "+ DatabaseHelper.COL_FEATURENAME +" varchar(100) NOT NULL, "+ DatabaseHelper.COL_IMPORTANCE +" Integer DEFAULT 2);";
               // + "INSERT INTO "+ name +" (FeatureID, FeatureName, Importance) VALUES ";

        return query;
    }

    public ArrayList<ContentValues> getInsertValues(){
        ArrayList<ContentValues> cvs = new ArrayList<ContentValues>();

        for(int i=0; i<featureNames.length;i++){
            ContentValues vals = new ContentValues();
            vals.put(DatabaseHelper.KEY_FEATUREID, i);
            vals.put(DatabaseHelper.COL_FEATURENAME, featureNames[i]);
            vals.put(DatabaseHelper.COL_IMPORTANCE, featureImportances[i]);
            cvs.add(vals);
        }

        return cvs;
    }

}
