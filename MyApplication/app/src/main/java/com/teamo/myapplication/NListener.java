package com.teamo.myapplication;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NListener extends NotificationListenerService
{

    private String TAG = this.getClass().getSimpleName();
    private NReceiver nRecv;

    @Override
    public void onCreate()
    {
        super.onCreate();
        nRecv = new NReceiver();
        IntentFilter filter = new IntentFilter();

        //standard for adding actions is package name + event in all caps with underscores
        filter.addAction("com.teamo.myapplication.NOTIFICATION_LISTENER");
        registerReceiver(nRecv, filter);

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(nRecv);
    }


    /**
     *
     * @param sNotif
     */
    @Override
    public void onNotificationPosted(StatusBarNotification sNotif)
    {
        Log.i(TAG, "Notification Posted");
        Log.i(TAG, "ID: " + sNotif.getId() + "\t" + sNotif.getNotification().tickerText + "\t" + sNotif.getPackageName());
        Intent post = new Intent("com.teamo.myapplication.NOTIFICATION_LISTENER");
        post.putExtra("notification_event", "onNotificationPosted:" + sNotif.getPackageName() + "\n");
        sendBroadcast(post);


    }

    /**
     *
     * @param sNotif
     */
    @Override
    public void onNotificationRemoved(StatusBarNotification sNotif)
    {
        Log.i(TAG, "Notification Removed");
        Log.i(TAG, "ID: " + sNotif.getId() + "\t" + sNotif.getNotification().tickerText + "\t" + sNotif.getPackageName());
        Intent remove = new Intent("com.teamo.myapplication.NOTIFICATION_LISTENER");
        remove.putExtra("notification_event", "onNotificationRemoved:" + sNotif.getPackageName() + "\n");
        sendBroadcast(remove);
    }

    /**
     * private nested class for braodcasting receiver
     * this allows us to receive notifications while app is not open
     */
    private class NReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (intent.getStringExtra("notification_event").equals("onNotificationPosted:"))
            {
                Log.i(TAG, "Notification Received");
            }
            else if (intent.getStringExtra("notification_event").equals("onNotificationRemoved:"))
            {
                Log.i(TAG, "Notification Received");
            }


        }
    }



}