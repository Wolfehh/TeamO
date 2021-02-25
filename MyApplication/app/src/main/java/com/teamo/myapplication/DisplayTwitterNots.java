package com.teamo.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
            }
        });


    }
}