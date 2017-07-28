package com.menginarsoft.control.function;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;

import org.apache.http.conn.util.InetAddressUtils;

import com.menginarsoft.control.database.ODataBase;
import com.menginarsoft.control.model.Kullanici;
import com.menginarsoft.control.model.ParamAyar;
import com.menginarsoft.control.model.Role;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;


public class Sabit {
	public static ODataBase MDbBaglanti;
	public static Kullanici MKullanici;
	public static ParamAyar MParamAyar;
	public static String TarihSaat = "";
	public static String Tip = "";
	public static Role MRole;
	public static int mTut = -1;
	public static int KontrolLisansSuresi(String BitisTarihi) {
		
		int Farks = 0;
		if (!(BitisTarihi.equals(""))) {
			int Yil = Integer.parseInt(BitisTarihi.substring(0, 4));
			int Ay = Integer.parseInt(BitisTarihi.substring(5, 7));
			int Gun = Integer.parseInt(BitisTarihi.substring(8, 10));
			
			int Saat = Integer.parseInt(BitisTarihi.substring(11, 13));
			int Dakika = Integer.parseInt(BitisTarihi.substring(14, 16));

			Calendar c = Calendar.getInstance();
			int mYear = c.get(Calendar.YEAR);
			int mMonth = c.get(Calendar.MONTH) + 1;
			int mDay = c.get(Calendar.DAY_OF_MONTH);
			
			int dakika = c.get(Calendar.MINUTE);
			int saat = c.get(Calendar.HOUR_OF_DAY);

			Date VadeTarih = new GregorianCalendar(Yil, Ay, Gun, Saat, Dakika)
					.getTime();
			Date SonTarih = new GregorianCalendar(mYear, mMonth, mDay, saat, dakika)
					.getTime();

			double Fark = (VadeTarih.getTime() - SonTarih.getTime()) / 1000;

			if ((int) Fark <= 0.0) {
				Farks = 0;
			} else {
				Farks = (int) Fark;
			}
		}
		
		return Farks;
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
