package com.teamo.myapplication.notification;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import com.teamo.myapplication.MainActivity;
import com.teamo.myapplication.R;

public class BigTextMainActivity extends Activity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_text_main);

        // Cancel Notification
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.cancel(MainActivity.NOTIFICATION_ID);

        // TODO: Handle and display reminder from your database
    }
}
