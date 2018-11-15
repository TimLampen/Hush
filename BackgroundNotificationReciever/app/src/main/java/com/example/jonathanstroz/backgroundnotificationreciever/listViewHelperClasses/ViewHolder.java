package com.example.jonathanstroz.backgroundnotificationreciever.listViewHelperClasses;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {
    public ImageView app_icon;
    public TextView app_name;

    public ViewHolder(ImageView icon, TextView name){
        //Bitmap b = BitmapFactory.decodeResource();
        app_name = name;
        app_icon = icon;
    }

    public void setDataIntoViewHolder(ListItem item){
        app_icon.setImageResource(item.getImage());
        app_name.setText(item.getName());
    }
}
