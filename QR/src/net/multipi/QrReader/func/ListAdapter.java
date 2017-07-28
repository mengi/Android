package net.multipi.QrReader.func;

import net.multipi.QrReader.R;
import net.multipi.QrReader.rowModel.FaturaHarRow;
import net.multipi.QrReader.rowModel.FaturaRow;

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
		FATURAHAR, FATURAUST
	}

	private IslemTipi ISLEMTIPI = IslemTipi.FATURAHAR;

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

		if (ISLEMTIPI == IslemTipi.FATURAHAR) {
			ResID = R.layout.listrow_fatura_har;
		} else if (ISLEMTIPI == IslemTipi.FATURAUST) {
			ResID = R.layout.listrow_fatura_ust;
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

		if (ISLEMTIPI == IslemTipi.FATURAHAR) {

			FaturaHarRow item = (FaturaHarRow) this.getItem(position);
			((TextView) view.findViewById(R.id.TxtId)).setText(Integer
					.toString(item.Id));
			((TextView) view.findViewById(R.id.TxtUrunKodu))
					.setText(item.UrunKodu);
			((TextView) view.findViewById(R.id.TxtStokAdi))
					.setText(item.StokAdi);
			((TextView) view.findViewById(R.id.TxtMiktar)).setText(Double
					.toString((item.Miktar)));
			((TextView) view.findViewById(R.id.TxtGonderimTipi))
					.setText(item.FaturaTarihi);
			
		} else if (ISLEMTIPI == IslemTipi.FATURAUST) {
			
			FaturaRow item = (FaturaRow) this.getItem(position);
			((TextView) view.findViewById(R.id.TxtId)).setText(Integer
					.toString(item.Id));
			((TextView) view.findViewById(R.id.TxtFAciklama))
					.setText(item.FaturaAciklama);
			((TextView) view.findViewById(R.id.TxtTarih)).setText(item.Tarih);
			((TextView) view.findViewById(R.id.TxtSiparisNo))
					.setText(item.SiparisNo);
			((TextView) view.findViewById(R.id.TxtSiparisAlan))
					.setText(item.SiparisAlan);
		
		}

		return view;
	}
}
