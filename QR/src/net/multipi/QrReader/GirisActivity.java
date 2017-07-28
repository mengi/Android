package net.multipi.QrReader;

import net.multipi.QrReader.database.ODataBase;
import net.multipi.QrReader.func.Hata;
import net.multipi.QrReader.func.Sabit;
import net.multipi.QrReader.model.KullaniciAyar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GirisActivity extends Activity {

	EditText TxtKullaniciAdi, TxtParola;
	Button BtnGiris;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_giris);
		
		Sabit.MDbBaglanti = new ODataBase(this);
		Sabit.Ayar = new KullaniciAyar();
		Sabit.Ayar.GetirKullaniciAyar();

		TxtKullaniciAdi = (EditText) findViewById(R.id.TxtKullaniciAdi);
		TxtParola = (EditText) findViewById(R.id.TxtParola);

		BtnGiris = (Button) findViewById(R.id.BtnGiris);

		BtnGiris.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!Kontrol().getHATAMI()) {
					Intent myIntent = new Intent(GirisActivity.this,
							AnaKontrolActivity.class);
					startActivity(myIntent);
					Temizle();
				} else {
					Kontrol().GosterMesajDialog(GirisActivity.this);
				}

			}
		});
	}

	public void Temizle() {
		TxtKullaniciAdi.setText("");
		TxtParola.setText("");
	}

	public Hata Kontrol() {
		Sabit.Ayar = new KullaniciAyar();
		Hata Sonuc = new Hata();
		try {
			Sabit.Ayar.GetirKullaniciAyar();

			if (!TxtKullaniciAdi.getText().toString()
					.equals(Sabit.Ayar.getKullaniciAdi())) {
				Sonuc = new Hata();
				Sonuc.setBASLIK("Giriþ");
				Sonuc.setHATAMI(true);
				Sonuc.setMESAJ("Giriþ Baþarýsýz!");
			}

			if (!TxtParola.getText().toString().equals(Sabit.Ayar.getParola())) {
				Sonuc = new Hata();
				Sonuc.setBASLIK("Giriþ");
				Sonuc.setHATAMI(true);
				Sonuc.setMESAJ("Giriþ Baþarýsýz!");
			}

		} catch (Exception e) {
			Sonuc = new Hata();
			Sonuc.setBASLIK("Giriþ");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Giriþ Baþarýsýz!");
		}
		return Sonuc;
	}

	@Override
	public void onResume() {

		Sabit.Ayar.GetirKullaniciAyar();
		boolean Durum = (Sabit.Ayar.getId() != -1);

		if (!Durum) {
			Intent myIntent = new Intent(GirisActivity.this,
					KayitActivity.class);
			startActivity(myIntent);
		}
		super.onResume();
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this).setTitle("Qr Çýkýþ")
				.setMessage("Uygulamadan Çýkmak Ýstediðinize Emin Misiniz ?")
				.setNegativeButton("Hayýr", null)
				.setIcon(R.drawable.ic_launcher)
				.setPositiveButton("Evet", new OnClickListener() {

					public void onClick(DialogInterface arg0, int arg1) {
						GirisActivity.super.onBackPressed();
					}
				}).create().show();
	}

}
