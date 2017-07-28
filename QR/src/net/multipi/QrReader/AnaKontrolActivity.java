package net.multipi.QrReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.multipi.QrReader.func.Hata;
import net.multipi.QrReader.func.Sabit;
import net.multipi.QrReader.func.XMLParser;

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
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParserException;

import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

public class AnaKontrolActivity extends Activity {

	private static String NAMESPACE = "http://tempuri.org/";
	private static String URL = "http://192.168.1.34:1212/PlasiyerServis.asmx";
	final static String URI = "http://sectortarim.net/b2b/json_fatura.php";
	JSONArray json;

	RelativeLayout BtnGonder, BtnAl, BtnBSiparis, BtnOSiparis, BtnAyar, BtnPdf;

	public static int Position = 0;
	public Handler countHandler;
	public Context ContIslem = this;

	public static int MaxProgress = 0;
	public static Boolean MaxHesapla = true;
	public static String FaturaUstId = "";
	public static String SorguCumle = "";
	ProgressDialog barProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anamenu);

		BtnGonder = (RelativeLayout) findViewById(R.id.BtnGonder);
		BtnAl = (RelativeLayout) findViewById(R.id.BtnAl);
		BtnBSiparis = (RelativeLayout) findViewById(R.id.BtnBSiparis);
		BtnOSiparis = (RelativeLayout) findViewById(R.id.BtnOSiparis);
		BtnAyar = (RelativeLayout) findViewById(R.id.BtnAyar);
		BtnPdf = (RelativeLayout) findViewById(R.id.BtnPdf);

		barProgressDialog = new ProgressDialog(AnaKontrolActivity.this);

		barProgressDialog.setTitle("Lütfen Bekleyiniz ...");
		barProgressDialog.setMessage("Parametreler Alýnýyor ...");
		barProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		barProgressDialog.setProgress(0);
		barProgressDialog.setMax(20);

		BtnGonder.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AsyncCallWS2 ParametreAl = new AsyncCallWS2();
				ParametreAl.TempTablo = "TBLNFATURAHAR";
				ParametreAl.TempMethodName = "GetB2BFaturaHar";
				ParametreAl.execute();
			}
		});

		BtnAl.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				AsyncCallWS ParametreAl = new AsyncCallWS();
				ParametreAl.TempTablo = "TBLNFATURAUST";
				ParametreAl.TempMethodName = "GetB2BFatura";
				ParametreAl.execute();
			}
		});

		// Bekleyen = 0, Oanylanan = 1
		BtnBSiparis.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (KontrolBSiparis() > 0) {
					Intent myIntent = new Intent(AnaKontrolActivity.this,
							FaturaListeActivity.class);
					myIntent.putExtra("Siparis", "0");
					startActivity(myIntent);
				}

				if (KontrolBSiparis() == 0) {
					Hata Sonuc = new Hata();
					Sonuc.setBASLIK("Bekleyen Sipariþler");
					Sonuc.setHATAMI(true);
					Sonuc.setMESAJ("Bekleyen Sipariþ Bulunmamaktadýr!");
					Sonuc.GosterMesajDialog(AnaKontrolActivity.this);
				}

			}
		});

		BtnOSiparis.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (KontrolOSiparis() > 0) {
					Intent myIntent = new Intent(AnaKontrolActivity.this,
							FaturaListeActivity.class);
					myIntent.putExtra("Siparis", "1");
					startActivity(myIntent);
				}

				if (KontrolOSiparis() == 0) {
					Hata Sonuc = new Hata();
					Sonuc.setBASLIK("Onaylanan Sipariþler");
					Sonuc.setHATAMI(true);
					Sonuc.setMESAJ("Onaylanan Sipariþ Bulunmamaktadýr!");
					Sonuc.GosterMesajDialog(AnaKontrolActivity.this);
				}
			}
		});

		BtnAyar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(AnaKontrolActivity.this,
						KayitActivity.class);
				startActivity(myIntent);
				finish();
			}
		});

		BtnPdf.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AsyncCallWS2 TaskCariHarYeni = new AsyncCallWS2();
				TaskCariHarYeni.TempTablo = "TBLNFATURAHAR";
				TaskCariHarYeni.TempMethodName = "GetB2BFaturaHar";
				TaskCariHarYeni.execute();
			}
		});

	}

	public int KontrolBSiparis() {
		int Sayac = 0;
		try {
			SQLiteDatabase DtBs = Sabit.MDbBaglanti.getReadableDatabase();
			SorguCumle = "SELECT COUNT(*) AS SAYAC FROM TBLNFATURAUST WHERE faturakesen='0'";
			Cursor Crs = DtBs.rawQuery(SorguCumle, null);

			while (Crs.moveToNext()) {
				Sayac = Crs.getInt(0);
			}

			DtBs.close();
		} catch (Exception e) {
			Hata Sonuc = new Hata();
			Sonuc.setBASLIK("Kontrol Sipariþ");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Bekleyen Sipariþ Sayýsý Hesaplanamadý!");
		}

		return Sayac;
	}

	public int KontrolOSiparis() {
		int Sayac = 0;
		try {
			SQLiteDatabase DtBs = Sabit.MDbBaglanti.getReadableDatabase();
			SorguCumle = "SELECT COUNT(*) AS SAYAC FROM TBLNFATURAUST WHERE faturakesen='1'";
			Cursor Crs = DtBs.rawQuery(SorguCumle, null);

			while (Crs.moveToNext()) {
				Sayac = Crs.getInt(0);
			}

			DtBs.close();
		} catch (Exception e) {
			Hata Sonuc = new Hata();
			Sonuc.setBASLIK("Kontrol Sipariþ");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Onaylanan Sipariþ Sayýsý Hesaplanamadý!");
		}

		return Sayac;
	}

	@SuppressLint("NewApi")
	private class AsyncCallWS extends AsyncTask<Void, Integer, Hata> {

		public String TempTablo;
		public String TempMethodName;

		@Override
		protected void onPreExecute() {
			barProgressDialog.show();
		}

		protected Hata doInBackground(Void... params) {

			Hata IslemSonuc = new Hata();
			IslemSonuc.setBASLIK("Aktarým Ýþlemi");

			List<ContentValues> myData = null;
			String SOAP_ACTION = "http://tempuri.org/" + TempMethodName;
			/*
			 * if (CSabit.URL.equals(CSabit.OpParamAyar.getDISIP())) { URL =
			 * CSabit.OpParamAyar.getDISIP(); } else { URL =
			 * CSabit.OpParamAyar.getICIP(); }
			 */

			SoapObject requestTest = new SoapObject("http://tempuri.org/",
					"TestEt");
			SoapSerializationEnvelope envelopeTest = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelopeTest.dotNet = true;
			envelopeTest.setOutputSoapObject(requestTest);
			HttpTransportSE androidHttpTransportTest = new HttpTransportSE(URL);
			try {
				androidHttpTransportTest.call("http://tempuri.org/TestEt",
						envelopeTest);
			} catch (IOException e) {
				IslemSonuc.setMESAJ("Time out");
				IslemSonuc.setHATAMI(true);
				return IslemSonuc;
			} catch (XmlPullParserException e) {
				IslemSonuc.setMESAJ("Time out");
				IslemSonuc.setHATAMI(true);
				return IslemSonuc;
			}

			try {
				SoapObject request = new SoapObject(NAMESPACE, TempMethodName);
				PropertyInfo SiparisKodu = new PropertyInfo();
				SiparisKodu.setName("SiparisAlanId");
				SiparisKodu.setValue(Sabit.Ayar.getKullaniciKodu());
				SiparisKodu.setType(string.class);
				request.addProperty(SiparisKodu);

				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.dotNet = true;

				envelope.setOutputSoapObject(request);

				HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

				androidHttpTransport.call(SOAP_ACTION, envelope);
				SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

				String TempXmlCumle = response.toString();

				if (TempXmlCumle != null) {
					try {
						BufferedReader br = new BufferedReader(
								new StringReader(TempXmlCumle));
						InputSource is = new InputSource(br);

						// Create XML Parser
						XMLParser parser = new XMLParser();
						parser.TabloAdi = TempTablo;
						parser.SonElement = "Table";

						SAXParserFactory factory = SAXParserFactory
								.newInstance();
						SAXParser sp = factory.newSAXParser();

						XMLReader reader = sp.getXMLReader();
						reader.setContentHandler(parser);
						reader.parse(is);

						myData = parser.Degiskenler;

						if (myData != null) {

							SQLiteDatabase DtBs1 = Sabit.MDbBaglanti
									.getWritableDatabase();
							DtBs1.delete(TempTablo, null, null);
							DtBs1.close();

							MaxHesapla = true;
							publishProgress(myData.size());
							MaxHesapla = false;
							int Pos = 0;
							for (ContentValues xmlRowData : myData) {
								Pos++;
								publishProgress(Pos);
								try {
									SQLiteDatabase DtBs = Sabit.MDbBaglanti
											.getWritableDatabase();
									DtBs.insert(TempTablo, null, xmlRowData);
									DtBs.close();
								} catch (Exception e) {
									IslemSonuc
											.setMESAJ("Veri Kaydedilirken Hata Oluþtu");
									IslemSonuc.setHATAMI(true);
									return IslemSonuc;
								}

							}
						}
					} catch (Exception e) {
						IslemSonuc.setMESAJ("Veriler Merkezden Alýnamadý");
						IslemSonuc.setHATAMI(true);
						return IslemSonuc;
					}
				}

			} catch (Exception e) {
				IslemSonuc.setMESAJ("Parametre Bilgileriniz Kontrol Ediniz");
				IslemSonuc.setHATAMI(true);
				return IslemSonuc;
			}
			IslemSonuc.setMESAJ("Aktarým Baþarýlý Bir Þekilde Tamamlandý");
			IslemSonuc.setHATAMI(false);
			return IslemSonuc;
		}

		protected void onProgressUpdate(Integer... progress) {
			AyarlaProgress(progress[0]);
		}

		@Override
		protected void onPostExecute(Hata result) {
			barProgressDialog.dismiss();
			result.GosterMesajDialog(AnaKontrolActivity.this);
		}
	}

	private class AsyncCallWS2 extends AsyncTask<Void, Integer, Hata> {

		public String TempTablo;
		public String TempMethodName;

		@Override
		protected void onPreExecute() {
			barProgressDialog.show();
		}

		@Override
		protected Hata doInBackground(Void... params) {

			List<ContentValues> myData = null;
			String SOAP_ACTION = "http://tempuri.org/" + TempMethodName;

			Hata IslemSonuc = new Hata();
			IslemSonuc.setBASLIK("Aktarým Ýþlemi");

			/*
			 * if (CSabit.URL.equals(Sabit.OpParamAyar.getDISIP())) { URL =
			 * CSabit.OpParamAyar.getDISIP(); } else { URL =
			 * CSabit.OpParamAyar.getICIP(); }
			 */

			SoapObject requestTest = new SoapObject("http://tempuri.org/",
					"TestEt");
			SoapSerializationEnvelope envelopeTest = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelopeTest.dotNet = true;
			envelopeTest.setOutputSoapObject(requestTest);
			HttpTransportSE androidHttpTransportTest = new HttpTransportSE(URL);
			try {
				androidHttpTransportTest.call("http://tempuri.org/TestEt",
						envelopeTest);
			} catch (IOException e) {
				IslemSonuc.setMESAJ("Time out");
				IslemSonuc.setHATAMI(true);
				return IslemSonuc;
			} catch (XmlPullParserException e) {
				IslemSonuc.setMESAJ("Time out");
				IslemSonuc.setHATAMI(true);
				return IslemSonuc;
			}

			try {

				SQLiteDatabase DtBs3 = Sabit.MDbBaglanti.getReadableDatabase();
				String SorguCumle = "SELECT faturaID FROM TBLNFATURAUST WHERE 1=1";
				Cursor Crs = DtBs3.rawQuery(SorguCumle, null);

				while (Crs.moveToNext()) {
					FaturaUstId = Crs.getString(0);

					SoapObject request = new SoapObject(NAMESPACE,
							TempMethodName);

					PropertyInfo FaturaKodProp = new PropertyInfo();
					FaturaKodProp.setName("FaturaUstId");
					FaturaKodProp.setValue(FaturaUstId);
					FaturaKodProp.setType(string.class);
					request.addProperty(FaturaKodProp);

					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
							SoapEnvelope.VER11);
					envelope.dotNet = true;

					envelope.setOutputSoapObject(request);

					HttpTransportSE androidHttpTransport = new HttpTransportSE(
							URL);

					androidHttpTransport.call(SOAP_ACTION, envelope);
					SoapPrimitive response = (SoapPrimitive) envelope
							.getResponse();
					String TempXmlCumle = response.toString();

					if (TempXmlCumle != null) {
						try {
							BufferedReader br = new BufferedReader(
									new StringReader(TempXmlCumle));
							InputSource is = new InputSource(br);

							// Create XML Parser
							XMLParser parser = new XMLParser();
							parser.TabloAdi = TempTablo;
							parser.SonElement = "Table";

							SAXParserFactory factory = SAXParserFactory
									.newInstance();
							SAXParser sp = factory.newSAXParser();

							XMLReader reader = sp.getXMLReader();
							reader.setContentHandler(parser);
							reader.parse(is);

							myData = parser.Degiskenler;

							if (myData != null) {

								SQLiteDatabase DtBs1 = Sabit.MDbBaglanti
										.getWritableDatabase();
								DtBs1.delete(TempTablo, "faturaID=?",
										new String[] { FaturaUstId });
								DtBs1.close();

								MaxHesapla = true;
								publishProgress(myData.size());
								MaxHesapla = false;
								int Pos = 0;
								for (ContentValues xmlRowData : myData) {
									Pos++;
									publishProgress(Pos);
									try {
										SQLiteDatabase DtBs = Sabit.MDbBaglanti
												.getWritableDatabase();
										DtBs.insert(TempTablo, null, xmlRowData);
										DtBs.close();
									} catch (Exception e) {
										IslemSonuc
												.setMESAJ("Veri Kaydedilirken Hata Oluþtu");
										IslemSonuc.setHATAMI(true);
										return IslemSonuc;
									}

								}
							}
						} catch (Exception e) {
							IslemSonuc.setMESAJ("Veriler Merkezden Alýnamadý");
							IslemSonuc.setHATAMI(true);
							return IslemSonuc;
						}
					}
				}
				DtBs3.close();
			} catch (Exception e) {
				IslemSonuc.setMESAJ("Parametre Bilgileriniz Kontrol Ediniz");
				IslemSonuc.setHATAMI(true);
				return IslemSonuc;
			}
			IslemSonuc.setMESAJ("Aktarým Baþarýlý Bir Þekilde Tamamlandý");
			IslemSonuc.setHATAMI(false);
			return IslemSonuc;

		}

		protected void onProgressUpdate(Integer... progress) {
			AyarlaProgress(progress[0]);
		}

		@Override
		protected void onPostExecute(Hata result) {
			barProgressDialog.dismiss();
			result.GosterMesajDialog(AnaKontrolActivity.this);
		}
	}

	public void AyarlaProgress(Integer Deger) {
		if (MaxHesapla) {
			barProgressDialog.setMax(Deger);
			barProgressDialog.setProgress(0);
		} else {
			barProgressDialog.setProgress(Deger);
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

	public class Game extends AsyncTask<Void, Integer, Hata> {

		@Override
		protected Hata doInBackground(Void... params) {
			Hata Sonuc = new Hata();
			try {
				json = readGameParks();
				
				
				int Sayac = json.length();
				
				for (int i = 0; i < json.length(); i++) {
					JSONObject JsonBDizi = json.getJSONObject(i);

					Sonuc = new Hata();
					boolean KayitDurum = false;

					if (!KayitDurum) {

						Sabit.FaturaUst.setFaturaID(Integer.parseInt(JsonBDizi
								.optString("userID").toString()));
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
		protected void onPostExecute(Hata result) {
			result.GosterMesajToast(AnaKontrolActivity.this);
		}
	}
}
