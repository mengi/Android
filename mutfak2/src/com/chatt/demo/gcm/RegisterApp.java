package com.chatt.demo.gcm;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.chatt.demo.GarsonLogin;
import com.chatt.demo.Masa;
import com.chatt.demo.custom.RSabit;
import com.chatt.demo.model.MAyar;
import com.chatt.demo.model.MKullanici;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class RegisterApp extends AsyncTask<Void, Void, String> {

	private static final String TAG = "MUTFAK";
	Context ctx;
	GoogleCloudMessaging gcm;
	final String PROJECT_ID = "279113929522";
	String regid = null;
	private int appVersion;

	public RegisterApp(Context ctx, GoogleCloudMessaging gcm, int appVersion) {
		this.ctx = ctx;
		this.gcm = gcm;
		this.appVersion = appVersion;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(Void... arg0) {
		String msg = "";
		try {
			if (gcm == null) {
				gcm = GoogleCloudMessaging.getInstance(ctx);
			}
			regid = gcm.register(PROJECT_ID);
			msg = "Registration ID=" + regid;
			sendRegistrationIdToBackend();
			storeRegistrationId(ctx, regid);

		} catch (IOException ex) {
			msg = "Error :" + ex.getMessage();

		}
		return msg;
	}

	private void storeRegistrationId(Context ctx, String regid) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				GarsonLogin.class.getSimpleName(), Context.MODE_PRIVATE);
		Log.i(TAG, "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("registration_id", regid);
		editor.putInt("appVersion", appVersion);
		editor.commit();

	}

	private void sendRegistrationIdToBackend() {
		URI url = null;
		RSabit.MKullanici = new MKullanici();
		RSabit.MAyar = new MAyar();
		RSabit.MAyar.GetirAyar();
		
		String GarsonKod = RSabit.MKullanici.GetirGarsonKod();
		String IpAdres = RSabit.MAyar.getIpAdres();
		try {
			url = new URI("http://"+ IpAdres +"/lokanta/android_connect/register.php?regId="+ regid +"&GarSonKod="+ GarsonKod);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet request = new HttpGet();
		request.setURI(url);
		try {
			httpclient.execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		Log.v(TAG, result);				
		
		Intent i = new Intent(ctx, Masa.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		ctx.startActivity(i);

	}
}