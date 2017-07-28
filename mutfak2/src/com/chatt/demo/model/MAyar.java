package com.chatt.demo.model;

import com.chatt.demo.custom.RSabit;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MAyar {
	
	public String IpAdres;
	
	public MAyar() {
		this.IpAdres = "";
	}
	
	public MAyar(String IpAdres) {
		this.setIpAdres(IpAdres);
	}
	
	public boolean KaydetAyar() {
		boolean Durum = false;
		try {
			SQLiteDatabase Db = RSabit.OpDbBaglanti.getWritableDatabase();
			
			Db.delete("TBL_NAYAR", null, null);

			ContentValues Values = new ContentValues();
			Values.put("IPADRES", this.getIpAdres());

			Db.insert("TBL_NAYAR", null, Values);
			Db.close();
			
			Durum = true;
		} catch (Exception e) {
			Durum = false;
		}
		
		return Durum;
	}
	
	public void GetirAyar() {
		try {
			SQLiteDatabase DtBs = RSabit.OpDbBaglanti.getReadableDatabase();
			Cursor Crs = DtBs.rawQuery("SELECT IPADRES FROM TBL_NAYAR", null);
			
			while (Crs.moveToNext()) {
				this.setIpAdres(Crs.getString(0));
			}
			DtBs.close();
		} catch (Exception e) {
			
		}
	}

	public String getIpAdres() {
		return IpAdres;
	}

	public void setIpAdres(String ipAdres) {
		IpAdres = ipAdres;
	}	
}
