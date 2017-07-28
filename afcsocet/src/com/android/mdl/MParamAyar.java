package com.android.mdl;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.fuk.MHata;
import com.android.fuk.MSabit;

public class MParamAyar {
	
	public int ID;
	
	public String ICIP, DISIP, PORTNUMARASI, SUBEADI;
	
	public MParamAyar() {
		this.ID = -1;
		this.ICIP = "";
		this.DISIP = "";
		this.PORTNUMARASI = "";
		this.SUBEADI = "";
	}
	
	public MParamAyar(String PORTNUMARASI, String DISIP, String ICIP, String SUBEADI) {
		this.setDISIP(DISIP);
		this.setPORTNUMARASI(PORTNUMARASI);
		this.setICIP(ICIP);
		this.setSUBEADI(SUBEADI);
	}
	
	public String getSUBEADI() {
		return SUBEADI;
	}

	public void setSUBEADI(String sUBEADI) {
		SUBEADI = sUBEADI;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getICIP() {
		return ICIP;
	}

	public void setICIP(String iCIP) {
		ICIP = iCIP;
	}

	public String getDISIP() {
		return DISIP;
	}

	public void setDISIP(String dISIP) {
		DISIP = dISIP;
	}

	public String getPORTNUMARASI() {
		return PORTNUMARASI;
	}

	public void setPORTNUMARASI(String pORTNUMARASI) {
		PORTNUMARASI = pORTNUMARASI;
	}
	
	public ArrayList<MParamAyar> GetirPayarList() {
		ArrayList<MParamAyar> PAyarList = new ArrayList<MParamAyar>();
		
		try {
			
			SQLiteDatabase DtBs = MSabit.MDbBaglanti.getReadableDatabase();
			String SorguCumle = "SELECT ID, ICIP, DISIP, PORTNUMARASI, SUBEADI FROM TBLNPARAMAYAR";
			Cursor Crs = DtBs.rawQuery(SorguCumle, null);
			
			while (Crs.moveToNext()) {
				MParamAyar MParam = new MParamAyar();
				
				MParam.setID(Crs.getInt(0));
				MParam.setICIP(Crs.getString(1));
				MParam.setDISIP(Crs.getString(2));
				MParam.setPORTNUMARASI(Crs.getString(3));
				MParam.setSUBEADI(Crs.getString(4));
				
				PAyarList.add(MParam);
			}
			
			DtBs.close();
			
		} catch (Exception e) {
			MHata Sonuc = new MHata();
			Sonuc.setBASLIK("Ayar");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Getirme Ýþlemi Baþarýsýz!");
		}
		
		
		return PAyarList;
	}
	
	
	public MHata GetirParamAyarId(int _ID) {
		MHata Sonuc = new MHata();
		
		try {
			
			SQLiteDatabase DtBs = MSabit.MDbBaglanti.getReadableDatabase();
			String SorguCumle = "SELECT ID, ICIP, DISIP, PORTNUMARASI, SUBEADI FROM TBLNPARAMAYAR WHERE ID=?";
			Cursor Crs = DtBs.rawQuery(SorguCumle, new String [] {Integer.toString(_ID)});
			
			while (Crs.moveToNext()) {
				this.setID(Crs.getInt(0));
				this.setICIP(Crs.getString(1));
				this.setDISIP(Crs.getString(2));
				this.setPORTNUMARASI(Crs.getString(3));
				this.setSUBEADI(Crs.getString(4));
			}
			
			DtBs.close();
			
		} catch (Exception e) {
			Sonuc = new MHata();
			Sonuc.setBASLIK("Ayar");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Getirme Ýþlemi Baþarýsýz!");
		}
		
		return Sonuc;
	}
	
	public MHata GetirParamAyarSubeAd(String _SUBEADI) {
		MHata Sonuc = new MHata();
		
		try {
			
			SQLiteDatabase DtBs = MSabit.MDbBaglanti.getReadableDatabase();
			String SorguCumle = "SELECT ID, ICIP, DISIP, PORTNUMARASI, SUBEADI FROM TBLNPARAMAYAR WHERE SUBEADI=?";
			Cursor Crs = DtBs.rawQuery(SorguCumle, new String [] {_SUBEADI});
			
			while (Crs.moveToNext()) {
				this.setID(Crs.getInt(0));
				this.setICIP(Crs.getString(1));
				this.setDISIP(Crs.getString(2));
				this.setPORTNUMARASI(Crs.getString(3));
				this.setSUBEADI(Crs.getString(4));
			}
			
			DtBs.close();
			
		} catch (Exception e) {
			Sonuc = new MHata();
			Sonuc.setBASLIK("Ayar");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Getirme Ýþlemi Baþarýsýz!");
		}
		
		return Sonuc;
	}

	public MHata GetirParamAyar() {
		MHata Sonuc = new MHata();
		
		try {
			
			SQLiteDatabase DtBs = MSabit.MDbBaglanti.getReadableDatabase();
			String SorguCumle = "SELECT ID, ICIP, DISIP, PORTNUMARASI, SUBEADI FROM TBLNPARAMAYAR LIMIT 1";
			Cursor Crs = DtBs.rawQuery(SorguCumle, null);
			
			while (Crs.moveToNext()) {
				this.setID(Crs.getInt(0));
				this.setICIP(Crs.getString(1));
				this.setDISIP(Crs.getString(2));
				this.setPORTNUMARASI(Crs.getString(3));
				this.setSUBEADI(Crs.getString(4));
			}
			
			DtBs.close();
			
		} catch (Exception e) {
			Sonuc = new MHata();
			Sonuc.setBASLIK("Ayar");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Getirme Ýþlemi Baþarýsýz!");
		}
		
		return Sonuc;
	}
	
	public MHata SilParamAyar(int _ID) {
		MHata Sonuc = new MHata();
		try {
			SQLiteDatabase DtBs = MSabit.MDbBaglanti.getWritableDatabase();
			DtBs.delete("TBLNPARAMAYAR", "ID=?", new String[] {Integer.toString(_ID)});
			DtBs.close();
		} catch (Exception e) {
			Sonuc = new MHata();
			Sonuc.setBASLIK("Parametre Ayar");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Sime Baþarýsýz!");
		}
		
		return Sonuc;
	}
	
	public MHata GuncelParamAyar() {
		MHata Sonuc = new MHata();
		
		return Sonuc;
	}
	
	public MHata KaydetParamAyar() {
		MHata Sonuc = new MHata();

		try {
		
			SQLiteDatabase DtBs = MSabit.MDbBaglanti.getWritableDatabase();
			
			ContentValues Values = new ContentValues();
			Values.put("ICIP", this.getICIP());
			Values.put("DISIP", this.getDISIP());
			Values.put("PORTNUMARASI", this.getPORTNUMARASI());
			Values.put("SUBEADI", this.getSUBEADI());
			
			DtBs.insert("TBLNPARAMAYAR", null, Values);
			
			DtBs.close();
		
		} catch (Exception e) {
			Sonuc = new MHata();
			Sonuc.setBASLIK("Ayar");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Kaydetme Ýþlemi Baþarýsýz!");
		}
		
		return Sonuc;
	}
	
	public int ParametreSayisi() {
		int Sayac = 0;
		try {
			SQLiteDatabase DtBs = MSabit.MDbBaglanti.getReadableDatabase();
			String Sorgu = "SELECT COUNT(*) AS PARAMETRESAYISI FROM TBLNPARAMAYAR";
			Cursor Crs = DtBs.rawQuery(Sorgu, null);
			
			while(Crs.moveToNext()) {
				Sayac = Crs.getInt(0);
			}
			
			DtBs.close();
		} catch (Exception e) {
			MHata Sonuc = new MHata();
			Sonuc.setBASLIK("Parametre Ayar");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Ýþlem Baþarýsýz!");
		}
		
		return Sayac;
	}
}
