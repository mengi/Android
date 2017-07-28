package com.android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ODataBase extends SQLiteOpenHelper {
	static final String DATABASE_ISIM = "afcsocket.db4";
	private static int VERSION = 1;

	public static final String TBLNKULLANICI = "CREATE TABLE TBLNKULLANICI (userID INTEGER, adsoyad VARCHAR(75), "
			+ "telefon VARCHAR(25), lisanskey VARCHAR(50), bitis VARCHAR(50), durum VARCHAR(25), kartsayisi VARCHAR(5), subesayisi VARCHAR(5))";

	public static final String TBLNPARAMAYAR = "CREATE TABLE TBLNPARAMAYAR (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ "SUBEADI VARCHAR(25), ICIP VARCHAR(25), DISIP VARCHAR(25), PORTNUMARASI VARCHAR(5))";

	public static final String TBLNTEXTNAME = "CREATE TABLE TBLNTEXTNAME (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ "SUBEID INTEGER, TEXTNAME VARCHAR(25), TEXTTIP VARCHAR(5), ROLEID INTEGER)";

	public ODataBase(Context context) {
		super(context, DATABASE_ISIM, null, VERSION);
		Log.d("Databse", "Databse Olustu");
	}

	@Override
	public void onCreate(SQLiteDatabase ODB) {

		ODB.execSQL(TBLNKULLANICI);
		ODB.execSQL(TBLNPARAMAYAR);
		ODB.execSQL(TBLNTEXTNAME);

		Log.d("Databse", "Tablo Oluþtu");
	}

	@Override
	public void onUpgrade(SQLiteDatabase ODB, int oldVersion, int newVersion) {
		onCreate(ODB);
	}

}
