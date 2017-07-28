package com.chatt.demo.model;

import com.chatt.demo.custom.RSabit;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class MUrunUstListe {
	
	public String ID, URUNGRUPADI;
	
	public MUrunUstListe() {
		this.ID = "-1";
		this.URUNGRUPADI = "";
	}
	
	public MUrunUstListe (String ID, String URUNGRUPADI) {
		this.setID(ID);
		this.setURUNGRUPADI(URUNGRUPADI);
	}
	
	public boolean KaydetUrunGrup () {
		boolean Durum = false;
		try {
			SQLiteDatabase Db = RSabit.OpDbBaglanti.getWritableDatabase();

			ContentValues Values = new ContentValues();
			
			Values.put("ID", this.getID());
			Values.put("URUNGRUPADI", this.getURUNGRUPADI());
			Db.insert("TBL_NURUNGRUP", null, Values);
			
			Db.close();
			Durum = true;
		} catch (Exception e) {
			Durum = false;
		}
		
		return Durum;
	}
	
	public boolean UrunGrupSil () {
		boolean Durum = false;
		try {
			SQLiteDatabase DtBs = RSabit.OpDbBaglanti.getReadableDatabase();
			
			DtBs.delete("TBL_NURUNGRUP", null, null);
			
			DtBs.close();
			Durum = true;
		} catch (Exception e) {
			Durum = false;
		}
		
		return Durum;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getURUNGRUPADI() {
		return URUNGRUPADI;
	}

	public void setURUNGRUPADI(String uRUNGRUPADI) {
		URUNGRUPADI = uRUNGRUPADI;
	}
}
