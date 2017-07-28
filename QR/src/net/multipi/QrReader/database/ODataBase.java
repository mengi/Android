package net.multipi.QrReader.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ODataBase extends SQLiteOpenHelper {
	static final String DATABASE_ISIM = "NetBarkod.db4";
	private static int VERSION = 1;

	public static final String TBLNKULLANICI = "CREATE TABLE TBLNKULLANICI (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ "KULLANICIKODU VARCHAR(15), KULLANICIADI VARCHAR(75), PAROLA VARCHAR(50))";

	public static final String TBLNFATURAHAR = "CREATE TABLE TBLNFATURAHAR (faturasepetiID INTEGER, faturaID INTEGER, stokID stokID VARCHAR(100), urunkodu VARCHAR(100),"
			+ "stokacik VARCHAR(100), miktar DECIMAL(10, 2), birimfiyat DECIMAL(10, 2), faturatarihi VARCHAR(100), durum VARCHAR(100), tfiyat DECIMAL(10, 2), iskonto DECIMAL(10, 2), "
			+ "iskontolufiyat DECIMAL(10, 2), vade VARCHAR(100), gonderimtipi VARCHAR(100), duzey INTEGER)";

	public static final String TBLNFATURAUST = "CREATE TABLE TBLNFATURAUST (faturaID INTEGER, nakliyeci INTEGER, siparisalanid INTEGER, siparisno INTEGER, faturakesen INTEGER, "
			+ "faturaacik VARCHAR(55), cariID VARCHAR(55), cariacik VARCHAR(55), faturatip VARCHAR(55), teslimtipi VARCHAR(55), teslimsuresi VARCHAR(55), nakliyeucretitipi VARCHAR(55), "
			+ "siparisalan VARCHAR(55), gumruklemeyeri VARCHAR(55), tarih VARCHAR(55), durum VARCHAR(55), okunma VARCHAR(55), siparisonaytrh VARCHAR(55), aciklama VARCHAR(55), "
			+ "duzey VARCHAR(55), aktarildimi VARCHAR(55), nakliyeucreti DECIMAL(10, 2), kdv DECIMAL(10, 2), toplamfiyat DECIMAL(10, 2), kur DECIMAL(10, 2))";

	public ODataBase(Context context) {
		super(context, DATABASE_ISIM, null, VERSION);
		Log.d("Databse", "Databse Olustu");
	}

	@Override
	public void onCreate(SQLiteDatabase ODB) {

		ODB.execSQL(TBLNKULLANICI);
		ODB.execSQL(TBLNFATURAHAR);
		ODB.execSQL(TBLNFATURAUST);

		Log.d("Databse", "Tablo Oluþtu");
	}

	@Override
	public void onUpgrade(SQLiteDatabase ODB, int oldVersion, int newVersion) {
		onCreate(ODB);
	}

}
