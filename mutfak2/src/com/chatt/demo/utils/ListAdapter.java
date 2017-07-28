package com.chatt.demo.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chatt.demo.R;
import com.chatt.demo.rw.model.MasaRow;
import com.chatt.demo.rw.model.SiparisRow;
import com.chatt.demo.rw.model.UrunGrupListeRow;
import com.chatt.demo.rw.model.UrunListRow;

@SuppressWarnings("rawtypes")
public class ListAdapter extends ArrayAdapter {

	private Activity activity;
	public int FocusId = -1;
	public int LastId = 0;
	public int LastToId = 0;
	public Boolean KAYMA = true;

	public enum IslemTipi {

		MASA, URUNGRUP, URUNLER, SIPARIS, SIPARISDURUM

	}

	private IslemTipi ISLEMTIPI = IslemTipi.MASA;

	@SuppressWarnings("unchecked")
	public ListAdapter(Activity activity, Context context,
			int textViewResourceId, Object[] objects, IslemTipi _ISLEMTIPI) {
		super(context, textViewResourceId, objects);
		ISLEMTIPI = _ISLEMTIPI;
		this.activity = activity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (KAYMA) {
			LastToId = LastId;
			LastId = position;
		} else {
			KAYMA = true;
		}
		int ResID = 0;
		if (ISLEMTIPI == IslemTipi.MASA) {
			ResID = R.layout.listrow_masa;
		} else if (ISLEMTIPI == IslemTipi.URUNGRUP) {
			ResID = R.layout.listrow_menu;
		} else if (ISLEMTIPI == IslemTipi.URUNLER) {
			ResID = R.layout.listrow_menudetay;
		} else if (ISLEMTIPI == IslemTipi.SIPARIS) {
			ResID = R.layout.listrow_siparis;
		} else if (ISLEMTIPI == IslemTipi.SIPARISDURUM) {
			ResID = R.layout.listrow_siparis_durum;
		}

		LinearLayout view = (LinearLayout) convertView;
		if (convertView != null)
			view = (LinearLayout) convertView;
		else
			view = ((LinearLayout) LayoutInflater.from(activity).inflate(ResID,
					parent, false));

		if (position == FocusId) {
			view.setBackgroundResource(R.drawable.listerowfocusstyle);
		} else {
			if (position % 2 == 0) {
				view.setBackgroundResource(R.drawable.listerowevenstyle);
			} else {
				view.setBackgroundResource(R.drawable.listerowoddstyle);
			}
		}
		if (ISLEMTIPI == IslemTipi.MASA) {

			MasaRow item = (MasaRow) this.getItem(position);
			((TextView) view.findViewById(R.id.TxtId)).setText(item.id);
			((TextView) view.findViewById(R.id.TxtMasaAdi))
					.setText(item.masaadi);
			((TextView) view.findViewById(R.id.TxtMasaDurum))
					.setText(item.masadurumu);
			((TextView) view.findViewById(R.id.TxtSiparisDurum))
					.setText(item.siparisdurumu);

		} else if (ISLEMTIPI == IslemTipi.URUNGRUP) {
			UrunGrupListeRow item = (UrunGrupListeRow) this.getItem(position);
			((TextView) view.findViewById(R.id.TxtId)).setText(item.ID);
			((TextView) view.findViewById(R.id.TxtMenuAdi))
					.setText(item.URUNGRUPADI);
		} else if (ISLEMTIPI == IslemTipi.URUNLER) {
			UrunListRow item = (UrunListRow) this.getItem(position);
			((TextView) view.findViewById(R.id.TxtId)).setText(item.StokID);
			((TextView) view.findViewById(R.id.TxtAdi)).setText(item.Aciklama);
			((TextView) view.findViewById(R.id.TxtFiyat)).setText(item.Fiyat);
		} else if (ISLEMTIPI == IslemTipi.SIPARIS) {
			SiparisRow item = (SiparisRow) this.getItem(position);
			((TextView) view.findViewById(R.id.TxtMasaKodu))
					.setText(item.MasaAdi);
			
			((TextView) view.findViewById(R.id.TxtSiparisDurum))
					.setText(item.SiparisDurum);
			
			((TextView) view.findViewById(R.id.TxtSiparisDurum))
					.setVisibility(View.GONE);
			((TextView) view.findViewById(R.id.TxtSiparisDurum))
					.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
			
			((TextView) view.findViewById(R.id.TxtUrunAdi))
					.setText(item.UrunAdi);
			((TextView) view.findViewById(R.id.TxtMiktar)).setText(item.Miktar);
			((TextView) view.findViewById(R.id.TxtToplam)).setText(item.Toplam);
		}

		else if (ISLEMTIPI == IslemTipi.SIPARISDURUM) {
			SiparisRow item = (SiparisRow) this.getItem(position);
			((TextView) view.findViewById(R.id.TxtMasaKodu))
					.setText(item.MasaAdi);
			((TextView) view.findViewById(R.id.TxtSiparisDurum))
					.setText(item.SiparisDurum);

			if (item.SiparisDurum.equals("Hazýrlanýyor")) {
				((TextView) view.findViewById(R.id.TxtSiparisDurum))
						.setTextColor(Color.parseColor("#f0ad4e"));
			} else if (item.SiparisDurum.equals("Hazýr")) {
				((TextView) view.findViewById(R.id.TxtSiparisDurum))
						.setTextColor(Color.parseColor("#d9534f"));
			}
			
			((TextView) view.findViewById(R.id.TxtUrunAdi))
					.setText(item.UrunAdi);
			((TextView) view.findViewById(R.id.TxtMiktar)).setText(item.Miktar);
			((TextView) view.findViewById(R.id.TxtToplam)).setText(item.Toplam);
		}
		return view;
	}
}
