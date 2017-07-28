package com.android.fuk;

import com.android.afcsocet.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MHata {

	public String BASLIK, MESAJ;
	public boolean HATAMI;

	public MHata() {
		this.BASLIK = "";
		this.MESAJ = "";
		this.HATAMI = false;
	}

	public MHata(String BASLIK, String MESAJ, boolean HATAMI) {
		this.BASLIK = BASLIK;
		this.MESAJ = MESAJ;
		this.HATAMI = HATAMI;
	}

	public void setBASLIK(String BASLIK) {
		this.BASLIK = BASLIK;
	}

	public String getBASLIK() {
		return this.BASLIK;
	}

	public void setMESAJ(String MESAJ) {
		this.MESAJ = MESAJ;
	}

	public String getMESAJ() {
		return this.MESAJ;
	}

	public void setHATAMI(boolean HATAMI) {
		this.HATAMI = HATAMI;
	}

	public Boolean getHATAMI() {
		return this.HATAMI;
	}

	public String ToString() {
		return "#" + this.BASLIK + "# " + this.MESAJ;
	}

	@SuppressLint({ "InflateParams", "InlinedApi" })
	public void GosterMesajToast(Context CONTEXT) {

		if (this.BASLIK != "")
			this.BASLIK = "(" + this.BASLIK + ") ";
		
		Context context = CONTEXT;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View customToastroot = inflater.inflate(R.layout.blue_toast, null);
		
		TextView text = (TextView) customToastroot.findViewById(R.id.textView1);
		text.setText(this.MESAJ);

		Toast customtoast = new Toast(context);

		customtoast.setView(customToastroot);
		customtoast.setGravity(Gravity.CENTER_HORIZONTAL
				| Gravity.CENTER_VERTICAL, 0, 0 | Gravity.END);
		
		customtoast.setDuration(Toast.LENGTH_SHORT);
		customtoast.show();
	}
}
