package com.android.afcsocet;

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

import com.android.afcsocet.R;
import com.android.db.ODataBase;
import com.android.fuk.MHata;
import com.android.fuk.MSabit;
import com.android.mdl.MKullanici;
import com.android.mdl.MParamAyar;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	final static String URI = "http://afcbilisim.com/socket/json.php";

	Button BtnGonder;

	TextView txt1, txt2, txt3;

	JSONArray json;
	EditText LisansKey;
	int LisansSayac = 0;
	int LisanskeyDurum = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		MSabit.MDbBaglanti = new ODataBase(this);
		MSabit.Kullanici = new MKullanici();
		MSabit.PAyar = new MParamAyar();
		MSabit.PAyar.GetirParamAyar();
		MSabit.Kullanici.GetirKullanici();

		LisansKey = (EditText) findViewById(R.id.TxtLisankey);
		BtnGonder = (Button) findViewById(R.id.BtnGonder);

		if (MSabit.GetirIpAdresi().equals("")) {
			MHata Sonuc = new MHata();
			Sonuc.setBASLIK("Lisans");
			Sonuc.setMESAJ("Að Baðlantýsýný Ayarlayýnýz!");
			Sonuc.setHATAMI(true);
			Sonuc.GosterMesajToast(MainActivity.this);
			return;
		}

		if (LisanskeyDurum == 0) {

			if (MSabit.Kullanici.getDurum() == null) {

				new Game().execute();
			}
		}

		BtnGonder.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!MSabit.GetirIpAdresi().equals("")) {
					if (!KontrolLisans().getHATAMI()) {
						if ((LisanskeyDurum == -1) || (LisanskeyDurum == 0)) {
							if (MSabit.KontrolLisansSuresi()) {
								MSabit.Kullanici.SilKullanici();
								new Game().execute();
								return;
							} else {
								new Game().execute();
							}
						}

					} else {
						KontrolLisans().GosterMesajToast(MainActivity.this);
					}

					Intent MyIntent = new Intent(MainActivity.this,
							Kontrol.class);
					startActivity(MyIntent);
				} else {
					MHata Sonuc = new MHata();
					Sonuc.setBASLIK("Lisans");
					Sonuc.setMESAJ("Að Baðlantýsýný Ayarlayýnýz!");
					Sonuc.setHATAMI(true);
					Sonuc.GosterMesajToast(MainActivity.this);
					return;
				}
			}
		});
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

	public class Game extends AsyncTask<Void, Integer, MHata> {

		@Override
		protected MHata doInBackground(Void... params) {
			MHata Sonuc = new MHata();
			try {
				json = readGameParks();
				for (int i = 0; i < json.length(); i++) {
					JSONObject JsonBDizi = json.getJSONObject(i);

					if (JsonBDizi.optString("lisanskey").toString()
							.equals(LisansKey.getText().toString())) {
						if (JsonBDizi.optString("durum").toString().equals("1")) {
							LisanskeyDurum = 0;

							if (LisanskeyDurum == 0) {
								Sonuc = new MHata();
								boolean KayitDurum = false;

								if (!KayitDurum) {

									MSabit.Kullanici.setUserID(Integer
											.parseInt(JsonBDizi.optString(
													"userID").toString()));
									MSabit.Kullanici.setAdsoyad(JsonBDizi
											.optString("adsoyad").toString());
									MSabit.Kullanici.setTelefon(JsonBDizi
											.optString("telefon").toString());
									MSabit.Kullanici.setLisanskey(JsonBDizi
											.optString("lisanskey").toString());
									MSabit.Kullanici.setBitis(JsonBDizi
											.optString("bitis").toString());
									MSabit.Kullanici.setDurum(JsonBDizi
											.optString("durum").toString());
									MSabit.Kullanici.setKartsayisi(JsonBDizi
											.optString("kartsayisi"));
									MSabit.Kullanici.setSubesayisi(JsonBDizi
											.optString("subesayisi"));

									Sonuc = MSabit.Kullanici.KaydetKullanici();

									if (!Sonuc.getHATAMI()) {
										KayitDurum = true;
										finish();
										Intent MyIntent = new Intent(
												MainActivity.this,
												RoleKontrol.class);
										startActivity(MyIntent);

									} else {
										Log.d("Hata", "Veri Eklenmedi");
									}
								}

							}
						} else {
							LisanskeyDurum = -1;
							Sonuc = new MHata();
							Sonuc.setBASLIK("Lisans");
							Sonuc.setMESAJ("Lisans Anahtarý Hatalý veya Aktif Edilmemiþ!");
							Sonuc.setHATAMI(true);
							LisansSayac++;
							return Sonuc;
						}

					} else {
						LisanskeyDurum = -1;
						if (LisansSayac != 0) {
							Sonuc = new MHata();
							Sonuc.setBASLIK("Lisans");
							Sonuc.setMESAJ("Lisans Anahtarý Hatalý veya Aktif Edilmemiþ!");
							Sonuc.setHATAMI(true);
							LisansSayac = 0;
							return Sonuc;
						}
						LisansSayac++;
					}
				}

				return Sonuc;
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(MHata result) {
			result.GosterMesajToast(MainActivity.this);
		}
	}

	public MHata KontrolLisans() {
		MHata Sonuc = new MHata();
		if (LisansKey.getText().equals("")) {
			Sonuc = new MHata();
			Sonuc.setBASLIK("Lisans Key");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Lisans Key Giriniz");
			return Sonuc;
		}

		return Sonuc;
	}

	@Override
	public void onResume() {

		MSabit.Kullanici.GetirKullanici();
		MSabit.PAyar.GetirParamAyar();
		LisanskeyDurum = 0;
		boolean VeriDurum = (MSabit.Kullanici.getUserID() != -1);
		boolean VeriDurum2 = (MSabit.PAyar.getID() != -1);

		if (!MSabit.KontrolLisansSuresi()) {
			if (VeriDurum) {
				if (VeriDurum2) {
					Intent MyIntent = new Intent(MainActivity.this,
							RoleKontrol.class);
					startActivity(MyIntent);
					finish();
				} else {
					Intent MyIntent = new Intent(MainActivity.this,
							RoleKontrol.class);
					startActivity(MyIntent);
					finish();
				}
			}
		}
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		if (id == R.id.bilgi) {
			Intent MyIntent = new Intent(MainActivity.this, Bilgi.class);
			startActivity(MyIntent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
