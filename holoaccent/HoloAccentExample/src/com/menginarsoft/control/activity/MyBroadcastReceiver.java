package com.menginarsoft.control.activity;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.menginarsoft.control.activity.AnaKontrolActivity;
import com.menginarsoft.control.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

/**
 * @author Prabu
 * 
 */
public class MyBroadcastReceiver extends BroadcastReceiver {

	private NotificationManager mManager;
	private String TutDurum = ""; 

	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent) {

		mManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Intent intent1 = new Intent(context, AnaKontrolActivity.class);
		
		Notification notification = new Notification(R.drawable.ok,
				"Cihaz Kapandi", System.currentTimeMillis());
		
		intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		KapatTask MyTaskKapat = new KapatTask();
		MyTaskKapat.execute("A");
		
		KapatTask MyTaskKapats = new KapatTask();
		MyTaskKapats.execute("B");
		
		KapatTask MyTaskKapatss = new KapatTask();
		MyTaskKapatss.execute("C");
		
		KapatTask MyTaskKapatsss = new KapatTask();
		MyTaskKapatsss.execute("D");
		
		KapatTask MyTaskKapatssss = new KapatTask();
		MyTaskKapatssss.execute("E");
		
		PendingIntent pendingNotificationIntent = PendingIntent.getActivity(
				context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.setLatestEventInfo(context, "", "Iþýklar Kapandý!",
				pendingNotificationIntent);

		
		mManager.notify(0, notification);
	}
	
	private class KapatTask extends AsyncTask<String, String, String> {
		String dstAddress = "192.168.1.50";
		int dstPort = 200;

		protected String doInBackground(String... params) {

			Socket socket = null;
			OutputStream os = null;
			InputStream inputStream = null;

			try {
				socket = new Socket(dstAddress, dstPort);

				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
						1024);
				byte[] buffer = new byte[1024];

				int bytesRead;
				inputStream = socket.getInputStream();
				os = socket.getOutputStream();
				DataOutputStream dos = new DataOutputStream(os);
				dos.writeUTF(params[0]);

				while ((bytesRead = inputStream.read(buffer)) != 'Y') {
					byteArrayOutputStream.write(buffer, 0, bytesRead);
					if (byteArrayOutputStream.toString("UTF-8").length() == 3) {
						TutDurum = byteArrayOutputStream.toString("UTF-8");
						break;
					}
				}

				os.close();
				inputStream.close();
				socket.close();

			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				TutDurum = "UnknownHostException: " + e.toString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				TutDurum = "IOException: " + e.toString();
			}

			return TutDurum;
		}

		@Override
		protected void onPostExecute(String result) {
			
		}
	}
}
