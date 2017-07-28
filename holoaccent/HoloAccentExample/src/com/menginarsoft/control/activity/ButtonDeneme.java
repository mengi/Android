package com.menginarsoft.control.activity;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.menginarsoft.control.function.ListAdapter;
import com.menginarsoft.control.R;
import com.menginarsoft.control.function.Hata;
import com.menginarsoft.control.function.Sabit;
import com.menginarsoft.control.model.Role;
import com.menginarsoft.control.rowmodel.AlarmRow;
import com.negusoft.holoaccent.activity.AccentActivity;
import com.negusoft.holoaccent.dialog.AccentAlertDialog;

public class ButtonDeneme extends AccentActivity {

	private int mSaat, mDakika;

	Button BtnKaydet, BtnIlkTarihSec, BtnKur, BtnDuzenle, BtnSil, BtnDurdur;
	EditText TxtSaatAralik;
	Role Role = new Role();
	Hata Sonuc = new Hata();
	ListView LstVw;

	public AlarmRow SecilenKapatmaSuresi = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buttondeneme);

		BtnKaydet = (Button) findViewById(R.id.BtnKaydet);
		BtnKur = (Button) findViewById(R.id.BtnKur);
		BtnIlkTarihSec = (Button) findViewById(R.id.BtnIlkTarihSec);
		BtnDuzenle = (Button) findViewById(R.id.BtnDuzenle);
		BtnSil = (Button) findViewById(R.id.BtnSil);
		BtnDurdur = (Button) findViewById(R.id.BtnDurdur);

		TxtSaatAralik = (EditText) findViewById(R.id.TxtSaatAralik);

		BtnIlkTarihSec.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final Calendar c = Calendar.getInstance();
				mDakika = c.get(Calendar.MINUTE);
				mSaat = c.get(Calendar.HOUR_OF_DAY);
				TimePickerDialog tdp = new TimePickerDialog(ButtonDeneme.this,
						new OnTimeSetListener() {

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

								TxtSaatAralik.setText(Saat + "-" + Dakika);
							}
						}, mDakika, mSaat, android.text.format.DateFormat
								.is24HourFormat(ButtonDeneme.this));
				tdp.show();
			}
		});

		BtnKur.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("unused")
			@Override
			public void onClick(View v) {
				Deneme alarm = new Deneme();
				Context context = getApplicationContext();
				if (alarm != null) {
					alarm.SetAlarm(context);
				} else {
					Toast.makeText(context, "Ayarlama Baþarýsýz",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		BtnKaydet.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (SecilenKapatmaSuresi != null) {
					if (SecilenKapatmaSuresi.Id > 0) {
						Sabit.mTut = SecilenKapatmaSuresi.Id;
						Kaydet();
						Temizle();
						Listele();
						Sabit.mTut = -1;
					}
				} else {
					Kaydet();
					Temizle();
					Listele();
				}

			}
		});

		BtnDuzenle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (SecilenKapatmaSuresi == null) {
					Hata Sonuc = new Hata();
					Sonuc.setHATAMI(true);
					Sonuc.setBASLIK("Aralýk");
					Sonuc.setMESAJ("Zaman Aralýðý Seçiniz");
					Sonuc.GosterMesajToast(ButtonDeneme.this);
					return;
				} else {
					YukleZaman();
				}
			}
		});

		BtnDurdur.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("unused")
			@Override
			public void onClick(View v) {
				Deneme alarm = new Deneme();
				Context context = getApplicationContext();
				if (alarm != null) {
					alarm.CancelAlarm(context);
				} else {
					Toast.makeText(context, "Durdurma Ýþlemi Baþarýsýz!",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		BtnSil.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Hata Sonuc = new Hata();
				if (SecilenKapatmaSuresi != null) {
					AccentAlertDialog.Builder AlertDialogBuilder = new AccentAlertDialog.Builder(
							ButtonDeneme.this);
					AlertDialogBuilder.setTitle("Sil");
					AlertDialogBuilder
							.setMessage("Silmek Ýstediðinizden Emin misiniz ?");
					AlertDialogBuilder.setCancelable(false);
					AlertDialogBuilder.setIcon(R.drawable.ok);
					AlertDialogBuilder.setPositiveButton("Evet",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Hata Sonuc = new Hata();
									Sonuc = Role
											.AralikSil(SecilenKapatmaSuresi.Id);
									if (!Sonuc.getHATAMI()) {
										Temizle();
										Listele();
									} else {
										Sonuc = new Hata();
										Sonuc.setHATAMI(true);
										Sonuc.setBASLIK("Zaman Aralýðý");
										Sonuc.setMESAJ("Zaman Aralýðý Silinemedi");
										Sonuc.GosterMesajToast(ButtonDeneme.this);
										return;
									}
								}
							});

					AlertDialogBuilder.setNegativeButton("Hayýr",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}
							});
					AlertDialog AlertDialog = AlertDialogBuilder.create();
					AlertDialog.show();
				} else {
					Sonuc = new Hata();
					Sonuc.setBASLIK("Zaman Aralýðý");
					Sonuc.setMESAJ("Zaman Aralýðý Seçiniz");
					Sonuc.setHATAMI(true);
					Sonuc.GosterMesajToast(ButtonDeneme.this);
					return;
				}
			}
		});

		LstVw = (ListView) findViewById(R.id.LstVw);
		LstVw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LstVw = (ListView) findViewById(R.id.LstVw);
				SecilenKapatmaSuresi = (AlarmRow) LstVw.getAdapter().getItem(
						position);
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

		Listele();
	}

	public void Temizle() {
		TxtSaatAralik.setText("");
	}

	public void Listele() {
		Sabit.MRole = new Role();

		try {

			ArrayList<AlarmRow> AlarmRowlar = new ArrayList<AlarmRow>();

			SQLiteDatabase DtBs = Sabit.MDbBaglanti.getReadableDatabase();
			String SorguCumle = "SELECT ID, SAATARALIGI, TIP FROM TBLNROLE ORDER BY SAATARALIGI";

			Cursor Crs = DtBs.rawQuery(SorguCumle, null);
			int Sayac = 1;
			while (Crs.moveToNext()) {
				AlarmRow AlarmRow = new AlarmRow();
				AlarmRow.Id = Crs.getInt(0);
				AlarmRow.Aciklama = "Kapatma Zamaný: "
						+ Integer.toString(Sayac);
				AlarmRow.Saat = Crs.getString(1);
				Sayac++;
				AlarmRowlar.add(AlarmRow);
			}

			DtBs.close();

			ListAdapter Adapter = new ListAdapter(this, this,
					R.layout.listrow_alarm, AlarmRowlar.toArray(),
					ListAdapter.IslemTipi.KAPATMABILGI);
			LstVw.setAdapter(Adapter);

		} catch (Exception e) {
			Sonuc = new Hata();
			Sonuc.setBASLIK("Aralýk");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Baþarýsýz!");
			Sonuc.GosterMesajToast(ButtonDeneme.this);
			return;
		}
	}

	public void YukleZaman() {
		try {
			Sabit.MRole = new Role();
			Sabit.MRole
					.GetirRoleById(Integer.toString(SecilenKapatmaSuresi.Id));
			TxtSaatAralik.setText(Sabit.MRole.getSaatAraligi());

		} catch (Exception e) {
			Sonuc = new Hata();
			Sonuc.setBASLIK("Aralýk");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Baþarýsýz!");
			Sonuc.GosterMesajToast(ButtonDeneme.this);
			return;
		}
	}

	public Hata Kaydet() {
		try {
			if (TxtSaatAralik.getText().toString().equals("")) {
				Sonuc = new Hata();
				Sonuc.setBASLIK("Aralýk");
				Sonuc.setHATAMI(true);
				Sonuc.setMESAJ("Baþarýsýz!");
				return Sonuc;
			}
			Sabit.MRole = new Role();
			Sabit.MRole.setSaatAraligi(TxtSaatAralik.getText().toString());
			Sabit.MRole.setTip("1");

			Sonuc = Sabit.MRole.KaydetRole();
		} catch (Exception e) {
			Sonuc = new Hata();
			Sonuc.setBASLIK("Aralýk");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Baþarýsýz!");
		}
		return Sonuc;
	}
}
