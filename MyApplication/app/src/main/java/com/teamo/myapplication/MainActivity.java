package com.teamo.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    /**
     * The onCreate method is what creates the default display on the screen. This is consistent in all activities.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method brings the user to a new screen which will display Facebook notification details
     * @param view
     */
    public void viewFbDetails (View view){
        Intent intent = new Intent(this, DisplayFbNots.class);
        startActivity(intent);
    }

    /**
     * This method brings the user to a new screen which will display Twitter notification details
     * @param view
     */
    public void viewTwitterDetails (View view){
        Intent intent = new Intent(this, DisplayTwitterNots.class);
        startActivity(intent);
    }

    /**
     * This method brings the user to a new screen which will display Snapchat notification details
     * @param view
     */
    public void viewSnapDetails (View view){
        Intent intent = new Intent(this, DisplaySnapNots.class);
        startActivity(intent);
    }

    /**
     * This method brings the user to a new screen which will display Instagram notification details
     * @param view
     */
    public void viewInstaDetails (View view){
        Intent intent = new Intent (this, DisplayInstaNots.class);
        startActivity(intent);
    }
}