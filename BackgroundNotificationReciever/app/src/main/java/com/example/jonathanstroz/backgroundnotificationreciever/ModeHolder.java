package com.example.jonathanstroz.backgroundnotificationreciever;

import android.support.constraint.ConstraintLayout;
import android.widget.TextView;

public class ModeHolder {

    private int displayModeNum;
    private int databaseModeNum;
    private String modeName;
    private TextView modeDisplay;
    private ConstraintLayout headerView;

    public ModeHolder(int mode, TextView tv, ConstraintLayout cv){
        modeDisplay = tv;
        this.headerView = cv;
        this.setDisplayMode(mode);
        this.setDatabaseMode(mode);

    }

    public int getDeepModeNumber() {
        return databaseModeNum;
    }

    public String getModeName() {
        return modeName;
    }

    public ConstraintLayout getHeaderView(){
        return headerView;
    }

    public TextView getModeDisplay(){
        return modeDisplay;
    }

    public void setDisplayMode(int modeNumber) {
        this.displayModeNum = modeNumber;
    }

    public void setDatabaseMode(int modeNumber){
        this.databaseModeNum = modeNumber;
    }

}
