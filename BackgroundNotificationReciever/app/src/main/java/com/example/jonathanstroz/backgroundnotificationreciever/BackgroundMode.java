package com.example.jonathanstroz.backgroundnotificationreciever;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import com.example.jonathanstroz.backgroundnotificationreciever.Activities.MainActivity;

public enum BackgroundMode {
    HUSH("Hush", "#000000", "#000000"),
    STUDY("Study", "#BCC4D6", "#000000"),
    SOCIAL("Social", "#FFCA28", "#000000"),
    WORK("Work", "#071C46", "");


    private final String title, colorPrimary, titleColor;
    BackgroundMode(String title, String colorPrimary, String titleColor){
        this.colorPrimary = colorPrimary;
        this.titleColor = titleColor;
        this.title = title;
    }

    public void update(TextView textView, ConstraintLayout constraintLayout) {
        applyTitle(textView);
        applyBackground(constraintLayout);
    }

    private void applyTitle(TextView textView){
        textView.setText(this.title);
        textView.setTextColor(Color.parseColor(this.titleColor));
    }

    private void applyBackground(ConstraintLayout constraintLayout){
        constraintLayout.setBackgroundColor(Color.parseColor(this.colorPrimary));
    }
}
