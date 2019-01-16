package com.example.jonathanstroz.backgroundnotificationreciever.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jonathanstroz.backgroundnotificationreciever.Activities.MainActivity;
import com.example.jonathanstroz.backgroundnotificationreciever.R;
import com.example.jonathanstroz.backgroundnotificationreciever.listViewHelperClasses.FeatureAdapter;
import com.example.jonathanstroz.backgroundnotificationreciever.listViewHelperClasses.FeatureListItem;

import java.util.ArrayList;

public class AppDetailsActivity extends AppCompatActivity {

    private int appLogo;
    private String appName;
    private int appId;

    private TextView nameView;
    private ImageView logoView;

    private ListView featureListView;
    private ArrayList<FeatureListItem> features;

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

        loadFeatures();
    }

    private void loadFeatures(){

        featureListView = (ListView)findViewById(R.id.featureView);

        // @TODO encorporate sql loading
        features = getList();

        FeatureAdapter adapter = new FeatureAdapter(this, R.layout.custom_list_element_features, features);
        featureListView.setAdapter(adapter);
    }

    public ArrayList<FeatureListItem> getList(){
        ArrayList<FeatureListItem> list = new ArrayList<FeatureListItem>();
        list.add(new FeatureListItem(10,"Event"));
        list.add(new FeatureListItem(60,"Birthday"));
        list.add(new FeatureListItem(70,"Message"));
        list.add(new FeatureListItem(90,"Post"));
        return list;
    }

    public void updateFeature(){

    }
}
