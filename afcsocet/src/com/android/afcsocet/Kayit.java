package com.android.afcsocet;

import java.util.ArrayList;

import com.android.fuk.ListAdapter;
import com.android.fuk.MHata;
import com.android.fuk.MSabit;
import com.android.mdl.MParamAyar;
import com.android.rwmdl.PAyarRow;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Kayit extends Activity {

	EditText SubeAdi, IcIp, DisIp, PortNumarasi;
	Button BtnKaydet, BtnSil, BtnGuncelle;
	ListView LstVw;

	public PAyarRow SecilenPAyarRow = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kayit);

		SubeAdi = (EditText) findViewById(R.id.TxtSubeAdi);
		IcIp = (EditText) findViewById(R.id.TxtIcIp);
		DisIp = (EditText) findViewById(R.id.TxtDisIp);
		PortNumarasi = (EditText) findViewById(R.id.TxtPortNumarasi);

		BtnKaydet = (Button) findViewById(R.id.BtnKaydet);
		BtnSil = (Button) findViewById(R.id.BtnSil);
		BtnGuncelle = (Button) findViewById(R.id.BtnGuncelle);

		BtnKaydet.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (SecilenPAyarRow == null) {
					Kaydet();
					Temizle();
					GetirPAyar();
				} else {
					MSabit.PAyar.SilParamAyar(SecilenPAyarRow.ID);
					Kaydet();
					Temizle();
					GetirPAyar();
				}
			}
		});

		BtnGuncelle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (SecilenPAyarRow != null) {
					YukleSecilenPayar();
					GetirPAyar();
				} else {
					MHata Sonuc = new MHata();
					Sonuc.setHATAMI(true);
					Sonuc.setBASLIK("Kayýt Panel");
					Sonuc.setMESAJ("Alan Seçiniz !!!");
					Sonuc.GosterMesajToast(Kayit.this);
				}
			}
		});

		BtnSil.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (SecilenPAyarRow != null) {
					MHata Sonuc = new MHata();
					Sonuc = MSabit.PAyar.SilParamAyar(SecilenPAyarRow.ID);
					if (!Sonuc.getHATAMI()) {
						GetirPAyar();
					} else {
						Sonuc.GosterMesajToast(Kayit.this);
					}

				} else {
					MHata Sonuc = new MHata();
					Sonuc.setHATAMI(true);
					Sonuc.setBASLIK("Kayýt Panel");
					Sonuc.setMESAJ("Alan Seçiniz !!!");
					Sonuc.GosterMesajToast(Kayit.this);
				}
			}
		});

		LstVw = (ListView) findViewById(R.id.LstVw);
		LstVw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LstVw = (ListView) findViewById(R.id.LstVw);
				SecilenPAyarRow = (PAyarRow) LstVw.getAdapter().getItem(
						position);

				((ListAdapter) LstVw.getAdapter()).KAYMA = false;
				((ListAdapter) LstVw.getAdapter()).FocusId = position;
				((ListAdapter) LstVw.getAdapter()).getView(position, view,
						(ViewGroup) LstVw);
				int EklenecekDeger = 0;
				if (((ListAdapter) LstVw.getAdapter()).LastToId < ((ListAdapter) LstVw
						.getAdapter()).LastId) {
					EklenecekDeger = ((ListAdapter) LstVw.getAdapter()).LastId
							- LstVw.getChildCount() + 1;
				} else {
					EklenecekDeger = ((ListAdapter) LstVw.getAdapter()).LastId;
				}
				for (int i = 0; i < LstVw.getChildCount(); i++) {
					((ListAdapter) LstVw.getAdapter()).getView(i
							+ EklenecekDeger, LstVw.getChildAt(i),
							(ViewGroup) LstVw);
				}
			};
		});
		GetirPAyar();
	}

	public void Temizle() {
		SubeAdi.setText("");
		DisIp.setText("");
		IcIp.setText("");
		PortNumarasi.setText("");
	}

	public void YukleSecilenPayar() {
		try {
			MSabit.PAyar.GetirParamAyarId(SecilenPAyarRow.ID);
			SubeAdi.setText(MSabit.PAyar.getSUBEADI());
			IcIp.setText(MSabit.PAyar.getICIP());
			DisIp.setText(MSabit.PAyar.getDISIP());
			PortNumarasi.setText(MSabit.PAyar.getPORTNUMARASI());
		} catch (Exception e) {
			MHata Sonuc = new MHata();
			Sonuc.setHATAMI(true);
			Sonuc.setBASLIK("Kayýt Panel");
			Sonuc.setMESAJ("Ýþlem Baþarýsýz !");
			Sonuc.GosterMesajToast(Kayit.this);
		}
	}

	public void GetirPAyar() {
		ArrayList<PAyarRow> PAyarRowList = new ArrayList<PAyarRow>();
		ArrayList<MParamAyar> PAyarList = new ArrayList<MParamAyar>();
		try {
			PAyarList = MSabit.PAyar.GetirPayarList();
			for (int i = 0; i < PAyarList.size(); i++) {
				PAyarRow pAyarRow = new PAyarRow();
				pAyarRow.ID = PAyarList.get(i).ID;
				pAyarRow.IcIp = PAyarList.get(i).ICIP;
				pAyarRow.DisIp = PAyarList.get(i).DISIP;
				pAyarRow.Port = PAyarList.get(i).PORTNUMARASI;
				pAyarRow.SubeAdi = PAyarList.get(i).SUBEADI;

				PAyarRowList.add(pAyarRow);
			}

			ListAdapter Adapter = new ListAdapter(this, this,
					R.layout.listrow_payar, PAyarRowList.toArray(),
					ListAdapter.IslemTipi.PAYARLIST);
			LstVw.setAdapter(Adapter);
		} catch (Exception e) {
			MHata Sonuc = new MHata();
			Sonuc.setHATAMI(true);
			Sonuc.setBASLIK("Kayýt Panel");
			Sonuc.setMESAJ("Gerekli Alanlarý Doldurunuz");
			Sonuc.GosterMesajToast(Kayit.this);
		}
	}

	public MHata Kaydet() {
		MHata Sonuc = new MHata();
		if (SubeAdi.getText().toString().equals("")) {
			Sonuc = new MHata();
			Sonuc.setHATAMI(true);
			Sonuc.setBASLIK("Kayýt Panel");
			Sonuc.setMESAJ("Gerekli Alanlarý Doldurunuz");
			Sonuc.GosterMesajToast(Kayit.this);
			return Sonuc;
		}

		if (IcIp.getText().toString().equals("")) {
			Sonuc = new MHata();
			Sonuc.setHATAMI(true);
			Sonuc.setBASLIK("Kayýt Panel");
			Sonuc.setMESAJ("Gerekli Alanlarý Doldurunuz");
			Sonuc.GosterMesajToast(Kayit.this);
			return Sonuc;
		}

		if (DisIp.getText().toString().equals("")) {
			Sonuc = new MHata();
			Sonuc.setHATAMI(true);
			Sonuc.setBASLIK("Kayýt Panel");
			Sonuc.setMESAJ("Gerekli Alanlarý Doldurunuz");
			Sonuc.GosterMesajToast(Kayit.this);
			return Sonuc;
		}

		try {

			String SubeAdis = SubeAdi.getText().toString();
			String IcIps = IcIp.getText().toString();
			String DisIps = DisIp.getText().toString();
			String PortNumarasis = PortNumarasi.getText().toString();
			MSabit.Kullanici.GetirKullanici();
			
			if (MSabit.PAyar.ParametreSayisi() < Integer.parseInt(MSabit.Kullanici.getSubesayisi())) {

				MSabit.PAyar = new MParamAyar(PortNumarasis, DisIps, IcIps,
						SubeAdis);
				MSabit.PAyar.KaydetParamAyar();
			} else {
				Sonuc = new MHata();
				Sonuc.setBASLIK("Kayit");
				Sonuc.setHATAMI(true);
				Sonuc.setMESAJ("Þube Açmaya Yetkiniz Yok!");
				Sonuc.GosterMesajToast(Kayit.this);
				return Sonuc;
			}
			


		} catch (Exception e) {
			Sonuc = new MHata();
			Sonuc.setHATAMI(true);
			Sonuc.setBASLIK("Kayýt Panel");
			Sonuc.setMESAJ("Gerekli Alanlarý Doldurunuz");
			Sonuc.GosterMesajToast(Kayit.this);
			return Sonuc;
		}

		return Sonuc;
	}
}
