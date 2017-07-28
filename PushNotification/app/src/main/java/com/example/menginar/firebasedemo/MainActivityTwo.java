package com.example.menginar.firebasedemo;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import com.example.menginar.firebasedemo.connect.FireBaseConfig;
import com.example.menginar.firebasedemo.utils.NotificationUtils;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by menginar on 14.06.2017.
 */

public class MainActivityTwo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_two);



        NotificationUtils.getFireBaseMessage(getApplicationContext());
    }


    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(NotificationUtils.mRegistrationBroadcastReceiver,
                new IntentFilter(FireBaseConfig.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(NotificationUtils.mRegistrationBroadcastReceiver,
                new IntentFilter(FireBaseConfig.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(NotificationUtils.mRegistrationBroadcastReceiver);
        super.onPause();
    }
}
