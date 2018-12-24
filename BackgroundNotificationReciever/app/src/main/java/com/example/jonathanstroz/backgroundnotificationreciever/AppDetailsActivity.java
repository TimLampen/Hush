package com.example.jonathanstroz.backgroundnotificationreciever;

import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class AppDetails extends AppCompatActivity {

    private int appLogo;
    private String appName;
    private int appId;

    private TextView nameView;
    private ImageView logoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        init(this.getIntent());
    }

    public void init(Intent i){
        appName = i.getStringExtra(MainActivity.APPNAME);
        appLogo = i.getIntExtra(MainActivity.APPIMAGE, 0);
        appId = i.getIntExtra(MainActivity.APPID, 0);

        nameView = (TextView)findViewById(R.id.appName);
        logoView = (ImageView)findViewById(R.id.appLogo);

        logoView.setImageResource(appLogo);
        nameView.setText(appName);

    }
}
