package com.example.jonathanstroz.backgroundnotificationreciever;

import android.support.constraint.ConstraintLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ModeHolder {

    private int displayModeNum;
    private int databaseModeNum;
    private String modeName;
    private TextView modeDisplay;
    private ConstraintLayout headerView;
    private TextView modeWord;
    private ListView contentListView;

    public ModeHolder(int mode, TextView tv, ConstraintLayout cv, TextView word, ListView lv){
        modeDisplay = tv;
        this.headerView = cv;
        this.modeWord = word;
        this.contentListView = lv;
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

    public TextView getModeWord(){
        return modeWord;
    }

    public ListView getContentListView(){
        return contentListView;
    }
}


