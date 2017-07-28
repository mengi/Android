package com.chatt.demo.custom;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.http.conn.util.InetAddressUtils;

import android.util.Log;

import com.chatt.demo.db.ODataBase;
import com.chatt.demo.model.MAyar;
import com.chatt.demo.model.MKullanici;
import com.chatt.demo.model.MMasa;
import com.chatt.demo.model.MUrunList;
import com.chatt.demo.model.MUrunUstListe;
import com.chatt.demo.model.MSiparis;

public class RSabit {
	public static ODataBase OpDbBaglanti;
	public static MAyar MAyar;
	public static MKullanici MKullanici;
	public static MMasa MMasa;
	public static MMasa ZiyaretMasa;
	public static MUrunList MUrunListe;
	public static MUrunUstListe MUrunUstListe;
	public static MSiparis MSiparis;
	
	public static String KullaniciId = "";
	public static String MasaAdi = "";
	public static String MasaId = "";
	
	public static double Cevir(String Fiyats) {
		String FiyatCevir  = "";
		try {
			for (int i = 0; i < Fiyats.length(); i++) {
				if (Fiyats.charAt(i) != ' ') {
					FiyatCevir += Fiyats.charAt(i);
				} else {
					break;
				}
			}
		} catch (Exception e) {
			FiyatCevir = "0.0";
		}
		return Double.parseDouble(FiyatCevir);
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
}
