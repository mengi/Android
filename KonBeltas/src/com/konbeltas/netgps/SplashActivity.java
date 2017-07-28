package com.konbeltas.netgps;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.konbeltas.netgps.db.DataBaseHelper;
import com.konbeltas.netgps.func.MHata;
import com.konbeltas.netgps.func.MSabit;
import com.konbeltas.netgps.model.OtoparkBilgi;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {

	private static int SPLASH_TIME_OUT = 5000;
	final static String URI = "http://konyasehircicek.com/afcotopark2/json_otopark.php";
	JSONArray json;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity);

		MSabit.MDbBaglanti = new DataBaseHelper(this);
		MSabit.MOtoparkBilgi = new OtoparkBilgi();
		MSabit.MOtoparkBilgi.GetirOotoparkBilgi();

		boolean durum = (MSabit.MOtoparkBilgi.getOtoparkKodu() != "");
		if (!MSabit.GetirIpAdresi().equals("")) {
			if (!durum) {
				new JsonGps().execute();
			} else {
				new JsonGps2().execute();
			}
		} else {
			MHata Sonuc = new MHata();
			Sonuc.setBASLIK("");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("");
			Sonuc.GosterMesajToast(SplashActivity.this);
			return;
		}
	}

	public class JsonGps2 extends AsyncTask<Void, Integer, MHata> {

		@Override
		protected MHata doInBackground(Void... params) {
			MHata Sonuc = new MHata();
			try {

				json = readGameParks();
				if (json != null) {
					for (int i = 0; i < json.length(); i++) {
						JSONObject JsonBDizi = json.getJSONObject(i);
						String OtoparkKodu = JsonBDizi.optString("3")
								.toString();

						if (!MSabit.MOtoparkBilgi.VarMiOtoparkKodu(OtoparkKodu)) {
							String OtoparkAd = JsonBDizi.optString("1")
									.toString();
							String Kapasite = JsonBDizi.optString("2")
									.toString();
							String Yikama = JsonBDizi.optString("4").toString();
							String HizmetSuresi = JsonBDizi.optString("5")
									.toString();
							String Enlem = JsonBDizi.optString("6").toString();
							String Boylam = JsonBDizi.optString("7").toString();

							MSabit.MOtoparkBilgi = new OtoparkBilgi(OtoparkAd,
									Kapasite, OtoparkKodu, Yikama,
									HizmetSuresi, Enlem, Boylam);
							MSabit.MOtoparkBilgi.KaydetOtoparkBilgi();
						}
					}
				}
				return Sonuc;
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(MHata result) {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					Intent i = new Intent(SplashActivity.this,
							MainActivity.class);
					startActivity(i);

					finish();
				}
			}, SPLASH_TIME_OUT);
		}
	}

	public class JsonGps extends AsyncTask<Void, Integer, MHata> {

		@Override
		protected MHata doInBackground(Void... params) {
			MHata Sonuc = new MHata();
			try {

				json = readGameParks();
				if (json != null) {
					SQLiteDatabase DtBs = MSabit.MDbBaglanti
							.getWritableDatabase();
					DtBs.delete("TBLOTOPARKBILGI", null, null);
					DtBs.close();
					for (int i = 0; i < json.length(); i++) {
						JSONObject JsonBDizi = json.getJSONObject(i);

						String OtoparkAd = JsonBDizi.optString("1").toString();
						String Kapasite = JsonBDizi.optString("2").toString();
						String OtoparkKodu = JsonBDizi.optString("3")
								.toString();
						String Yikama = JsonBDizi.optString("4").toString();
						String HizmetSuresi = JsonBDizi.optString("5")
								.toString();
						String Enlem = JsonBDizi.optString("6").toString();
						String Boylam = JsonBDizi.optString("7").toString();

						MSabit.MOtoparkBilgi = new OtoparkBilgi(OtoparkAd,
								Kapasite, OtoparkKodu, Yikama, HizmetSuresi,
								Enlem, Boylam);
						MSabit.MOtoparkBilgi.KaydetOtoparkBilgi();

					}
				}
				return Sonuc;
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(MHata result) {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					Intent i = new Intent(SplashActivity.this,
							MainActivity.class);
					startActivity(i);

					finish();
				}
			}, SPLASH_TIME_OUT);
		}
	}

	protected JSONArray readGameParks() throws ClientProtocolException,
			IOException, JSONException {
		HttpClient Client = new DefaultHttpClient();
		HttpGet Get = new HttpGet(URI);
		HttpResponse response = Client.execute(Get);
		StatusLine statusline = response.getStatusLine();

		int s = statusline.getStatusCode();

		if (s == 200) {
			HttpEntity entity = response.getEntity();
			String Data = EntityUtils.toString(entity);
			JSONArray Posts = new JSONArray(Data);
			return Posts;
		}

		return null;
	}


}
