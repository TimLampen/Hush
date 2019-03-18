package com.example.jonathanstroz.backgroundnotificationreciever;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jonathanstroz.backgroundnotificationreciever.Activities.MainActivity;

import java.util.List;

public enum BackgroundMode {
    HUSH("Hush", "#1E1F34", "#ffffff", "#dddcde"),
    STUDY("Study", "#BCC4D6", "#000000", "#e4e8ff"),
    SOCIAL("Social", "#F5D698", "#000000", "#fff8e8"),
    WORK("Work", "#071C46", "#ffffff", "#d2daf5");


    private final String title, colorPrimary, titleColor, modeColor, contentColor;
    BackgroundMode(String title, String colorPrimary, String titleColor, String contentColor){
        this.colorPrimary = colorPrimary;
        this.titleColor = titleColor;
        this.title = title;
        this.modeColor = titleColor;
        this.contentColor = contentColor;
    }

    public void update(TextView textView, ConstraintLayout constraintLayout, TextView modeText, ListView lv) {
        applyTitle(textView);
        applyBackground(constraintLayout);
        applyColorToText(modeText);
        applyContent(lv);
    }

    private void applyTitle(TextView textView){
        textView.setText(this.title);
        textView.setTextColor(Color.parseColor(this.titleColor));
    }

    private void applyBackground(ConstraintLayout constraintLayout){
        constraintLayout.setBackgroundColor(Color.parseColor(this.colorPrimary));
    }

    private void applyColorToText(TextView mode){
        mode.setTextColor(Color.parseColor(this.modeColor));
    }

    private void applyContent(ListView lv){
        lv.setBackgroundColor(Color.parseColor(this.contentColor));
    }
}
