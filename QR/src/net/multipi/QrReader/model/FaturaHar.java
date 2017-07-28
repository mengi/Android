package net.multipi.QrReader.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import net.multipi.QrReader.func.Hata;
import net.multipi.QrReader.func.Sabit;

public class FaturaHar {

	Hata Sonuc = new Hata();

	public int faturasepetiID, faturaID, duzey;
	public String stokID, urunkodu, stokacik, faturatarihi, durum, vade,
			gonderimtipi, aktarildimi;
	public double birimfiyat, tfiyat, iskonto, iskontolufiyat, miktar;

	public FaturaHar() {
		this.faturaID = -1;
		this.faturasepetiID = -1;
		this.miktar = 0;
		this.duzey = 0;
		this.stokID = "";
		this.urunkodu = "";
		this.stokacik = "";
		this.faturatarihi = "";
		this.durum = "";
		this.vade = "";
		this.gonderimtipi = "";
		this.birimfiyat = 0;
		this.tfiyat = 0;
		this.iskonto = 0;
		this.iskontolufiyat = 0;
		this.aktarildimi = "0";
	}

	public FaturaHar(int faturaID, int duzey, String stokID,
			String urunkodu, String stokacik, String faturatarihi,
			String durum, String vade, String gonderimtipi, String aktarildimi,
			double birimfiyat, double tfiyat, double iskonto, double iskontolufiyat, double miktar) {
		
		
		this.setFaturaID(faturaID);
		this.setMiktar(miktar);
		this.setDuzey(duzey);
		this.setUrunkodu(urunkodu);
		this.setStokacik(stokacik);
		this.setFaturatarihi(faturatarihi);
		this.setDurum(durum);
		this.setVade(vade);
		this.setGonderimtipi(gonderimtipi);
		this.setBirimfiyat(birimfiyat);
		this.setTfiyat(tfiyat);
		this.setIskonto(iskonto);
		this.setIskontolufiyat(iskontolufiyat);
		this.setAktarildimi(aktarildimi);
	}

	public String getAktarildimi() {
		return aktarildimi;
	}

	public void setAktarildimi(String aktarildimi) {
		this.aktarildimi = aktarildimi;
	}

	public Hata GetirFaturaHarById(int _FaturaId) {
		try {
			SQLiteDatabase DtBs = Sabit.MDbBaglanti.getReadableDatabase();
			String SorguCumle = "SELECT faturasepetiID, faturaID, miktar, duzey, stokID, urunkodu, stokacik, faturatarihi, "
					+ "durum, vade, gonderimtipi, birimfiyat, tfiyat, iskonto, iskontolufiyat, aktarildimi FROM TBLNFATURAHAR WHERE faturaID = ?";
			Cursor Crs = DtBs.rawQuery(SorguCumle,
					new String[] { Integer.toString(_FaturaId) });
			while (Crs.moveToNext()) {
				this.setFaturasepetiID(Crs.getInt(0));
				this.setFaturaID(Crs.getInt(1));
				this.setMiktar(Crs.getInt(2));
				this.setDuzey(Crs.getInt(3));
				this.setStokID(Crs.getString(4));
				this.setUrunkodu(Crs.getString(5));
				this.setStokacik(Crs.getString(6));
				this.setFaturatarihi(Crs.getString(7));
				this.setDurum(Crs.getString(8));
				this.setVade(Crs.getString(9));
				this.setGonderimtipi(Crs.getString(10));
				this.setBirimfiyat(Crs.getDouble(11));
				this.setTfiyat(Crs.getDouble(12));
				this.setIskonto(Crs.getDouble(13));
				this.setIskontolufiyat(Crs.getDouble(14));
				this.setAktarildimi(Crs.getString(15));
			}
			
			DtBs.close();
		} catch (Exception e) {
			Sonuc = new Hata();
			Sonuc.setBASLIK("Fatura Hareket");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Gtirme Baþarýsýz!");
		}
		return Sonuc;
	}
	
	public Hata GuncelleFaturaHar(int _FaturaSepetiID) {
		try {
			SQLiteDatabase DtBs = Sabit.MDbBaglanti.getWritableDatabase();
			DtBs.execSQL("UPDATE TBLNFATURAHAR SET duzey='1' WHERE faturasepetiID="
					+ Integer.toString(_FaturaSepetiID));
			DtBs.close();
		} catch (Exception e) {
			Sonuc = new Hata();
			Sonuc.setBASLIK("Fatura Hareket");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Güncelleme Baþarýsýz!");
		}
		return Sonuc;
	}
	
	public void IptalFaturaHar(int _FaturaSepetiID) {
		try {
			SQLiteDatabase DtBs = Sabit.MDbBaglanti.getWritableDatabase();
			DtBs.execSQL("UPDATE TBLNFATURAHAR SET duzey='0' WHERE faturasepetiID="
					+ Integer.toString(_FaturaSepetiID));
			DtBs.close();
		} catch (Exception e) {
			Sonuc = new Hata();
			Sonuc.setBASLIK("Fatura Hareket");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Güncelleme Baþarýsýz!");
		}
	}

	public int getFaturasepetiID() {
		return faturasepetiID;
	}

	public void setFaturasepetiID(int faturasepetiID) {
		this.faturasepetiID = faturasepetiID;
	}

	public int getFaturaID() {
		return faturaID;
	}

	public void setFaturaID(int faturaID) {
		this.faturaID = faturaID;
	}

	public String getStokID() {
		return stokID;
	}

	public void setStokID(String stokID) {
		this.stokID = stokID;
	}

	public String getUrunkodu() {
		return urunkodu;
	}

	public void setUrunkodu(String urunkodu) {
		this.urunkodu = urunkodu;
	}

	public String getStokacik() {
		return stokacik;
	}

	public void setStokacik(String stokacik) {
		this.stokacik = stokacik;
	}

	public double getMiktar() {
		return miktar;
	}

	public void setMiktar(double miktar) {
		this.miktar = miktar;
	}

	public double getBirimfiyat() {
		return birimfiyat;
	}

	public void setBirimfiyat(double birimfiyat) {
		this.birimfiyat = birimfiyat;
	}

	public String getFaturatarihi() {
		return faturatarihi;
	}

	public void setFaturatarihi(String faturatarihi) {
		this.faturatarihi = faturatarihi;
	}

	public String getDurum() {
		return durum;
	}

	public void setDurum(String durum) {
		this.durum = durum;
	}

	public double getTfiyat() {
		return tfiyat;
	}

	public void setTfiyat(double tfiyat) {
		this.tfiyat = tfiyat;
	}

	public double getIskonto() {
		return iskonto;
	}

	public void setIskonto(double iskonto) {
		this.iskonto = iskonto;
	}

	public double getIskontolufiyat() {
		return iskontolufiyat;
	}

	public void setIskontolufiyat(double iskontolufiyat) {
		this.iskontolufiyat = iskontolufiyat;
	}

	public String getVade() {
		return vade;
	}

	public void setVade(String vade) {
		this.vade = vade;
	}

	public String getGonderimtipi() {
		return gonderimtipi;
	}

	public void setGonderimtipi(String gonderimtipi) {
		this.gonderimtipi = gonderimtipi;
	}

	public int getDuzey() {
		return duzey;
	}

	public void setDuzey(int duzey) {
		this.duzey = duzey;
	}

}
