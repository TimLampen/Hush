package com.example.jonathanstroz.backgroundnotificationreciever.listViewHelperClasses;

import android.widget.ImageView;
import android.widget.TextView;

public class MainViewHolder {
    private ImageView appIcon;
    private TextView appName;
    private int appId;
    private MainListItem appDetails;

    public MainViewHolder(ImageView icon, TextView name, int id, MainListItem item){
        //Bitmap b = BitmapFactory.decodeResource();
        appName = name;
        appIcon = icon;
        appId = id;
        appDetails = item;
    }

    public void setDataIntoViewHolder(MainListItem item){
        appIcon.setImageResource(item.getImage());
        appName.setText(item.getName());
        appId = item.getAppId();
        appDetails = item;
    }

    public int getAppId(){
        return appId;
    }

    public ImageView getAppImageView(){
        return appIcon;
    }

    public int getAppImage(){
        return appDetails.getImage();
    }

    public String getAppName(){
        return (String)appName.getText();
    }
}
