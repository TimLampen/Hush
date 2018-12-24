package com.example.jonathanstroz.backgroundnotificationreciever.listViewHelperClasses;

public class FeatureListItem {

    private int featureImportance;
    private String featureName;

    public FeatureListItem(int progress, String name){
        featureImportance = progress;
        featureName = name;
    }

    public String getName(){
        return featureName;
    }

    public int getImportance(){
        return featureImportance;
    }
}
