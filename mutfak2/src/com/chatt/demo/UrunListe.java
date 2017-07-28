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
import com.chatt.demo.custom.RSabit;
import com.chatt.demo.model.MAyar;
import com.chatt.demo.model.MSiparis;
import com.chatt.demo.model.MUrunList;
import com.chatt.demo.rw.model.UrunListRow;
import com.chatt.demo.utils.JSONParser;
import com.chatt.demo.utils.ListAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class UrunListe extends Activity {

	private ProgressDialog pDialog;

	ListView LstVw;
	EditText TxtMiktar;
	ImageView BtnUrunEkle;

	JSONParser jParser = new JSONParser();
	JSONArray jGarson = null;
	ArrayList<HashMap<String, String>> productsList;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_URUN = "urunler";
	private String UrunGrupId = "";
	private UrunListRow SecilenUrun = null;
	private ArrayList<UrunListRow> Urunler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.urun_liste);

		RSabit.MAyar = new MAyar();
		RSabit.MSiparis = new MSiparis();
		RSabit.MAyar.GetirAyar();

		BtnUrunEkle = (ImageView) findViewById(R.id.BtnUrunEkle);
		TxtMiktar = (EditText) findViewById(R.id.TxtMiktar);

		setTitle("          " + RSabit.MasaAdi);

		Bundle bundle = getIntent().getExtras();
		UrunGrupId = bundle.getString("UrunUstId");
		new UrunListesi().execute(UrunGrupId);

		BtnUrunEkle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int Miktar = 0;
				try {
					Miktar = Integer.parseInt(TxtMiktar.getText().toString());
				} catch (Exception e) {
					Miktar = 0;
				}

				if (SecilenUrun != null) {
					if (Miktar > 0) {
						RSabit.MSiparis = new MSiparis(RSabit.KullaniciId,
								SecilenUrun.Aciklama, RSabit.MasaAdi, RSabit
										.Cevir(SecilenUrun.Fiyat), Integer
										.toString(Miktar), RSabit.MasaId, "0",
								SecilenUrun.StokID);
						if (!RSabit.MSiparis.KaydetSiparis()) {
							new SweetAlertDialog(UrunListe.this,
									SweetAlertDialog.ERROR_TYPE)
									.setTitleText("Oops...")
									.setContentText("Ürün Ekleme Baþarýsýz !!!")
									.show();
						}

						TxtMiktar.setText("");
					} else {
						new SweetAlertDialog(UrunListe.this,
								SweetAlertDialog.ERROR_TYPE)
								.setTitleText("Oops...")
								.setContentText(
										"Lütfen Ürün Miktarý Belirtiniz !!!")
								.show();
					}
				} else {
					new SweetAlertDialog(UrunListe.this,
							SweetAlertDialog.WARNING_TYPE)
							.setTitleText("Oops...")
							.setContentText("Lütfen Ürün Seçiniz !!!").show();
				}
			}
		});

		LstVw = (ListView) findViewById(R.id.LstVw);
		LstVw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LstVw = (ListView) findViewById(R.id.LstVw);
				SecilenUrun = (UrunListRow) LstVw.getAdapter()
						.getItem(position);

				((ListAdapter) LstVw.getAdapter()).KAYMA = false;
				((ListAdapter) LstVw.getAdapter()).FocusId = position;
				((ListAdapter) LstVw.getAdapter()).getView(position, view,
						(ViewGroup) LstVw);
				int EklenecekDeger = 0;
				if (((ListAdapter) LstVw.getAdapter()).LastToId < ((ListAdapter) LstVw
						.getAdapter()).LastId) {
					EklenecekDeger = ((ListAdapter) LstVw.getAdapter()).LastId
							- LstVw.getChildCount() + 1;
				} else {
					EklenecekDeger = ((ListAdapter) LstVw.getAdapter()).LastId;
				}
				for (int i = 0; i < LstVw.getChildCount(); i++) {
					((ListAdapter) LstVw.getAdapter()).getView(i
							+ EklenecekDeger, LstVw.getChildAt(i),
							(ViewGroup) LstVw);
				}
			};
		});

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.al:
			Intent myIntent = new Intent(UrunListe.this, SiparisListe.class);
			startActivity(myIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void GetirUrupListe() {
		try {
			Urunler = new ArrayList<UrunListRow>();
			SQLiteDatabase DtBs = RSabit.OpDbBaglanti.getReadableDatabase();
			String SorguCumle = "SELECT STOKID, ACIKLAMA, FIYAT FROM TBL_NURUNLER WHERE URUNGRUPID=?";
			Cursor Crs = DtBs.rawQuery(SorguCumle, new String[] { UrunGrupId });

			while (Crs.moveToNext()) {
				UrunListRow UrunListRow = new UrunListRow();

				UrunListRow.StokID = Crs.getString(0);
				UrunListRow.Aciklama = Crs.getString(1);
				UrunListRow.Fiyat = Double.toString(Crs.getDouble(2)) + " TL";
				Urunler.add(UrunListRow);
			}

			ListAdapter adapter = new ListAdapter(this, this,
					R.layout.listrow_menudetay, Urunler.toArray(),
					ListAdapter.IslemTipi.URUNLER);
			LstVw.setAdapter(adapter);
			DtBs.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void AramaYap (String AranacakKelime) {
		try {
			Urunler = new ArrayList<UrunListRow>();
			SQLiteDatabase DtBs = RSabit.OpDbBaglanti.getReadableDatabase();
			String SorguCumle = "SELECT STOKID, ACIKLAMA, FIYAT FROM TBL_NURUNLER WHERE ACIKLAMA LIKE '%"+AranacakKelime+"%'";
			Cursor Crs = DtBs.rawQuery(SorguCumle, new String[] { UrunGrupId });

			while (Crs.moveToNext()) {
				UrunListRow UrunListRow = new UrunListRow();

				UrunListRow.StokID = Crs.getString(0);
				UrunListRow.Aciklama = Crs.getString(1);
				UrunListRow.Fiyat = Double.toString(Crs.getDouble(2)) + " TL";
				Urunler.add(UrunListRow);
			}

			ListAdapter adapter = new ListAdapter(this, this,
					R.layout.listrow_menudetay, Urunler.toArray(),
					ListAdapter.IslemTipi.URUNLER);
			LstVw.setAdapter(adapter);
			DtBs.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	class UrunListesi extends AsyncTask<String, String, String> {
		String url_urun_liste = "";

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			RSabit.MAyar.GetirAyar();
			if (RSabit.MAyar.getIpAdres() != "") {
				url_urun_liste = "http://" + RSabit.MAyar.IpAdres
						+ "/lokanta/android_connect/urun_liste.php";
			}

			pDialog = ProgressDialog.show(UrunListe.this, null,
					getString(R.string.alert_loading));
		}

		protected String doInBackground(String... args) {

			String Sonuc = "0";

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id", args[0]));

			JSONObject json = jParser.makeHttpRequest(url_urun_liste, "POST",
					params);

			Log.d("Create Response", json.toString());

			try {
				int success = json.getInt(TAG_SUCCESS);
				RSabit.MUrunListe = new MUrunList();
				if (success == 1) {
					if (RSabit.MUrunListe.UrunSil()) {
						jGarson = json.getJSONArray(TAG_URUN);

						for (int i = 0; i < jGarson.length(); i++) {
							JSONObject c = jGarson.getJSONObject(i);
							String s = c.getString("stokID");
							String a = c.getString("acik");
							double f = c.getDouble("fiyat");
							String u = c.getString("urungrupid");

							RSabit.MUrunListe = new MUrunList(s, a, u, f);
							if (!RSabit.MUrunListe.KaydetUrunListe()) {
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
				pDialog.dismiss();
			}

			return Sonuc;
		}

		protected void onPostExecute(String file_url) {
			if (file_url.equals("1")) {
				GetirUrupListe();
				pDialog.dismiss();

			} else if (file_url.equals("0")) {
				pDialog.dismiss();
				new SweetAlertDialog(UrunListe.this,
						SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...")
						.setContentText("Ürün Bulunamadý !!!").show();
			} else if (file_url.equals("3")) {
				pDialog.dismiss();
				new SweetAlertDialog(UrunListe.this, SweetAlertDialog.WARNING_TYPE)
				.setTitleText("Oops...")
				.setContentText("Baðlantý Ayarlarýný Kontrol Ediniz !!!").show();
			}

		}
	}
}
