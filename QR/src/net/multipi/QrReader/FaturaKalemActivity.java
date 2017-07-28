package net.multipi.QrReader;

import java.util.ArrayList;

import net.multipi.QrReader.func.Hata;
import net.multipi.QrReader.func.ListAdapter;
import net.multipi.QrReader.func.Sabit;
import net.multipi.QrReader.model.FaturaHar;
import net.multipi.QrReader.model.FaturaUst;
import net.multipi.QrReader.rowModel.FaturaHarRow;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class FaturaKalemActivity extends Activity {

	EditText TxtUrunKodu;
	ImageButton BtnBarkodAc;
	ListView LstVw;

	public static final String RESULT = "result";
	int Durum = 0;

	public FaturaHarRow faturaHarRow = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fatura_kalem);

		Intent intent = getIntent();

		TxtUrunKodu = (EditText) findViewById(R.id.TxtUrunKodu);
		BtnBarkodAc = (ImageButton) findViewById(R.id.BtnBarkodAc);
		LstVw = (ListView) findViewById(R.id.LstVw);
		
		try {
			TxtUrunKodu.setText(intent.getStringExtra(RESULT));
			Sabit.TutUrunKodu = intent.getStringExtra(RESULT);
		} catch (Exception e) {
			TxtUrunKodu.setText(Sabit.TutUrunKodu);
		}
		
		LstVw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LstVw = (ListView) findViewById(R.id.LstVw);

				if (!TxtUrunKodu.getText().toString().equals("")) {
					faturaHarRow = (FaturaHarRow) LstVw.getAdapter().getItem(
							position);
					if (faturaHarRow != null) {
						AlertDialog.Builder AlertDialogBuilder = new AlertDialog.Builder(
								FaturaKalemActivity.this);
						AlertDialogBuilder.setTitle("Sipariþ");
						AlertDialogBuilder.setMessage(" Sipariþ Tamamla");
						AlertDialogBuilder.setCancelable(false);
						AlertDialogBuilder.setIcon(R.drawable.ic_launcher);
						AlertDialogBuilder.setPositiveButton("Evet",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										Hata Sonuc = new Hata();
										Sabit.FaturaHar = new FaturaHar();
										Sonuc = Sabit.FaturaHar
												.GuncelleFaturaHar(faturaHarRow.Id);
										if (!Sonuc.getHATAMI()) {
											TxtUrunKodu.setText("");
											GetirFaturaKalem();
										} else {
											Sonuc = new Hata();
											Sonuc.setHATAMI(true);
											Sonuc.setBASLIK("Sipariþ");
											Sonuc.setMESAJ("Seçilen Sipariþ Tamamlanmadý!");
											Sonuc.GosterMesajToast(FaturaKalemActivity.this);
											return;
										}
									}
								});

						AlertDialogBuilder.setNegativeButton("Hayýr",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										TxtUrunKodu.setText("");
									}
								});
						AlertDialog AlertDialog = AlertDialogBuilder.create();
						AlertDialog.show();

					} else {
						Hata Sonuc = new Hata();
						Sonuc.setBASLIK("Sipariþ");
						Sonuc.setHATAMI(true);
						Sonuc.setMESAJ("Sipariþ Sec");
						Sonuc.GosterMesajToast(FaturaKalemActivity.this);
						return;
					}
				}
			};
		});

		BtnBarkodAc.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(FaturaKalemActivity.this,
						QKActivity.class);
				startActivity(myIntent);
			}
		});

		if (!TxtUrunKodu.getText().toString().equals("")) {
			if (!TxtUrunKodu.getText().toString().equals("")) {
				int Sayac = KarFaturaKalem();
				
				if (Sayac == 0) {
					AlertDialog.Builder AlertDialogBuilder = new AlertDialog.Builder(
							FaturaKalemActivity.this);
					AlertDialogBuilder.setTitle("Sipariþ");
					AlertDialogBuilder.setMessage(" Boyle Bir Sipariþ Bulunmamaktadýr!");
					AlertDialogBuilder.setCancelable(false);
					AlertDialogBuilder.setIcon(R.drawable.ic_launcher);
					AlertDialogBuilder.setPositiveButton("Tamam",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									TxtUrunKodu.setText("");
									GetirFaturaKalem();
								}
							});
					
					AlertDialog AlertDialog = AlertDialogBuilder.create();
					AlertDialog.show();
				}
			} else {
				Hata Sonuc = new Hata();
				Sonuc.setBASLIK("Sipariþ");
				Sonuc.setHATAMI(true);
				Sonuc.setMESAJ("Sipariþte Böyle Bir Ürün Bulunmamktadýr");
				Sonuc.GosterMesajToast(FaturaKalemActivity.this);
				return;
			}
		} else {
			GetirFaturaKalem();
		}
	}

	public void GetirFaturaKalem() {

		try {
			ArrayList<FaturaHarRow> FaturaHarlar = new ArrayList<FaturaHarRow>();
			SQLiteDatabase DtBs = Sabit.MDbBaglanti.getReadableDatabase();
			String SorguCumle = "SELECT faturasepetiID, urunkodu, stokacik, miktar, faturatarihi "
					+ "FROM TBLNFATURAHAR WHERE duzey='0' AND faturaID=?";
			Cursor Crs = DtBs.rawQuery(SorguCumle,
					new String[] { Integer.toString(Sabit.TutFaturaId) });

			while (Crs.moveToNext()) {
				FaturaHarRow faturaRow = new FaturaHarRow();
				faturaRow.Id = Crs.getInt(0);
				faturaRow.UrunKodu = Crs.getString(1);
				faturaRow.StokAdi = Crs.getString(2);
				faturaRow.Miktar = Crs.getDouble(3);
				faturaRow.FaturaTarihi = Crs.getString(4);
				FaturaHarlar.add(faturaRow);
			}
			
			DtBs.close();
			
			if (FaturaHarlar.size() == 0) {
				Sabit.FaturaUst = new FaturaUst();
				Sabit.FaturaUst.GuncelleFaturaUst(Sabit.TutFaturaId);
			}
			
			ListAdapter Adapter = new ListAdapter(this, this,
					R.layout.listrow_fatura_har, FaturaHarlar.toArray(),
					ListAdapter.IslemTipi.FATURAHAR);
			LstVw.setAdapter(Adapter);
		} catch (Exception e) {
			Hata Sonuc = new Hata();
			Sonuc.setBASLIK("Sipariþ Liste");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Getirme Baþarýsýz!");
			Sonuc.GosterMesajToast(FaturaKalemActivity.this);
			return;
		}
	}

	public int KarFaturaKalem() {
		int Sayac = 0;
		try {
			ArrayList<FaturaHarRow> FaturaHarlar = new ArrayList<FaturaHarRow>();
			SQLiteDatabase DtBs = Sabit.MDbBaglanti.getReadableDatabase();
			String SorguCumle = "SELECT faturasepetiID, urunkodu, stokacik, miktar, faturatarihi FROM TBLNFATURAHAR"
					+ " WHERE faturaID='"
					+ Sabit.TutFaturaId
					+ "' AND urunkodu='"
					+ TxtUrunKodu.getText().toString()
					+ "' AND duzey='0'";
			Cursor Crs = DtBs.rawQuery(SorguCumle, null);

			while (Crs.moveToNext()) {
				FaturaHarRow faturaRow = new FaturaHarRow();
				faturaRow.Id = Crs.getInt(0);
				faturaRow.UrunKodu = Crs.getString(1);
				faturaRow.StokAdi = Crs.getString(2);
				faturaRow.Miktar = Crs.getDouble(3);
				faturaRow.FaturaTarihi = Crs.getString(4);
				FaturaHarlar.add(faturaRow);
			}
			
			DtBs.close();
			
			Sayac = FaturaHarlar.size();
			
			ListAdapter Adapter = new ListAdapter(this, this,
					R.layout.listrow_fatura_har, FaturaHarlar.toArray(),
					ListAdapter.IslemTipi.FATURAHAR);
			LstVw.setAdapter(Adapter);
		} catch (Exception e) {
			Hata Sonuc = new Hata();
			Sonuc.setBASLIK("Sipariþ Liste");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Getirme Baþarýsýz!");
			Sonuc.GosterMesajToast(FaturaKalemActivity.this);
		}
		
		return Sayac;
	}
}
