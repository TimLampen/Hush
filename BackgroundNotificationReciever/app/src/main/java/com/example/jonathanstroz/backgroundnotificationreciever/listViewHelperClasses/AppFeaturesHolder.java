package com.example.jonathanstroz.backgroundnotificationreciever.ListViewHelperClasses;

import java.util.ArrayList;

public class AppFeaturesHolder {

    // add keywords later
    private int appID;
    private ArrayList<Integer> featureIDs;
    private ArrayList<String> featureNames;
    private ArrayList<Integer> featureImportances;

    public AppFeaturesHolder(int id, ArrayList<Integer> fIDs, ArrayList<String> names, ArrayList<Integer> importances){
        appID = id;
        featureIDs = fIDs;
        featureNames = names;
        featureImportances = importances;
    }

    public ArrayList<String> getFeatureNames(){
        return featureNames;
    }

    public ArrayList<Integer> getFeatureImportances(){
        return featureImportances;
    }

    public int getAppID(){
        return appID;
    }

    public ArrayList<FeatureListItem> getFeatureListItems(){
        ArrayList<FeatureListItem> fl = new ArrayList<FeatureListItem>();

        for(int i=0; i<featureNames.size(); i++){
            fl.add(new FeatureListItem(appID, featureIDs.get(i), featureImportances.get(i), featureNames.get(i)));
        }

        return fl;
    }
}
