package com.chatt.demo;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import cn.pedant.SweetAlert.widget.SweetAlertDialog;
import com.chatt.demo.custom.RSabit;
import com.chatt.demo.model.MSiparis;
import com.chatt.demo.rw.model.SiparisRow;
import com.chatt.demo.utils.JSONParser;
import com.chatt.demo.utils.ListAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class SiparisDurum extends Activity {

	ListView LstVw;

	private ProgressDialog pDialog;

	JSONParser jParser = new JSONParser();
	JSONArray jSiparis = null;
	ArrayList<SiparisRow> SiparisRowlar = null;

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESAGGE = "message";
	private static final String TAG_PRODUCTS = "siparishereketler";
	String GelenMesaj = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.siparis_durum);

		new MasaSiparisDurum().execute();

		LstVw = (ListView) findViewById(R.id.LstVw);

	}

	class MasaSiparisDurum extends AsyncTask<String, String, String> {
		String url_urun_liste = "";
		ArrayList<MSiparis> MasaSiparisleri = new ArrayList<MSiparis>();
		JSONObject json = null;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			RSabit.MAyar.GetirAyar();

			if (RSabit.MAyar.getIpAdres() != "") {
				url_urun_liste = "http://" + RSabit.MAyar.IpAdres
						+ "/lokanta/android_connect/siparis_durum_liste.php";
			}

			pDialog = ProgressDialog.show(SiparisDurum.this, null,
					getString(R.string.alert_loading));
		}

		protected String doInBackground(String... args) {

			String Sonuc = "0";

			RSabit.MSiparis = new MSiparis();

			String SiparisId = RSabit.MMasa.GetirMasaSiparisID(RSabit.MasaId);

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("masaid", RSabit.MasaId));
			params.add(new BasicNameValuePair("siparisid", SiparisId));

			json = jParser.makeHttpRequest(url_urun_liste, "POST", params);

			Log.d("Create Response", json.toString());

			try {
				int success = json.getInt(TAG_SUCCESS);
				GelenMesaj = json.getString(TAG_MESAGGE);

				if (success == 1) {
					SiparisRowlar = new ArrayList<SiparisRow>();
					jSiparis = json.getJSONArray(TAG_PRODUCTS);
					for (int i = 0; i < jSiparis.length(); i++) {
						SiparisRow siparisRow = new SiparisRow();
						JSONObject Siparis = jSiparis.getJSONObject(i);

						// Gelen Veriler
						siparisRow.MasaAdi = RSabit.MasaAdi;
						siparisRow.UrunAdi = Siparis.getString("urunadi");
						siparisRow.Miktar = Siparis.getString("adet");
						siparisRow.Toplam = Siparis.getString("tutar");
						siparisRow.SiparisDurum = Siparis
								.getString("siparisdurum");

						if (siparisRow.SiparisDurum.equals("1")) {
							siparisRow.SiparisDurum = "Hazýrlanýyor";
						} else if (siparisRow.SiparisDurum.equals("2")) {
							siparisRow.SiparisDurum = "Hazýr";
						}
						SiparisRowlar.add(siparisRow);
					}

					Sonuc = Integer.toString(1);
				} else if (success == 3) {
					Sonuc = Integer.toString(3);
				} else if (success == 2) {
					Sonuc = Integer.toString(2);
				} else{
					Sonuc = Integer.toString(0);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				pDialog.dismiss();
			}

			return Sonuc;
		}

		protected void onPostExecute(String file_url) {
			if (file_url.equals("1")) {
				pDialog.dismiss();
				runOnUiThread(new Runnable() {
					public void run() {
						ListAdapter adapter = new ListAdapter(
								SiparisDurum.this, SiparisDurum.this,
								R.layout.listrow_siparis_durum,
								SiparisRowlar.toArray(),
								ListAdapter.IslemTipi.SIPARISDURUM);
						LstVw.setAdapter(adapter);
					}
				});

			} else if (file_url.equals("0")) {
				pDialog.dismiss();
				new SweetAlertDialog(SiparisDurum.this,
						SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...")
						.setContentText("Sipariþ Liste Getirilemedi !!!")
						.show();
			} else if (file_url.equals("3")) {
				pDialog.dismiss();
				new SweetAlertDialog(SiparisDurum.this,
						SweetAlertDialog.WARNING_TYPE)
						.setTitleText("Oops...")
						.setContentText(
								"Baðlantý Ayarlarýný Kontrol Ediniz !!!")
						.show();
			} else if (file_url.equals("2")) {
				pDialog.dismiss();
				new SweetAlertDialog(SiparisDurum.this,
						SweetAlertDialog.WARNING_TYPE).setTitleText("Oops...")
						.setContentText(GelenMesaj).show();
			}

		}
	}

}
