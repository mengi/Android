package net.multipi.QrReader;

import java.util.ArrayList;

import net.multipi.QrReader.func.Hata;
import net.multipi.QrReader.func.ListAdapter;
import net.multipi.QrReader.func.Sabit;
import net.multipi.QrReader.model.FaturaUst;
import net.multipi.QrReader.rowModel.FaturaRow;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class FaturaListeActivity extends Activity {

	ListView LstVw;
	FaturaRow faturaRow = new FaturaRow();
	Hata Sonuc = new Hata();

	public static String DurumSiparis = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fatura_liste);

		Intent myIntent = getIntent();

		try {
			DurumSiparis = myIntent.getStringExtra("Siparis");
		} catch (Exception e) {

		}

		LstVw = (ListView) findViewById(R.id.LstVw);
		LstVw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LstVw = (ListView) findViewById(R.id.LstVw);
				faturaRow = (FaturaRow) LstVw.getAdapter().getItem(position);

				if (DurumSiparis.equals("0")) {
					Intent myIntent = new Intent(FaturaListeActivity.this,
							FaturaKalemActivity.class);
					myIntent.putExtra("USTID", Integer.toString(faturaRow.Id));
					Sabit.TutFaturaId = faturaRow.Id;
					startActivity(myIntent);
				}
				
				if (DurumSiparis.equals("1")) {
					if (faturaRow != null) {
						AlertDialog.Builder AlertDialogBuilder = new AlertDialog.Builder(
								FaturaListeActivity.this);
						AlertDialogBuilder.setTitle("Sipariþ Ýptal");
						AlertDialogBuilder.setMessage(" Seçilen Sipariþ Ýptal Edilsin Mi ?");
						AlertDialogBuilder.setCancelable(false);
						AlertDialogBuilder.setIcon(R.drawable.ic_launcher);
						AlertDialogBuilder.setPositiveButton("Evet",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										Hata Sonuc = new Hata();
										Sabit.FaturaUst = new FaturaUst();
										Sonuc = Sabit.FaturaUst.IptalFaturaUst(faturaRow.Id);
										if (!Sonuc.getHATAMI()) {
											GetirVeriler();
										} else {
											Sonuc = new Hata();
											Sonuc.setHATAMI(true);
											Sonuc.setBASLIK("Sipariþ");
											Sonuc.setMESAJ("Seçilen Sipariþ Ýptal Edilmedi!");
											Sonuc.GosterMesajToast(FaturaListeActivity.this);
											return;
										}
									}
								});

						AlertDialogBuilder.setNegativeButton("Hayýr",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										
									}
								});
						AlertDialog AlertDialog = AlertDialogBuilder.create();
						AlertDialog.show();

					} else {
						Hata Sonuc = new Hata();
						Sonuc.setBASLIK("Sipariþ");
						Sonuc.setHATAMI(true);
						Sonuc.setMESAJ("Sipariþ Sec");
						Sonuc.GosterMesajToast(FaturaListeActivity.this);
						return;
					}
				}

			};
		});

		GetirVeriler();
	}

	public void GetirVeriler() {
		ArrayList<FaturaRow> Faturalar = new ArrayList<FaturaRow>();
		try {
			SQLiteDatabase DtBs = Sabit.MDbBaglanti.getReadableDatabase();
			String SorguCumle = "SELECT faturaID, siparisno, faturaacik, tarih, siparisalan "
					+ "FROM TBLNFATURAUST WHERE faturakesen='"
					+ DurumSiparis
					+ "'";

			Cursor Crs = DtBs.rawQuery(SorguCumle, null);
			while (Crs.moveToNext()) {
				FaturaRow faturaRow = new FaturaRow();
				faturaRow.Id = Crs.getInt(0);
				faturaRow.SiparisNo = Crs.getString(1);
				faturaRow.FaturaAciklama = Crs.getString(2);
				faturaRow.Tarih = Crs.getString(3);
				faturaRow.SiparisAlan = Crs.getString(4);
				Faturalar.add(faturaRow);
			}
			DtBs.close();
			
			ListAdapter Adapter = new ListAdapter(this, this,
					R.layout.listrow_fatura_ust, Faturalar.toArray(),
					ListAdapter.IslemTipi.FATURAUST);
			LstVw.setAdapter(Adapter);
		} catch (Exception e) {
			Sonuc = new Hata();
			Sonuc.setBASLIK("Giriþ");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Giriþ Baþarýsýz!");
			Sonuc.GosterMesajToast(FaturaListeActivity.this);
		}
	}
}
