package net.multipi.QrReader.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import net.multipi.QrReader.func.Hata;
import net.multipi.QrReader.func.Sabit;

public class FaturaUst {

	Hata Sonuc = new Hata();

	public int faturaID, nakliyeci, siparisalanid, siparisno, faturakesen;

	public String faturaacik, cariID, cariacik, faturatip, teslimtipi,
			teslimsuresi, nakliyeucretitipi, gumruklemeyeri, tarih, durum,
			siparisalan, okunma, siparisonaytrh, aciklama, duzey, aktarildimi;

	public double nakliyeucreti, kdv, toplamfiyat, kur;

	public FaturaUst() {
		this.faturaID = -1;
		this.nakliyeci = -1;
		this.siparisalanid = -1;
		this.siparisno = -1;
		this.faturakesen = -1;
		this.faturaacik = "";
		this.cariID = "";
		this.cariacik = "";
		this.faturatip = "";
		this.teslimtipi = "";
		this.teslimsuresi = "";
		this.nakliyeucretitipi = "";
		this.gumruklemeyeri = "";
		this.tarih = "";
		this.durum = "";
		this.siparisalan = "";
		this.okunma = "";
		this.siparisonaytrh = "";
		this.aciklama = "";
		this.duzey = "";
		this.nakliyeucreti = 0;
		this.kdv = 0;
		this.toplamfiyat = 0;
		this.kur = 0;
		this.aktarildimi = "0";
	}

	public Hata GetirFaturaUstById() {

		try {

			SQLiteDatabase DtBs = Sabit.MDbBaglanti.getReadableDatabase();
			String SorguCumle = "SELECT faturaID, nakliyeci, siparisalanid, siparisno, faturakesen, faturaacik, cariID, cariacik, faturatip, teslimtipi, "
					+ "teslimsuresi,  nakliyeucretitipi, gumruklemeyeri, tarih, durum, siparisalan, okunma, siparisonaytrh, aciklama , duzey, nakliyeucreti, kdv , "
					+ "toplamfiyat, kur, aktarildimi FROM TBLNFATURAUST";

			Cursor Crs = DtBs.rawQuery(SorguCumle, null);
			while (Crs.moveToNext()) {
				this.setFaturaID(Crs.getInt(0));
				this.setNakliyeci(Crs.getInt(1));
				this.setSiparisalanid(Crs.getInt(2));
				this.setSiparisno(Crs.getInt(3));
				this.setFaturakesen(Crs.getInt(4));
				this.setFaturaacik(Crs.getString(5));
				this.setCariID(Crs.getString(6));
				this.setCariacik(Crs.getString(7));
				this.setFaturatip(Crs.getString(8));
				this.setTeslimtipi(Crs.getString(9));
				this.setTeslimsuresi(Crs.getString(10));
				this.setNakliyeucretitipi(Crs.getString(11));
				this.setGumruklemeyeri(Crs.getString(12));
				this.setTarih(Crs.getString(13));
				this.setDurum(Crs.getString(14));
				this.setSiparisalan(Crs.getString(15));
				this.setOkunma(Crs.getString(16));
				this.setSiparisonaytrh(Crs.getString(17));
				this.setAciklama(Crs.getString(18));
				this.setDuzey(Crs.getString(19));
				this.setNakliyeucreti(Crs.getDouble(20));
				this.setKdv(Crs.getDouble(21));
				this.setToplamfiyat(Crs.getDouble(22));
				this.setKur(Crs.getDouble(23));
				this.setAktarildimi(Crs.getString(24));
			}

			DtBs.close();
		} catch (Exception e) {
			Sonuc = new Hata();
			Sonuc.setBASLIK("Fatura");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Gtirme Baþarýsýz!");
		}
		return Sonuc;
	}

	public Hata GuncelleFaturaUst(int _FaturaID) {
		try {
			SQLiteDatabase DtBs = Sabit.MDbBaglanti.getWritableDatabase();
			DtBs.execSQL("UPDATE TBLNFATURAUST SET faturakesen='1' WHERE faturaID="
					+ Integer.toString(_FaturaID));

			DtBs.close();
		} catch (Exception e) {
			Sonuc = new Hata();
			Sonuc.setBASLIK("Fatura");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Güncelleme Baþarýsýz!");
		}
		return Sonuc;
	}

	public Hata IptalFaturaUst(int _faturaID) {
		
		try {
			SQLiteDatabase DtBs = Sabit.MDbBaglanti.getWritableDatabase();
			DtBs.execSQL("UPDATE TBLNFATURAUST SET faturakesen='0' WHERE faturaID="
					+ Integer.toString(_faturaID));
			DtBs.close();
		
		} catch (Exception e) {
			Sonuc = new Hata();
			Sonuc.setBASLIK("Fatura");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Güncelleme Baþarýsýz!");
		}
		
		try {
			SQLiteDatabase DtBs = Sabit.MDbBaglanti.getReadableDatabase();
			String SorguCumle = "SELECT faturasepetiID FROM TBLNFATURAHAR WHERE faturaID='"+Integer.toString(_faturaID)+"'";
			Cursor Crs = DtBs.rawQuery(SorguCumle, null);

			while (Crs.moveToNext()) {
				Sabit.FaturaHar = new FaturaHar();
				Sabit.FaturaHar.IptalFaturaHar(Crs.getInt(0));
			}
			
			DtBs.close();
		} catch (Exception e) {
			Sonuc = new Hata();
			Sonuc.setBASLIK("Fatura");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Güncelleme Baþarýsýz!");
		}
		return Sonuc;
	}

	public String getAktarildimi() {
		return aktarildimi;
	}

	public void setAktarildimi(String aktarildimi) {
		this.aktarildimi = aktarildimi;
	}

	public int getFaturaID() {
		return faturaID;
	}

	public void setFaturaID(int faturaID) {
		this.faturaID = faturaID;
	}

	public int getNakliyeci() {
		return nakliyeci;
	}

	public void setNakliyeci(int nakliyeci) {
		this.nakliyeci = nakliyeci;
	}

	public int getSiparisalanid() {
		return siparisalanid;
	}

	public void setSiparisalanid(int siparisalanid) {
		this.siparisalanid = siparisalanid;
	}

	public int getSiparisno() {
		return siparisno;
	}

	public void setSiparisno(int siparisno) {
		this.siparisno = siparisno;
	}

	public int getFaturakesen() {
		return faturakesen;
	}

	public void setFaturakesen(int faturakesen) {
		this.faturakesen = faturakesen;
	}

	public String getFaturaacik() {
		return faturaacik;
	}

	public void setFaturaacik(String faturaacik) {
		this.faturaacik = faturaacik;
	}

	public String getCariID() {
		return cariID;
	}

	public void setCariID(String cariID) {
		this.cariID = cariID;
	}

	public String getCariacik() {
		return cariacik;
	}

	public void setCariacik(String cariacik) {
		this.cariacik = cariacik;
	}

	public String getFaturatip() {
		return faturatip;
	}

	public void setFaturatip(String faturatip) {
		this.faturatip = faturatip;
	}

	public String getTeslimtipi() {
		return teslimtipi;
	}

	public void setTeslimtipi(String teslimtipi) {
		this.teslimtipi = teslimtipi;
	}

	public String getTeslimsuresi() {
		return teslimsuresi;
	}

	public void setTeslimsuresi(String teslimsuresi) {
		this.teslimsuresi = teslimsuresi;
	}

	public String getNakliyeucretitipi() {
		return nakliyeucretitipi;
	}

	public void setNakliyeucretitipi(String nakliyeucretitipi) {
		this.nakliyeucretitipi = nakliyeucretitipi;
	}

	public String getGumruklemeyeri() {
		return gumruklemeyeri;
	}

	public void setGumruklemeyeri(String gumruklemeyeri) {
		this.gumruklemeyeri = gumruklemeyeri;
	}

	public String getTarih() {
		return tarih;
	}

	public void setTarih(String tarih) {
		this.tarih = tarih;
	}

	public String getDurum() {
		return durum;
	}

	public void setDurum(String durum) {
		this.durum = durum;
	}

	public String getSiparisalan() {
		return siparisalan;
	}

	public void setSiparisalan(String siparisalan) {
		this.siparisalan = siparisalan;
	}

	public String getOkunma() {
		return okunma;
	}

	public void setOkunma(String okunma) {
		this.okunma = okunma;
	}

	public String getSiparisonaytrh() {
		return siparisonaytrh;
	}

	public void setSiparisonaytrh(String siparisonaytrh) {
		this.siparisonaytrh = siparisonaytrh;
	}

	public String getAciklama() {
		return aciklama;
	}

	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
	}

	public String getDuzey() {
		return duzey;
	}

	public void setDuzey(String duzey) {
		this.duzey = duzey;
	}

	public double getNakliyeucreti() {
		return nakliyeucreti;
	}

	public void setNakliyeucreti(double nakliyeucreti) {
		this.nakliyeucreti = nakliyeucreti;
	}

	public double getKdv() {
		return kdv;
	}

	public void setKdv(double kdv) {
		this.kdv = kdv;
	}

	public double getToplamfiyat() {
		return toplamfiyat;
	}

	public void setToplamfiyat(double toplamfiyat) {
		this.toplamfiyat = toplamfiyat;
	}

	public double getKur() {
		return kur;
	}

	public void setKur(double kur) {
		this.kur = kur;
	}

}
