package com.menginarsoft.control.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.menginarsoft.control.R;
import com.menginarsoft.control.function.Hata;
import com.menginarsoft.control.function.Sabit;
import com.menginarsoft.control.model.ParamAyar;
import com.negusoft.holoaccent.activity.AccentActivity;
import com.negusoft.holoaccent.dialog.AccentAlertDialog;

public class GirisActivity extends AccentActivity {

	EditText TxtIpAdres, TxtPortNumara, TxtParola;
	Button BtnGiris;
	Hata Sonuc = new Hata();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_giris);

		TxtIpAdres = (EditText) findViewById(R.id.TxtIpAdres);
		TxtPortNumara = (EditText) findViewById(R.id.TxtPortNumara);
		TxtParola = (EditText) findViewById(R.id.TxtParola);

		Sabit.KontrolLisansSuresi("");

		boolean Durum = (Sabit.MParamAyar.getID() != -1);

		if (Durum) {
			DegerAta();
		}

		BtnGiris = (Button) findViewById(R.id.BtnGiris);

		BtnGiris.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Sonuc = new Hata();

				boolean Durum = (Sabit.MParamAyar.getID() != -1);

				if (!Durum) {
					if (!Kontrol().getHATAMI()) {
						Kayit();
					} else {
						Kontrol().GosterMesajToast(GirisActivity.this);
					}
				}

				Sonuc = Kontrol();
				if (!Sonuc.getHATAMI()) {

					Intent myIntent = new Intent(GirisActivity.this,
							AnaKontrolActivity.class);
					startActivity(myIntent);
				} else {
					Sonuc.GosterMesajToast(GirisActivity.this);
				}
			}
		});
	}

	public void DegerAta() {
		Sabit.MParamAyar.GetirParamAyar();
		TxtIpAdres.setText(Sabit.MParamAyar.getIPADRES());
		TxtPortNumara.setText(Sabit.MParamAyar.getPORT());
	}

	public Hata Kayit() {
		try {
			Sabit.MParamAyar = new ParamAyar();
			Sabit.MParamAyar.setIPADRES(TxtIpAdres.getText().toString());
			Sabit.MParamAyar.setPORT(TxtPortNumara.getText().toString());

			Sabit.MParamAyar.KaydetParamAyar();

		} catch (Exception e) {
			Sonuc = new Hata();
			Sonuc.setBASLIK("Giriþ");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Ýþlem Baþarýsýz!");
		}

		return Sonuc;
	}

	public Hata Kontrol() {
		try {
			if (TxtIpAdres.getText().toString().equals("")) {
				Sonuc = new Hata();
				Sonuc.setBASLIK("Giriþ");
				Sonuc.setHATAMI(true);
				Sonuc.setMESAJ("Gerekli Alanlarý Doldurunuz!");
			}

			if (TxtPortNumara.getText().toString().equals("")) {
				Sonuc = new Hata();
				Sonuc.setBASLIK("Giriþ");
				Sonuc.setHATAMI(true);
				Sonuc.setMESAJ("Gerekli Alanlarý Doldurunuz!");
			}

			if (TxtParola.getText().toString().equals("")) {
				Sonuc = new Hata();
				Sonuc.setBASLIK("Giriþ");
				Sonuc.setHATAMI(true);
				Sonuc.setMESAJ("Gerekli Alanlarý Doldurunuz!");
			}

			if (!TxtParola.getText().toString()
					.equals(Sabit.MKullanici.getParola())) {
				Sonuc = new Hata();
				Sonuc.setBASLIK("Giriþ");
				Sonuc.setHATAMI(true);
				Sonuc.setMESAJ("Parola Yanlýþ!");
				TxtParola.setText("");
			}

		} catch (Exception e) {
			Sonuc = new Hata();
			Sonuc.setBASLIK("Giriþ");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Ýþlem Baþarýsýz!");
		}

		return Sonuc;
	}

	@Override
	public void onBackPressed() {
		new AccentAlertDialog.Builder(this).setTitle("NetHome Çýkýþ")
				.setMessage("Uygulamadan Çýkmak Ýstediðinize Emin Misiniz ?")
				.setNegativeButton("Hayýr", null)
				.setPositiveButton("Evet", new OnClickListener() {

					public void onClick(DialogInterface arg0, int arg1) {
						GirisActivity.super.onBackPressed();
					}
				}).create().show();
	}
}
