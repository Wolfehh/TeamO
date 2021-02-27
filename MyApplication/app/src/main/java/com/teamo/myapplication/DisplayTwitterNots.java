package com.teamo.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DisplayTwitterNots extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_twitter_nots);
        LinearLayout linearLayout = new LinearLayout(this); // Creates a linear layout when the apps created?
        createNotification(linearLayout,"Spaghetti");
        createNotification(linearLayout,"Spaghetti.");
        createNotification(linearLayout,"Spaghetti!");
        createNotification(linearLayout,"Spaghetti?");
        createNotification(linearLayout,"Spaghetti!!!");
        createNotification(linearLayout,"Spaghetti :(");
        createNotification(linearLayout,"Spaghetti :)");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification","My Notification",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(DisplayTwitterNots.this,"My Notification");
        builder.setContentTitle("Test Title");
        builder.setContentText("Test Text");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(DisplayTwitterNots.this);
        managerCompat.notify(1,builder.build());
    }

    void createNotification(LinearLayout linearLayout, String notification){
        setContentView(linearLayout);   // Sets to the linear layout we create in onCreate
        linearLayout.setOrientation(LinearLayout.VERTICAL); // Ensures the layout is a vertical one and not a horizontal one
        String theNotification = "Notification: " + notification;   // Concatenates the text so it says Notification:
        TextView textView = new TextView(this); // Creates a new textbox in the activity context
        int id = (int)System.currentTimeMillis();   // Creates an id based off the time that it was created
        textView.setId(id); // Sets an id. Don't know if we will need it, but its something incase
        textView.setWidth(linearLayout.getWidth()); // Sets width
        textView.setHeight(79); // Sets height
        textView.setText(theNotification);  // Sets text of the textview
        linearLayout.addView(textView); // Adds textview to the screen

        createButtons(textView, linearLayout); // Create buttons for notification interactions

    }

    /**
     * Helper method. Creates like, reply, retweet, and clear buttons for each notification.
     * @param textView - the notification the buttons are being made for
     * @param linearLayout - the layout for both notifications and buttons
     */
    void createButtons(TextView textView, LinearLayout linearLayout) {

        // Create like Button
        Button like = new Button(this);
        like.setWidth(linearLayout.getWidth()); // Sets width
        like.setHeight(79); // Sets height
        String Like = "Like";
        like.setText(Like);  // Sets text of the Button
        linearLayout.addView(like); // Adds Button to the screen

        // Create reply Button
        Button reply = new Button(this);
        reply.setWidth(linearLayout.getWidth()); // Sets width
        reply.setHeight(79); // Sets height
        String Reply = "Reply";
        reply.setText(Reply);  // Sets text of the Button
        linearLayout.addView(reply); // Adds Button to the screen

        // Create retweet Button
        Button retweet = new Button(this);
        retweet.setWidth(linearLayout.getWidth()); // Sets width
        retweet.setHeight(79); // Sets height
        String Retweet = "Retweet";
        retweet.setText(Retweet);  // Sets text of the Button
        linearLayout.addView(retweet); // Adds Button to the screen

        // Create clear Button
        Button clear = new Button(this);
        clear.setWidth(linearLayout.getWidth()); // Sets width
        clear.setHeight(79); // Sets height
        String CLEAR = "CLEAR";
        clear.setText(CLEAR);  // Sets text of the Button
        linearLayout.addView(clear); // Adds Button to the screen

        // Remove all associated Buttons and notification TextView on click
        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Remove Buttons
                linearLayout.removeView(like);
                linearLayout.removeView(reply);
                linearLayout.removeView(retweet);
                linearLayout.removeView(clear);

                linearLayout.removeView(textView); // Remove TextView
            }
        });
    }
}