package com.example.jonathanstroz.backgroundnotificationreciever.Activities;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.jonathanstroz.backgroundnotificationreciever.Database.DatabaseHelper;
import com.example.jonathanstroz.backgroundnotificationreciever.HushNotification;
import com.example.jonathanstroz.backgroundnotificationreciever.NotifBroadcastReciever;
import com.example.jonathanstroz.backgroundnotificationreciever.R;
import com.example.jonathanstroz.backgroundnotificationreciever.ListViewHelperClasses.MainAdapter;
import com.example.jonathanstroz.backgroundnotificationreciever.ListViewHelperClasses.MainListItem;
import com.example.jonathanstroz.backgroundnotificationreciever.ListViewHelperClasses.MainViewHolder;

import java.util.ArrayList;

import static com.example.jonathanstroz.backgroundnotificationreciever.Hush.CHANNEL_1_ID;
import static com.example.jonathanstroz.backgroundnotificationreciever.Hush.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity {
    public static NotificationManagerCompat notificationManager;

    NotifBroadcastReciever notifBroadcastReciever = new NotifBroadcastReciever();

    public static DatabaseHelper mDatabaseHelper;

    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
    public static final String APPID = "com.example.jonathanstroz.backgroundnotificationreciever.appId";
    public static final String APPNAME = "com.example.jonathanstroz.backgroundnotificationreciever.appName";
    public static final String APPIMAGE = "com.example.jonathanstroz.backgroundnotificationreciever.appImage";




    private ImageView interceptedNotificationImageView;
    private ImageChangeBroadcastReceiver imageChangeBroadcastReceiver;
    private AlertDialog enableNotificationListenerAlertDialog;
    private ListView contentListView;
    private ArrayList<MainListItem> homeMainListItems;
    private Button loadingScreenButton;
    private View.OnClickListener appSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notificationManager = NotificationManagerCompat.from(this);

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(notifBroadcastReciever, filter);

        // If the user did not turn the notification listener service on we prompt him to do so
        if(!isNotificationServiceEnabled()){
            enableNotificationListenerAlertDialog = buildNotificationServiceAlertDialog();
            enableNotificationListenerAlertDialog.show();
        }

        mDatabaseHelper = new DatabaseHelper(this);

        // Finally we register a receiver to tell the MainActivity when a notification has been received
        imageChangeBroadcastReceiver = new ImageChangeBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.github.chagall.notificationlistenerexample");
        registerReceiver(imageChangeBroadcastReceiver,intentFilter);

        // check Database for app initiated flag
        if(!mDatabaseHelper.isInitialized()){
            setContentView(R.layout.loading_screen);
        }
        else{
            loadApp();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(imageChangeBroadcastReceiver);
    }

    public void setupUser(View v){
        Log.e("XXXX","method has been run");
        if(!mDatabaseHelper.isInitialized()) {
            Intent i = new Intent(this, SetupActivity.class); // @TODO check use of activity for this, back arrow may be difficult
            startActivity(i);
        }
        else{
            loadApp();
        }
    }

    public void loadApp(){
        // @TODO load data from sql Database
        homeMainListItems = getList();
        setContentView(R.layout.main_screen);

        contentListView = (ListView) this.findViewById(R.id.contentListView);

        contentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                appSelected(view,position,id);
            }

        });

        contentListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });


        MainAdapter adapter = new MainAdapter(this, R.layout.custom_list_element_main, homeMainListItems);

        contentListView.setAdapter(adapter);

    }

    public void appSelected(View v, int pos, long id) {
        Intent i = new Intent(this, AppDetailsActivity.class);
        MainViewHolder holder = (MainViewHolder) v.getTag();
        i.putExtra(APPID, holder.getAppId());
        i.putExtra(APPNAME, holder.getAppName());
        i.putExtra(APPIMAGE, holder.getAppImage());
        startActivity(i);
    }

    /**
     * @TODO make use of database
     * @return
     */
    public ArrayList<MainListItem> getList(){
        ArrayList<MainListItem> listContent = mDatabaseHelper.getActiveApps(); // @TODO this will be calling the database function

//        MainListItem item1 = new MainListItem("Facebook", R.drawable.facebook_logo_small, HushNotification.InterceptedNotificationCode.FACEBOOK_CODE);
//        MainListItem item2 = new MainListItem("Instagram", R.drawable.instagram_logo_small, HushNotification.InterceptedNotificationCode.INSTAGRAM_CODE);
//
//        listContent.add(item1);
//        listContent.add(item2);
        return listContent;
    }

    /**
     * Change Intercepted Notification Image
     * Changes the MainActivity image based on which notification was intercepted
     * @param notificationCode The intercepted notification code
     */
    private void changeInterceptedNotificationImage(int notificationCode){
        //@TODO uncomment
        switch(notificationCode){
            case HushNotification.InterceptedNotificationCode.FACEBOOK_CODE:
                interceptedNotificationImageView.setImageResource(R.drawable.facebook_logo);
                break;
            case HushNotification.InterceptedNotificationCode.INSTAGRAM_CODE:
                interceptedNotificationImageView.setImageResource(R.drawable.instagram_logo);
                break;
            case HushNotification.InterceptedNotificationCode.WHATSAPP_CODE:
                interceptedNotificationImageView.setImageResource(R.drawable.whatsapp_logo);
                break;
            case HushNotification.InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE:
                interceptedNotificationImageView.setImageResource(R.drawable.other_notification_logo);
                break;
        }
    }

    /**
     * Is Notification Service Enabled.
     * Verifies if the notification listener service is enabled.
     * Got it from: https://github.com/kpbird/NotificationListenerService-Example/blob/master/NLSExample/src/main/java/com/kpbird/nlsexample/NLService.java
     * @return True if eanbled, false otherwise.
     */
    private boolean isNotificationServiceEnabled(){
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(),
                ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Image Change Broadcast Receiver.
     * We use this Broadcast Receiver to notify the Main Activity when
     * a new notification has arrived, so it can properly change the
     * notification image
     * */
    public class ImageChangeBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int receivedNotificationCode = intent.getIntExtra("Notification Code",-1);
            changeInterceptedNotificationImage(receivedNotificationCode);
        }
    }


    /**
     * Build Notification Listener Alert Dialog.
     * Builds the alert dialog that pops up if the user has not turned
     * the Notification Listener Service on yet.
     * @return An alert dialog which leads to the notification enabling screen
     */
    private AlertDialog buildNotificationServiceAlertDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.notification_listener_service);
        alertDialogBuilder.setMessage(R.string.notification_listener_service_explanation);
        alertDialogBuilder.setPositiveButton(R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
                    }
                });
        alertDialogBuilder.setNegativeButton(R.string.no,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // If you choose to not enable the notification listener
                        // the app. will not work as expected
                    }
                });
        return(alertDialogBuilder.create());
    }



    public void sendOnChannel1(View v){
        //Set up notification bucket
        String title = "Example Notification";
        String message = "Example Message";
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_announcement_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        notificationManager.notify(1,notification);
    }

    public void sendOnChannel2(View v){
        String title1 = "Title 1";
        String message1 = "Message 1";

        Notification notification1 = new NotificationCompat.Builder(MainActivity.this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_announcement_black_24dp)
                .setContentTitle(title1)
                .setContentText(message1)
                .setGroup("example_group")
                .build();

        Notification summaryNotification = new NotificationCompat.Builder(MainActivity.this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_announcement_black_24dp)
                .setStyle(new NotificationCompat.InboxStyle()
                        //.addLine(title1 + " " + message1)
                        .setBigContentTitle("Notification Bucket"))
                .setGroup("example_group")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setGroupSummary(true)
                .build();

        int id1 = (int) System.currentTimeMillis();
        notificationManager.notify(id1 ,notification1);
        notificationManager.notify(1000, summaryNotification);
    }


}
