package com.example.jonathanstroz.backgroundnotificationreciever.ManagedApps;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AppHolder {

    private int id;
    private String name;
    private ArrayList<Integer> featureImportances;
    private ArrayList<String> featureNames;

    public AppHolder(String tempName, int tempID, ArrayList<Integer> fImportances, ArrayList<String> fNames){
        if(fImportances.size() == fNames.size()) {
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

    public ArrayList<Integer> getFeatureImportances() {
        return featureImportances;
    }

    public ArrayList<String> getFeatureNames() {
        return featureNames;
    }

    public String toCreateQuery(){
        String query = "CREATE TABLE "+name+" ( FeatureID Integer PRIMARY KEY, FeatureName varchar(100) NOT NULL, Importance Integer DEFAULT 2);"
                + "INSERT INTO "+ name +" (FeatureID, FeatureName, Importance) VALUES ";


        for(int i=0; i<featureNames.size()-1;i++){
            query += "("+i+", "+featureNames.get(i)+", "+ featureImportances.get(i)+"),";
        }

        query += "("+(featureNames.size()-1)+", "+featureNames.get(featureNames.size()-1)+", "+ featureImportances.get(featureNames.size()-1)+");";

        return query;
    }
}
