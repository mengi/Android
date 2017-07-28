package com.menginarsoft.control.service;

import com.menginarsoft.control.activity.AnaKontrolActivity;
import com.menginarsoft.control.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

/**
 * @author Prabu
 * 
 */
public class MyBroadcastReceiver extends BroadcastReceiver {

	private NotificationManager mManager;

	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent) {

		mManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Intent intent1 = new Intent(context, AnaKontrolActivity.class);

		Notification notification = new Notification(R.drawable.ic_launcher,
				"Cihaz Kapandi", System.currentTimeMillis());

		intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);

		PendingIntent pendingNotificationIntent = PendingIntent.getActivity(
				context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.setLatestEventInfo(context, "", "Cihaz Kapandi!",
				pendingNotificationIntent);

		mManager.notify(0, notification);

		// Vibrate the mobile phone
		Vibrator vibrator = (Vibrator) context
				.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(2000);
	}

}
