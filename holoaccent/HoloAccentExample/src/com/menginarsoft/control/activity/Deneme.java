package com.menginarsoft.control.activity;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;

import com.menginarsoft.control.R;
import com.menginarsoft.control.function.Sabit;
import com.menginarsoft.control.model.ParamAyar;
import com.menginarsoft.control.model.Role;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.widget.Toast;

/**
 * @author Menginar
 * 
 */
public class Deneme extends BroadcastReceiver {

	private NotificationManager mManager;
	private String TutDurum = "";
	private int mSaat, mDakika;
	private String TutSaats = "";
	static ArrayList<String> GelenSaatAraliklari = new ArrayList<String>();
	final public static String ONE_TIME = "onetime";

	@SuppressLint("Wakelock")
	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent) {
		PowerManager pm = (PowerManager) context
				.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wl = pm.newWakeLock(
				PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
		// Acquire the lock
		
		wl.acquire();

		Sabit.MParamAyar = new ParamAyar();
		Sabit.MParamAyar.GetirParamAyar();

		final Calendar c = Calendar.getInstance();
		mDakika = c.get(Calendar.MINUTE);
		mSaat = c.get(Calendar.HOUR_OF_DAY);
		String Saat = Integer.toString(mSaat);
		String Dakika = Integer.toString(mDakika);

		if (Dakika.length() == 1) {
			Dakika = "0" + Dakika;
		}

		if (Saat.length() == 1) {
			Saat = "0" + Saat;
		}

		TutSaats = Saat + "-" + Dakika;

		for (int i = 0; i < GelenSaatAraliklari.size(); i++) {

			if (GelenSaatAraliklari.get(i).equals(TutSaats)) {
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

				PendingIntent pendingNotificationIntent = PendingIntent
						.getActivity(context, 0, intent,
								PendingIntent.FLAG_UPDATE_CURRENT);
				notification.flags |= Notification.FLAG_AUTO_CANCEL;
				notification.setLatestEventInfo(context, "",
						"Iþýklar Kapandý!", pendingNotificationIntent);

				mManager.notify(0, notification);
				GelenSaatAraliklari.remove(i);
			}
		}
		wl.release();

	}

	public void SetAlarm(Context context) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, Deneme.class);
		intent.putExtra(ONE_TIME, Boolean.FALSE);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		
		Sabit.MRole = new Role();
		GelenSaatAraliklari = Sabit.MRole.GetirRole("1");		
		
		Toast.makeText(context, "Zaman Aralýklarý Kuruldu", Toast.LENGTH_LONG)
		.show();
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 5 , pi); 
	}

	public void CancelAlarm(Context context) {
		Intent intent = new Intent(context, Deneme.class);
		PendingIntent sender = PendingIntent
				.getBroadcast(context, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
	}

	public void setOnetimeTimer(Context context) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, Deneme.class);
		intent.putExtra(ONE_TIME, Boolean.TRUE);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);
	}

	private class KapatTask extends AsyncTask<String, String, String> {
		String dstAddress = Sabit.MParamAyar.getIPADRES();
		int dstPort = Integer.parseInt(Sabit.MParamAyar.getPORT());

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
