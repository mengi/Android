package com.android.mdl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.fuk.MHata;
import com.android.fuk.MSabit;

public class MKullanici {

	private int userID = -1;

	private String adsoyad, telefon, lisanskey, bitis, durum, kartsayisi,
			subesayisi;

	public MKullanici() {
		this.userID = -1;
		this.adsoyad = "";
		this.telefon = "";
		this.lisanskey = "";
		this.bitis = "";
		this.durum = "";
		this.kartsayisi = "";
		this.subesayisi = "";
	}

	public MKullanici(String adsoyad, String telefon, String lisanskey,
			String bitis, String durum, String kartsayisi, String subesayisi) {
		this.adsoyad = adsoyad;
		this.telefon = telefon;
		this.lisanskey = lisanskey;
		this.bitis = bitis;
		this.durum = durum;
		this.kartsayisi = kartsayisi;
		this.subesayisi = subesayisi;
	}

	public String getSubesayisi() {
		return subesayisi;
	}

	public void setSubesayisi(String subesayisi) {
		this.subesayisi = subesayisi;
	}

	public String getKartsayisi() {
		return kartsayisi;
	}

	public void setKartsayisi(String kartsayisi) {
		this.kartsayisi = kartsayisi;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getAdsoyad() {
		return adsoyad;
	}

	public void setAdsoyad(String adsoyad) {
		this.adsoyad = adsoyad;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getLisanskey() {
		return lisanskey;
	}

	public void setLisanskey(String lisanskey) {
		this.lisanskey = lisanskey;
	}

	public String getBitis() {
		return bitis;
	}

	public void setBitis(String bitis) {
		this.bitis = bitis;
	}

	public String getDurum() {
		return durum;
	}

	public void setDurum(String durum) {
		this.durum = durum;
	}

	public MHata SilKullanici() {
		MHata Sonuc = new MHata();

		try {
			SQLiteDatabase DtBs = MSabit.MDbBaglanti.getWritableDatabase();
			DtBs.delete("TBLNKULLANICI", null, null);

			DtBs.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return Sonuc;
	}

	public MHata GetirKullanici() {
		MHata Sonuc = new MHata();

		try {

			SQLiteDatabase DtBs = MSabit.MDbBaglanti.getReadableDatabase();
			String SorguCumle = "SELECT userID, adsoyad, telefon, lisanskey, bitis, durum, kartsayisi, subesayisi FROM TBLNKULLANICI LIMIT 1";
			Cursor Crs = DtBs.rawQuery(SorguCumle, null);

			while (Crs.moveToNext()) {
				this.setUserID(Crs.getInt(0));
				this.setAdsoyad(Crs.getString(1));
				this.setTelefon(Crs.getString(2));
				this.setLisanskey(Crs.getString(3));
				this.setBitis(Crs.getString(4));
				this.setDurum(Crs.getString(5));
				this.setKartsayisi(Crs.getString(6));
				this.setSubesayisi(Crs.getString(7));
			}

			DtBs.close();
		} catch (Exception e) {
			Sonuc = new MHata();
			Sonuc.setBASLIK("Kullanýcý Kayýt");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Kullanýcý Kayýt Baþarýsýz!");
		}

		return Sonuc;
	}

	public MHata KaydetKullanici() {
		MHata Sonuc = new MHata();

		try {
			SQLiteDatabase DtBs = MSabit.MDbBaglanti.getWritableDatabase();
			DtBs.delete("TBLNKULLANICI", null, null);

			ContentValues Values = new ContentValues();
			Values.put("adsoyad", this.getAdsoyad());
			Values.put("telefon", this.getTelefon());
			Values.put("lisanskey", this.getLisanskey());
			Values.put("bitis", this.getBitis());
			Values.put("durum", this.getDurum());
			Values.put("kartsayisi", this.getKartsayisi());
			Values.put("subesayisi", this.getSubesayisi());
			DtBs.insert("TBLNKULLANICI", null, Values);

			DtBs.close();

		} catch (Exception e) {
			Sonuc = new MHata();
			Sonuc.setBASLIK("Kullanýcý Kayýt");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Kullanýcý Kayýt Baþarýsýz!");
		}

		return Sonuc;
	}
}
