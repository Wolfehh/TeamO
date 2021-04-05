package com.teamo.myapplication.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.teamo.myapplication.R;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class GlobalNotification extends AppCompatActivity {
    public static final String ACTION_DISMISS =
            ".handlers.action.DISMISS";
    public static final String ACTION_LIKE =
            ".handlers.action.LIKE";
    public static void buildNotification(Context context)
        {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        Intent snoozeIntent = new Intent(context, GlobalNotification.class);
        snoozeIntent.setAction(GlobalNotification.ACTION_LIKE);
        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(context, 0, snoozeIntent, 0);

        Intent dismissIntent = new Intent(context, GlobalNotification.class);
        dismissIntent.setAction(GlobalNotification.ACTION_DISMISS);

        Intent intent = new Intent(context, GlobalNotification.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "My Notification");
        builder.setContentTitle("Test Title");
        builder.setContentText("Test Text");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(1, builder.build());

    }


}
