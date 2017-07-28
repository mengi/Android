package com.konbeltas.netgps.func;

import android.content.Context;
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
	
    public void GosterMesajToast(Context CONTEXT) {
		
    	if (this.BASLIK != "")
			this.BASLIK = "(" + this.BASLIK + ") ";
		Toast.makeText(CONTEXT, this.BASLIK + this.MESAJ, Toast.LENGTH_SHORT).show();
    }
}
