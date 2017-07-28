package com.konbeltas.netgps.func;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

import org.apache.http.conn.util.InetAddressUtils;

import android.util.Log;

import com.konbeltas.netgps.db.DataBaseHelper;
import com.konbeltas.netgps.model.MDetay;
import com.konbeltas.netgps.model.OtoparkBilgi;

public class MSabit {
	public static DataBaseHelper MDbBaglanti;
	public static OtoparkBilgi MOtoparkBilgi;
	public static MDetay MOtoparkDetay;
	public static ArrayList<MDetay> MDetaylar;
 	
	public static String OtoparkAdi = "";
	public static String Kapasite = "";
	public static String HizmetSuresi = "";
	public static String Bos = "";
	public static String Dolu = "";
	public static String Yikama = "";
	public static String OtoparkTipi = "";
	
	public static double MEnlem;
	public static double MBoylam;

	public static int[] selectionsort(int[] A, int n) {
		int tmp;
		int min;

		for (int i = 0; i < n - 1; i++) {
			min = i;

			for (int j = i; j < n; j++) {
				if (A[j] < A[min]) {

					min = j;
				}
			}
			tmp = A[i];
			A[i] = A[min];
			A[min] = tmp;
		}
		return A;
	}
	
	public static String CevirSureTurkce(String Sure) {
		String CevirSure = "";
		int Sayac = 0;
		
		if (Sure.length() <= 7) {
			for (int i = 0; i < Sure.length(); i++) {
				if (Sayac == 0) {
					if (Sure.charAt(i) != ' ') {
						CevirSure += Sure.charAt(i);
					}	
				}
				
				if (Sure.charAt(i) == ' ') {
					Sayac += 1;
					if (Sayac == 1) {
						CevirSure += " dakika";
					}	 
				}
			}
		}
		
		if (Sure.length() > 7) {
			for (int i = 0; i < Sure.length(); i++) {
				if (Sayac == 0) {
					if (Sure.charAt(i) != ' ') {
						CevirSure += Sure.charAt(i);
					}	
				}
				
				if (Sure.charAt(i) == ' ') {
					Sayac += 1;
					if (Sayac == 1) {
						CevirSure += " saat";
					}	 
				}
				
				if (Sayac == 2) {
					CevirSure += Sure.charAt(i);
				}
			}
			
			CevirSure = CevirSure + " dakika";
		}

		
		return CevirSure;
	}
	
	public static String CevirOtoparkKodu(String _OtoparkKodu) {
		String Cevir = "";
		
		for (int i = 0; i < _OtoparkKodu.length(); i++) {
			if (_OtoparkKodu.charAt(i) == '1') {
				Cevir = "Açýk";
				break;
			}
			
			if (_OtoparkKodu.charAt(i) == '2') {
				Cevir = "Kapalý";
				break;
			}
		}
		
		return Cevir;
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