package com.example.jonathanstroz.backgroundnotificationreciever.ListViewHelperClasses;

import android.widget.SeekBar;
import android.widget.TextView;

public class FeatureViewHolder {
    private SeekBar featureImportance;
    private TextView featureName;
    private FeatureListItem featureDetails;

    public FeatureViewHolder(TextView name, SeekBar bar, FeatureListItem item){
        featureImportance = bar;
        featureName = name;
        featureDetails = item;
    }

    public void setDataIntoViewHolder(FeatureListItem item){
        featureImportance.setProgress(item.getImportance());
        featureName.setText(item.getName());
        featureDetails = item;
    }

    public int getImportance(){
        return featureImportance.getProgress();
    }

    public String getFeatureName(){
        return (String)featureName.getText();
    }
}
