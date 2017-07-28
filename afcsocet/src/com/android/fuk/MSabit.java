package com.android.fuk;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;

import org.apache.http.conn.util.InetAddressUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.afcsocet.Kontrol;
import com.android.db.ODataBase;
import com.android.mdl.MKullanici;
import com.android.mdl.MParamAyar;
import com.android.mdl.MTextName;

public class MSabit {
	public static ODataBase MDbBaglanti;
	public static MKullanici Kullanici;
	public static MParamAyar PAyar;
	public static String MPort = "";
	public static String MSeriNumara;
	public static String MIpAdresi;
	public static String BtnTip;
	public static String GelenCevap;
	public static String MUrl = "";
	public static String MSubeAdi = "";
	public static int MSubeID = -1;
	public static MTextName MTextIsim;

	public static void SorDisMiIcMi(final Context Conn, final boolean Durum,
			final String SubeAdi, final Activity Activity, final String RoleSay) {

		final CharSequence[] Ipler = { "Ýç", "Dýþ" };
		AlertDialog.Builder alert = new AlertDialog.Builder(Conn);
		alert.setTitle("Ýp Durumunuzu Seçin");
		alert.setSingleChoiceItems(Ipler, -1,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (Ipler[which].equals("Ýç")) {

							MSabit.PAyar.GetirParamAyarSubeAd(SubeAdi);

							if (!MSabit.PAyar.getICIP().equals("")) {

								MSabit.MUrl = MSabit.PAyar.getICIP();
								MSabit.MPort = MSabit.PAyar.getPORTNUMARASI();

								if (Durum) {
									Intent MyIntent = new Intent(Conn,
											Kontrol.class);
									MyIntent.putExtra("KontrolRole", RoleSay);
									Activity.startActivity(MyIntent);
								} else {
									MHata Sonuc = new MHata();
									Sonuc.setBASLIK("Role Kontrol");
									Sonuc.setHATAMI(true);
									Sonuc.setMESAJ("Kontrol - "
											+ RoleSay
											+ " izin tanýmlanmamýþ. Ýletiþime geçiniz");
									Sonuc.GosterMesajToast(Conn);
									return;
								}
							} else {
								MHata Sonuc = new MHata();
								Sonuc.setHATAMI(true);
								Sonuc.setBASLIK("Dýþ, Ýç Ýp");
								Sonuc.setMESAJ("Parametre Ayarlarýný Giriniz");
								Sonuc.GosterMesajToast(Conn);
								return;
							}

						}

						if (Ipler[which].equals("Dýþ")) {

							MSabit.PAyar.GetirParamAyarSubeAd(SubeAdi);

							if (!MSabit.PAyar.getDISIP().equals("")) {

								MSabit.MUrl = MSabit.PAyar.getDISIP();
								MSabit.MPort = MSabit.PAyar.getPORTNUMARASI();

								if (Durum) {
									Intent MyIntent = new Intent(Conn,
											Kontrol.class);
									MyIntent.putExtra("KontrolRole", RoleSay);
									Activity.startActivity(MyIntent);
								} else {
									MHata Sonuc = new MHata();
									Sonuc.setBASLIK("Role Kontrol");
									Sonuc.setHATAMI(true);
									Sonuc.setMESAJ("Kontrol - "
											+ RoleSay
											+ " izin tanýmlanmamýþ. Ýletiþime geçiniz");
									Sonuc.GosterMesajToast(Conn);
									return;
								}
							} else {
								MHata Sonuc = new MHata();
								Sonuc.setHATAMI(true);
								Sonuc.setBASLIK("Dýþ, Ýç Ýp");
								Sonuc.setMESAJ("Parametre Ayarlarýný Giriniz");
								Sonuc.GosterMesajToast(Conn);
								return;
							}
						}
						dialog.dismiss();
					}

				});
		alert.show();
	}

	public static boolean KontrolLisansSuresi() {
		boolean DurumKontrol = true;

		MSabit.Kullanici = new MKullanici();
		MSabit.Kullanici.GetirKullanici();

		String BitisTarihi = MSabit.Kullanici.getBitis();

		if (!(BitisTarihi.equals(""))) {
			int Yil = Integer.parseInt(BitisTarihi.substring(0, 4));
			int Ay = Integer.parseInt(BitisTarihi.substring(5, 7));
			int Gun = Integer.parseInt(BitisTarihi.substring(8, 10));

			Calendar c = Calendar.getInstance();
			int mYear = c.get(Calendar.YEAR);
			int mMonth = c.get(Calendar.MONTH) + 1;
			int mDay = c.get(Calendar.DAY_OF_MONTH);

			Date VadeTarih = new GregorianCalendar(Yil, Ay, Gun, 00, 00)
					.getTime();
			Date SonTarih = new GregorianCalendar(mYear, mMonth, mDay, 00, 00)
					.getTime();

			double Fark = (VadeTarih.getTime() - SonTarih.getTime())
					/ (1000 * 60 * 60 * 24);

			if ((int) Fark >= 0.0) {
				DurumKontrol = false;
			} else {
				DurumKontrol = true;
			}
		}
		return DurumKontrol;
	}

	public static ArrayList<String> GetirSubeler() {
		ArrayList<String> Subeler = new ArrayList<String>();

		try {
			SQLiteDatabase Dtbs = MDbBaglanti.getReadableDatabase();
			String SorguCumle = "SELECT SUBEADI FROM TBLNPARAMAYAR ORDER BY SUBEADI";
			Cursor Crs = Dtbs.rawQuery(SorguCumle, null);
			while (Crs.moveToNext()) {
				Subeler.add(Crs.getString(0));
			}
			Dtbs.close();
		} catch (Exception e) {

		}

		return Subeler;
	}

	public static String GetirTextNames(int _SUBEID, String _TEXTTIP,
			int _ROLEID) {
		MHata Sonuc = new MHata();
		String TextName = "";
		try {
			SQLiteDatabase DtBs = MSabit.MDbBaglanti.getReadableDatabase();
			String SorguCumle = "SELECT TEXTNAME FROM TBLNTEXTNAME WHERE SUBEID='"
					+ Integer.toString(_SUBEID)
					+ "' AND TEXTTIP='"
					+ _TEXTTIP
					+ "' AND ROLEID='" + _ROLEID + "'";

			Cursor Crs = DtBs.rawQuery(SorguCumle, null);

			while (Crs.moveToNext()) {
				TextName = Crs.getString(0);
			}
		} catch (Exception e) {
			Sonuc = new MHata();
			Sonuc.setBASLIK("Text Ýsim");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Ýþlem Baþarýsýz");
		}
		return TextName;
	}

	public static int GetirPAyarID(String SubeAdi) {
		MHata Sonuc = new MHata();
		int ID = -1;
		try {
			SQLiteDatabase DtBs = MSabit.MDbBaglanti.getReadableDatabase();
			String SorguCumle = "SELECT ID FROM TBLNPARAMAYAR WHERE SUBEADI='"
					+ SubeAdi + "'";

			Cursor Crs = DtBs.rawQuery(SorguCumle, null);

			while (Crs.moveToNext()) {
				ID = Crs.getInt(0);
			}
		} catch (Exception e) {
			Sonuc = new MHata();
			Sonuc.setBASLIK("Text Ýsim");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Ýþlem Baþarýsýz");
		}
		return ID;
	}

	public static String GetirIpAdresi() {
		String ipadresi = "";
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()
							&& InetAddressUtils.isIPv4Address(inetAddress
									.getHostAddress())) {
						ipadresi = inetAddress.getHostAddress();
					}
				}
			}

		} catch (SocketException ex) {
			Log.d("", ex.toString());
		}
		return ipadresi;
	}

	public static String GetirSeriNumara(Context context) {
		TelephonyManager tManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String UID = tManager.getDeviceId();
		return UID;
	}
}
