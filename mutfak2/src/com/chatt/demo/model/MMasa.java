package com.chatt.demo.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chatt.demo.custom.RSabit;

public class MMasa {
	
	public String MASAADI, ID, MASADURUMU, SIPARISDURUMU, SIPARISID;
	
	public MMasa() {
		this.ID = "-1";
		this.MASAADI = "";
		this.MASADURUMU = "";
		this.SIPARISDURUMU = "";
		this.SIPARISID = "-1";
	}
	
	public MMasa(String ID, String MASAADI, String MASADURUMU, String SIPARISDURUMU, String SIPARISID) {
		this.setID(ID);
		this.setMASAADI(MASAADI);
		this.setMASADURUMU(MASADURUMU);
		this.setSIPARISDURUMU(SIPARISDURUMU);
		this.setSIPARISID(SIPARISID);
		
	}
	
	public boolean KaydetMasa() {
		boolean Durum = false;
		
		try {
			SQLiteDatabase Db = RSabit.OpDbBaglanti.getWritableDatabase();

			ContentValues Values = new ContentValues();
			Values.put("ID", this.getID());
			Values.put("MASAADI", this.getMASAADI());
			Values.put("MASADURUMU", this.getMASADURUMU());
			Values.put("SIPARISDURUMU", this.getSIPARISDURUMU());
			Values.put("SIPARISID", this.getSIPARISID());
			Db.insert("TBL_NMASA", null, Values);
			Db.close();
			Durum = true;
		} catch (Exception e) {
			Durum = false;
		}
		
		return Durum;
	}
	
	public void GetirMasa(String _ID) {
		try {
			SQLiteDatabase DtBs = RSabit.OpDbBaglanti.getReadableDatabase();
			String Sorgu = "SELECT ID, MASAADI, MASADURUMU, SIPARISDURUMU FROM TBL_NMASA WHERE ID=?";
			Cursor Crs = DtBs.rawQuery(Sorgu, new String [] {_ID});
			
			while (Crs.moveToNext()) {
				this.setID(Crs.getString(0));
				this.setMASAADI(Crs.getString(1));

			}
			
			DtBs.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public String GetirMasaSiparisID (String _ID) {
		String SiparisID = "";
		try {
			SQLiteDatabase DtBs = RSabit.OpDbBaglanti.getReadableDatabase();
			String Sorgu = "SELECT SIPARISID FROM TBL_NMASA WHERE ID=?";
			Cursor Crs = DtBs.rawQuery(Sorgu, new String [] {_ID});
			
			while (Crs.moveToNext()) {
				this.setSIPARISID(Crs.getString(0));
			}
			
			SiparisID = this.getSIPARISID();
			DtBs.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return SiparisID;
	}
	
	
	public boolean GuncelleMasa(String _ID, String DURUM) {


		try {
			SQLiteDatabase DtBs = RSabit.OpDbBaglanti.getWritableDatabase();
			DtBs.execSQL("UPDATE TBL_NMASA SET MASADURUMU='"+DURUM+"' WHERE ID="+_ID);
			DtBs.close();
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	public boolean GuncelleMasaSiparis (String _ID, String _SIPARISID) {


		try {
			SQLiteDatabase DtBs = RSabit.OpDbBaglanti.getWritableDatabase();
			DtBs.execSQL("UPDATE TBL_NMASA SET SIPARISID='"+_SIPARISID+"' WHERE ID="+_ID);
			DtBs.close();
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	public boolean MasaSil() {
		boolean Durum = false;
		try {
			SQLiteDatabase Db = RSabit.OpDbBaglanti.getReadableDatabase();
			Db.delete("TBL_NMASA", null, null);
			Db.close();
			Durum = true;
		} catch (Exception e) {
			Durum = false;
		}
		
		return Durum;
	}
	
	public String getMASAADI() {
		return MASAADI;
	}
	public void setMASAADI(String mASAADI) {
		MASAADI = mASAADI;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}

	public String getMASADURUMU() {
		return MASADURUMU;
	}

	public void setMASADURUMU(String mASADURUMU) {
		MASADURUMU = mASADURUMU;
	}

	public String getSIPARISDURUMU() {
		return SIPARISDURUMU;
	}

	public void setSIPARISDURUMU(String sIPARISDURUMU) {
		SIPARISDURUMU = sIPARISDURUMU;
	}

	public String getSIPARISID() {
		return SIPARISID;
	}

	public void setSIPARISID(String sIPARISID) {
		SIPARISID = sIPARISID;
	}
	
}
