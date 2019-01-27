package com.example.jonathanstroz.backgroundnotificationreciever.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jonathanstroz.backgroundnotificationreciever.Database.DatabaseHelper;
import com.example.jonathanstroz.backgroundnotificationreciever.R;
import com.example.jonathanstroz.backgroundnotificationreciever.ListViewHelperClasses.MainAdapter;
import com.example.jonathanstroz.backgroundnotificationreciever.ListViewHelperClasses.MainListItem;
import com.example.jonathanstroz.backgroundnotificationreciever.ListViewHelperClasses.MainViewHolder;

import java.util.ArrayList;

public class SetupActivity extends AppCompatActivity {

    private static DatabaseHelper mDatabaseHelper;
    private static TextView test;

    private ArrayList<Integer> appsToActivate;
    private ArrayList<Integer> activatedApps;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        appsToActivate = new ArrayList<Integer>();

        mDatabaseHelper = MainActivity.mDatabaseHelper;

//        mDatabaseHelper.reset();
//
//        int i = 1/0;

        mDatabaseHelper.initializeApplicationTable();

        setContentView(R.layout.activity_setup);


        Button b  = (Button) findViewById(R.id.activationButton);
        ListView listV = findViewById(R.id.setupListView);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activateApps(v);
            }
        });

        listV.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainViewHolder holder = (MainViewHolder) view.getTag();

                if(appsToActivate.indexOf(holder.getAppId()) > -1){
                    appsToActivate.remove(appsToActivate.indexOf(holder.getAppId()));
                    Log.e("APP DEACTIVATED", holder.getAppName()+"");
                    ImageView symbol = (ImageView) view.findViewById(R.id.sign);
                    symbol.setImageResource(R.drawable.plus_sign);
                }
                else {
                    appsToActivate.add(holder.getAppId());
                    Log.e("APP ACTIVATED", holder.getAppName()+"");
                    ImageView symbol = (ImageView) view.findViewById(R.id.sign);
                    symbol.setImageResource(R.drawable.minus_sign);
                }
            }

        });

        listV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });

        ArrayList<MainListItem> list = mDatabaseHelper.getInActiveApps();

        MainAdapter adapter = new MainAdapter(this, R.layout.custom_list_element_setup, list);

        listV.setAdapter(adapter);
    }

    public void activateApps(View v) {
        // @TODO set the app to active in the database
        if(appsToActivate.size() > 0) {
            mDatabaseHelper.activateApps(appsToActivate);
            finish();
        }
    }

}
