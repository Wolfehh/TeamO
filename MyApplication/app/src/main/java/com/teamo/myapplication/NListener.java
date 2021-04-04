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
        //Logging this to debug only because we know for sure this works
        //and it is not very good information for us to clutter the information logs
        Log.d(TAG, "Notification Posted");
        Log.d(TAG, "ID: " + sNotif.getId() + "\t" + sNotif.getNotification().tickerText + "\t" + sNotif.getPackageName());

        //Creating an INTENT to post a notification using our package
        //then I am BROADCASTING this so a receiver can pick it up.
        //example broadcast - onNotificationPosted:com.snapchat.android
        Intent post = new Intent("com.teamo.myapplication.NOTIFICATION_LISTENER");
        post.putExtra("notification_event", "onNotificationPosted:" + sNotif.getPackageName() + "\n");
        post.putExtra("notification_information", "" + sNotif.getNotification().tickerText);
        sendBroadcast(post);


    }

    /**
     *
     * @param sNotif
     */
    @Override
    public void onNotificationRemoved(StatusBarNotification sNotif)
    {
        //Logging this to debug only because we know for sure this works
        //and it is not very good information for us to clutter the information logs
        Log.d(TAG, "Notification Removed");
        Log.d(TAG, "ID: " + sNotif.getId() + "\t" + sNotif.getNotification().tickerText + "\t" + sNotif.getPackageName());

        //Creating an INTENT to post a notification using our package
        //then I am BROADCASTING this so a receiver can pick it up.
        //example broadcast - onNotificationRemoved:com.snapchat.android
        Intent remove = new Intent("com.teamo.myapplication.NOTIFICATION_LISTENER");
        remove.putExtra("notification_event", "onNotificationRemoved:" + sNotif.getPackageName() + "\n");
        sendBroadcast(remove);
    }

    /**
     * private nested class for braodcasting receiver
     * logs notifications as they are received
     */
    private class NReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (intent.getStringExtra("notification_event").contains("onNotificationPosted:"))
            {
                String temp = intent.getStringExtra("notification_event");
                Log.i(TAG, "Posted notification brodcast received");
            }
            else if (intent.getStringExtra("notification_event").contains("onNotificationRemoved:"))
            {
                Log.i(TAG, "Removed notification broadcast received");
            }


        }
    }



}