package com.example.jonathanstroz.backgroundnotificationreciever.listViewHelperClasses;

public class FeatureListItem {

    private int featureImportance;
    private String featureName;
    private int featureID;
    private int appID;

    public FeatureListItem(int feature, int app, int progress, String name){
        featureID = feature;
        appID = app;
        featureImportance = progress;
        featureName = name;
    }

    public String getName(){
        return featureName;
    }

    public int getImportance(){
        return featureImportance;
    }

    public int getFeatureID() {
        return featureID;
    }

    public int getAppID(){
        return appID;
    }

    public void setImportance(int newImportance){
        featureImportance = newImportance;
    }

}
