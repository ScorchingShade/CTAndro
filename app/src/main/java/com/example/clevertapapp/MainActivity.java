package com.example.clevertapapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.clevertap.android.sdk.CleverTapAPI;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

//Last change made 10-11-2020
//commit for logs
public class MainActivity extends AppCompatActivity {

    Button btn;

    private static Context sContext;


    public static Context getContext() {
        return sContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sContext = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




       //initialise CleverTap API
        final CleverTapAPI clevertap = CleverTapAPI.getDefaultInstance(getContext());
        CleverTapAPI.setDebugLevel(3);

        String fcmRegId = FirebaseInstanceId.getInstance().getToken();
        clevertap.pushFcmRegistrationId(fcmRegId,true);



        //logs for test
        System.out.println("CleverTap*****************************************************************\n"+clevertap);
        System.out.println("getApplicationContext()+++++++++++++++++++++++++++++++++++++++++++++++++++++\n"+getApplicationContext());
        System.out.println("Token is============================================ "+fcmRegId);


        //Initialising clevertap notification
        CleverTapAPI cleverTapAPI = CleverTapAPI.getDefaultInstance(getApplicationContext());
        cleverTapAPI.createNotificationChannel(getApplicationContext(),"1","1","its 1",NotificationManager.IMPORTANCE_MAX,true);



        //Using a sidepass for FCM Notification for more flexibility
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("MyNotification","MyNotification",NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager =getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }


        // Using the button listener to upload to profile
        btn=(Button) findViewById(R.id.btnSDK);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> profileUpdate = new HashMap<String, Object>();

                //Update pre-defined profile properties
                profileUpdate.put("Name", "Ankush Sharma");
                profileUpdate.put("Email", "Ankush+ankushors789@clevertap.com");
                //Update custom profile properties
                profileUpdate.put("Plan Type", "Gold");
                profileUpdate.put("Favorite Food", "Beer");

                HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
                prodViewedAction.put("Product ID", "1");
                prodViewedAction.put("Product Image", "https://d35fo82fjcw0y8.cloudfront.net/2018/07/26020307/customer-success-clevertap.jpg");




                clevertap.pushProfile(profileUpdate);
                clevertap.pushEvent("Product viewed", prodViewedAction);

                clevertap.pushEvent("Product viewed", prodViewedAction);

                System.out.println(clevertap+"-----------------------------------------------");

                //Confirm button click
                Toast.makeText(MainActivity.this, "User info analysed", Toast.LENGTH_SHORT).show();
            }
        });


    }


}