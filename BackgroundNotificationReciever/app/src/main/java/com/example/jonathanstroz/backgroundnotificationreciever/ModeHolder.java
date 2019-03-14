package com.example.jonathanstroz.backgroundnotificationreciever;

import android.widget.TextView;

public class ModeHolder {

    private int displayModeNum;
    private int databaseModeNum;
    private String modeName;
    private TextView modeDisplay;

    public ModeHolder(int mode, TextView tv){
        modeDisplay = tv;
        this.setDisplayMode(mode);
        this.setDatabaseMode(mode);
    }

    public int getDeepModeNumber() {
        return databaseModeNum;
    }

    public String getModeName() {
        return modeName;
    }

    public void setDisplayMode(int modeNumber) {
        this.displayModeNum = modeNumber;
        this.modeName = HushNotification.Modes.MODE_NAMES[modeNumber];
        modeDisplay.setText(modeName);
    }

    public void setDatabaseMode(int modeNumber){
        this.databaseModeNum = modeNumber;
    }

}
