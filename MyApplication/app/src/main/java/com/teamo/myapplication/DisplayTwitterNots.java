package com.teamo.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DisplayTwitterNots extends AppCompatActivity {
    ArrayList<String> notifications;
    ArrayList<String> notificationsTracker;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_twitter_nots);
        loadData(); // Loads previous data in shared preference and initializes notifications
        //Loading any new incoming notifications from Main Activity
        Intent intent = getIntent();
        ArrayList<String> incomingNotifications = intent.getStringArrayListExtra("twitterNots");
        //Adding new notifications to display to our pre-loaded notification list. Removing them from incoming notifications in Main Activity.
        while (!incomingNotifications.isEmpty()){
            if(notifications.contains(incomingNotifications.get(0))){
                incomingNotifications.remove(0);
                MainActivity.twitterNots.remove(0);
            }
            else{
                notifications.add(incomingNotifications.remove(0));
                MainActivity.twitterNots.remove(0);
            }

        }
        notificationsTracker = new ArrayList<>(); // Makes an array list that can be modified
        LinearLayout linearLayout = findViewById(R.id.twitterLayout); // Creating a variable to reference our linear layout during the activity
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
        //Attempting to prevent deletion of needed items from Main Activity's list of Notifications
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        ArrayList<String> temp = gson.fromJson(json, type);
        while (!temp.isEmpty()){
            MainActivity.twitterNots.add(temp.remove(0));
        }
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
        Log.i("string", notification);

        //These lines are commented out - I've hardcoded an ID for our linearlayout and have set the layout to be vertical in the xml file
        //We can delete them before this branch is pulled, provided everyone agrees this change works properly during testing
        //App will crash if they are not commented out - I will have to rework my code if we wish to create a brand new linear layout instead
        //setContentView(linearLayout);   // Sets to the linear layout we create in onCreate
        //linearLayout.setOrientation(LinearLayout.VERTICAL); // Ensures the layout is a vertical one and not a horizontal one


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
     * @param linearLayout - the layout containing the notifications
     */
    void createButtons(TextView textView, LinearLayout linearLayout) {
        // Create new layout so buttons can be arranged horizontally below the notification
        LinearLayout buttonLayout = new LinearLayout(this);
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.addView(buttonLayout); // Add new layout to the notification layout

        String TAG = this.getClass().getSimpleName();

        // Create like Button
        Button like = new Button(this);
        like.setHeight(79); // Sets height
        String Like = "Like";
        like.setText(Like);  // Sets text of the Button
        buttonLayout.addView(like); // Adds Button to the screen

        // Create onClickListener for like Button
        like.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(TAG, "LIKE Button pushed."); // Send log message for testing purposes
            }
        });

        // Create reply Button
        Button reply = new Button(this);
        reply.setHeight(79); // Sets height
        String Reply = "Reply";
        reply.setText(Reply);  // Sets text of the Button
        buttonLayout.addView(reply); // Adds Button to the screen

        // Create onClickListener for reply Button
        reply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(TAG, "REPLY Button pushed."); // Send log message for testing purposes
            }
        });

        // Create retweet Button
        Button retweet = new Button(this);
        retweet.setHeight(79); // Sets height
        String Retweet = "Retweet";
        retweet.setText(Retweet);  // Sets text of the Button
        buttonLayout.addView(retweet); // Adds Button to the screen

        // Create onClickListener for retweet Button
        retweet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(TAG, "RETWEET Button pushed."); // Send log message for testing purposes
            }
        });

        // Create clear Button
        Button clear = new Button(this);
        clear.setHeight(79); // Sets height
        String CLEAR = "CLEAR";
        clear.setText(CLEAR);  // Sets text of the Button
        buttonLayout.addView(clear); // Adds Button to the screen

        // Remove all associated Buttons and notification TextView on click
        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(TAG, "CLEAR Button pushed."); // Send log message for testing purposes
                // Remove Buttons
                buttonLayout.removeView(like);
                buttonLayout.removeView(reply);
                buttonLayout.removeView(retweet);
                buttonLayout.removeView(clear);
                // Remove TextView
                linearLayout.removeView(textView);
                Log.i("substring",textView.getText().toString().substring(14));
                notificationsTracker.remove(textView.getText().toString().substring(14));

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