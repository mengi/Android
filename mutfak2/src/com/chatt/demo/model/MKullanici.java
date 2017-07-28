package com.chatt.demo.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chatt.demo.custom.RSabit;

public class MKullanici {
	
	public String KullaniciAdi, BulunduguYer, Parola, GarsonKod;
	
	
	public MKullanici() {
		this.KullaniciAdi = "";
		this.BulunduguYer = "";
		this.Parola = "";
	}
	
	public MKullanici(String KullaniciAdi, String BulunduguYer, String Parola, String GarsonKod) {
		this.setBulunduguYer(BulunduguYer);
		this.setKullaniciAdi(KullaniciAdi);
		this.setParola(Parola);
		this.setGarsonKod(GarsonKod);
	}
	
	public boolean KaydetKullanici () {
		boolean Durum = false;
		try {
			SQLiteDatabase Db = RSabit.OpDbBaglanti.getWritableDatabase();
			
			Db.delete("TBL_NKULLANICI", null, null);

			ContentValues Values = new ContentValues();
			Values.put("KULLANICIADI", this.getKullaniciAdi());
			Values.put("PAROLA", this.getParola());
			Values.put("BULUNDUGUYER", this.getBulunduguYer());
			Values.put("GARSONKOD", this.getGarsonKod());
			
			Db.insert("TBL_NKULLANICI", null, Values);
			Db.close();
			
			Durum = true;
		} catch (Exception e) {
			Durum = false;
		}
		
		return Durum;
	}
	
	public String GetirKullaniciYeri () {
		String BYer = "";
		try {
			SQLiteDatabase DtBs = RSabit.OpDbBaglanti.getReadableDatabase();
			Cursor Crs = DtBs.rawQuery("SELECT BULUNDUGUYER FROM TBL_NKULLANICI", null);
			
			while (Crs.moveToNext()) {
				this.setBulunduguYer(Crs.getString(0));
				BYer = this.getBulunduguYer();
			}
			DtBs.close();
		} catch (Exception e) {
			
		}
		
		return BYer;
	}
	
	public String GetirGarsonKod () {
		String BGarsonKod = "";
		try {
			SQLiteDatabase DtBs = RSabit.OpDbBaglanti.getReadableDatabase();
			Cursor Crs = DtBs.rawQuery("SELECT GARSONKOD FROM TBL_NKULLANICI", null);
			
			while (Crs.moveToNext()) {
				this.setGarsonKod(Crs.getString(0));
				BGarsonKod = this.getGarsonKod();
			}
			
			DtBs.close();
		} catch (Exception e) {
			BGarsonKod = "";
		}
		
		return BGarsonKod;
	}
	
	public String getGarsonKod() {
		return GarsonKod;
	}

	public void setGarsonKod(String garsonKod) {
		GarsonKod = garsonKod;
	}

	public String getKullaniciAdi() {
		return KullaniciAdi;
	}

	public void setKullaniciAdi(String kullaniciAdi) {
		KullaniciAdi = kullaniciAdi;
	}

	public String getBulunduguYer() {
		return BulunduguYer;
	}

	public void setBulunduguYer(String bulunduguYer) {
		BulunduguYer = bulunduguYer;
	}

	public String getParola() {
		return Parola;
	}

	public void setParola(String parola) {
		Parola = parola;
	}
	
	
}
