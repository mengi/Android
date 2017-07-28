package com.menginarsoft.control.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ODataBase extends SQLiteOpenHelper {
		static final String DATABASE_ISIM = "SmartHome.db4";
		private static int VERSION = 1;
		
		public static final String TBLNKULLANICI = "CREATE TABLE TBLNKULLANICI (userID INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "ADSOYAD VARCHAR(75), PAROLA VARCHAR(50), PAROLATEKRAR VARCHAR(50))";
		
		public static final String TBLNPARAMAYAR = "CREATE TABLE TBLNPARAMAYAR (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "IPADRES VARCHAR(15), PORT VARCHAR(5))";
		
		public static final String TBLNROLE = "CREATE TABLE TBLNROLE (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "SAATARALIGI VARCHAR(15), TIP VARCHAR(5))";
	

		public ODataBase(Context context) {
			super(context, DATABASE_ISIM, null, VERSION);
			Log.d("Databse", "Databse Olustu");
		}

		@Override
		public void onCreate(SQLiteDatabase ODB) {
			
			ODB.execSQL(TBLNKULLANICI);
			ODB.execSQL(TBLNPARAMAYAR);
			ODB.execSQL(TBLNROLE);
			
			Log.d("Databse", "Tablo Oluþtu");
		}

		@Override
		public void onUpgrade(SQLiteDatabase ODB, int oldVersion, int newVersion) {
			onCreate(ODB);
		}

}
