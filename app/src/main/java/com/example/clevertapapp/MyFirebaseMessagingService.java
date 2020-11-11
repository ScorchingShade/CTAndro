package com.example.clevertapapp;

import android.os.Bundle;
import android.util.Log;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.pushnotification.NotificationInfo;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage message){
        try {
            if (message.getData().size() > 0) {
                Bundle extras = new Bundle();
                for (Map.Entry<String, String> entry : message.getData().entrySet()) {
                    extras.putString(entry.getKey(), entry.getValue());
                }
                Log.e("TAG","onReceived Message Called");
                NotificationInfo info = CleverTapAPI.getNotificationInfo(extras);
                if (info.fromCleverTap) {
                    CleverTapAPI.createNotification(getApplicationContext(), extras);
                }
            }
        } catch (Throwable t) {
            Log.d("MYFCMLIST", "Error parsing FCM message", t);
        }
    }
    @Override
    public void onNewToken(String token) {
        CleverTapAPI.getDefaultInstance(this).pushFcmRegistrationId(token,true);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(token);
    }
}