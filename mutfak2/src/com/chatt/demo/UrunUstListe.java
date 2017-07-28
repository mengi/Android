package com.chatt.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import cn.pedant.SweetAlert.widget.SweetAlertDialog;
import com.chatt.demo.custom.RSabit;
import com.chatt.demo.model.MAyar;
import com.chatt.demo.model.MMasa;
import com.chatt.demo.model.MUrunUstListe;
import com.chatt.demo.rw.model.UrunGrupListeRow;
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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

public class UrunUstListe extends Activity {
	
	ListView LstVw;
	EditText TxtUrunAdi;
	
	private ProgressDialog pDialog;

	JSONParser jParser = new JSONParser();
	JSONArray jGarson = null;
	ArrayList<HashMap<String, String>> productsList; 
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_URUN = "urunler";
	private static String ID = "-1";
	public UrunGrupListeRow SecilenUrunGrup = null;
	private ArrayList<UrunGrupListeRow> UrunGruplar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.urunust_liste);
		
		RSabit.MAyar = new MAyar();
		RSabit.ZiyaretMasa = new MMasa();
		RSabit.MAyar.GetirAyar();
		Bundle Bundle = getIntent().getExtras();
		
		try {
			ID = Bundle.getString("MasaId");
			RSabit.ZiyaretMasa.GetirMasa(ID);
			setTitle("          "+RSabit.ZiyaretMasa.getMASAADI());
		} catch (Exception e) {
			ID = "-1";
		}
		
		TxtUrunAdi = (EditText) findViewById(R.id.TxtUrunAdi);
		TxtUrunAdi.addTextChangedListener(new TextWatcher() {

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
		LstVw = (ListView) findViewById(R.id.LstVw);
		LstVw.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LstVw = (ListView) findViewById(R.id.LstVw);
				SecilenUrunGrup = (UrunGrupListeRow) LstVw.getAdapter().getItem(position);
				
				Intent myIntent = new Intent(UrunUstListe.this, UrunListe.class);
				myIntent.putExtra("UrunUstId", SecilenUrunGrup.ID);
				startActivity(myIntent);
				
			};
		});
		
		new UrunUsListe().execute();
		
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.al:
	        	Intent myIntent = new Intent(UrunUstListe.this, SiparisListe.class);
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
	
	
	public void GetirUrupGrupListe() {
		try {
			ArrayList<UrunGrupListeRow> UrunGruplar = new ArrayList<UrunGrupListeRow>();
			SQLiteDatabase DtBs = RSabit.OpDbBaglanti.getReadableDatabase();
			String SorguCumle = "SELECT ID, URUNGRUPADI FROM TBL_NURUNGRUP";
			Cursor Crs = DtBs.rawQuery(SorguCumle, null);
			
			while (Crs.moveToNext()) {
				UrunGrupListeRow urunGrupListeRow = new UrunGrupListeRow();

				urunGrupListeRow.ID = Crs.getString(0);
				urunGrupListeRow.URUNGRUPADI = Crs.getString(1);
				
				UrunGruplar.add(urunGrupListeRow);
			}
			
			ListAdapter adapter = new ListAdapter(this, this,
					R.layout.listrow_menu,
					UrunGruplar.toArray(),
					ListAdapter.IslemTipi.URUNGRUP);
			LstVw.setAdapter(adapter);
			DtBs.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void AramaYap (String AranacakKelime) {
		try {
			UrunGruplar = new ArrayList<UrunGrupListeRow>();
			SQLiteDatabase DtBs = RSabit.OpDbBaglanti.getReadableDatabase();
			String SorguCumle = "SELECT ID, URUNGRUPADI FROM TBL_NURUNGRUP WHERE URUNGRUPADI LIKE '%"+AranacakKelime+"%'";
			Cursor Crs = DtBs.rawQuery(SorguCumle, null);
			
			while (Crs.moveToNext()) {
				UrunGrupListeRow urunGrupListeRow = new UrunGrupListeRow();

				urunGrupListeRow.ID = Crs.getString(0);
				urunGrupListeRow.URUNGRUPADI = Crs.getString(1);
				
				UrunGruplar.add(urunGrupListeRow);
			}
			
			ListAdapter adapter = new ListAdapter(this, this,
					R.layout.listrow_menu,
					UrunGruplar.toArray(),
					ListAdapter.IslemTipi.URUNGRUP);
			LstVw.setAdapter(adapter);
			DtBs.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	class UrunUsListe extends AsyncTask<String, String, String> {
		String url_urun_liste = "";
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			if (RSabit.MAyar.getIpAdres() != "") {
				url_urun_liste = "http://"+RSabit.MAyar.IpAdres+"/lokanta/android_connect/urunust_liste.php";
			}
			
			pDialog = ProgressDialog.show(UrunUstListe.this, null,
					getString(R.string.alert_loading));
		}

		protected String doInBackground(String... args) {
			
			String Sonuc = "0";

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			JSONObject json = jParser.makeHttpRequest(url_urun_liste, "GET", params);

			Log.d("Create Response", json.toString());

			try {
				int success = json.getInt(TAG_SUCCESS);
				RSabit.MUrunUstListe = new MUrunUstListe();
				if (success == 1) {
					if (RSabit.MUrunUstListe.UrunGrupSil()) {
						jGarson = json.getJSONArray(TAG_URUN);
						
						for (int i = 0; i < jGarson.length(); i++) {
							JSONObject c = jGarson.getJSONObject(i);
							String id = c.getString("id");
							String a = c.getString("acik");
							
							RSabit.MUrunUstListe = new MUrunUstListe(id, a);
							if (!RSabit.MUrunUstListe.KaydetUrunGrup()) {
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
     
				GetirUrupGrupListe();
				
				pDialog.dismiss();
                
			} else if (file_url.equals("0")) {
				pDialog.dismiss();
                new SweetAlertDialog(UrunUstListe.this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText("Kullanýcý Adý veya Parola Hatalý!")
                .show();
			} else if (file_url.equals("3")) {
				pDialog.dismiss();
				new SweetAlertDialog(UrunUstListe.this, SweetAlertDialog.WARNING_TYPE)
				.setTitleText("Oops...")
				.setContentText("Baðlantý Ayarlarýný Kontrol Ediniz !!!").show();
			}
			
		}
	}
}
