package com.example.jonathanstroz.backgroundnotificationreciever;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.widget.TextView;

import com.example.jonathanstroz.backgroundnotificationreciever.Activities.MainActivity;

public enum BackgroundMode {
    HUSH("Hush", "#000000", "#000000", "#D81B60", "#ffffff", "#e6e6e6", "#F1AF37"),
    STUDY("Study", "#BCC4D6", "#000000", "#D81B60", "#ffffff", "#e6e6e6", "#F1AF37"),
    SOCIAL("Social", "#FFCA28", "#000000", "#D81B60", "#ffffff", "#e6e6e6", "#F1AF37"),
    DAY("Day", "#071C46", "", "", "", "", "");


    private final String title, colorPrimary, colorPrimaryDark, colorAccent, titleColor, header, buttonColor;
    BackgroundMode(String title, String colorPrimary, String colorPrimaryDark, String colorAccent, String titleColor, String header, String buttonColor){
        this.colorPrimary = colorPrimary;
        this.colorPrimaryDark = colorPrimaryDark;
        this.colorAccent = colorAccent;
        this.titleColor = titleColor;
        this.title = title;
        this.header = header;
        this.buttonColor = buttonColor;
    }


    public void apply(MainActivity mainActivity){
        ConstraintLayout constraintLayout = mainActivity.findViewById(R.id.constraintLayout2);
        constraintLayout.setBackgroundColor(Color.parseColor(this.colorPrimary));

        TextView textView = mainActivity.findViewById(R.id.textView2);
        textView.setText(this.title);
    }
}
