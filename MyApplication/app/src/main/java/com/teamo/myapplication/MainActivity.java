package com.teamo.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class MainActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();
    private NReceiver nRecv;
    private TwitterLoginButton loginButton;

    //These array lists will store the string information to be printed in each notification method

   public static ArrayList<String> twitterNots = new ArrayList<>();
   public static ArrayList<String> fbNots = new ArrayList<>();
   public static ArrayList<String> snapNots = new ArrayList<>();
   public static ArrayList<String> instaNots = new ArrayList<>();

    /**
     * The onCreate method is what creates the default display on the screen. This is consistent in all activities.
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_main);

        // Initialize and set up Twitter login button
        loginButton = (TwitterLoginButton) findViewById(R.id.login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;

                // Show login success message on success
                Toast.makeText(getApplicationContext(),"Login success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(TwitterException exception) {
                // Show login fail message on failure
                Toast.makeText(getApplicationContext(),"Login fail", Toast.LENGTH_LONG).show();
            }
        });

        //Initializing and adding a random value for testing purposes - can comment out or alter to see other changes later
        twitterNots.add("Random to Test");
        twitterNots.add("Test2");
        //fbNots.add("Test");
        //snapNots.add("snap");
        //instaNots.add("Words");
        //Creating variables to alter which notifications are visible on the home screen
        TextView twitter = findViewById(R.id.textView2);
        Button twitterButton = findViewById(R.id.button2);
        TextView fb = findViewById(R.id.textView);
        Button fbButton = findViewById(R.id.button);
        TextView snap = findViewById(R.id.textView3);
        Button snapButton = findViewById(R.id.button3);
        TextView insta = findViewById(R.id.textView4);
        Button instaButton = findViewById(R.id.button4);
        //Changing visiblity of Views if there are no notifications from a particular site
        if(twitterNots.isEmpty()){
            twitter.setVisibility(View.GONE);
            twitterButton.setVisibility(View.GONE);
        }
        if(fbNots.isEmpty()){
            fb.setVisibility(View.GONE);
            fbButton.setVisibility(View.GONE);
        }
        if(instaNots.isEmpty()){
            insta.setVisibility(View.GONE);
            instaButton.setVisibility(View.GONE);
        }
        if(snapNots.isEmpty()){
            snap.setVisibility(View.GONE);
            snapButton.setVisibility(View.GONE);
        }


        nRecv = new NReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.teamo.myapplication.NOTIFICATION_LISTENER");
        registerReceiver(nRecv, filter);

        getPermissions();





    }


    /**
     * I really believe this is working
     * The system level permissions
     * are without a doubt the most ridiculous things in world history to access
     */
    public void getPermissions()
    {

        ContentResolver contentResolver = this.getContentResolver();
        String enabledNotificationListeners = Settings.Secure.getString(contentResolver, "enabled_notification_listeners");
        Log.i(TAG, enabledNotificationListeners);

        NotificationManagerCompat.getEnabledListenerPackages(this);
        //Checks if the string has the proper notification listener within it.
        if (enabledNotificationListeners == null || !enabledNotificationListeners.contains(getPackageName()))
        {
            //if it doesn't we request permissions through the dialog box (aka move to settings)
            Log.i(TAG, "Requesting Permissions");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setNegativeButton("Open Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                    startActivity(intent);
                }
            });
            builder.setPositiveButton("Ignore", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.i(TAG, "User denied permission request");
                }
            });
            builder.setTitle("Notification permissions needed");
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else
        {
            //if it does we just log this and move on.
            Log.i(TAG, "permissions already granted no need to request");
        }
    }

    // Pass the result of the authentication Activity back to the login button
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * This method brings the user to a new screen which will display Facebook notification details
     * @param view
     */
    public void viewFbDetails (View view){
        Intent intent = new Intent(this, DisplayFbNots.class);
        //This line sends whatever exists in the fbNots array to the fbNots activity
        intent.putExtra("fbNots", fbNots);
        startActivity(intent);
    }

    /**
     * This method brings the user to a new screen which will display Twitter notification details
     * @param view
     */
    public void viewTwitterDetails (View view){
        Intent intent = new Intent(this, DisplayTwitterNots.class);
        //This line sends whatever exists in the twitterNots array to the TwitterNots activity
        intent.putExtra("twitterNots", twitterNots);
        startActivity(intent);
    }

    /**
     * This method brings the user to a new screen which will display Snapchat notification details
     * @param view
     */
    public void viewSnapDetails (View view){
        Intent intent = new Intent(this, DisplaySnapNots.class);
        //This line sends whatever exists in the snapNots array to the snapNots activity
        intent.putExtra("snapNots",snapNots);
        startActivity(intent);
    }

    /**
     * This method brings the user to a new screen which will display Instagram notification details
     * @param view
     */
    public void viewInstaDetails (View view){
        Intent intent = new Intent (this, DisplayInstaNots.class);
        //This line sends whatever exists in the instaNots array to the instaNots activity
        intent.putExtra("instaNots", instaNots);
        startActivity(intent);
    }

    public void addToList (String full, String notif, ArrayList arr){
            arr.add(notif);
    }
    /**
     * private nested class for braodcasting receiver
     * logs notifications as they are received
     */
    private class NReceiver extends BroadcastReceiver
    {
        MainActivity main = new MainActivity();
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String temp = intent.getStringExtra("notification_event");
            //temp will be in this format - onNotificationPosted:com.snapchat.android
            //therefore we can then filter this out to be -> snapchat
            //this will also come in this format - onNotificationRemoved:com.snapchat.android
            //this means that the notification was destroyed
            //might be useful in the future
            //for now I am just going to log this.
            //Log.i(TAG, temp);
            if(temp.contains("Posted")) {
                if (temp.contains("twitter")) {
                    Log.d(TAG, "twitter worked");
                    String notif = intent.getStringExtra("notification_information");
                    Log.d(TAG, notif);
                    main.addToList(temp, notif, twitterNots);

                }
            }

        }




    }


}