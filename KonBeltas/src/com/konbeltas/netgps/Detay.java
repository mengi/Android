package com.konbeltas.netgps;

import java.io.IOException;
import java.util.ArrayList;

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

import com.konbeltas.netgps.adapter.ListAdapter;
import com.konbeltas.netgps.func.MHata;
import com.konbeltas.netgps.func.MSabit;
import com.konbeltas.netgps.model.MDetay;
import com.konbeltas.netgps.rowmodel.DetayRow;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;
import android.widget.TextView;

public class Detay extends FragmentActivity {

	final static String URIDOLUBOS = "http://konyasehircicek.com/afcotopark2/json_dolubos.php";
	final static String URITARIFE = "http://konyasehircicek.com/afcotopark2/json_tarife.php";

	TextView TxtOtoparkAdi, TxtDolu, TxtBos, TxtOtoparkDurum, TxtOtoparkTipi,
			TxtOtoparkKapasite, TxtOtoparkHSuresi;
	JSONArray dolubosjson, tarifejson;
	public String GOtoparkKodu = "";
	
	ListView LstVw;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detay);

		TxtOtoparkAdi = (TextView) findViewById(R.id.TxtOtoparkAdi);
		TxtDolu = (TextView) findViewById(R.id.TxtDolu);
		TxtBos = (TextView) findViewById(R.id.TxtBos);
		TxtOtoparkDurum = (TextView) findViewById(R.id.TxtOtoparkDurum);
		TxtOtoparkTipi = (TextView) findViewById(R.id.TxtOtoparkTipi);
		TxtOtoparkKapasite = (TextView) findViewById(R.id.TxtOtoparkKapasite);
		TxtOtoparkHSuresi = (TextView) findViewById(R.id.TxtOtoparkHSuresi);
		
		LstVw = (ListView) findViewById(R.id.LstVw);
		
		Intent MyIntent = getIntent();

		try {
			GOtoparkKodu = MyIntent.getStringExtra("SecilenID");
		} catch (Exception e) {

		}

		new JsonVeriYukle().execute();
		

	}

	public class JsonVeriYukle extends AsyncTask<Void, Integer, MHata> {

		@Override
		protected MHata doInBackground(Void... params) {
			MHata Sonuc = new MHata();
			try {

				dolubosjson = readGameParks(URIDOLUBOS);
				tarifejson = readGameParks(URITARIFE);

				if (dolubosjson != null) {
					for (int i = 0; i < dolubosjson.length(); i++) {
						JSONObject JsonBDizi = dolubosjson.getJSONObject(i);

						String OtoparKodu = JsonBDizi.optString("2").toString();

						if (OtoparKodu.equals(GOtoparkKodu)) {
							MSabit.MOtoparkBilgi
									.GetirOotoparkBilgiById(OtoparKodu);
							String IcerdekiAracSayisi = JsonBDizi
									.optString("3").toString();
							int BosSayisi = Integer
									.parseInt(MSabit.MOtoparkBilgi
											.getKapasite())
									- Integer.parseInt(IcerdekiAracSayisi);

							MSabit.Bos = Integer.toString(BosSayisi);
							MSabit.Dolu = IcerdekiAracSayisi;
							MSabit.OtoparkAdi = MSabit.MOtoparkBilgi
									.getOtoparkAd();
							MSabit.Yikama = MSabit.MOtoparkBilgi.getYikama();
							MSabit.HizmetSuresi = MSabit.MOtoparkBilgi
									.getHizmetSuresi();
							MSabit.Kapasite = MSabit.MOtoparkBilgi
									.getKapasite();
							MSabit.OtoparkTipi = MSabit
									.CevirOtoparkKodu(OtoparKodu);
						}

					}
				}

				if (tarifejson != null) {
					MSabit.MDetaylar = new ArrayList<MDetay>();
					for (int i = 0; i < tarifejson.length(); i++) {
						JSONObject JsonBDizi = tarifejson.getJSONObject(i);

						String OtoparKodu = JsonBDizi.optString("1").toString();

						if (OtoparKodu.equals(GOtoparkKodu)) {
							String Ucret = JsonBDizi.optString("2") + " TL";
							String Aciklama = JsonBDizi.optString("3") + " Arası";
							MSabit.MOtoparkDetay = new MDetay(Aciklama, Ucret);
	
							MSabit.MDetaylar.add(MSabit.MOtoparkDetay);
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
			if (MSabit.Yikama.equals("")) {
				MSabit.Yikama = "Yok";
			}

			if (MSabit.HizmetSuresi.equals("")) {
				MSabit.HizmetSuresi = "08:00-23:00";
			}

			TxtBos.setText(MSabit.Bos);
			TxtDolu.setText(MSabit.Dolu);
			TxtOtoparkAdi.setText(MSabit.OtoparkAdi);
			TxtOtoparkDurum.setText(MSabit.Yikama);
			TxtOtoparkHSuresi.setText(MSabit.HizmetSuresi);
			TxtOtoparkKapasite.setText(MSabit.Kapasite);
			TxtOtoparkTipi.setText(MSabit.OtoparkTipi);
			
			try {
				ArrayList<DetayRow> DetayRows = new ArrayList<DetayRow>();
				for (int i = 0; i < MSabit.MDetaylar.size(); i++) {
					DetayRow detayRow = new DetayRow();
					detayRow.Aciklama = MSabit.MDetaylar.get(i).Aciklama;
					detayRow.Ucret = MSabit.MDetaylar.get(i).Ucret;
					
					DetayRows.add(detayRow);
				}
				
				ListAdapter Adapter = new ListAdapter(Detay.this, Detay.this,
						R.layout.listrow_detay, DetayRows.toArray(),
						ListAdapter.IslemTipi.OTOPARKACIKLAMA);
				LstVw.setAdapter(Adapter);
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	}

	protected JSONArray readGameParks(String URI)
			throws ClientProtocolException, IOException, JSONException {
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
