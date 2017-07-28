package com.menginarsoft.control.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.menginarsoft.control.function.Hata;
import com.menginarsoft.control.function.Sabit;

public class ParamAyar {

	public int ID;
	public String IPADRES, PORT;
	Hata Sonuc = new Hata();
	
	public ParamAyar() {
		this.ID = -1;
		this.IPADRES = "";
		this.PORT = "";
	}
	
	public ParamAyar(String IpAdres, String Port) {
		this.IPADRES = IpAdres;
		this.PORT = Port;
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getIPADRES() {
		return IPADRES;
	}

	public void setIPADRES(String iPADRES) {
		IPADRES = iPADRES;
	}

	public String getPORT() {
		return PORT;
	}

	public void setPORT(String pORT) {
		PORT = pORT;
	}

	public Hata KaydetParamAyar() {
		
		try {
			
			SQLiteDatabase DtBs = Sabit.MDbBaglanti.getWritableDatabase();
			DtBs.delete("TBLNPARAMAYAR", null, null);
			
			ContentValues Values = new ContentValues();
			Values.put("IPADRES", this.getIPADRES());
			Values.put("PORT",  this.getPORT());
			
			DtBs.insert("TBLNPARAMAYAR", null, Values);
			
			DtBs.close();
			
		} catch (Exception e) {
			Sonuc = new Hata();
			Sonuc.setBASLIK("Parametre Ayarlarý");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Baþarýsýz!");
		}
		
		return Sonuc;
	}
	
	public Hata GetirParamAyar() {
		try {
			SQLiteDatabase DtBs = Sabit.MDbBaglanti.getReadableDatabase();
			String Sorgu = "SELECT ID, IPADRES, PORT FROM TBLNPARAMAYAR LIMIT 1";
			
			Cursor Crs = DtBs.rawQuery(Sorgu, null);
			
			while (Crs.moveToNext()) {
				this.setID(Crs.getInt(0));
				this.setIPADRES(Crs.getString(1));
				this.setPORT(Crs.getString(2));
			}
			
			DtBs.close();
		} catch (Exception e) {
			Sonuc = new Hata();
			Sonuc.setBASLIK("Parametre Ayarlarý");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Baþarýsýz!");
		}
		
		return Sonuc;
	}
}
