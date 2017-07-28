package com.chatt.demo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ODataBase extends SQLiteOpenHelper {
	static final String DATABASE_ISIM = "restdb.db4";
	private static int VERSION = 1;


	public static final String TBL_NAYAR = "CREATE TABLE TBL_NAYAR (IPADRES VARCHAR(15))";

	public static final String TBL_NKULLANICI = "CREATE TABLE TBL_NKULLANICI (KULLANICIADI VARCHAR(55), PAROLA VARCHAR(55), BULUNDUGUYER VARCHAR(55), GARSONKOD VARCHAR(55))";
	public static final String TBL_NMASA = "CREATE TABLE TBL_NMASA (ID VARCHAR(11), MASAADI VARCHAR(55), MASADURUMU VARCHAR(55), SIPARISDURUMU VARCHAR(55), SIPARISID VARCHAR(11))";
	public static final String TBL_NURUNGRUP = "CREATE TABLE TBL_NURUNGRUP (ID VARCHAR(11), URUNGRUPADI VARCHAR(55))";
	public static final String TBL_NURUNLER = "CREATE TABLE TBL_NURUNLER (STOKID VARCHAR(11), ACIKLAMA VARCHAR(50), FIYAT DECIMAL(15, 2), URUNGRUPID VARCHAR(11))";
	public static final String TBL_NSIPARIS = "CREATE TABLE TBL_NSIPARIS (KULLANICIID VARCHAR(15), MASAID VARCHAR(11), MASAADI VARCHAR(50), " +
			"URUNADI VARCHAR(11), FIYAT DECIMAL(15, 2), MIKTAR VARCHAR(11), AKTARILDIMI VARCHAR(3), STOKID VARCHAR(11))";

	public ODataBase(Context context) {
		super(context, DATABASE_ISIM, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase ODB) {
		ODB.execSQL(TBL_NAYAR);
		ODB.execSQL(TBL_NKULLANICI);
		ODB.execSQL(TBL_NMASA);
		ODB.execSQL(TBL_NURUNGRUP);
		ODB.execSQL(TBL_NURUNLER);
		ODB.execSQL(TBL_NSIPARIS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase ODB, int oldVersion, int newVersion) {
		onCreate(ODB);
	}

}
