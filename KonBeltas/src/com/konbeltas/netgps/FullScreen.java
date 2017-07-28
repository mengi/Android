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
import com.konbeltas.netgps.adapter.GPSTracker;
import com.konbeltas.netgps.func.MHata;
import com.konbeltas.netgps.func.MSabit;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class FullScreen extends FragmentActivity {
	GoogleMap map;

	ArrayList<LatLng> markerPoints;
	TextView tvDistanceDuration;

	@SuppressWarnings("unused")
	private Marker BulYer;
	@SuppressWarnings("unused")
	private Marker GitYer;

	private LatLng BulEnBoy;
	public LatLng GitEnBoy;

	GPSTracker Gps;
	public String GOtoparkKodu = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// remove title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_map);

		Intent MyIntent = getIntent();

		YukleOtoPark();

		// Icon Tanim
		final BitmapDescriptor bulunduguyer = BitmapDescriptorFactory
				.fromResource(R.drawable.bulunduguyer);

		// Konum Bilgisi
		try {

			Gps = new GPSTracker(FullScreen.this);

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

		try {
			GOtoparkKodu = MyIntent.getStringExtra("SecilenID");
			
			ArrayList<String> EnBoy = new ArrayList<String>();
			EnBoy = MSabit.MOtoparkBilgi.GetirGpsEnBoy(GOtoparkKodu);
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

			GitYer = map.addMarker(new MarkerOptions().position(GitEnBoy).icon(
					bulunduguyer));

			map.moveCamera(CameraUpdateFactory.newLatLngZoom(GitEnBoy, 14.0f));
		} catch (Exception e) {

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
			Sonuc.GosterMesajToast(FullScreen.this);
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

			map.addPolyline(lineOptions);
		}
	}

	public void GetirKonum() {
		try {

			Gps = new GPSTracker(FullScreen.this);

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
			Sonuc.GosterMesajToast(FullScreen.this);
			return;
		}
	}
}
