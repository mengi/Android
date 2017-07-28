package com.konbeltas.netgps.model;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.konbeltas.netgps.func.MHata;
import com.konbeltas.netgps.func.MSabit;

public class OtoparkBilgi {

	public String OtoparkAd, Kapasite, OtoparkKodu, Yikama, HizmetSuresi,
			Enlem, Boylam;

	public OtoparkBilgi() {
		this.OtoparkAd = "";
		this.Kapasite = "";
		this.OtoparkKodu = "";
		this.Yikama = "";
		this.HizmetSuresi = "";
		this.Enlem = "";
		this.Boylam = "";
	}

	public OtoparkBilgi(String OtoparkAd, String Kapasite, String OtoparkKodu,
			String Yikama, String HizmetSuresi, String Enlem, String Boylam) {

		this.OtoparkAd = OtoparkAd;
		this.Kapasite = Kapasite;
		this.OtoparkKodu = OtoparkKodu;
		this.Yikama = Yikama;
		this.HizmetSuresi = HizmetSuresi;
		this.Enlem = Enlem;
		this.Boylam = Boylam;
	}
	
	public boolean VarMiOtoparkKodu(String _OtoparkKodu) {
		boolean durum = false;
		
		try {
			SQLiteDatabase DtBs = MSabit.MDbBaglanti.getReadableDatabase();
			String SorguC = "SELECT OTOPARKKODU "
					+ "FROM TBLOTOPARKBILGI WHERE OTOPARKKODU=?";
			
			Cursor Crs = DtBs.rawQuery(SorguC, new String [] {_OtoparkKodu});
			while (Crs.moveToNext()) {
				durum = true;
			}
			
			DtBs.close();
		} catch (Exception e) {
			MHata Sonuc = new MHata();
			Sonuc.setBASLIK("");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("");
		}
		
		return durum;
		
	}
	
	public ArrayList<String> GetirGpsEnBoy(String _OtoparkKodu) {
		ArrayList<String> EnBoyList = new ArrayList<String>();
		
		try {
			SQLiteDatabase DtBs = MSabit.MDbBaglanti.getReadableDatabase();
			String SorguC = "SELECT ENLEM, BOYLAM "
					+ "FROM TBLOTOPARKBILGI WHERE OTOPARKKODU=?";
			
			Cursor Crs = DtBs.rawQuery(SorguC, new String [] {_OtoparkKodu});
			while (Crs.moveToNext()) {
				this.setEnlem(Crs.getString(0));
				this.setBoylam(Crs.getString(1));
				
				EnBoyList.add(this.getEnlem());
				EnBoyList.add(this.getBoylam());
			}
			
			DtBs.close();
		} catch (Exception e) {
			MHata Sonuc = new MHata();
			Sonuc.setBASLIK("");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("");
		}
		
		return EnBoyList;
		
	}
	
	public ArrayList<OtoparkBilgi> GetirOtoparkBilgiler() {
		ArrayList<OtoparkBilgi> OtoparkBilgiler = new ArrayList<OtoparkBilgi>();
		
		try {
			SQLiteDatabase DtBs = MSabit.MDbBaglanti.getReadableDatabase();
			String SorguC = "SELECT OTOPARKADI, KAPASITE, OTOPARKKODU, YIKAMA, HIZMETSURESI, ENLEM, BOYLAM "
					+ "FROM TBLOTOPARKBILGI";
			
			Cursor Crs = DtBs.rawQuery(SorguC, null);
			while (Crs.moveToNext()) {
				
				OtoparkBilgi otoparkBilgi = new OtoparkBilgi();
				
				otoparkBilgi.OtoparkAd = Crs.getString(0);
				otoparkBilgi.Kapasite = Crs.getString(1);
				otoparkBilgi.OtoparkKodu = Crs.getString(2);
				otoparkBilgi.Yikama = Crs.getString(3);
				otoparkBilgi.HizmetSuresi = Crs.getString(4);
				otoparkBilgi.Enlem = Crs.getString(5);
				otoparkBilgi.Boylam = Crs.getString(6);
				
				OtoparkBilgiler.add(otoparkBilgi);
			}
			DtBs.close();
		} catch (Exception e) {
			MHata Sonuc = new MHata();
			Sonuc.setBASLIK("");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("");
		}
		
		return OtoparkBilgiler;
	}

	public void GetirOotoparkBilgiById(String _OtoparkKodu) {

		try {
			SQLiteDatabase DtBs = MSabit.MDbBaglanti.getReadableDatabase();
			String SorguC = "SELECT OTOPARKADI, KAPASITE, OTOPARKKODU, YIKAMA, HIZMETSURESI, ENLEM, BOYLAM "
					+ "FROM TBLOTOPARKBILGI WHERE OTOPARKKODU=?";
			
			Cursor Crs = DtBs.rawQuery(SorguC, new String [] {_OtoparkKodu});
			while (Crs.moveToNext()) {
				this.setOtoparkAd(Crs.getString(0));
				this.setKapasite(Crs.getString(1));
				this.setOtoparkKodu(Crs.getString(2));
				this.setYikama(Crs.getString(3));
				this.setHizmetSuresi(Crs.getString(4));
				this.setEnlem(Crs.getString(5));
				this.setBoylam(Crs.getString(6));
			}
			
			DtBs.close();
		} catch (Exception e) {
			MHata Sonuc = new MHata();
			Sonuc.setBASLIK("");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("");
		}
	}
	
	public void GetirOotoparkBilgi() {

		try {
			SQLiteDatabase DtBs = MSabit.MDbBaglanti.getReadableDatabase();
			String SorguC = "SELECT OTOPARKADI, KAPASITE, OTOPARKKODU, YIKAMA, HIZMETSURESI, ENLEM, BOYLAM "
					+ "FROM TBLOTOPARKBILGI";
			
			Cursor Crs = DtBs.rawQuery(SorguC, null);
			while (Crs.moveToNext()) {
				this.setOtoparkAd(Crs.getString(0));
				this.setKapasite(Crs.getString(1));
				this.setOtoparkKodu(Crs.getString(2));
				this.setYikama(Crs.getString(3));
				this.setHizmetSuresi(Crs.getString(4));
				this.setEnlem(Crs.getString(5));
				this.setBoylam(Crs.getString(6));
			}
			
			DtBs.close();
		} catch (Exception e) {
			MHata Sonuc = new MHata();
			Sonuc.setBASLIK("");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("");
		}
	}

	public MHata KaydetOtoparkBilgi() {
		MHata Sonuc = new MHata();
		try {
			SQLiteDatabase DtBs = MSabit.MDbBaglanti.getWritableDatabase();

			ContentValues Values = new ContentValues();

			Values.put("OTOPARKADI", this.getOtoparkAd());
			Values.put("KAPASITE", this.getKapasite());
			Values.put("OTOPARKKODU", this.getOtoparkKodu());
			Values.put("YIKAMA", this.getYikama());
			Values.put("HIZMETSURESI", this.getHizmetSuresi());
			Values.put("ENLEM", this.getEnlem());
			Values.put("BOYLAM", this.getBoylam());

			DtBs.insert("TBLOTOPARKBILGI", null, Values);
		} catch (Exception e) {
			Sonuc = new MHata();
			Sonuc.setBASLIK("");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("");
		}

		return Sonuc;
	}

	public String getOtoparkAd() {
		return OtoparkAd;
	}

	public void setOtoparkAd(String otoparkAd) {
		OtoparkAd = otoparkAd;
	}

	public String getKapasite() {
		return Kapasite;
	}

	public void setKapasite(String kapasite) {
		Kapasite = kapasite;
	}

	public String getOtoparkKodu() {
		return OtoparkKodu;
	}

	public void setOtoparkKodu(String otoparkKodu) {
		OtoparkKodu = otoparkKodu;
	}

	public String getYikama() {
		return Yikama;
	}

	public void setYikama(String yikama) {
		Yikama = yikama;
	}

	public String getHizmetSuresi() {
		return HizmetSuresi;
	}

	public void setHizmetSuresi(String hizmetSuresi) {
		HizmetSuresi = hizmetSuresi;
	}

	public String getEnlem() {
		return Enlem;
	}

	public void setEnlem(String enlem) {
		Enlem = enlem;
	}

	public String getBoylam() {
		return Boylam;
	}

	public void setBoylam(String boylam) {
		Boylam = boylam;
	}

}
