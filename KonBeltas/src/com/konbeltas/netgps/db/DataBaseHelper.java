package com.konbeltas.netgps.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

	static final String DATABASE_ISIM = "NetGPS.db3";
	private static int VERSION = 1;
	
	public static final String TBLOTOPARKBILGI = "CREATE TABLE TBLOTOPARKBILGI (OTOPARKADI VARCHAR(100), KAPASITE VARCHAR(5), "
			+ "OTOPARKKODU VARCHAR(5), YIKAMA VARCHAR(5), HIZMETSURESI VARCHAR(30), ENLEM VARCHAR(15), BOYLAM VARCHAR(15))";

	public DataBaseHelper(Context context) {
		super(context, DATABASE_ISIM, null, VERSION);
		Log.d("Databse", "Databse Olustu");
	}

	@Override
	public void onCreate(SQLiteDatabase ODB) {
		
		ODB.execSQL(TBLOTOPARKBILGI);
		
		Log.d("Databse", "Tablo Oluþtu");
	}

	@Override
	public void onUpgrade(SQLiteDatabase ODB, int oldVersion, int newVersion) {
		onCreate(ODB);
	}

}
