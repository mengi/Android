package net.multipi.QrReader.func;

import java.util.ArrayList;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParser extends DefaultHandler {

	public Cursor Crs;
	public String TabloAdi;
	public String SonElement;
	boolean DegerOku;
	public List<ContentValues> Degiskenler = null;

	public String Sonuc = "";
	public String SonucValues = "";

	StringBuilder builder;

	ContentValues Degerler = null;

	public String getKolonTip(String KolonAdi) {
		for (int i = 0; i < Crs.getCount(); i++) {
			Crs.moveToPosition(i);
			if (Crs.getString(1).equals(KolonAdi)) {
				return Crs.getString(2);
			}
		}
		return "";
	}

	@Override
	public void startDocument() throws SAXException {

		Degiskenler = new ArrayList<ContentValues>();

		SQLiteDatabase DtBs = Sabit.MDbBaglanti.getWritableDatabase();
		Crs = DtBs.rawQuery("PRAGMA table_info(" + TabloAdi + ")", null);

	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		builder = new StringBuilder();

		if (localName.equals("Veriler")) {
			DegerOku = false;
		} else if (localName.equals("Table")) {
			Degerler = new ContentValues();
			DegerOku = false;
		} else {
			DegerOku = true;
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (!localName.equals("Table") && !localName.equals("Veriler")) {
			// Degerler.put(localName, builder.toString());

			String KolonTipi = getKolonTip(localName.toString());

			if (!KolonTipi.equals("".toString())) {
				if (KolonTipi.equals("INTEGER")) {
					Degerler.put(localName,
							Integer.parseInt(builder.toString()));
				} else {
					String KolonTipiIki = KolonTipi.substring(0, 7).toString();
					String KolonTipiUc = KolonTipi.substring(0, 4).toString();
					if (KolonTipiIki.equals("DECIMAL")) {
						Degerler.put(localName,
								Double.parseDouble(builder.toString()));
					} else if (KolonTipiIki.equals("VARCHAR")) {
						Degerler.put(localName, builder.toString());
					} else if (KolonTipiUc.equals("CHAR")) {
						Degerler.put(localName, builder.toString());
					} else {
						Degerler.put(localName, builder.toString());

					}
				}
			}

		} else {
			if (localName.equals("Table")) {
				Degiskenler.add(Degerler);
			}
		}

	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		String tempString = new String(ch, start, length);
		builder.append(tempString);
	}
}