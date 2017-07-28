package com.menginarsoft.control.activity;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.menginarsoft.control.R;
import com.menginarsoft.control.function.Hata;
import com.menginarsoft.control.function.Sabit;
import com.negusoft.holoaccent.activity.AccentActivity;

/**
 * @author Menginar
 * 
 */

public class AnaKontrolActivity extends AccentActivity {

	public String Sicaklik = "F";
	public String TutDurum = "";
	ImageButton BtnAydinlatma, BtnSicaklik, BtnKapiyiAc, BtnHizArAz, BtnTumuAc,
			BtnTumuKapat;
	private int mYear, mMonth, mDay, mSaat, mDakika;
	private String TutTarihSaat = "";
	private String TutSaat = "";
	private int Btn = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anakontrol);

		BtnAydinlatma = (ImageButton) findViewById(R.id.BtnAydinlatma);
		BtnHizArAz = (ImageButton) findViewById(R.id.BtnHizArAz);
		BtnSicaklik = (ImageButton) findViewById(R.id.BtnSicaklik);
		BtnKapiyiAc = (ImageButton) findViewById(R.id.BtnKapiyiAc);
		BtnTumuAc = (ImageButton) findViewById(R.id.BtnTumuAc);
		BtnTumuKapat = (ImageButton) findViewById(R.id.BtnTumuKapat);

		BtnAydinlatma.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// led
				Intent myIntent = new Intent(AnaKontrolActivity.this,
						AyKontrolActivity.class);
				startActivity(myIntent);
			}
		});

		BtnHizArAz.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(AnaKontrolActivity.this,
						HizArAzActivity.class);
				startActivity(myIntent);
			}
		});

		BtnSicaklik.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Sicaklik MyTaskSicaklik = new Sicaklik();
				MyTaskSicaklik.execute(Sicaklik);

			}
		});

		BtnKapiyiAc.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Role

			}
		});

		BtnTumuAc.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final Calendar c = Calendar.getInstance();
				mYear = c.get(Calendar.YEAR);
				mMonth = c.get(Calendar.MONTH);
				mDay = c.get(Calendar.DAY_OF_MONTH);

				if (Btn == 0) {
					DatePickerDialog dpd = new DatePickerDialog(
							AnaKontrolActivity.this,
							new DatePickerDialog.OnDateSetListener() {
								@Override
								public void onDateSet(DatePicker view,
										int year, int monthOfYear,
										int dayOfMonth) {
									String YenimonthOfYear = Integer
											.toString(monthOfYear + 1);
									String YenidayOfYear = Integer
											.toString(dayOfMonth);

									if (YenimonthOfYear.length() == 1) {
										YenimonthOfYear = "0" + YenimonthOfYear;
									}

									if (YenidayOfYear.length() == 1) {
										YenidayOfYear = "0" + YenidayOfYear;
									}

									TutTarihSaat = year + "-" + YenimonthOfYear
											+ "-" + YenidayOfYear + "-"
											+ TutSaat;
									Sabit.TarihSaat = TutTarihSaat;
									Btn = 1;
								}
							}, mYear, mMonth, mDay);
					dpd.show();

					mDakika = c.get(Calendar.MINUTE);
					mSaat = c.get(Calendar.HOUR_OF_DAY);

					TimePickerDialog tdp = new TimePickerDialog(
							AnaKontrolActivity.this, new OnTimeSetListener() {

								@Override
								public void onTimeSet(TimePicker view,
										int hourOfDay, int minute) {

									String Saat = Integer.toString(hourOfDay);
									String Dakika = Integer.toString(minute);

									if (Dakika.length() == 1) {
										Dakika = "0" + Dakika;
									}

									if (Saat.length() == 1) {
										Saat = "0" + Saat;
									}

									TutSaat = Saat + "-" + Dakika;
								}
							}, mDakika, mSaat, android.text.format.DateFormat
									.is24HourFormat(AnaKontrolActivity.this));
					tdp.show();
				}

				if (Btn == 1) {
					if (!Sabit.TarihSaat.equals("")) {
						int TutSure = Sabit.KontrolLisansSuresi(Sabit.TarihSaat);
						if (Sabit.KontrolLisansSuresi(Sabit.TarihSaat) > 0) {
							Intent intent = new Intent(AnaKontrolActivity.this, MyBroadcastReceiver.class);

							PendingIntent pendingIntent = PendingIntent.getBroadcast(
									getApplicationContext(), 234324243, intent, 0);
							
							AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
							
							alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
									+ (TutSure * 1000), pendingIntent);
							
							Toast.makeText(AnaKontrolActivity.this, "Alarm set in " + Integer.toString(TutSure) + " seconds",
									Toast.LENGTH_LONG).show();
							Btn = 0;
						} else {
							Hata Sonuc = new Hata();
							Sonuc.setBASLIK("");
							Sonuc.setHATAMI(true);
							Sonuc.setMESAJ("Girilen Tarih Hatalý!");
							Sonuc.GosterMesajToast(AnaKontrolActivity.this);
							Btn = 0;
							return;
						}
					}
				}
			}
		});

		BtnTumuKapat.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(AnaKontrolActivity.this, ButtonDeneme.class);
				startActivity(myIntent);
			}
		});
	}

	private class Sicaklik extends AsyncTask<String, String, String> {
		String dstAddress = Sabit.MParamAyar.getIPADRES();
		int dstPort = Integer.parseInt(Sabit.MParamAyar.getPORT());

		protected String doInBackground(String... params) {

			Socket socket = null;
			OutputStream os = null;
			InputStream inputStream = null;

			try {
				socket = new Socket(dstAddress, dstPort);

				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
						1024);
				byte[] buffer = new byte[1024];

				int bytesRead;
				inputStream = socket.getInputStream();
				os = socket.getOutputStream();
				DataOutputStream dos = new DataOutputStream(os);
				dos.writeUTF(params[0]);

				while ((bytesRead = inputStream.read(buffer)) != 'Y') {
					byteArrayOutputStream.write(buffer, 0, bytesRead);
					if (byteArrayOutputStream.toString("UTF-8").length() == 5) {
						TutDurum = byteArrayOutputStream.toString("UTF-8");
						break;
					}
				}

				os.close();
				inputStream.close();
				socket.close();

			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				TutDurum = "UnknownHostException: " + e.toString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				TutDurum = "IOException: " + e.toString();
			}

			return TutDurum;
		}

		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(AnaKontrolActivity.this,
					"Ortam Sýcaklýðý : " + result, Toast.LENGTH_LONG).show();
		}
	}

}
