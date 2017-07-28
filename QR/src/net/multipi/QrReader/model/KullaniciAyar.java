package net.multipi.QrReader.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import net.multipi.QrReader.func.Hata;
import net.multipi.QrReader.func.Sabit;

public class KullaniciAyar {
	
	Hata Sonuc = new Hata();
	public int Id;
	public String KullaniciAdi, KullaniciKodu, Parola;
	
	public KullaniciAyar() {
		this.Id = -1;
		this.KullaniciAdi = "";
		this.KullaniciKodu = "";
		this.Parola = "";
	}
	
	public KullaniciAyar(String KullaniciAdi, String KullaniciKodu, String Parola) {
		this.setKullaniciAdi(KullaniciAdi);
		this.setKullaniciKodu(KullaniciKodu);
		this.setParola(Parola);
	}
	
	public Hata KayitKullaniciAyar() {
		try {
			SQLiteDatabase DtBs = Sabit.MDbBaglanti.getWritableDatabase();
			
			DtBs.delete("TBLNKULLANICI", null, null);
			
			ContentValues Values = new ContentValues();
			Values.put("KULLANICIKODU", this.getKullaniciKodu());
			Values.put("KULLANICIADI", this.getKullaniciAdi());
			Values.put("PAROLA", this.getParola());
			
			DtBs.insert("TBLNKULLANICI", null, Values);
			
			DtBs.close();
		} catch (Exception e) {
			Sonuc = new Hata();
			Sonuc.setBASLIK("Kullanýcý Ayar");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Kayýt Baþarýsýz!");
		}
		return Sonuc;
	}
	
	public Hata GetirKullaniciAyar() {
		try {
			SQLiteDatabase Dtbs = Sabit.MDbBaglanti.getReadableDatabase();
			String Sorgu = "SELECT KULLANICIADI, KULLANICIKODU, PAROLA, ID FROM TBLNKULLANICI";
			Cursor Crs = Dtbs.rawQuery(Sorgu, null);
			
			while (Crs.moveToNext()) {
				this.setKullaniciAdi(Crs.getString(0));
				this.setKullaniciKodu(Crs.getString(1));
				this.setParola(Crs.getString(2));
				this.setId(Crs.getInt(3));
			}
			
			Dtbs.close();
		} catch (Exception e) {
			Sonuc = new Hata();
			Sonuc.setBASLIK("Kullanýc Ayar");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Getirme Baþarýsýz!");
		}
		
		return Sonuc;
	}
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getKullaniciAdi() {
		return KullaniciAdi;
	}
	public void setKullaniciAdi(String kullaniciAdi) {
		KullaniciAdi = kullaniciAdi;
	}
	public String getKullaniciKodu() {
		return KullaniciKodu;
	}
	public void setKullaniciKodu(String kullaniciKodu) {
		KullaniciKodu = kullaniciKodu;
	}
	public String getParola() {
		return Parola;
	}
	public void setParola(String parola) {
		Parola = parola;
	}
	
}
