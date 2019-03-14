package com.example.jonathanstroz.backgroundnotificationreciever;

import android.widget.TextView;

public class ModeHolder {

    private int modeNumber;
    private String modeName;
    private TextView modeDisplay;

    public ModeHolder(int mode, TextView tv){
        modeDisplay = tv;
        this.setMode(mode);
    }

    public int getModeNumber() {
        return modeNumber;
    }

    public String getModeName() {
        return modeName;
    }

    public void setMode(int modeNumber) {
        this.modeNumber = modeNumber;
        this.modeName = HushNotification.Modes.MODE_NAMES[modeNumber];
        modeDisplay.setText(modeName);
    }

}
