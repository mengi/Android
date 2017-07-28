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
import com.chatt.demo.model.MMasa;
import com.chatt.demo.model.MSiparis;
import com.chatt.demo.rw.model.MasaRow;
import com.chatt.demo.utils.JSONParser;
import com.chatt.demo.utils.ListAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Masa extends Activity {

	EditText TxtMasaAdi;
	ListView LstVw;

	private ProgressDialog pDialog;

	Button BtnAc, BtnKapat, BtnSiparisGonder, BtnSiparisDurum;

	public MasaRow SecilenMasa = null;
	private ArrayList<MasaRow> Masalar;

	JSONParser jParser = new JSONParser();
	JSONArray jGarson = null;
	ArrayList<HashMap<String, String>> productsList;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_MASA = "masalar";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.masa);

		TxtMasaAdi = (EditText) findViewById(R.id.TxtMasaAdi);
		TxtMasaAdi.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				String AranacakKelime = s.toString();
				AramaYap(AranacakKelime);
			}
		});
		RSabit.MSiparis = new MSiparis();

		BtnAc = (Button) findViewById(R.id.BtnAc);
		BtnAc.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (SecilenMasa != null) {
					new MasaAc().execute();
				} else {
					new SweetAlertDialog(Masa.this, SweetAlertDialog.ERROR_TYPE)
							.setTitleText("Oops...")
							.setContentText("Lütfen Masa Seçiniz !!!").show();
				}

			}
		});

		BtnKapat = (Button) findViewById(R.id.BtnKapat);
		BtnKapat.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (SecilenMasa != null) {
					RSabit.MMasa.GuncelleMasa(SecilenMasa.id, "off");
					RSabit.MSiparis.SiparisSilMasa(RSabit.MasaAdi);
					GetirMasaListe();
				} else {
					new SweetAlertDialog(Masa.this,
							SweetAlertDialog.WARNING_TYPE)
							.setTitleText("Oops...")
							.setContentText("Lütfen Masa Seçiniz !!!").show();
				}
			}
		});

		BtnSiparisGonder = (Button) findViewById(R.id.BtnSiparisGonder);
		BtnSiparisGonder.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (SecilenMasa != null) {
					new MasaSiparis().execute();
				} else {
					new SweetAlertDialog(Masa.this,
							SweetAlertDialog.WARNING_TYPE)
							.setTitleText("Oops...")
							.setContentText("Lütfen Masa Seçiniz !!!").show();
				}

			}
		});

		BtnSiparisDurum = (Button) findViewById(R.id.BtnSiparisDurum);
		BtnSiparisDurum.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (SecilenMasa != null) {
					Intent myIntent = new Intent(Masa.this, SiparisDurum.class);
					startActivity(myIntent);
				} else {
					new SweetAlertDialog(Masa.this,
							SweetAlertDialog.WARNING_TYPE)
							.setTitleText("Oops...")
							.setContentText("Lütfen Masa Seçiniz !!!").show();
				}

			}
		});
		
		LstVw = (ListView) findViewById(R.id.LstVw);
		LstVw.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LstVw = (ListView) findViewById(R.id.LstVw);
				SecilenMasa = (MasaRow) LstVw.getAdapter().getItem(position);
				RSabit.MasaAdi = SecilenMasa.masaadi;
				RSabit.MasaId = SecilenMasa.id;

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
		GetirMasaListe();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.al:
			Intent myIntent = new Intent(Masa.this, SiparisListe.class);
			startActivity(myIntent);
			return true;
		case R.id.siparis:
			new MasaYenile().execute();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_iki, menu);
		return true;
	}

	public void GetirMasaListe() {
		Masalar = new ArrayList<MasaRow>();
		try {
			SQLiteDatabase DtBs = RSabit.OpDbBaglanti.getReadableDatabase();
			String SorguCumle = "SELECT ID, MASAADI, MASADURUMU, SIPARISDURUMU FROM TBL_NMASA";
			Cursor Crs = DtBs.rawQuery(SorguCumle, null);

			while (Crs.moveToNext()) {
				MasaRow masarow = new MasaRow();

				masarow.id = Crs.getString(0);
				masarow.masaadi = Crs.getString(1);
				masarow.masadurumu = Crs.getString(2);
				masarow.siparisdurumu = Crs.getString(3);

				if (masarow.masadurumu.equals("off")) {
					masarow.masadurumu = "Kapalı";
					masarow.siparisdurumu = "Alınmadı";
				}

				if (masarow.masadurumu.equals("on")) {
					masarow.masadurumu = "Açık";
					masarow.siparisdurumu = "Alınıyor";
				}
				Masalar.add(masarow);
			}

			ListAdapter adapter = new ListAdapter(this, this,
					R.layout.listrow_masa, Masalar.toArray(),
					ListAdapter.IslemTipi.MASA);
			LstVw.setAdapter(adapter);
			pDialog.dismiss();
			DtBs.close();
		} catch (Exception e) {

		}
	}

	public void AramaYap(String AranacakKelime) {
		Masalar = new ArrayList<MasaRow>();
		try {
			SQLiteDatabase DtBs = RSabit.OpDbBaglanti.getReadableDatabase();
			String SorguCumle = "SELECT ID, MASAADI, MASADURUMU, SIPARISDURUMU FROM TBL_NMASA WHERE MASAADI LIKE '%"
					+ AranacakKelime + "%'";
			Cursor Crs = DtBs.rawQuery(SorguCumle, null);

			while (Crs.moveToNext()) {
				MasaRow masarow = new MasaRow();

				masarow.id = Crs.getString(0);
				masarow.masaadi = Crs.getString(1);
				masarow.masadurumu = Crs.getString(2);
				masarow.siparisdurumu = Crs.getString(3);

				if (masarow.masadurumu.equals("off")) {
					masarow.masadurumu = "Kapalı";
					masarow.siparisdurumu = "Alınmadı";
				}

				if (masarow.masadurumu.equals("on")) {
					masarow.masadurumu = "Açık";
					masarow.siparisdurumu = "Alınıyor";
				}
				Masalar.add(masarow);
			}

			ListAdapter adapter = new ListAdapter(this, this,
					R.layout.listrow_masa, Masalar.toArray(),
					ListAdapter.IslemTipi.MASA);
			LstVw.setAdapter(adapter);
			DtBs.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	class MasaSiparis extends AsyncTask<String, String, String> {
		String url_urun_liste = "";
		ArrayList<MSiparis> MasaSiparisleri = new ArrayList<MSiparis>();
		JSONObject json = null;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			RSabit.MAyar.GetirAyar();

			if (RSabit.MAyar.getIpAdres() != "") {
				url_urun_liste = "http://" + RSabit.MAyar.IpAdres
						+ "/lokanta/android_connect/siparis_kayit.php";
			}

			pDialog = ProgressDialog.show(Masa.this, null,
					getString(R.string.alert_loading));
		}

		protected String doInBackground(String... args) {

			String Sonuc = "0";

			RSabit.MSiparis = new MSiparis();
			MasaSiparisleri = RSabit.MSiparis.MasaSiparisGetir(SecilenMasa.id);

			for (int i = 0; i < MasaSiparisleri.size(); i++) {
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();

				params.add(new BasicNameValuePair("stokid", MasaSiparisleri
						.get(i).getSTOKID()));
				params.add(new BasicNameValuePair("adet", MasaSiparisleri
						.get(i).getMIKTAR()));
				params.add(new BasicNameValuePair("masaid", MasaSiparisleri
						.get(i).getMASAID()));

				json = jParser.makeHttpRequest(url_urun_liste, "POST", params);

				Log.d("Create Response", json.toString());

				try {
					int success = json.getInt(TAG_SUCCESS);
					if (success == 1) {
						RSabit.MSiparis = new MSiparis();
					
						RSabit.MSiparis.GuncelDurum(MasaSiparisleri.get(i)
								.getURUNADI(), MasaSiparisleri.get(i)
								.getMASAID());
					
						Sonuc = Integer.toString(1);
					} else if (success == 3) {
						Sonuc = Integer.toString(3);
					} else {
						Sonuc = Integer.toString(0);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					pDialog.dismiss();
				}
			}

			return Sonuc;
		}

		protected void onPostExecute(String file_url) {
			RSabit.MSiparis = new MSiparis();
			if (file_url.equals("1")) {
				pDialog.dismiss();
				
				if (RSabit.MSiparis.MasaSiparisSil(RSabit.MasaId)) {
					new SweetAlertDialog(Masa.this, SweetAlertDialog.SUCCESS_TYPE)
					.setTitleText("Oops...")
					.setContentText("Sipariş Gönderildi !!!").show();
				} else {
					new SweetAlertDialog(Masa.this, SweetAlertDialog.ERROR_TYPE)
					.setTitleText("Oops...")
					.setContentText("Sipariş Silme İşlemi Başarısız !!!").show();
				}


			} else if (file_url.equals("0")) {
				pDialog.dismiss();
				new SweetAlertDialog(Masa.this, SweetAlertDialog.ERROR_TYPE)
						.setTitleText("Oops...")
						.setContentText("Sipariş Gönderilemedi !!!").show();
			} else if (file_url.equals("3")) {
				pDialog.dismiss();
				new SweetAlertDialog(Masa.this, SweetAlertDialog.WARNING_TYPE)
						.setTitleText("Oops...")
						.setContentText(
								"Bağlantı Ayarlarını Kontrol Ediniz !!!")
						.show();
			}

		}
	}

	class MasaAc extends AsyncTask<String, String, String> {
		String url_urun_liste = "";
		JSONObject json = null;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			RSabit.MAyar.GetirAyar();

			if (RSabit.MAyar.getIpAdres() != "") {
				url_urun_liste = "http://" + RSabit.MAyar.IpAdres
						+ "/lokanta/android_connect/masa_ac.php";
			}

			pDialog = ProgressDialog.show(Masa.this, null,
					getString(R.string.alert_loading));
		}

		protected String doInBackground(String... args) {

			String Sonuc = "0";

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("masaid", SecilenMasa.id));
			params.add(new BasicNameValuePair("garsonkodu", RSabit.MKullanici
					.getGarsonKod()));

			json = jParser.makeHttpRequest(url_urun_liste, "POST", params);

			Log.d("Create Response", json.toString());

			try {
				int success = json.getInt(TAG_SUCCESS);
				String GelenSiparisId = json.getString(TAG_MESSAGE);
				RSabit.MMasa = new MMasa();
				
				if (success == 1) {
					RSabit.MMasa.GuncelleMasaSiparis(SecilenMasa.id, GelenSiparisId);
					Sonuc = Integer.toString(1);
				} else if (success == 3) {
					Sonuc = Integer.toString(3);
				} else if (success == 4) {
					Sonuc = Integer.toString(4);
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
				pDialog.dismiss();
				RSabit.MMasa.GuncelleMasa(SecilenMasa.id, "on");
				RSabit.MasaAdi = SecilenMasa.masaadi;
				Intent myIntent = new Intent(Masa.this, UrunUstListe.class);
				myIntent.putExtra("MasaId", SecilenMasa.id);
				startActivity(myIntent);
				GetirMasaListe();

			} else if (file_url.equals("0")) {
				pDialog.dismiss();
				new SweetAlertDialog(Masa.this, SweetAlertDialog.ERROR_TYPE)
						.setTitleText("Oops...")
						.setContentText("Sipariş Gönderilemedi !!!").show();
			} else if (file_url.equals("3")) {
				pDialog.dismiss();
				new SweetAlertDialog(Masa.this, SweetAlertDialog.WARNING_TYPE)
						.setTitleText("Oops...")
						.setContentText(
								"Bağlantı Ayarlarını Kontrol Ediniz !!!")
						.show();
			} else if (file_url.equals("4")) {
				pDialog.dismiss();
				new SweetAlertDialog(Masa.this, SweetAlertDialog.WARNING_TYPE)
						.setTitleText("Oops...")
						.setContentText(
								"Masayı Açmaya Yetkiniz Yok !!!")
						.show();
			}

		}
	}

	class MasaYenile extends AsyncTask<String, String, String> {
		String url_masa_liste = "";
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			if (RSabit.MAyar.getIpAdres() != "") {
				url_masa_liste = "http://"+RSabit.MAyar.IpAdres+"/lokanta/android_connect/masa_liste.php";
			}
			
			pDialog = ProgressDialog.show(Masa.this, null,
					getString(R.string.alert_loading));
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
				GetirMasaListe();
			} else if (file_url.equals("0")) {
				pDialog.dismiss();
                new SweetAlertDialog(Masa.this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText("Masa Bilgileri Getirilemedi !!!")
                .show();
			} else if (file_url.equals("3")) {
				pDialog.dismiss();
				new SweetAlertDialog(Masa.this, SweetAlertDialog.WARNING_TYPE)
				.setTitleText("Oops...")
				.setContentText("Bağlantı Ayarlarını Kontrol Ediniz !!!").show();
			}
			
		}
	}
	
	@Override
	public void onBackPressed() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
        .setTitleText("Uygulamıyı Kapat !!!")
        .setContentText("Uylamadan Çıkmak İstediğinize Emin Misiniz ?")
        .setCancelText("Hayır !")
        .setConfirmText("Evet !")
        .showCancelButton(true)
        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                // reuse previous dialog instance, keep widget user state, reset them if you need
                sDialog.setTitleText("Hayır!")
                        .setContentText("Uygulamdan Çıkılmadı !!!")
                        .setConfirmText("OK")
                        .showCancelButton(false)
                        .setCancelClickListener(null)
                        .setConfirmClickListener(null)
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

            }
        })
        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
            	Intent intent  = new Intent(); // need to set your Intent View here
            	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);        
            	Masa.this.finish();
            }
        })
        .show();
        /*
		new AlertDialog.Builder(this).setTitle("Qr Çıkış")
				.setMessage("Uygulamadan Çıkmak İstediğinize Emin Misiniz ?")
				.setNegativeButton("Hayır", null)
				.setIcon(R.drawable.ic_launcher)
				.setPositiveButton("Evet", new OnClickListener() {

					public void onClick(DialogInterface arg0, int arg1) {
						GirisActivity.super.onBackPressed();
					}
				}).create().show();
				*/
	}
	
}
