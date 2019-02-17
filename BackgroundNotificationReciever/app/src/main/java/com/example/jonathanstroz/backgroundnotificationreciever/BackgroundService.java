package com.example.jonathanstroz.backgroundnotificationreciever;

import android.app.Service;
import android.content.*;
import android.net.ConnectivityManager;
import android.os.*;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

public class BackgroundService extends Service {
    public NotifBroadcastReciever notifBroadcastReciever = new NotifBroadcastReciever();
    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service created!", Toast.LENGTH_LONG).show();

        handler = new Handler();
        runnable = new Runnable() {
            public void run() {

                IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
                registerReceiver(notifBroadcastReciever, filter);
            }
        };

        handler.postDelayed(runnable, 15000);
    }

    @Override
    public void onDestroy() {
        /* IF YOU WANT THIS SERVICE KILLED WITH THE APP THEN UNCOMMENT THE FOLLOWING LINE */
        //handler.removeCallbacks(runnable);
        Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();
        unregisterReceiver(notifBroadcastReciever);
    }

    @Override
    public void onStart(Intent intent, int startid) {
        Toast.makeText(this, "Service started by user.", Toast.LENGTH_LONG).show();
    }
}
