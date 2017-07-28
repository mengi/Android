package com.android.mdl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.fuk.MHata;
import com.android.fuk.MSabit;

public class MTextName {

	public int ID, SUBEID, ROLEID;

	public String TEXTNAME, TEXTTIP;

	public MTextName() {
		this.ID = -1;
		this.SUBEID = -1;
		this.ROLEID = -1;
		this.TEXTNAME = "";
		this.TEXTTIP = "";
	}

	public MTextName(int SUBEID, int ROLEID, String TEXTNAME, String TEXTTIP) {
		this.setSUBEID(SUBEID);
		this.setTEXTNAME(TEXTNAME);
		this.setTEXTTIP(TEXTTIP);
		this.setROLEID(ROLEID);
	}

	public MHata EkleTextName() {
		MHata Sonuc = new MHata();
		try {
			SQLiteDatabase Dtbs = MSabit.MDbBaglanti.getWritableDatabase();

			ContentValues Values = new ContentValues();

			Values.put("SUBEID", this.getSUBEID());
			Values.put("TEXTNAME", this.getTEXTNAME());
			Values.put("TEXTTIP", this.getTEXTTIP());
			Values.put("ROLEID", this.getROLEID());

			Dtbs.insert("TBLNTEXTNAME", null, Values);
			Dtbs.close();

		} catch (Exception e) {
			Sonuc = new MHata();
			Sonuc.setBASLIK("Text Ýsim");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Kayýt Baþarýsýz !");
		}
		return Sonuc;
	}

	public MHata GuncelleTextName() {
		MHata Sonuc = new MHata();

		try {
			SQLiteDatabase DtBs = MSabit.MDbBaglanti.getWritableDatabase();
			String SorguCumle = "UPDATE TBLNTEXTNAME SET TEXTNAME = '"
					+ this.getTEXTNAME() + "' WHERE SUBEID='"
					+ this.getSUBEID() + "' AND TEXTTIP='" + this.getTEXTTIP()
					+ "' AND ROLEID='" + this.getROLEID() + "'";
			DtBs.execSQL(SorguCumle);
			DtBs.close();
		} catch (Exception e) {
			Sonuc = new MHata();
			Sonuc.setBASLIK("Text Ýsim");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Kayýt Baþarýsýz !");
		}
		return Sonuc;
	}

	public boolean VarMiTextName() {
		boolean Durum = false;
		try {
			SQLiteDatabase DtBs = MSabit.MDbBaglanti.getReadableDatabase();
			String SorguCumle = "SELECT COUNT(*) AS SAY FROM TBLNTEXTNAME WHERE SUBEID='"
					+ this.getSUBEID()
					+ "' AND TEXTTIP='"
					+ this.getTEXTTIP()
					+ "' AND ROLEID='" + this.getROLEID() + "'";

			Cursor Crs = DtBs.rawQuery(SorguCumle, null);

			while (Crs.moveToNext()) {
				if (Crs.getInt(0) != 0) {
					Durum = true;
				}
			}
		} catch (Exception e) {
			MHata Sonuc = new MHata();
			Sonuc.setBASLIK("Text Ýsim");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Ýþlem Baþarýsýz");
		}
		return Durum;
	}

	public int getROLEID() {
		return ROLEID;
	}

	public void setROLEID(int rOLEID) {
		ROLEID = rOLEID;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getTEXTTIP() {
		return TEXTTIP;
	}

	public void setTEXTTIP(String tEXTTIP) {
		TEXTTIP = tEXTTIP;
	}

	public int getSUBEID() {
		return SUBEID;
	}

	public void setSUBEID(int sUBEID) {
		SUBEID = sUBEID;
	}

	public String getTEXTNAME() {
		return TEXTNAME;
	}

	public void setTEXTNAME(String tEXTNAME) {
		TEXTNAME = tEXTNAME;
	}

}
