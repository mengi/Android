package com.konbeltas.netgps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.konbeltas.netgps.adapter.DirectionsJSONParser;
import com.konbeltas.netgps.adapter.ListAdapter;
import com.konbeltas.netgps.R;
import com.konbeltas.netgps.adapter.GPSTracker;
import com.konbeltas.netgps.db.DataBaseHelper;
import com.konbeltas.netgps.func.MHata;
import com.konbeltas.netgps.func.MSabit;
import com.konbeltas.netgps.model.OtoparkBilgi;
import com.konbeltas.netgps.rowmodel.GpsRow;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	GoogleMap map;

	ArrayList<LatLng> markerPoints;
	TextView tvDistanceDuration;

	@SuppressWarnings("unused")
	private Marker BulYer;
	@SuppressWarnings("unused")
	private Marker GitYer;

	private LatLng BulEnBoy;
	public LatLng GitEnBoy;

	Button BtnYolTarifi, BtnAyrintiBilgi;
	EditText TxtOtoparkAra;

	ImageButton BtnFullScreen;

	GPSTracker Gps;
	ListView LstVw;

	public GpsRow SecilenGps = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		BtnFullScreen = (ImageButton) findViewById(R.id.BtnFullScreen);
		tvDistanceDuration = (TextView) findViewById(R.id.tv_distance_time);
		BtnAyrintiBilgi = (Button) findViewById(R.id.BtnAyrintiBilgi);
		BtnYolTarifi = (Button) findViewById(R.id.BtnYolTarifi);

		BtnFullScreen.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("InlinedApi")
			@Override
			public void onClick(View v) {
				if (SecilenGps != null) {
					Intent myIntent = new Intent(MainActivity.this,
							FullScreen.class);
					myIntent.putExtra("SecilenID", SecilenGps.OtoparkKodu);
					startActivity(myIntent);
				}
			}
		});

		MSabit.MDbBaglanti = new DataBaseHelper(this);

		// Internet Baðlantýsý Kontrolu
		if (MSabit.GetirIpAdresi().equals("")) {
			MHata Sonuc = new MHata();
			Sonuc.setBASLIK("Að Baðlantýsý");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Lütfen Að Baðlantýsýn Açýnýz !!!");
			Sonuc.GosterMesajToast(MainActivity.this);
			return;
		} else {
			YukleOtoPark();
		}

		// Icon Tanim
		final BitmapDescriptor bulunduguyer = BitmapDescriptorFactory
				.fromResource(R.drawable.bulunduguyer);

		// Konum Bilgisi
		try {

			Gps = new GPSTracker(MainActivity.this);

			if (Gps.canGetLocation()) {

				BulEnBoy = new LatLng(Gps.getLatitude(), Gps.getLongitude());

			} else {

				Gps.showSettingsAlert();
			}
		} catch (Exception e) {
			return;
		}

		// Map Taným
		SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);

		map = fm.getMap();
		map.setMyLocationEnabled(true);
		map.setTrafficEnabled(true);
		map.getUiSettings().setCompassEnabled(true);

		BulYer = map.addMarker(new MarkerOptions().position(BulEnBoy).icon(
				bulunduguyer));

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(BulEnBoy, 12.0f));

		LstVw = (ListView) findViewById(R.id.LstVw);
		LstVw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LstVw = (ListView) findViewById(R.id.LstVw);

				SecilenGps = (GpsRow) LstVw.getAdapter().getItem(position);

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

		BtnAyrintiBilgi.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (SecilenGps != null) {
					Intent myIntent = new Intent(MainActivity.this, Detay.class);
					myIntent.putExtra("SecilenID", SecilenGps.OtoparkKodu);
					startActivity(myIntent);
				} else {
					MHata Sonuc = new MHata();
					Sonuc.setBASLIK("Gps");
					Sonuc.setHATAMI(true);
					Sonuc.setMESAJ("Otopark Seçiniz !!");
					Sonuc.GosterMesajToast(MainActivity.this);
					return;
				}

			}
		});

		BtnYolTarifi.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (SecilenGps != null) {
					ArrayList<String> EnBoy = new ArrayList<String>();
					EnBoy = MSabit.MOtoparkBilgi
							.GetirGpsEnBoy(SecilenGps.OtoparkKodu);
					GitEnBoy = new LatLng(Double.parseDouble(EnBoy.get(0)),
							Double.parseDouble(EnBoy.get(1)));

					markerPoints = new ArrayList<LatLng>();

					markerPoints.add(BulEnBoy);
					markerPoints.add(GitEnBoy);

					if (markerPoints.size() >= 2) {
						LatLng origin = markerPoints.get(0);
						LatLng dest = markerPoints.get(1);

						// Getting URL to the Google Directions API
						String url = getYolUrl(origin, dest);

						IndirTask indirTask = new IndirTask();

						indirTask.execute(url);
					}

					GitYer = map.addMarker(new MarkerOptions().position(
							GitEnBoy).icon(bulunduguyer));

					map.moveCamera(CameraUpdateFactory.newLatLngZoom(GitEnBoy,
							14.0f));
				} else {
					MHata Sonuc = new MHata();
					Sonuc.setBASLIK("Gps");
					Sonuc.setHATAMI(true);
					Sonuc.setMESAJ("Otopark Seçiniz !!");
					Sonuc.GosterMesajToast(MainActivity.this);
					return;
				}

			}
		});

		GetirOtoparkVeri();
	}

	@SuppressLint("DefaultLocale")
	public void AramaYap(String OtoparkAdi) {
		ArrayList<GpsRow> Otoparklar = new ArrayList<GpsRow>();
		try {
			SQLiteDatabase DtBs = MSabit.MDbBaglanti.getReadableDatabase();
			String SorguCumle = "SELECT OTOPARKADI, KAPASITE, OTOPARKKODU"
					+ " FROM TBLOTOPARKBILGI ";

			if (!OtoparkAdi.equals("")) {
				SorguCumle += "WHERE OTOPARKADI LIKE '%"
						+ OtoparkAdi.toLowerCase() + "%' OR OTOPARKADI LIKE '%"
						+ OtoparkAdi.toUpperCase() + "%'";
			}

			Cursor Crs = DtBs.rawQuery(SorguCumle, null);
			while (Crs.moveToNext()) {
				GpsRow gpsRow = new GpsRow();
				gpsRow.OtoparkAdi = Crs.getString(0);
				gpsRow.OtoparkTipi = Crs.getString(1);
				gpsRow.OtoparkKodu = Crs.getString(2);
				Otoparklar.add(gpsRow);
			}
			DtBs.close();

			ListAdapter Adapter = new ListAdapter(this, this,
					R.layout.listrow_gps, Otoparklar.toArray(),
					ListAdapter.IslemTipi.OTOPARKBILGI);
			LstVw.setAdapter(Adapter);
		} catch (Exception e) {
			MHata Sonuc = new MHata();
			Sonuc.setBASLIK("Giriþ");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Giriþ Baþarýsýz!");
			Sonuc.GosterMesajToast(MainActivity.this);
		}
	}

	public void YukleOtoPark() {

		try {

			SQLiteDatabase DtBs = MSabit.MDbBaglanti.getReadableDatabase();
			String SorguCumle = "SELECT ENLEM, BOYLAM, OTOPARKADI FROM TBLOTOPARKBILGI";
			Cursor Crs = DtBs.rawQuery(SorguCumle, null);

			while (Crs.moveToNext()) {
				SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
						.findFragmentById(R.id.map);

				map = fm.getMap();

				BitmapDescriptor gidilecekyer = BitmapDescriptorFactory
						.fromResource(R.drawable.gidilecekyer);

				GitEnBoy = new LatLng(Double.parseDouble(Crs.getString(0)),
						Double.parseDouble(Crs.getString(1)));

				GitYer = map.addMarker(new MarkerOptions().position(GitEnBoy)
						.title("OtoPark").snippet(Crs.getString(2))
						.icon(gidilecekyer));
			}
			DtBs.close();
		} catch (Exception e) {
			MHata Sonuc = new MHata();
			Sonuc.setBASLIK("");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("");
			Sonuc.GosterMesajToast(MainActivity.this);
			return;
		}
	}

	public void GetirOtoparkVeri() {
		ArrayList<OtoparkBilgi> OtoparkGpsler = new ArrayList<OtoparkBilgi>();
		ArrayList<GpsRow> GpsRowlar = new ArrayList<GpsRow>();
		try {
			OtoparkGpsler = MSabit.MOtoparkBilgi.GetirOtoparkBilgiler();
			for (int i = 0; i < OtoparkGpsler.size(); i++) {
				GpsRow GpsRows = new GpsRow();
				GpsRows.OtoparkKodu = OtoparkGpsler.get(i).getOtoparkKodu();
				GpsRows.OtoparkAdi = OtoparkGpsler.get(i).getOtoparkAd();
				GpsRows.OtoparkTipi = OtoparkGpsler.get(i).getKapasite();
				GpsRowlar.add(GpsRows);
			}

			ListAdapter Adapter = new ListAdapter(this, this,
					R.layout.listrow_gps, GpsRowlar.toArray(),
					ListAdapter.IslemTipi.OTOPARKBILGI);
			LstVw.setAdapter(Adapter);

		} catch (Exception e) {
			MHata Sonuc = new MHata();
			Sonuc.setBASLIK("Otopark Bilgi");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Otopark Bilgilari Alma Ýþlemi Baþarýsýz!");
			Sonuc.GosterMesajToast(MainActivity.this);
			return;
		}
	}

	private String getYolUrl(LatLng origin, LatLng dest) {

		String str_origin = "origin=" + origin.latitude + ","
				+ origin.longitude;

		String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

		String sensor = "sensor=false";

		String parameters = str_origin + "&" + str_dest + "&" + sensor;

		String output = "json";

		String url = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + parameters;

		return url;
	}

	private String YukleUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);

			urlConnection = (HttpURLConnection) url.openConnection();

			urlConnection.connect();

			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();

			br.close();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}
		return data;
	}

	private class IndirTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... url) {

			String data = "";

			try {
				data = YukleUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			ParserTask parserTask = new ParserTask();

			// Invokes the thread for parsing the JSON data
			parserTask.execute(result);

		}
	}

	private class ParserTask extends
			AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

		@Override
		protected List<List<HashMap<String, String>>> doInBackground(
				String... jsonData) {

			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;

			try {
				jObject = new JSONObject(jsonData[0]);
				DirectionsJSONParser parser = new DirectionsJSONParser();

				routes = parser.parse(jObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return routes;
		}

		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> result) {

			ArrayList<LatLng> points = null;
			PolylineOptions lineOptions = null;

			String distance = "";
			String duration = "";

			if (result.size() < 1) {
				Toast.makeText(getBaseContext(), "No Points",
						Toast.LENGTH_SHORT).show();
				return;
			}

			for (int i = 0; i < result.size(); i++) {
				points = new ArrayList<LatLng>();
				lineOptions = new PolylineOptions();

				List<HashMap<String, String>> path = result.get(i);

				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);

					if (j == 0) {
						distance = (String) point.get("distance");
						continue;
					} else if (j == 1) {
						duration = (String) point.get("duration");
						continue;
					}

					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);

					points.add(position);
				}

				lineOptions.addAll(points);
				lineOptions.width(3);
				lineOptions.color(Color.BLUE);

			}

			tvDistanceDuration.setText("Mesafe:" + distance + ", Süre:"
					+ MSabit.CevirSureTurkce(duration));

			map.addPolyline(lineOptions);
		}
	}

	public void GetirKonum() {
		try {

			Gps = new GPSTracker(MainActivity.this);

			if (Gps.canGetLocation()) {

				MSabit.MEnlem = Gps.getLatitude(); // enlem
				MSabit.MBoylam = Gps.getLongitude(); // boylam

			} else {

				Gps.showSettingsAlert();
			}
		} catch (Exception e) {
			MHata Sonuc = new MHata();
			Sonuc.setBASLIK("Gps Ayarlarý");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Gps Ayarlarý Alma Ýþlemi Baþarýsýz!");
			Sonuc.GosterMesajToast(MainActivity.this);
			return;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.aramayap)
				.getActionView();

		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		searchView.setIconifiedByDefault(true);

		SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextChange(String newText) {
				AramaYap(newText);
				return true;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				AramaYap(query);
				return true;
			}
		};

		searchView.setOnQueryTextListener(textChangeListener);

		return super.onCreateOptionsMenu(menu);
	}
	
	public void onStart() {
		super.onStart();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		if (id == R.id.aramayap) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
