package com.chatt.demo;

import java.util.ArrayList;


import cn.pedant.SweetAlert.widget.SweetAlertDialog;
import com.chatt.demo.custom.RSabit;
import com.chatt.demo.model.MSiparis;
import com.chatt.demo.rw.model.SiparisRow;
import com.chatt.demo.utils.ListAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SiparisListe extends Activity {

	private static final int ALERT_DIALOG2 = 2;
	public SiparisRow SecilenSiparis = null;

	ListView LstVw;
	TextView TxtIndirim, TxtTutar;
	Button BtnEkle, BtnDuzenle, BtnSil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.urun_ekle);

		BtnEkle = (Button) findViewById(R.id.BtnEkle);
		BtnDuzenle = (Button) findViewById(R.id.BtnDuzenle);
		BtnSil = (Button) findViewById(R.id.BtnSil);

		TxtIndirim = (TextView) findViewById(R.id.TxtIndirim);
		TxtTutar = (TextView) findViewById(R.id.TxtTutar);

		setTitle("          " + RSabit.MasaAdi);

		BtnSil.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				RSabit.MSiparis = new MSiparis();
				if (SecilenSiparis != null) {
					if (RSabit.MSiparis.SiparisSil(RSabit.MasaAdi,
							SecilenSiparis.UrunAdi)) {
						SiparisListele();
					} else {
						new SweetAlertDialog(SiparisListe.this,
								SweetAlertDialog.WARNING_TYPE)
								.setTitleText("Oops...")
								.setContentText(
										"Siparis Silme Ýþlemi Baþarýsýz !!!")
								.show();
					}
				} else {
					new SweetAlertDialog(SiparisListe.this,
							SweetAlertDialog.WARNING_TYPE)
							.setTitleText("Oops...")
							.setContentText("Lütfen Siparis Seçiniz !!!")
							.show();
				}
			}
		});

		BtnDuzenle.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				if (SecilenSiparis != null) {
					showDialog(ALERT_DIALOG2);
					SiparisListele();
				} else {
					new SweetAlertDialog(SiparisListe.this,
							SweetAlertDialog.WARNING_TYPE)
							.setTitleText("Oops...")
							.setContentText("Lütfen Siparis Seçiniz !!!")
							.show();
				}
			}
		});

		BtnEkle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(SiparisListe.this,
						UrunUstListe.class);
				startActivity(myIntent);
				finish();
			}
		});

		LstVw = (ListView) findViewById(R.id.LstVw);
		LstVw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LstVw = (ListView) findViewById(R.id.LstVw);
				SecilenSiparis = (SiparisRow) LstVw.getAdapter().getItem(
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

		SiparisListele();
	}

	public void SiparisListele() {
		double Indirim = 0;
		double Toplam = 0;
		try {
			ArrayList<SiparisRow> Siparisler = new ArrayList<SiparisRow>();
			SQLiteDatabase DtBs = RSabit.OpDbBaglanti.getReadableDatabase();
			String SorguCumle = "SELECT URUNADI, MASAADI, FIYAT, MIKTAR, AKTARILDIMI FROM TBL_NSIPARIS WHERE MASAADI=?";
			Cursor Crs = DtBs.rawQuery(SorguCumle,
					new String[] { RSabit.MasaAdi });

			while (Crs.moveToNext()) {
				SiparisRow siparisRow = new SiparisRow();

				siparisRow.UrunAdi = Crs.getString(0);
				siparisRow.MasaAdi = Crs.getString(1);
				siparisRow.Toplam = Double.toString(Crs.getDouble(2)
						* Double.parseDouble(Crs.getString(3)))
						+ " TL";
				siparisRow.Miktar = Crs.getString(3);
				Toplam += Crs.getDouble(2)
						* Double.parseDouble(Crs.getString(3));
				
				if (Crs.getString(4).equals("0")) {
					siparisRow.SiparisDurum = "Gönderilmedi";
				} else if(Crs.getString(4).equals("1")) {
					siparisRow.SiparisDurum = "Bekleniyor";
				}
				
				Siparisler.add(siparisRow);
			}

			TxtIndirim.setText(Double.toString(Indirim) + " TL");
			TxtTutar.setText(Double.toString(Toplam) + " TL");
			ListAdapter adapter = new ListAdapter(this, this,
					R.layout.listrow_siparis_durum, Siparisler.toArray(),
					ListAdapter.IslemTipi.SIPARISDURUM);
			LstVw.setAdapter(adapter);
			DtBs.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@SuppressLint("InflateParams")
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog;
		AlertDialog.Builder builder;
		final LayoutInflater inflater = SiparisListe.this.getLayoutInflater();

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		final View dialogView = inflater
				.inflate(R.layout.siparis_duzenle, null);
		switch (id) {
		case ALERT_DIALOG2:
			builder = new AlertDialog.Builder(this);
			builder.setView(dialogView)
					.setCancelable(false)
					.setPositiveButton("Giriþ",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									EditText Miktar = (EditText) dialogView
											.findViewById(R.id.TxtMiktar);
									RSabit.MSiparis = new MSiparis();

									if (RSabit.MSiparis.GuncelMiktar(
											SecilenSiparis.UrunAdi, Miktar
													.getText().toString(),
											SecilenSiparis.MasaAdi)) {
										SiparisListele();
										Miktar.setText("");
									}

								}
							})
					.setNegativeButton("Çýkýþ",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {

									EditText Miktar = (EditText) dialogView
											.findViewById(R.id.TxtMiktar);

									Miktar.setText("");
								}
							});

			dialog = builder.create();
			break;
		default:
			dialog = null;
		}
		return dialog;

	}
}
