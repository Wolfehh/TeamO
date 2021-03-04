package com.teamo.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DisplayTwitterNots extends AppCompatActivity {
    ArrayList<String> notifications;
    ArrayList<String> notificationsTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_twitter_nots);
        loadData(); // Loads previous data in shared preference and initializes notifications
        notificationsTracker = new ArrayList<>(); // Makes an array list that can be modified
        LinearLayout linearLayout = new LinearLayout(this); // Creates a linear layout when the activity is opened
        if(!(notifications.isEmpty())){ // Loads all of the notifications in the shared preferences
            for(int i = 0; i < notifications.size(); i++){
                createNotification(linearLayout, notifications.get(i));
            }
        }

        createNotification(linearLayout,"Spaghetti"); // Test notif. One should add every time you open Twitter
        /*createNotification(linearLayout,"Spaghetti.");
        createNotification(linearLayout,"Spaghetti!");
        createNotification(linearLayout,"Spaghetti?");
        createNotification(linearLayout,"Spaghetti!!!");
        createNotification(linearLayout,"Spaghetti :(");
        createNotification(linearLayout,"Spaghetti :)");*/

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

    // This method saves the data and keeps the notifications array list updated between activities.
    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(notifications);
        editor.putString("Notification List", json);
        editor.apply();
    }

    // This method loads the data, updates, and initializes the array list
    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Notification List", null);
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        notifications = gson.fromJson(json, type);

        if(notifications == null){
            notifications = new ArrayList<>();
        }

    }

    void createNotification(LinearLayout linearLayout, String notification){
        notificationsTracker.add(notification); // Adds to the modifiable array list
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
        // Create clear Button
        Button clear = new Button(this);
        clear.setId(id); // Same id as notification TextView
        clear.setWidth(linearLayout.getWidth()); // Sets width
        clear.setHeight(79); // Sets height
        String CLEAR = "CLEAR";
        clear.setText(CLEAR);  // Sets text of the Button
        linearLayout.addView(clear); // Adds Button to the screen

        // Remove both Button and TextView on click
        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                linearLayout.removeView(clear); // Remove Button
                linearLayout.removeView(textView); // Remove TextView
                notificationsTracker.remove(notification); // Removes from the modifiable array list
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notifications.clear(); // Empties the array list
        notifications.addAll(notificationsTracker); // Adds the new array list of leftover notifications
        saveData(); // Saves to shared preferences
    }
}