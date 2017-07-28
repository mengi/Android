package com.menginarsoft.control.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.menginarsoft.control.R;
import com.menginarsoft.control.function.Hata;
import com.menginarsoft.control.function.Sabit;
import com.menginarsoft.control.model.Kullanici;
import com.negusoft.holoaccent.activity.AccentActivity;

public class KayitActivity extends AccentActivity {
	
	EditText TxtAdSoyad, TxtParola, TxtParolaTekrar;
	Button BtnKayit;
	Hata Sonuc = new Hata();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kayit);
		
		TxtAdSoyad = (EditText) findViewById(R.id.TxtAdSoyad);
		TxtParola = (EditText) findViewById(R.id.TxtParola);
		TxtParolaTekrar = (EditText) findViewById(R.id.TxtParolaTekrar);
		
		BtnKayit = (Button) findViewById(R.id.BtnKayit);
		
		BtnKayit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (Kayit().getHATAMI()){
					Kayit().GosterMesajToast(KayitActivity.this);
				} else {
					Temizle();
					Intent myIntent = new Intent(KayitActivity.this, GirisActivity.class);
					startActivity(myIntent);
				}
			}
		});
	}
	
	public void Temizle() {
		TxtAdSoyad.setText("");
		TxtParola.setText("");
		TxtParolaTekrar.setText("");
	}
	
	public Hata Kayit() {
		try {
			if (TxtAdSoyad.getText().toString().equals("")) {
				Sonuc = new Hata();
				Sonuc.setBASLIK("Kayýt");
				Sonuc.setHATAMI(true);
				Sonuc.setMESAJ("Gerekli Alanlarý Doldurunuz!");
				return Sonuc;
			}
			
			if (TxtParola.getText().toString().equals("")) {
				Sonuc = new Hata();
				Sonuc.setBASLIK("Kayýt");
				Sonuc.setHATAMI(true);
				Sonuc.setMESAJ("Gerekli Alanlarý Doldurunuz!");
				return Sonuc;
			}
			
			if (TxtParolaTekrar.getText().toString().equals("")) {
				Sonuc = new Hata();
				Sonuc.setBASLIK("Kayýt");
				Sonuc.setHATAMI(true);
				Sonuc.setMESAJ("Gerekli Alanlarý Doldurunuz!");
				return Sonuc;
			}
			
			if (!TxtParola.getText().toString().equals(TxtParolaTekrar.getText().toString())) {
				Sonuc = new Hata();
				Sonuc.setBASLIK("Kayýt");
				Sonuc.setHATAMI(true);
				Sonuc.setMESAJ("Parolayý Doðru Giriniz!");
				return Sonuc;
			}
			
			Sonuc = new Hata();
			Sabit.MKullanici = new Kullanici();
			Sabit.MKullanici.setAdSoyad(TxtAdSoyad.getText().toString());
			Sabit.MKullanici.setParola(TxtParola.getText().toString());
			Sabit.MKullanici.setParolaTekrar(TxtParolaTekrar.getText().toString());
			
			Sonuc = Sabit.MKullanici.KaydetKullanici();
			if (Sonuc.getHATAMI()) {
				Sonuc = new Hata();
				Sonuc.setBASLIK("Kayýt");
				Sonuc.setHATAMI(true);
				Sonuc.setMESAJ("Ýþlem Baþarýsýz!");
				Sonuc.GosterMesajToast(KayitActivity.this);
			}
		} catch (Exception e) {
			Sonuc = new Hata();
			Sonuc.setBASLIK("Kayýt");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Ýþlem Baþarýsýz!");
			Sonuc.GosterMesajToast(KayitActivity.this);
		}
		
		return Sonuc;
	}
}
