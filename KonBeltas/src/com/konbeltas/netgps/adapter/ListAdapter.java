package com.konbeltas.netgps.adapter;

import com.konbeltas.netgps.R;
import com.konbeltas.netgps.rowmodel.DetayRow;
import com.konbeltas.netgps.rowmodel.GpsRow;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressWarnings("rawtypes")
public class ListAdapter extends ArrayAdapter {

	private Activity activity;
	public int FocusId = -1;
	public int LastId = 0;
	public int LastToId = 0;
	public Boolean KAYMA = true;

	public enum IslemTipi {

		OTOPARKBILGI, OTOPARKACIKLAMA

	}

	private IslemTipi ISLEMTIPI = IslemTipi.OTOPARKBILGI;

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
		if (ISLEMTIPI == IslemTipi.OTOPARKBILGI) {
			ResID = R.layout.listrow_gps;
		} else if (ISLEMTIPI == IslemTipi.OTOPARKACIKLAMA) {
			ResID = R.layout.listrow_detay;
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

		if (ISLEMTIPI == IslemTipi.OTOPARKBILGI) {

			GpsRow item = (GpsRow) this.getItem(position);
			((TextView) view.findViewById(R.id.TxtOtoparkKodu))
					.setText(item.OtoparkKodu);
			((TextView) view.findViewById(R.id.TxtOtopark))
					.setText(item.OtoparkAdi);
			((TextView) view.findViewById(R.id.TxtOtoparkTipi))
					.setText(item.OtoparkTipi);
		} else if (ISLEMTIPI == IslemTipi.OTOPARKACIKLAMA) {

			DetayRow item = (DetayRow) this.getItem(position);
			((TextView) view.findViewById(R.id.TxtAciklama))
					.setText(item.Aciklama);
			((TextView) view.findViewById(R.id.TxtUcret))
					.setText(item.Ucret);
		}

		return view;
	}
}
