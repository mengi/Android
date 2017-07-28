package com.konbeltas.netgps.model;

public class MDetay {
	
	public String Aciklama, Ucret;
	
	public MDetay() {
		this.Aciklama = "";
		this.Ucret = "";
	}
	
	public MDetay(String Aciklama, String Ucret) {
		this.Aciklama = Aciklama;
		this.Ucret = Ucret;
	}
	
	public String getAciklama() {
		return Aciklama;
	}

	public void setAciklama(String aciklama) {
		Aciklama = aciklama;
	}

	public String getUcret() {
		return Ucret;
	}

	public void setUcret(String ucret) {
		Ucret = ucret;
	}
}
