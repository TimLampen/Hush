package com.example.jonathanstroz.backgroundnotificationreciever;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
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

import static com.example.jonathanstroz.backgroundnotificationreciever.DatabaseHelper.getDb;

import com.example.jonathanstroz.backgroundnotificationreciever.listViewHelperClasses.CustomAdapter;
import com.example.jonathanstroz.backgroundnotificationreciever.listViewHelperClasses.ListItem;
import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.jonathanstroz.backgroundnotificationreciever.Hush.CHANNEL_1_ID;
import static com.example.jonathanstroz.backgroundnotificationreciever.Hush.CHANNEL_2_ID;
import static com.example.jonathanstroz.backgroundnotificationreciever.Hush.CHANNEL_3_ID;
import static com.example.jonathanstroz.backgroundnotificationreciever.Hush.CHANNEL_4_ID;

public class MainActivity extends AppCompatActivity {
    public NotificationManagerCompat notificationManager;

    public static final String high_channel = "HUSH_HIGH";
    public static final String low_channel = "HUSH_LOW";
    public static final String medium_channel = "HUSH_MEDIUM";
    public static final String bucket_channel = "HUSH_BUCKET";
    public static final String groupId = "bucket_group";

    NotifBroadcastReciever notifBroadcastReciever = new NotifBroadcastReciever();

    public static DatabaseHelper mDatabaseHelper;

    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";

    private ImageView interceptedNotificationImageView;
    private ImageChangeBroadcastReceiver imageChangeBroadcastReceiver;
    private AlertDialog enableNotificationListenerAlertDialog;
    private ListView contentListView;
    private ArrayList<ListItem> homeListItems;
    private Button loadingScreenButton;
    private View.OnClickListener appSelector;

    //maybe this works

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notificationManager = NotificationManagerCompat.from(this);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(notifBroadcastReciever, filter);
        createNotifcationChannels();
        setContentView(R.layout.loading_screen);


        // If the user did not turn the notification listener service on we prompt him to do so
        if(!isNotificationServiceEnabled()){
            enableNotificationListenerAlertDialog = buildNotificationServiceAlertDialog();
            enableNotificationListenerAlertDialog.show();
        }

        mDatabaseHelper = new DatabaseHelper(this);

        // check Database for app initiated flag
        loadingScreenButton = (Button) findViewById(R.id.getStartedBtn);

        loadingScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadApp(v);
            }
        });

        // Finally we register a receiver to tell the MainActivity when a notification has been received
        imageChangeBroadcastReceiver = new ImageChangeBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.github.chagall.notificationlistenerexample");
        registerReceiver(imageChangeBroadcastReceiver,intentFilter);

        Firebase.setAndroidContext(this);

        //Newer version of Firebase
        if(!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(imageChangeBroadcastReceiver);
        unregisterReceiver(notifBroadcastReciever);
    }

    public void loadApp(View v){

        loadingScreenButton = null;
        // @TODO load data from sql Database

        setContentView(R.layout.main_screen);

        contentListView = (ListView) this.findViewById(R.id.contentListView);

        contentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }

        });

        contentListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });

        homeListItems = getList();
        CustomAdapter adapter = new CustomAdapter(this, R.layout.custom_list_element_main, homeListItems);

        contentListView.setAdapter(adapter);

    }

    public ArrayList<ListItem> getList(){
        ArrayList<ListItem> listContent = new ArrayList<ListItem>(); // @TODO this will be calling the database function

        ListItem item1 = new ListItem("Facebook", R.drawable.facebook_logo_small);
        ListItem item2 = new ListItem("Instagram", R.drawable.instagram_logo_small);

        listContent.add(item1);
        listContent.add(item2);
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


    /*
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setChannels(){
        //create notification channels
        NotificationManager nManager = getApplicationContext().getSystemService(NotificationManager.class);

        NotificationChannel highChannel = new NotificationChannel("SENDHIGH", "HUSH_SEND_ON_HIGH", NotificationManager.IMPORTANCE_HIGH );
        nManager.createNotificationChannel(highChannel);

        NotificationChannel defChannel = new NotificationChannel("SENDDEF", "HUSH_SEND_ON_DEFAULT", NotificationManager.IMPORTANCE_DEFAULT );
        nManager.createNotificationChannel(defChannel);

        NotificationChannel lowChannel = new NotificationChannel("SENDLOW", "HUSH_SEND_ON_LOW", NotificationManager.IMPORTANCE_MIN );
        nManager.createNotificationChannel(lowChannel);

        NotificationChannel bucketChannel = new NotificationChannel("SENDBUCKET", "HUSH_SEND_ON_BUCKET", NotificationManager.IMPORTANCE_LOW);
        nManager.createNotificationChannel(bucketChannel);
    } */

    private void createNotifcationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    high_channel,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("High Priority Notification Route");

            NotificationChannel channel3 = new NotificationChannel(
                    CHANNEL_3_ID,
                    low_channel,
                    NotificationManager.IMPORTANCE_LOW
            );
            channel3.setDescription("Low Priority Notification Route");

            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    medium_channel,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel2.enableLights(true);
            channel2.setLightColor(Color.RED);
            channel2.enableVibration(true);
            channel2.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            channel3.setDescription("Medium Priority Notification Route");

            NotificationChannel channel4 = new NotificationChannel(
                    CHANNEL_4_ID,
                    bucket_channel,
                    NotificationManager.IMPORTANCE_MIN //FIX
            );

            channel4.setDescription("Bucket Notification Route");


            CharSequence groupName = "Some Group";
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannelGroup(new NotificationChannelGroup(groupId, groupName));
            channel4.setGroup(groupId);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
            manager.createNotificationChannel(channel3);
            manager.createNotificationChannel(channel4);
        }
    }
}
