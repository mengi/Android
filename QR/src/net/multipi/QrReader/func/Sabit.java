package net.multipi.QrReader.func;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import net.multipi.QrReader.database.ODataBase;
import net.multipi.QrReader.model.FaturaHar;
import net.multipi.QrReader.model.FaturaUst;
import net.multipi.QrReader.model.KullaniciAyar;

import org.apache.http.conn.util.InetAddressUtils;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;


public class Sabit {
	public static ODataBase MDbBaglanti;
	public static KullaniciAyar Ayar;
	public static FaturaUst FaturaUst;
	public static FaturaHar FaturaHar;
	public static int TutFaturaId;
	public static String TutUrunKodu = "";
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
