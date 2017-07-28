package com.chatt.demo;


import cn.pedant.SweetAlert.widget.SweetAlertDialog;

import com.chatt.demo.custom.RSabit;
import com.chatt.demo.model.MAyar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Ayar extends Activity {

	EditText ipadres, port;
	Button BtnKaydet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ayar);
		
		RSabit.MAyar = new MAyar();
		RSabit.MAyar.GetirAyar();
		
		ipadres = (EditText) findViewById(R.id.ipadres);
		BtnKaydet = (Button) findViewById(R.id.BtnKaydet);
		
		if (!RSabit.MAyar.getIpAdres().equals("")) {
			ipadres.setText(RSabit.MAyar.getIpAdres());
		}
		
		BtnKaydet.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (Kaydet()) {
					Intent myIntent = new Intent(Ayar.this, GarsonLogin.class);
					finish();
					startActivity(myIntent);
				} else {
					new SweetAlertDialog(Ayar.this, SweetAlertDialog.ERROR_TYPE)
					.setTitleText("Oops...")
					.setContentText(" Kaydetme Ýþlemi Baþarýsýz !!!").show();
				}
			}
		});
	}
	
	public boolean Kaydet() {
		boolean Durum = false;
		try {
			if (ipadres.getText().toString().equals("")) {
				Durum = false;
			} else {
				RSabit.MAyar = new MAyar(ipadres.getText().toString());
				if (RSabit.MAyar.KaydetAyar()) {
					Durum = true;
					ipadres.setText(RSabit.MAyar.getIpAdres());
				} else {
					Durum = false;
				}
			}
			
		} catch (Exception e) {
			Durum = false;
		}
		
		return Durum;
	}
}
