package com.teamo.myapplication;


import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NListener extends NotificationListenerService
{

    private String TAG = this.getClass().getSimpleName();

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sNotif)
    {
        Log.i(TAG, ":Notification Posted");
        Log.i(TAG, "ID: " + sNotif.getId() + "\t" + sNotif.getNotification().tickerText + "\t" + sNotif.getPackageName());


    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sNotif)
    {
        Log.i(TAG, ":Notification Removed");
        Log.i(TAG, "ID: " + sNotif.getId() + "\t" + sNotif.getNotification().tickerText + "\t" + sNotif.getPackageName());

    }



}