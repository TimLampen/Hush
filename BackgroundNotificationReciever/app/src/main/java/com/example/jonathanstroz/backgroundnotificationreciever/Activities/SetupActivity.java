package com.example.jonathanstroz.backgroundnotificationreciever.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jonathanstroz.backgroundnotificationreciever.Database.DatabaseHelper;
import com.example.jonathanstroz.backgroundnotificationreciever.R;
import com.example.jonathanstroz.backgroundnotificationreciever.listViewHelperClasses.MainAdapter;
import com.example.jonathanstroz.backgroundnotificationreciever.listViewHelperClasses.MainListItem;
import com.example.jonathanstroz.backgroundnotificationreciever.listViewHelperClasses.MainViewHolder;

import java.util.ArrayList;

public class SetupActivity extends AppCompatActivity {

    private static DatabaseHelper mDatabaseHelper;
    private static TextView test;

    private ArrayList<Integer> appsToActivate;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        appsToActivate = new ArrayList<Integer>();

        mDatabaseHelper = MainActivity.mDatabaseHelper;

        mDatabaseHelper.initializeApplicationTable();

        setContentView(R.layout.activity_setup);

        ListView v = findViewById(R.id.setupListView);

        Log.e("XXXX", v.getId()+"");

        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainViewHolder holder = (MainViewHolder) view.getTag();
                appsToActivate.add(holder.getAppId());
            }

        });

        v.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });

        ArrayList<MainListItem> list = mDatabaseHelper.getInActiveApps();

        Log.e("XXXX", list.size()+"");
        MainAdapter adapter = new MainAdapter(this, R.layout.custom_list_element_setup, list);

        v.setAdapter(adapter);
    }

    public void activateApps(View v){
        // @TODO set the app to active in the database
        mDatabaseHelper.activateApps(appsToActivate);
    }

}
