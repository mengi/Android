package com.example.menginar.firebasedemo.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.menginar.firebasedemo.MainActivity;
import com.example.menginar.firebasedemo.R;
import com.example.menginar.firebasedemo.connect.FireBaseConfig;
import com.example.menginar.firebasedemo.model.FireBaseData;
import com.example.menginar.firebasedemo.utils.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

/**
 * Created by menginar on 13.06.2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    FireBaseData fireBaseData = new FireBaseData();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage == null)
            return;

        if (remoteMessage.getData().size() > 0) {   //Data message
            String jsonData = remoteMessage.getData().toString();
            try {
                fireBaseData = new FireBaseData();
                Gson gson = new Gson();

                fireBaseData = gson.fromJson(jsonData, FireBaseData.class);
                handleDataMessage(fireBaseData, remoteMessage);

            } catch (Exception e) {

            }
        }
    }

    private void handleDataMessage(FireBaseData getFireBaseDataMe, RemoteMessage remoteMessage) {
        try {
            String message = getFireBaseDataMe.getData().getMessage();
            String title = getFireBaseDataMe.getData().getTitle();
            String activityName = getFireBaseDataMe.getData().getPageActivity();

            if (!NotificationUtils.isAppIsInBackground(getApplicationContext(), activityName)) {
                Intent pushNotification = new Intent(FireBaseConfig.PUSH_NOTIFICATION);
                pushNotification.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                pushNotification.putExtra("message", getFireBaseDataMe.getData().getMessage());
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            } else {
                sendNotification(title, message, activityName);
            }

        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    private void sendNotification(String messageTitle, String messageBody, String activityName) {
        Class<?> className = MainActivity.class;
        try {
            className = Class.forName(getPackageName() + "." + activityName);

        } catch (Exception e) {
            Log.e("TAG", "Error: " + e.getStackTrace());
        }

        Intent intent = new Intent(this, className);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        long[] pattern = {500, 500, 500, 500};//Titreşim ayarı

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setVibrate(pattern)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        try {
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + this.getPackageName() + "/raw/notification");
            Ringtone r = RingtoneManager.getRingtone(this, alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
