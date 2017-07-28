package com.chatt.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.widget.SweetAlertDialog;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chatt.demo.custom.RSabit;
import com.chatt.demo.db.ODataBase;
import com.chatt.demo.gcm.RegisterApp;
import com.chatt.demo.model.MAyar;
import com.chatt.demo.model.MKullanici;
import com.chatt.demo.model.MMasa;
import com.chatt.demo.utils.JSONParser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GarsonLogin extends Activity {

	public EditText kullanici, parola;
	public Button BtnGiris, BtnAyar;

	private ProgressDialog pDialog;

	JSONParser jParser = new JSONParser();
	JSONArray jGarson = null;
	ArrayList<HashMap<String, String>> productsList; 
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_GARSON = "garson";
	private static final String TAG_MASA = "masalar";
	
	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private static final String TAG = "MUTFAK";
	GoogleCloudMessaging gcm;
	String regid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kulllanicigiris);
		
		BtnGiris = (Button) findViewById(R.id.BtnGiris);
		BtnAyar = (Button) findViewById(R.id.BtnAyar);
		
		kullanici = (EditText) findViewById(R.id.kullanici);
		parola = (EditText) findViewById(R.id.parola);
		
		RSabit.OpDbBaglanti = new ODataBase(this);
		
		RSabit.MAyar = new MAyar();
		RSabit.MAyar.GetirAyar();
		
		BtnGiris.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!RSabit.GetirIpAdresi().equals("")) {
					String u = kullanici.getText().toString();
					String p = parola.getText().toString();
					if (u.length() == 0 || p.length() == 0) {
						new SweetAlertDialog(GarsonLogin.this, SweetAlertDialog.ERROR_TYPE)
								.setTitleText("Oops...")
								.setContentText("Gerekli Alanlarý Doldurunuz!").show();
						return;
					}
					
					if (!RSabit.MAyar.getIpAdres().equals("")) {
						new CreateNewProduct().execute();
					} else {
						new SweetAlertDialog(GarsonLogin.this, SweetAlertDialog.ERROR_TYPE)
						.setTitleText("Oops...")
						.setContentText("Sistem Ayarlarýný Giriniz!").show();
						return;
					}
				} else {
					new SweetAlertDialog(GarsonLogin.this, SweetAlertDialog.WARNING_TYPE)
					.setTitleText("Oops...")
					.setContentText("Að Baðlantýnýzý Kontrol Ediniz !!!").show();
					return;
				}
				
			}
		});
		
		BtnAyar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(GarsonLogin.this, Ayar.class), 10);
				
			}
		});

	}

	class CreateNewProduct extends AsyncTask<String, String, String> {
		String url_garson_giris = "";
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			if (RSabit.MAyar.getIpAdres() != "") {
				url_garson_giris = "http://"+RSabit.MAyar.IpAdres+"/lokanta/android_connect/garson_giris.php";
			}
			
			/*
			if (RSabit.MAyar.getPort() != "") {
				url_garson_giris = "http://"+RSabit.MAyar.getIpAdres()+":"+RSabit.MAyar.getPort()+"/android_connect/garson_giris.php";
			}
			*/
			pDialog = ProgressDialog.show(GarsonLogin.this, null,
					getString(R.string.alert_loading));
		}

		protected String doInBackground(String... args) {
			String KullaniciAdi = kullanici.getText().toString();
			String Parola = parola.getText().toString();
			String Sonuc = "0";

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("parola", Parola));
			params.add(new BasicNameValuePair("garsonkod", KullaniciAdi));
			
			JSONObject json = jParser.makeHttpRequest(url_garson_giris, "POST",
					params);

			Log.d("Create Response", json.toString());

			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					
					jGarson = json.getJSONArray(TAG_GARSON);

					for (int i = 0; i < jGarson.length(); i++) {
						JSONObject c = jGarson.getJSONObject(i);
						String k = c.getString("KULLANICIADI");
						String p = c.getString("PAROLA");
						String b = c.getString("BULUNDUGUYER");
						String g = c.getString("GARSONKOD");
						
						RSabit.MKullanici = new MKullanici(k, b, p, g);
						if (!RSabit.MKullanici.KaydetKullanici()) {
							
							Sonuc = Integer.toString(0);
						} else {
							RSabit.KullaniciId = g;
							Sonuc = Integer.toString(success);
						}
					}
					
				} else if(success == 3) {
					Sonuc = Integer.toString(3);
				} else {
					Sonuc = Integer.toString(0);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return Sonuc;
		}

		protected void onPostExecute(String file_url) {

			if (file_url.equals("1")) {
				
				new MasaS().execute();
                
			} else if (file_url.equals("0")) {
				pDialog.dismiss();
                new SweetAlertDialog(GarsonLogin.this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText("Kullanýcý Adý veya Parola Hatalý!")
                .show();
			} else if (file_url.equals("3")) {
				pDialog.dismiss();
				new SweetAlertDialog(GarsonLogin.this, SweetAlertDialog.WARNING_TYPE)
				.setTitleText("Oops...")
				.setContentText("Baðlantý Ayarlarýný Kontrol Ediniz !!!").show();
			}
			
		}
	}

	class MasaS extends AsyncTask<String, String, String> {
		String url_masa_liste = "";
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			if (RSabit.MAyar.getIpAdres() != "") {
				url_masa_liste = "http://"+RSabit.MAyar.IpAdres+"/lokanta/android_connect/masa_liste.php";
			}
		}

		protected String doInBackground(String... args) {
			
			String Sonuc = "0";

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("kat", RSabit.MKullanici.GetirKullaniciYeri()));
			
			JSONObject json = jParser.makeHttpRequest(url_masa_liste, "POST",
					params);

			Log.d("Create Response", json.toString());

			try {
				int success = json.getInt(TAG_SUCCESS);
				RSabit.MMasa = new MMasa();
				if (success == 1) {
					if (RSabit.MMasa.MasaSil()) {
						jGarson = json.getJSONArray(TAG_MASA);

						for (int i = 0; i < jGarson.length(); i++) {
							JSONObject c = jGarson.getJSONObject(i);
							String id = c.getString("id");
							String a = c.getString("acik");
							String ak = c.getString("aktif");
							String s = c.getString("siparis");
							String sid = c.getString("siparisid");
							
							RSabit.MMasa = new MMasa(id, a, ak, s, sid);
							if (!RSabit.MMasa.KaydetMasa()) {
								Sonuc = Integer.toString(0);
							} else {
								Sonuc = Integer.toString(success);
							}
						}
					} else {
						Sonuc = Integer.toString(0);
					}
					
				} else if(success == 3) {
					Sonuc = Integer.toString(3);
				} else {
					Sonuc = Integer.toString(0);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return Sonuc;
		}

		protected void onPostExecute(String file_url) {
			
			if (file_url.equals("1")) {
				
				if (checkPlayServices()) {
					gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
					regid = getRegistrationId(getApplicationContext());
					pDialog.dismiss();

					if (regid.isEmpty()) {
						new RegisterApp(getApplicationContext(), gcm,
								getAppVersion(getApplicationContext())).execute();
					} else {

						Intent myIntent = new Intent(GarsonLogin.this, Masa.class);
		                startActivity(myIntent);
		                finish();
					}

				}

                
			} else if (file_url.equals("0")) {
				pDialog.dismiss();
                new SweetAlertDialog(GarsonLogin.this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText("Masa Bilgileri Getirilemedi !!!")
                .show();
			} else if (file_url.equals("3")) {
				pDialog.dismiss();
				pDialog.dismiss();
				new SweetAlertDialog(GarsonLogin.this, SweetAlertDialog.WARNING_TYPE)
				.setTitleText("Oops...")
				.setContentText("Baðlantý Ayarlarýný Kontrol Ediniz !!!").show();
			}
			
		}
	}
	
	private boolean checkPlayServices() {

		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i(TAG, "Google Play Servis Yükleyin.");
				finish();
			}
			return false;
		}
		return true;
	}

	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i(TAG, "Registration id bulunamadý.");
			return "";
		}
		
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
				Integer.MIN_VALUE);
		int currentVersion = getAppVersion(getApplicationContext());
		if (registeredVersion != currentVersion) {
			Log.i(TAG, "App version deðiþmiþ.");
			return "";
		}
		return registrationId;
	}

	private SharedPreferences getGCMPreferences(Context context) {
		return getSharedPreferences(GarsonLogin.class.getSimpleName(),
				Context.MODE_PRIVATE);
	}

	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Paket versiyonu bulunamadý: " + e);
		}
	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 10 && resultCode == RESULT_OK)
			finish();
	}
}
