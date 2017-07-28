package com.chatt.demo.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.chatt.demo.custom.RSabit;

public class MUrunList {
	
	public String STOKID, ACIKLAMA, URUNGRUPID;
	public double FIYAT;
	
	public MUrunList() {
		this.STOKID = "-1";
		this.ACIKLAMA = "";
		this.URUNGRUPID = "-1";
		this.FIYAT = 0.0;
	}
	
	public MUrunList(String STOKID, String ACIKLAMA, String URUNGRUPID, double FIYAT) {
		this.setSTOKID(STOKID);
		this.setACIKLAMA(ACIKLAMA);
		this.setURUNGRUPID(URUNGRUPID);
		this.setFIYAT(FIYAT);
	}
	
	public boolean KaydetUrunListe () {
		boolean Durum = false;
		try {
			SQLiteDatabase Db = RSabit.OpDbBaglanti.getWritableDatabase();

			ContentValues Values = new ContentValues();
			
			Values.put("STOKID", this.getSTOKID());
			Values.put("ACIKLAMA", this.getACIKLAMA());
			Values.put("FIYAT", this.getFIYAT());
			Values.put("URUNGRUPID", this.getURUNGRUPID());
			Db.insert("TBL_NURUNLER", null, Values);
			
			Db.close();
			Durum = true;
		} catch (Exception e) {
			Durum = false;
		}
		
		return Durum;
	}
	
	public boolean UrunSil () {
		boolean Durum = false;
		try {
			SQLiteDatabase DtBs = RSabit.OpDbBaglanti.getReadableDatabase();
			
			DtBs.delete("TBL_NURUNLER", null, null);
			
			DtBs.close();
			Durum = true;
		} catch (Exception e) {
			Durum = false;
		}
		
		return Durum;
	}

	public String getSTOKID() {
		return STOKID;
	}

	public void setSTOKID(String sTOKID) {
		STOKID = sTOKID;
	}

	public String getACIKLAMA() {
		return ACIKLAMA;
	}

	public void setACIKLAMA(String aCIKLAMA) {
		ACIKLAMA = aCIKLAMA;
	}

	public String getURUNGRUPID() {
		return URUNGRUPID;
	}

	public void setURUNGRUPID(String uRUNGRUPID) {
		URUNGRUPID = uRUNGRUPID;
	}

	public double getFIYAT() {
		return FIYAT;
	}

	public void setFIYAT(double fIYAT) {
		FIYAT = fIYAT;
	}
	
	

}
