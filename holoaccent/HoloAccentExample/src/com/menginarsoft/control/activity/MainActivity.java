package com.menginarsoft.control.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.menginarsoft.control.database.ODataBase;
import com.menginarsoft.control.function.Sabit;
import com.menginarsoft.control.model.Kullanici;
import com.menginarsoft.control.model.ParamAyar;
import com.negusoft.holoaccent.activity.AccentActivity;
import com.menginarsoft.control.R;

public class MainActivity extends AccentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		Sabit.MDbBaglanti = new ODataBase(this);
		Sabit.MKullanici = new Kullanici();
		Sabit.MParamAyar = new ParamAyar();
		Sabit.MParamAyar.GetirParamAyar();
		Sabit.MKullanici.GetirKullanici();

		findViewById(R.id.GirisPaneli).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(android.view.View view) {
						Intent MyInteny = new Intent(MainActivity.this,
								GirisActivity.class);
						startActivity(MyInteny);
					}
				});

		findViewById(R.id.KayitPaneli).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(android.view.View view) {
						Intent MyInteny = new Intent(MainActivity.this,
								KayitActivity.class);
						startActivity(MyInteny);
					}
				});
	}
	
	@Override
	public void onResume() {

		Sabit.MKullanici.GetirKullanici();
		boolean Durum = (Sabit.MKullanici.getId() != -1);

		if (!Durum) {
			Intent myIntent = new Intent(MainActivity.this, KayitActivity.class);
			startActivity(myIntent);
			
		} else {
			Intent myIntent = new Intent(MainActivity.this, GirisActivity.class);
			startActivity(myIntent);
			finish();
		}
		super.onResume();
	}
	
}
