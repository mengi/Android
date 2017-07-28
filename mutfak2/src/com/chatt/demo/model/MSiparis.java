package com.chatt.demo.model;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chatt.demo.custom.RSabit;

public class MSiparis {
	public String KULLANICIID, URUNADI, MASAADI, MIKTAR, MASAID, AKTARILDIMI, STOKID;
	public double FIYAT;
	
	public MSiparis () {
		this.MASAID = "-1";
		this.AKTARILDIMI = "0";
		this.KULLANICIID = "";
		this.URUNADI = "";
		this.MASAADI = "";
		this.FIYAT = 0.0;
		this.MIKTAR = "0";
		this.STOKID = "0";
	}
	
	public MSiparis (String KULLANICIID, String URUNADI, String MASAADI, double FIYAT, String MIKTAR, String MASAID, String AKTARILDIMI, String STOKID) {
		this.setKULLANICIID(KULLANICIID);
		this.setURUNADI(URUNADI);
		this.setMASAADI(MASAADI);
		this.setFIYAT(FIYAT);
		this.setMIKTAR(MIKTAR);
		this.setMASAID(MASAID);
		this.setAKTARILDIMI(AKTARILDIMI);
		this.setSTOKID(STOKID);
	}
	
	public boolean KaydetSiparis () {
		boolean Durum = false;
		try {
			SQLiteDatabase Db = RSabit.OpDbBaglanti.getWritableDatabase();

			ContentValues Values = new ContentValues();
			
			Values.put("KULLANICIID", this.getKULLANICIID());
			Values.put("URUNADI", this.getURUNADI());
			Values.put("MASAADI", this.getMASAADI());
			Values.put("FIYAT", this.getFIYAT());
			Values.put("MIKTAR", this.getMIKTAR());
			Values.put("MASAID", this.getMASAID());
			Values.put("AKTARILDIMI", this.getAKTARILDIMI());
			Values.put("STOKID", this.getSTOKID());
			
			Db.insert("TBL_NSIPARIS", null, Values);
			
			Db.close();
			Durum = true;
		} catch (Exception e) {
			Durum = false;
		}
		
		return Durum;
	}
	
	public ArrayList<MSiparis> MasaSiparisGetir(String _MasaId) {
		ArrayList<MSiparis> MasaSiparisleri = new ArrayList<MSiparis>();
		try {
			SQLiteDatabase DtBs = RSabit.OpDbBaglanti.getReadableDatabase();
			String Sorgu = "SELECT STOKID, URUNADI, MIKTAR, MASAID, FIYAT FROM TBL_NSIPARIS WHERE AKTARILDIMI ='0' AND MASAID=?";
			Cursor Crs = DtBs.rawQuery(Sorgu, new String [] {_MasaId});
			while (Crs.moveToNext()) {
				MSiparis mSiparis = new MSiparis();
				mSiparis.setSTOKID(Crs.getString(0));
				mSiparis.setURUNADI(Crs.getString(1));
				mSiparis.setMIKTAR(Crs.getString(2));
				mSiparis.setMASAID(Crs.getString(3));
				mSiparis.setFIYAT(Crs.getDouble(4));
				MasaSiparisleri.add(mSiparis);
			}
			
			DtBs.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return MasaSiparisleri;
	}
	
	public boolean SiparisSil (String _MasaAdi, String _UrunAdi) {
		boolean Durum = false;
		try {
			SQLiteDatabase DtBs = RSabit.OpDbBaglanti.getReadableDatabase();
			
			DtBs.execSQL("DELETE FROM TBL_NSIPARIS WHERE MASAADI='"+_MasaAdi+"' AND URUNADI='"+_UrunAdi+"'");
			
			DtBs.close();
			Durum = true;
		} catch (Exception e) {
			Durum = false;
		}
		
		return Durum;
	}
	
	public boolean MasaSiparisSil (String _MasaId) {
		boolean Durum = false;
		try {
			SQLiteDatabase DtBs = RSabit.OpDbBaglanti.getReadableDatabase();
			
			DtBs.execSQL("DELETE FROM TBL_NSIPARIS WHERE MASAID='"+_MasaId+"' AND AKTARILDIMI = '1'");
			
			DtBs.close();
			Durum = true;
		} catch (Exception e) {
			Durum = false;
		}
		
		return Durum;
	}
	
	public boolean SiparisSilMasa (String _MasaAdi) {
		boolean Durum = false;
		try {
			SQLiteDatabase DtBs = RSabit.OpDbBaglanti.getReadableDatabase();
			
			DtBs.delete("TBL_NSIPARIS", "MASAADI = ?", new String[] { _MasaAdi });
			
			DtBs.close();
			Durum = true;
		} catch (Exception e) {
			Durum = false;
		}
		
		return Durum;
	}
	
	public boolean GuncelMiktar (String _UrunAdi, String _Miktar, String _MasaAdi) {
		boolean Durum = false;
		try {
			SQLiteDatabase DtBs = RSabit.OpDbBaglanti.getReadableDatabase();
			
			DtBs.execSQL("UPDATE TBL_NSIPARIS SET MIKTAR = '"+_Miktar+"' WHERE MASAADI='"+_MasaAdi+"' AND URUNADI='"+_UrunAdi+"'");
			
			DtBs.close();
			Durum = true;
		} catch (Exception e) {
			Durum = false;
		}
		
		return Durum;
	}
	
	public boolean GuncelDurum (String _UrunAdi, String _MasaId) {
		boolean Durum = false;
		try {
			SQLiteDatabase DtBs = RSabit.OpDbBaglanti.getReadableDatabase();
			
			DtBs.execSQL("UPDATE TBL_NSIPARIS SET AKTARILDIMI = '1' WHERE MASAID='"+_MasaId+"' AND URUNADI='"+_UrunAdi+"'");
			
			DtBs.close();
			Durum = true;
		} catch (Exception e) {
			Durum = false;
		}
		
		return Durum;
	}
	
	public String getAKTARILDIMI() {
		return AKTARILDIMI;
	}

	public void setAKTARILDIMI(String aKTARILDIMI) {
		AKTARILDIMI = aKTARILDIMI;
	}

	public String getSTOKID() {
		return STOKID;
	}

	public void setSTOKID(String sTOKID) {
		STOKID = sTOKID;
	}

	public String getMASAID() {
		return MASAID;
	}

	public void setMASAID(String mASAID) {
		MASAID = mASAID;
	}

	public String getKULLANICIID() {
		return KULLANICIID;
	}

	public void setKULLANICIID(String kULLANICIID) {
		KULLANICIID = kULLANICIID;
	}

	public String getURUNADI() {
		return URUNADI;
	}

	public void setURUNADI(String uRUNADI) {
		URUNADI = uRUNADI;
	}

	public String getMASAADI() {
		return MASAADI;
	}

	public void setMASAADI(String mASAADI) {
		MASAADI = mASAADI;
	}

	public double getFIYAT() {
		return FIYAT;
	}

	public void setFIYAT(double fIYAT) {
		FIYAT = fIYAT;
	}
	
	public String getMIKTAR() {
		return MIKTAR;
	}

	public void setMIKTAR(String mIKTAR) {
		MIKTAR = mIKTAR;
	}
}
