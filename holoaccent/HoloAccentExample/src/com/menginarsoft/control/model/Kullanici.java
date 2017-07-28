package com.menginarsoft.control.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.menginarsoft.control.function.Hata;
import com.menginarsoft.control.function.Sabit;

public class Kullanici {
	
	public int Id;
	public String AdSoyad, Parola, ParolaTekrar;
	Hata Sonuc = new Hata();
	
	public Kullanici() {
		this.Id = -1;
		this.AdSoyad = "";
		this.Parola = "";
		this.ParolaTekrar = "";
	}
	
	public Kullanici(String AdSoyad, String Parola, String ParolaTekrar) {
		this.AdSoyad = AdSoyad;
		this.Parola = Parola;
		this.ParolaTekrar = ParolaTekrar;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getAdSoyad() {
		return AdSoyad;
	}

	public void setAdSoyad(String adSoyad) {
		AdSoyad = adSoyad;
	}

	public String getParola() {
		return Parola;
	}

	public void setParola(String parola) {
		Parola = parola;
	}

	public String getParolaTekrar() {
		return ParolaTekrar;
	}

	public void setParolaTekrar(String parolaTekrar) {
		ParolaTekrar = parolaTekrar;
	}
	
	public Hata KaydetKullanici() {
		try {
			SQLiteDatabase DtBs = Sabit.MDbBaglanti.getWritableDatabase();
			DtBs.delete("TBLNKULLANICI", null, null);
			
			ContentValues Values = new ContentValues();
			Values.put("ADSOYAD", this.getAdSoyad());
			Values.put("PAROLA", this.getParola());
			Values.put("PAROLATEKRAR", this.getParolaTekrar());
			
			DtBs.insert("TBLNKULLANICI", null, Values);
			
			DtBs.close();
		} catch (Exception e) {
			Sonuc = new Hata();
			Sonuc.setBASLIK("");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Ýþlem Baþarýsýz!");
		}
		return Sonuc;
	}
	
	public Hata GetirKullanici() {
		try {
			SQLiteDatabase DtBs = Sabit.MDbBaglanti.getReadableDatabase();
			String Sonuc = "SELECT ADSOYAD, PAROLA, PAROLATEKRAR, userID FROM TBLNKULLANICI LIMIT 1";
			Cursor Crs = DtBs.rawQuery(Sonuc, null);
			
			while (Crs.moveToNext()) {
				this.setAdSoyad(Crs.getString(0));
				this.setParola(Crs.getString(1));
				this.setParolaTekrar(Crs.getString(2));
				this.setId(Crs.getInt(3));
			}
			
			DtBs.close();
		} catch (Exception e) {
			Sonuc = new Hata();
			Sonuc.setBASLIK("");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Ýþlem Baþarýsýz!");
		}
		return Sonuc;
	}
}
