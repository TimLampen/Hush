package com.example.jonathanstroz.backgroundnotificationreciever.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.jonathanstroz.backgroundnotificationreciever.ListViewHelperClasses.AppFeaturesHolder;
import com.example.jonathanstroz.backgroundnotificationreciever.R;
import com.example.jonathanstroz.backgroundnotificationreciever.ListViewHelperClasses.FeatureAdapter;
import com.example.jonathanstroz.backgroundnotificationreciever.ListViewHelperClasses.FeatureListItem;

import java.util.ArrayList;

public class AppDetailsActivity extends AppCompatActivity {

    private int appLogo;
    private String appName;
    private int appId;
    private SeekBar importanceBar;

    private TextView nameView;
    private ImageView logoView;

    private ListView featureListView;
    private ArrayList<FeatureListItem> features;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        importanceBar = (SeekBar) this.findViewById(R.id.importanceBar);
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

        loadApp();
    }

    private void loadApp() {

        featureListView = (ListView) findViewById(R.id.featureView);

        AppFeaturesHolder details = MainActivity.mDatabaseHelper.getAppFeatures(appId);

        features = details.getFeatureListItems();
        int[] importanceHolder = {details.getAppID(),details.getAppImportance()};
        importanceBar.setTag(importanceHolder);
        importanceBar.setProgress(details.getAppImportance());
        importanceBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int[] importanceHolder = (int[])seekBar.getTag();
                int id =  importanceHolder[0];
                int importance = importanceHolder[1];

                if(importance != seekBar.getProgress()) {
                    MainActivity.mDatabaseHelper.updateImportance(id, seekBar.getProgress());
                    int[] newImportance = {id, seekBar.getProgress()};
                    seekBar.setTag(newImportance);
                }
            }
        });

        FeatureAdapter adapter = new FeatureAdapter(this, R.layout.custom_list_element_features, features);
        featureListView.setAdapter(adapter);
    }
}
