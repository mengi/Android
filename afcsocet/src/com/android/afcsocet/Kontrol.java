package com.android.afcsocet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import java.net.*;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import com.android.fuk.MHata;
import com.android.fuk.MSabit;
import com.android.mdl.MTextName;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Kontrol extends Activity {

	protected static final String Name = null;
	
	public static String KapatDurums = "";
	
	public int SayacAc = 0;
	public int SayacKapat = 0;
	String Durum = "";
	String Durum2 = "";
	String response = "";
	public String TutDurum = "";
	public String KapatDurum = "";
	public String YakDurum = "";
	public String GelenRole = "-1";

	ImageButton Btn1, Btn2, Btn3, Btn4, Btn5, Btn6, Btn7, Btn8, Btn9, Btn10,
			Btn11, Btn12, Btn13, Btn14, Btn15, Btn16, BtnTKapat, BtnTAc;

	TextView a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15,
			a16, TxtTKapali, TxtTAc, TxtPanelBir;

	int BtnTikla1 = -1;
	int BtnTikla2 = -1;
	int BtnTikla3 = -1;
	int BtnTikla4 = -1;
	int BtnTikla5 = -1;
	int BtnTikla6 = -1;
	int BtnTikla7 = -1;
	int BtnTikla8 = -1;
	int BtnTikla9 = -1;
	int BtnTikla10 = -1;
	int BtnTikla11 = -1;
	int BtnTikla12 = -1;
	int BtnTikla13 = -1;
	int BtnTikla14 = -1;
	int BtnTikla15 = -1;
	int BtnTikla16 = -1;
	int BtnTiklaTumunuAc = -1;
	int BtnTiklaTumunuKapat = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kontrol);

		MSabit.PAyar.GetirParamAyar();
		MSabit.MTextIsim = new MTextName();

		Intent MyIntent = getIntent();

		a1 = (TextView) findViewById(R.id.a1);
		a2 = (TextView) findViewById(R.id.a2);
		a3 = (TextView) findViewById(R.id.a3);
		a4 = (TextView) findViewById(R.id.a4);
		a5 = (TextView) findViewById(R.id.a5);
		a6 = (TextView) findViewById(R.id.a6);
		a7 = (TextView) findViewById(R.id.a7);
		a8 = (TextView) findViewById(R.id.a8);
		a9 = (TextView) findViewById(R.id.a9);
		a10 = (TextView) findViewById(R.id.a10);
		a11 = (TextView) findViewById(R.id.a11);
		a12 = (TextView) findViewById(R.id.a12);
		a13 = (TextView) findViewById(R.id.a13);
		a14 = (TextView) findViewById(R.id.a14);
		a15 = (TextView) findViewById(R.id.a15);
		a16 = (TextView) findViewById(R.id.a16);

		TxtTKapali = (TextView) findViewById(R.id.TxtTKapali);
		TxtTAc = (TextView) findViewById(R.id.TxtTAc);
		TxtPanelBir = (TextView) findViewById(R.id.TxtPanelBir);

		Btn1 = (ImageButton) findViewById(R.id.Btn1);
		Btn2 = (ImageButton) findViewById(R.id.Btn2);
		Btn3 = (ImageButton) findViewById(R.id.Btn3);
		Btn4 = (ImageButton) findViewById(R.id.Btn4);
		Btn5 = (ImageButton) findViewById(R.id.Btn5);
		Btn6 = (ImageButton) findViewById(R.id.Btn6);
		Btn7 = (ImageButton) findViewById(R.id.Btn7);
		Btn8 = (ImageButton) findViewById(R.id.Btn8);
		Btn9 = (ImageButton) findViewById(R.id.Btn9);
		Btn10 = (ImageButton) findViewById(R.id.Btn10);
		Btn11 = (ImageButton) findViewById(R.id.Btn11);
		Btn12 = (ImageButton) findViewById(R.id.Btn12);
		Btn13 = (ImageButton) findViewById(R.id.Btn13);
		Btn14 = (ImageButton) findViewById(R.id.Btn14);
		Btn15 = (ImageButton) findViewById(R.id.Btn15);
		Btn16 = (ImageButton) findViewById(R.id.Btn16);
		BtnTKapat = (ImageButton) findViewById(R.id.BtnTKapat);
		BtnTAc = (ImageButton) findViewById(R.id.BtnTAc);

		this.setTitle("");
		TxtTAc.setText("Tümünü Aç");
		TxtTKapali.setText("Tümünü Kapat");

		try {
			GelenRole = MyIntent.getStringExtra("KontrolRole");
		} catch (Exception e) {
			MHata Sonuc = new MHata();
			Sonuc.setBASLIK("Role Kontrol Paneli");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Roleden Veri Gelmedi!");
			Sonuc.GosterMesajToast(Kontrol.this);
			return;
		}

		if (!MSabit.GetirTextNames(MSabit.GetirPAyarID(MSabit.MSubeAdi), "a1",
				Integer.parseInt(GelenRole)).equals("")) {
			a1.setText(MSabit.GetirTextNames(
					MSabit.GetirPAyarID(MSabit.MSubeAdi), "a1",
					Integer.parseInt(GelenRole)));
		}

		if (!MSabit.GetirTextNames(MSabit.GetirPAyarID(MSabit.MSubeAdi), "a2",
				Integer.parseInt(GelenRole)).equals("")) {
			a2.setText(MSabit.GetirTextNames(
					MSabit.GetirPAyarID(MSabit.MSubeAdi), "a2",
					Integer.parseInt(GelenRole)));
		}

		if (!MSabit.GetirTextNames(MSabit.GetirPAyarID(MSabit.MSubeAdi), "a3",
				Integer.parseInt(GelenRole)).equals("")) {
			a3.setText(MSabit.GetirTextNames(
					MSabit.GetirPAyarID(MSabit.MSubeAdi), "a3",
					Integer.parseInt(GelenRole)));
		}

		if (!MSabit.GetirTextNames(MSabit.GetirPAyarID(MSabit.MSubeAdi), "a4",
				Integer.parseInt(GelenRole)).equals("")) {
			a4.setText(MSabit.GetirTextNames(
					MSabit.GetirPAyarID(MSabit.MSubeAdi), "a4",
					Integer.parseInt(GelenRole)));
		}

		if (!MSabit.GetirTextNames(MSabit.GetirPAyarID(MSabit.MSubeAdi), "a5",
				Integer.parseInt(GelenRole)).equals("")) {
			a5.setText(MSabit.GetirTextNames(
					MSabit.GetirPAyarID(MSabit.MSubeAdi), "a5",
					Integer.parseInt(GelenRole)));
		}

		if (!MSabit.GetirTextNames(MSabit.GetirPAyarID(MSabit.MSubeAdi), "a6",
				Integer.parseInt(GelenRole)).equals("")) {
			a6.setText(MSabit.GetirTextNames(
					MSabit.GetirPAyarID(MSabit.MSubeAdi), "a6",
					Integer.parseInt(GelenRole)));
		}
		if (!MSabit.GetirTextNames(MSabit.GetirPAyarID(MSabit.MSubeAdi), "a7",
				Integer.parseInt(GelenRole)).equals("")) {
			a7.setText(MSabit.GetirTextNames(
					MSabit.GetirPAyarID(MSabit.MSubeAdi), "a7",
					Integer.parseInt(GelenRole)));
		}

		if (!MSabit.GetirTextNames(MSabit.GetirPAyarID(MSabit.MSubeAdi), "a8",
				Integer.parseInt(GelenRole)).equals("")) {
			a8.setText(MSabit.GetirTextNames(
					MSabit.GetirPAyarID(MSabit.MSubeAdi), "a8",
					Integer.parseInt(GelenRole)));
		}

		if (!MSabit.GetirTextNames(MSabit.GetirPAyarID(MSabit.MSubeAdi), "a9",
				Integer.parseInt(GelenRole)).equals("")) {
			a9.setText(MSabit.GetirTextNames(
					MSabit.GetirPAyarID(MSabit.MSubeAdi), "a9",
					Integer.parseInt(GelenRole)));
		}

		if (!MSabit.GetirTextNames(MSabit.GetirPAyarID(MSabit.MSubeAdi), "a10",
				Integer.parseInt(GelenRole)).equals("")) {
			a10.setText(MSabit.GetirTextNames(
					MSabit.GetirPAyarID(MSabit.MSubeAdi), "a10",
					Integer.parseInt(GelenRole)));
		}

		if (!MSabit.GetirTextNames(MSabit.GetirPAyarID(MSabit.MSubeAdi), "a11",
				Integer.parseInt(GelenRole)).equals("")) {
			a11.setText(MSabit.GetirTextNames(
					MSabit.GetirPAyarID(MSabit.MSubeAdi), "a11",
					Integer.parseInt(GelenRole)));
		}

		if (!MSabit.GetirTextNames(MSabit.GetirPAyarID(MSabit.MSubeAdi), "a12",
				Integer.parseInt(GelenRole)).equals("")) {
			a12.setText(MSabit.GetirTextNames(
					MSabit.GetirPAyarID(MSabit.MSubeAdi), "a12",
					Integer.parseInt(GelenRole)));
		}

		if (!MSabit.GetirTextNames(MSabit.GetirPAyarID(MSabit.MSubeAdi), "a13",
				Integer.parseInt(GelenRole)).equals("")) {
			a13.setText(MSabit.GetirTextNames(
					MSabit.GetirPAyarID(MSabit.MSubeAdi), "a13",
					Integer.parseInt(GelenRole)));
		}

		if (!MSabit.GetirTextNames(MSabit.GetirPAyarID(MSabit.MSubeAdi), "a14",
				Integer.parseInt(GelenRole)).equals("")) {
			a14.setText(MSabit.GetirTextNames(
					MSabit.GetirPAyarID(MSabit.MSubeAdi), "a14",
					Integer.parseInt(GelenRole)));
		}
		if (!MSabit.GetirTextNames(MSabit.GetirPAyarID(MSabit.MSubeAdi), "a15",
				Integer.parseInt(GelenRole)).equals("")) {
			a15.setText(MSabit.GetirTextNames(
					MSabit.GetirPAyarID(MSabit.MSubeAdi), "a15",
					Integer.parseInt(GelenRole)));
		}
		if (!MSabit.GetirTextNames(MSabit.GetirPAyarID(MSabit.MSubeAdi), "a16",
				Integer.parseInt(GelenRole)).equals("")) {
			a16.setText(MSabit.GetirTextNames(
					MSabit.GetirPAyarID(MSabit.MSubeAdi), "a16",
					Integer.parseInt(GelenRole)));
		}

		a1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Dialog("a1", a1);
			}
		});

		a2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Dialog("a2", a2);
			}
		});

		a3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Dialog("a3", a3);
			}
		});

		a4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Dialog("a4", a4);
			}
		});

		a5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Dialog("a5", a5);
			}
		});

		a6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Dialog("a6", a6);
			}
		});

		a7.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Dialog("a7", a7);
			}
		});

		a8.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Dialog("a8", a8);
			}
		});

		a9.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Dialog("a9", a9);
			}
		});

		a10.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Dialog("a10", a10);
			}
		});

		a11.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Dialog("a11", a11);
			}
		});

		a12.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Dialog("a12", a12);
			}
		});
		a13.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Dialog("a13", a13);
			}
		});
		a14.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Dialog("a14", a14);
			}
		});
		a15.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Dialog("a15", a15);
			}
		});
		a16.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Dialog("a16", a16);
			}
		});

		TxtPanelBir.setText(MSabit.MSubeAdi + " Aydýnlatma Kontrol Paneli - "
				+ GelenRole);

		if (MSabit.GetirIpAdresi().equals("")) {
			MHata Sonuc = new MHata();
			Sonuc.setBASLIK("Að Baðlantýsý");
			Sonuc.setHATAMI(true);
			Sonuc.setMESAJ("Að Baðlantýnýz Kontrol Ediniz!");
			Sonuc.GosterMesajToast(Kontrol.this);
			return;
		}
		
		
		for (int i = 1; i <= 16; i++) {
			MyClientTask myClientTask = new MyClientTask();
			if (i < 10) {
				Durum = "X" + GelenRole + "D0" + Integer.toString(i) + "Y";
				myClientTask.execute(Durum);
			} else {
				Durum2 = "X" + GelenRole + "D" + Integer.toString(i) + "Y";
				myClientTask.execute(Durum2);
			}

		}

		Btn1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (BtnTikla1 == 0) {
					YakDurum = "X" + GelenRole + "A01Y";
					MyClientTask myClientTaskAc = new MyClientTask();
					myClientTaskAc.execute(YakDurum);
				}

				if (BtnTikla1 == 1) {
					KapatDurum = "X" + GelenRole + "K01Y";
					MyClientTask myClientTaskKapat = new MyClientTask();
					myClientTaskKapat.execute(KapatDurum);
				}

				if (BtnTikla1 == -2) {
					MHata Sonuc = new MHata();
					Sonuc.setBASLIK("Kontrol");
					Sonuc.setHATAMI(true);
					Sonuc.setMESAJ("Baðlantý Ýþlemi Baþarýsýz!");
					Sonuc.GosterMesajToast(Kontrol.this);
					return;
				}
			}
		});

		Btn2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (BtnTikla2 == 0) {
					YakDurum = "X" + GelenRole + "A02Y";
					MyClientTask myClientTaskAc = new MyClientTask();
					myClientTaskAc.execute(YakDurum);
				}

				if (BtnTikla2 == 1) {
					KapatDurum = "X" + GelenRole + "K02Y";
					MyClientTask myClientTaskKapat = new MyClientTask();
					myClientTaskKapat.execute(KapatDurum);
				}

				if (BtnTikla2 == -2) {
					MHata Sonuc = new MHata();
					Sonuc.setBASLIK("Kontrol");
					Sonuc.setHATAMI(true);
					Sonuc.setMESAJ("Baðlantý Ýþlemi Baþarýsýz!");
					Sonuc.GosterMesajToast(Kontrol.this);
					return;
				}
			}
		});

		Btn3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (BtnTikla3 == 0) {
					YakDurum = "X" + GelenRole + "A03Y";
					MyClientTask myClientTaskAc = new MyClientTask();
					myClientTaskAc.execute(YakDurum);
				}

				if (BtnTikla3 == 1) {
					KapatDurum = "X" + GelenRole + "K03Y";
					MyClientTask myClientTaskKapat = new MyClientTask();
					myClientTaskKapat.execute(KapatDurum);
				}

				if (BtnTikla3 == -2) {
					MHata Sonuc = new MHata();
					Sonuc.setBASLIK("Kontrol");
					Sonuc.setHATAMI(true);
					Sonuc.setMESAJ("Baðlantý Ýþlemi Baþarýsýz!");
					Sonuc.GosterMesajToast(Kontrol.this);
					return;
				}
			}
		});

		Btn4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (BtnTikla4 == 0) {
					YakDurum = "X" + GelenRole + "A04Y";
					MyClientTask myClientTaskAc = new MyClientTask();
					myClientTaskAc.execute(YakDurum);
				}

				if (BtnTikla4 == 1) {
					KapatDurum = "X" + GelenRole + "K04Y";
					MyClientTask myClientTaskKapat = new MyClientTask();
					myClientTaskKapat.execute(KapatDurum);
				}

				if (BtnTikla4 == -2) {
					MHata Sonuc = new MHata();
					Sonuc.setBASLIK("Kontrol");
					Sonuc.setHATAMI(true);
					Sonuc.setMESAJ("Baðlantý Ýþlemi Baþarýsýz!");
					Sonuc.GosterMesajToast(Kontrol.this);
					return;
				}
			}
		});

		Btn5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (BtnTikla5 == 0) {
					YakDurum = "X" + GelenRole + "A05Y";
					MyClientTask myClientTaskAc = new MyClientTask();
					myClientTaskAc.execute(YakDurum);
				}

				if (BtnTikla5 == 1) {
					KapatDurum = "X" + GelenRole + "K05Y";
					MyClientTask myClientTaskKapat = new MyClientTask();
					myClientTaskKapat.execute(KapatDurum);
				}

				if (BtnTikla5 == -2) {
					MHata Sonuc = new MHata();
					Sonuc.setBASLIK("Kontrol");
					Sonuc.setHATAMI(true);
					Sonuc.setMESAJ("Baðlantý Ýþlemi Baþarýsýz!");
					Sonuc.GosterMesajToast(Kontrol.this);
					return;
				}
			}
		});

		Btn6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (BtnTikla6 == 0) {
					YakDurum = "X" + GelenRole + "A06Y";
					MyClientTask myClientTaskAc = new MyClientTask();
					myClientTaskAc.execute(YakDurum);
				}

				if (BtnTikla6 == 1) {
					KapatDurum = "X" + GelenRole + "K06Y";
					MyClientTask myClientTaskKapat = new MyClientTask();
					myClientTaskKapat.execute(KapatDurum);
				}

				if (BtnTikla6 == -2) {
					MHata Sonuc = new MHata();
					Sonuc.setBASLIK("Kontrol");
					Sonuc.setHATAMI(true);
					Sonuc.setMESAJ("Baðlantý Ýþlemi Baþarýsýz!");
					Sonuc.GosterMesajToast(Kontrol.this);
					return;
				}
			}
		});

		Btn7.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (BtnTikla7 == 0) {
					YakDurum = "X" + GelenRole + "A07Y";
					MyClientTask myClientTaskAc = new MyClientTask();
					myClientTaskAc.execute(YakDurum);
				}

				if (BtnTikla7 == 1) {
					KapatDurum = "X" + GelenRole + "K07Y";
					MyClientTask myClientTaskKapat = new MyClientTask();
					myClientTaskKapat.execute(KapatDurum);
				}

				if (BtnTikla7 == -2) {
					MHata Sonuc = new MHata();
					Sonuc.setBASLIK("Kontrol");
					Sonuc.setHATAMI(true);
					Sonuc.setMESAJ("Baðlantý Ýþlemi Baþarýsýz!");
					Sonuc.GosterMesajToast(Kontrol.this);
					return;
				}
			}
		});

		Btn8.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (BtnTikla8 == 0) {
					YakDurum = "X" + GelenRole + "A08Y";
					MyClientTask myClientTaskAc = new MyClientTask();
					myClientTaskAc.execute(YakDurum);
				}

				if (BtnTikla8 == 1) {
					KapatDurum = "X" + GelenRole + "K08Y";
					MyClientTask myClientTaskKapat = new MyClientTask();
					myClientTaskKapat.execute(KapatDurum);
				}

				if (BtnTikla8 == -2) {
					MHata Sonuc = new MHata();
					Sonuc.setBASLIK("Kontrol");
					Sonuc.setHATAMI(true);
					Sonuc.setMESAJ("Baðlantý Ýþlemi Baþarýsýz!");
					Sonuc.GosterMesajToast(Kontrol.this);
					return;
				}
			}
		});

		Btn9.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (BtnTikla9 == 0) {
					YakDurum = "X" + GelenRole + "A09Y";
					MyClientTask myClientTaskAc = new MyClientTask();
					myClientTaskAc.execute(YakDurum);
				}

				if (BtnTikla9 == 1) {
					KapatDurum = "X" + GelenRole + "K09Y";
					MyClientTask myClientTaskKapat = new MyClientTask();
					myClientTaskKapat.execute(KapatDurum);
				}

				if (BtnTikla9 == -2) {
					MHata Sonuc = new MHata();
					Sonuc.setBASLIK("Kontrol");
					Sonuc.setHATAMI(true);
					Sonuc.setMESAJ("Baðlantý Ýþlemi Baþarýsýz!");
					Sonuc.GosterMesajToast(Kontrol.this);
					return;
				}
			}
		});

		Btn10.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (BtnTikla10 == 0) {
					YakDurum = "X" + GelenRole + "A10Y";
					MyClientTask myClientTaskAc = new MyClientTask();
					myClientTaskAc.execute(YakDurum);
				}

				if (BtnTikla10 == 1) {
					KapatDurum = "X" + GelenRole + "K10Y";
					MyClientTask myClientTaskKapat = new MyClientTask();
					myClientTaskKapat.execute(KapatDurum);
				}

				if (BtnTikla10 == -2) {
					MHata Sonuc = new MHata();
					Sonuc.setBASLIK("Kontrol");
					Sonuc.setHATAMI(true);
					Sonuc.setMESAJ("Baðlantý Ýþlemi Baþarýsýz!");
					Sonuc.GosterMesajToast(Kontrol.this);
					return;
				}
			}
		});

		Btn11.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (BtnTikla11 == 0) {
					YakDurum = "X" + GelenRole + "A11Y";
					MyClientTask myClientTaskAc = new MyClientTask();
					myClientTaskAc.execute(YakDurum);
				}

				if (BtnTikla11 == 1) {
					KapatDurum = "X" + GelenRole + "K11Y";
					MyClientTask myClientTaskKapat = new MyClientTask();
					myClientTaskKapat.execute(KapatDurum);
				}

				if (BtnTikla11 == -2) {
					MHata Sonuc = new MHata();
					Sonuc.setBASLIK("Kontrol");
					Sonuc.setHATAMI(true);
					Sonuc.setMESAJ("Baðlantý Ýþlemi Baþarýsýz!");
					Sonuc.GosterMesajToast(Kontrol.this);
					return;
				}
			}
		});

		Btn12.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (BtnTikla12 == 0) {
					YakDurum = "X" + GelenRole + "A12Y";
					MyClientTask myClientTaskAc = new MyClientTask();
					myClientTaskAc.execute(YakDurum);
				}

				if (BtnTikla12 == 1) {
					KapatDurum = "X" + GelenRole + "K12Y";
					MyClientTask myClientTaskKapat = new MyClientTask();
					myClientTaskKapat.execute(KapatDurum);
				}

				if (BtnTikla12 == -2) {
					MHata Sonuc = new MHata();
					Sonuc.setBASLIK("Kontrol");
					Sonuc.setHATAMI(true);
					Sonuc.setMESAJ("Baðlantý Ýþlemi Baþarýsýz!");
					Sonuc.GosterMesajToast(Kontrol.this);
					return;
				}
			}
		});

		Btn13.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (BtnTikla13 == 0) {
					YakDurum = "X" + GelenRole + "A13Y";
					MyClientTask myClientTaskAc = new MyClientTask();
					myClientTaskAc.execute(YakDurum);
				}

				if (BtnTikla13 == 1) {
					KapatDurum = "X" + GelenRole + "K13Y";
					MyClientTask myClientTaskKapat = new MyClientTask();
					myClientTaskKapat.execute(KapatDurum);
				}

				if (BtnTikla13 == -2) {
					MHata Sonuc = new MHata();
					Sonuc.setBASLIK("Kontrol");
					Sonuc.setHATAMI(true);
					Sonuc.setMESAJ("Baðlantý Ýþlemi Baþarýsýz!");
					Sonuc.GosterMesajToast(Kontrol.this);
					return;
				}
			}
		});

		Btn14.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (BtnTikla14 == 0) {
					YakDurum = "X" + GelenRole + "A14Y";
					MyClientTask myClientTaskAc = new MyClientTask();
					myClientTaskAc.execute(YakDurum);
				}

				if (BtnTikla14 == 1) {
					KapatDurum = "X" + GelenRole + "K14Y";
					MyClientTask myClientTaskKapat = new MyClientTask();
					myClientTaskKapat.execute(KapatDurum);
				}

				if (BtnTikla14 == -2) {
					MHata Sonuc = new MHata();
					Sonuc.setBASLIK("Kontrol");
					Sonuc.setHATAMI(true);
					Sonuc.setMESAJ("Baðlantý Ýþlemi Baþarýsýz!");
					Sonuc.GosterMesajToast(Kontrol.this);
					return;
				}
			}
		});

		Btn15.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (BtnTikla15 == 0) {
					YakDurum = "X" + GelenRole + "A15Y";
					MyClientTask myClientTaskAc = new MyClientTask();
					myClientTaskAc.execute(YakDurum);
				}

				if (BtnTikla15 == 1) {
					KapatDurum = "X" + GelenRole + "K15Y";
					MyClientTask myClientTaskKapat = new MyClientTask();
					myClientTaskKapat.execute(KapatDurum);
				}

				if (BtnTikla15 == -2) {
					MHata Sonuc = new MHata();
					Sonuc.setBASLIK("Kontrol");
					Sonuc.setHATAMI(true);
					Sonuc.setMESAJ("Baðlantý Ýþlemi Baþarýsýz!");
					Sonuc.GosterMesajToast(Kontrol.this);
					return;
				}
			}
		});

		Btn16.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (BtnTikla16 == 0) {
					YakDurum = "X" + GelenRole + "A16Y";
					MyClientTask myClientTaskAc = new MyClientTask();
					myClientTaskAc.execute(YakDurum);
				}

				if (BtnTikla16 == 1) {
					KapatDurum = "X" + GelenRole + "K16Y";
					MyClientTask myClientTaskKapat = new MyClientTask();
					myClientTaskKapat.execute(KapatDurum);
				}

				if (BtnTikla16 == -2) {
					MHata Sonuc = new MHata();
					Sonuc.setBASLIK("Kontrol");
					Sonuc.setHATAMI(true);
					Sonuc.setMESAJ("Baðlantý Ýþlemi Baþarýsýz!");
					Sonuc.GosterMesajToast(Kontrol.this);
					return;
				}
			}
		});

		BtnTKapat.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (BtnTiklaTumunuKapat == -2) {
					MHata Sonuc = new MHata();
					Sonuc.setBASLIK("Kontrol");
					Sonuc.setHATAMI(true);
					Sonuc.setMESAJ("Baðlantý Ýþlemi Baþarýsýz!");
					Sonuc.GosterMesajToast(Kontrol.this);
					return;
				}

				MyClientTask myClientTask = new MyClientTask();
				String DurumTKapat = "X" + GelenRole + "K00" + "Y";
				myClientTask.execute(DurumTKapat);

			}
		});

		BtnTAc.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (BtnTiklaTumunuAc == -2) {
					MHata Sonuc = new MHata();
					Sonuc.setBASLIK("Kontrol");
					Sonuc.setHATAMI(true);
					Sonuc.setMESAJ("Baðlantý Ýþlemi Baþarýsýz!");
					Sonuc.GosterMesajToast(Kontrol.this);
					return;
				}
				
				final MyClientTasks myClientTask = new MyClientTasks();
				String DurumTAc = "X" + GelenRole + "A00" + "Y";
				myClientTask.execute(DurumTAc);

			}
		});
	}
	
	@SuppressWarnings("unused")
	private String extract(InputStream inputStream) throws IOException {	
		ByteArrayOutputStream baos = new ByteArrayOutputStream();				
		byte[] buffer = new byte[1024];
		int read = 0;
		while ((read = inputStream.read(buffer, 0, buffer.length)) != 'Y') {
			baos.write(buffer, 0, read);
			break;
		}		
		baos.flush();		
		return  new String(baos.toByteArray(), "UTF-8");
	}

	public class MyClientTask extends AsyncTask<String, String, String> {
		
		@Override
		protected String doInBackground(String... params) {

			Socket socket = null;
			OutputStream os = null;
			InputStream inputStream = null;

			try {
				socket = new Socket(MSabit.MUrl, Integer.parseInt(MSabit.MPort));
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
						7);
				byte[] buffer = new byte[7];

				int bytesRead;
				inputStream = socket.getInputStream();
				os = socket.getOutputStream();
				DataOutputStream dos = new DataOutputStream(os);
				dos.writeUTF(params[0]);
				dos.flush();
				try {
					while(true) {
						bytesRead = inputStream.read(buffer, 0, 6);
						byteArrayOutputStream.write(buffer, 0, bytesRead);
						if (byteArrayOutputStream.toString("UTF-8").length() == 6) {
							TutDurum = byteArrayOutputStream.toString("UTF-8");
							break;
						}
					}			
				} catch (UnsupportedEncodingException ex) {
			       TutDurum = "GelenPaket";
			    }
			    catch (IOException ex) {
			        TutDurum = "GelenPaket";
			    }

				
				/*
				if (inputStream.available() == 0) {
					TutDurum = "Error";
				} else {
				
					while ((bytesRead = inputStream.read(buffer)) != 'Y') {
						byteArrayOutputStream.write(buffer, 0, bytesRead);
						if (byteArrayOutputStream.toString("UTF-8").length() == 6) {
							TutDurum = byteArrayOutputStream.toString("UTF-8");
							break;
						}
					}
				} 
				*/
				os.close();
				inputStream.close();
				socket.close();

			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				MHata Sonuc = new MHata();
				Sonuc.setBASLIK("Kontrol");
				Sonuc.setHATAMI(true);
				Sonuc.setMESAJ("Baðlantý Ýþlemi Baþarýsýz!");

				TutDurum = "UnknownHostException"; // + e.toString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				MHata Sonuc = new MHata();
				Sonuc.setBASLIK("Kontrol");
				Sonuc.setHATAMI(true);
				Sonuc.setMESAJ("Baðlantý Ýþlemi Baþarýsýz!");

				TutDurum = "IOException"; // + e.toString();
			}

			return TutDurum;
		}

		@Override
		protected void onPostExecute(String result) {

			MSabit.GelenCevap = result;

			// Bir

			if (result.equals("UnknownHostException")) {
				BtnTikla1 = -2;
				BtnTikla2 = -2;
				BtnTikla3 = -2;
				BtnTikla4 = -2;
				BtnTikla5 = -2;
				BtnTikla6 = -2;
				BtnTikla7 = -2;
				BtnTikla8 = -2;
				BtnTikla9 = -2;
				BtnTikla10 = -2;
				BtnTikla11 = -2;
				BtnTikla12 = -2;
				BtnTikla13 = -2;
				BtnTikla14 = -2;
				BtnTikla15 = -2;
				BtnTikla16 = -2;
				BtnTiklaTumunuAc = -2;
				BtnTiklaTumunuKapat = -2;
			}

			if (result.equals("IOException")) {
				BtnTikla1 = -2;
				BtnTikla2 = -2;
				BtnTikla3 = -2;
				BtnTikla4 = -2;
				BtnTikla5 = -2;
				BtnTikla6 = -2;
				BtnTikla7 = -2;
				BtnTikla8 = -2;
				BtnTikla9 = -2;
				BtnTikla10 = -2;
				BtnTikla11 = -2;
				BtnTikla12 = -2;
				BtnTikla13 = -2;
				BtnTikla14 = -2;
				BtnTikla15 = -2;
				BtnTikla16 = -2;
				BtnTiklaTumunuAc = -2;
				BtnTiklaTumunuKapat = -2;
			}
			
			if (result.equals("GelenPaket")) {
				Toast.makeText(Kontrol.this, "Gelen Paket Hatalý!", Toast.LENGTH_SHORT).show();
				return;
			}

			if ((result == null) || (result.equals(""))) {
				TutDurum = "Cihaz Kapalý veya Að Kablosu Takýlý Deðil";
				Toast.makeText(Kontrol.this, TutDurum, Toast.LENGTH_SHORT)
						.show();
			}

			if (result.equals("X" + GelenRole + "A01Y")) {
				Btn1.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla1 = 1;
			}

			if (result.equals("X" + GelenRole + "K01Y")) {
				Btn1.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla1 = 0;
			}

			// Ýki
			if (result.equals("X" + GelenRole + "A02Y")) {
				Btn2.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla2 = 1;
			}

			if (result.equals("X" + GelenRole + "K02Y")) {
				Btn2.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla2 = 0;
			}
			// Üç
			if (result.equals("X" + GelenRole + "A03Y")) {
				Btn3.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla3 = 1;
			}

			if (result.equals("X" + GelenRole + "K03Y")) {
				Btn3.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla3 = 0;
			}

			// Dört
			if (result.equals("X" + GelenRole + "A04Y")) {
				Btn4.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla4 = 1;
			}

			if (result.equals("X" + GelenRole + "K04Y")) {
				Btn4.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla4 = 0;
			}

			// Beþ
			if (result.equals("X" + GelenRole + "A05Y")) {
				Btn5.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla5 = 1;
			}

			if (result.equals("X" + GelenRole + "K05Y")) {
				Btn5.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla5 = 0;
			}

			// Altý

			if (result.equals("X" + GelenRole + "A06Y")) {
				Btn6.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla6 = 1;
			}

			if (result.equals("X" + GelenRole + "K06Y")) {
				Btn6.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla6 = 0;
			}

			// Yedi
			if (result.equals("X" + GelenRole + "A07Y")) {
				Btn7.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla7 = 1;
			}

			if (result.equals("X" + GelenRole + "K07Y")) {
				Btn7.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla7 = 0;
			}
			// Sekiz
			if (result.equals("X" + GelenRole + "A08Y")) {
				Btn8.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla8 = 1;
			}

			if (result.equals("X" + GelenRole + "K08Y")) {
				Btn8.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla8 = 0;
			}

			// Dokuz
			if (result.equals("X" + GelenRole + "A09Y")) {
				Btn9.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla9 = 1;
			}

			if (result.equals("X" + GelenRole + "K09Y")) {
				Btn9.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla9 = 0;
			}

			// On
			if (result.equals("X" + GelenRole + "A10Y")) {
				Btn10.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla10 = 1;
			}

			if (result.equals("X" + GelenRole + "K10Y")) {
				Btn10.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla10 = 0;
			}

			// On Bir
			if (result.equals("X" + GelenRole + "A11Y")) {
				Btn11.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla11 = 1;
			}

			if (result.equals("X" + GelenRole + "K11Y")) {
				Btn11.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla11 = 0;
			}

			// On Iki
			if (result.equals("X" + GelenRole + "A12Y")) {
				Btn12.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla12 = 1;
			}

			if (result.equals("X" + GelenRole + "K12Y")) {
				Btn12.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla12 = 0;
			}

			// On Uç
			if (result.equals("X" + GelenRole + "A13Y")) {
				Btn13.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla13 = 1;
			}

			if (result.equals("X" + GelenRole + "K13Y")) {
				Btn13.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla13 = 0;
			}

			// On Dort
			if (result.equals("X" + GelenRole + "A14Y")) {
				Btn14.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla14 = 1;
			}

			if (result.equals("X" + GelenRole + "K14Y")) {
				Btn14.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla14 = 0;
			}

			// On Bes
			if (result.equals("X" + GelenRole + "A15Y")) {
				Btn15.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla15 = 1;
			}

			if (result.equals("X" + GelenRole + "K15Y")) {
				Btn15.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla15 = 0;
			}

			// On Altý
			if (result.equals("X" + GelenRole + "A16Y")) {
				Btn16.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla16 = 1;
			}

			if (result.equals("X" + GelenRole + "K16Y")) {
				Btn16.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla16 = 0;
			}

			if (result.equals("X" + GelenRole + "A00Y")) {

				Btn1.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla1 = 1;

				Btn2.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla2 = 1;

				Btn3.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla3 = 1;

				Btn4.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla4 = 1;

				Btn5.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla5 = 1;

				Btn6.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla6 = 1;

				Btn7.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla7 = 1;

				Btn8.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla8 = 1;

				Btn9.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla9 = 1;

				Btn10.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla10 = 1;

				Btn11.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla11 = 1;

				Btn12.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla12 = 1;

				Btn13.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla13 = 1;

				Btn14.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla14 = 1;

				Btn15.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla15 = 1;

				Btn16.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla16 = 1;
			}

			if (result.equals("X" + GelenRole + "K00Y")) {

				Btn1.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla1 = 0;

				Btn2.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla2 = 0;

				Btn3.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla3 = 0;

				Btn4.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla4 = 0;

				Btn5.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla5 = 0;

				Btn6.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla6 = 0;

				Btn7.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla7 = 0;

				Btn8.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla8 = 0;

				Btn9.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla9 = 0;

				Btn10.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla10 = 0;

				Btn11.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla11 = 0;

				Btn12.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla12 = 0;

				Btn13.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla13 = 0;

				Btn14.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla14 = 0;

				Btn15.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla15 = 0;

				Btn16.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla16 = 0;
			}
		}
	}
	
	public class MyClientTasks extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {

			Socket socket = null;
			OutputStream os = null;
			InputStream inputStream = null;

			try {
				socket = new Socket(MSabit.MUrl, Integer.parseInt(MSabit.MPort));

				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
						1024);
				byte[] buffer = new byte[1024];

				int bytesRead;
				@SuppressWarnings("unused")
				boolean deneme = false;
				inputStream = socket.getInputStream();
				os = socket.getOutputStream();
				DataOutputStream dos = new DataOutputStream(os);
				dos.writeUTF(params[0]);

				while ((bytesRead = inputStream.read(buffer)) != 'Y') {
					byteArrayOutputStream.write(buffer, 0, bytesRead);
					if (byteArrayOutputStream.toString("UTF-8").length() == 6) {
						TutDurum = byteArrayOutputStream.toString("UTF-8");
						break;
					}
				}

				os.close();
				inputStream.close();
				socket.close();

			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				MHata Sonuc = new MHata();
				Sonuc.setBASLIK("Kontrol");
				Sonuc.setHATAMI(true);
				Sonuc.setMESAJ("Baðlantý Ýþlemi Baþarýsýz!");

				TutDurum = "UnknownHostException"; // + e.toString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				MHata Sonuc = new MHata();
				Sonuc.setBASLIK("Kontrol");
				Sonuc.setHATAMI(true);
				Sonuc.setMESAJ("Baðlantý Ýþlemi Baþarýsýz!");

				TutDurum = "IOException"; // + e.toString();
			}

			return TutDurum;
		}

		@Override
		protected void onPostExecute(String result) {

			MSabit.GelenCevap = result;

			// Bir

			if (result.equals("UnknownHostException")) {
				BtnTikla1 = -2;
				BtnTikla2 = -2;
				BtnTikla3 = -2;
				BtnTikla4 = -2;
				BtnTikla5 = -2;
				BtnTikla6 = -2;
				BtnTikla7 = -2;
				BtnTikla8 = -2;
				BtnTikla9 = -2;
				BtnTikla10 = -2;
				BtnTikla11 = -2;
				BtnTikla12 = -2;
				BtnTikla13 = -2;
				BtnTikla14 = -2;
				BtnTikla15 = -2;
				BtnTikla16 = -2;
				BtnTiklaTumunuAc = -2;
				BtnTiklaTumunuKapat = -2;
			}

			if (result.equals("IOException")) {
				BtnTikla1 = -2;
				BtnTikla2 = -2;
				BtnTikla3 = -2;
				BtnTikla4 = -2;
				BtnTikla5 = -2;
				BtnTikla6 = -2;
				BtnTikla7 = -2;
				BtnTikla8 = -2;
				BtnTikla9 = -2;
				BtnTikla10 = -2;
				BtnTikla11 = -2;
				BtnTikla12 = -2;
				BtnTikla13 = -2;
				BtnTikla14 = -2;
				BtnTikla15 = -2;
				BtnTikla16 = -2;
				BtnTiklaTumunuAc = -2;
				BtnTiklaTumunuKapat = -2;
			}

			if ((result == null) || (result.equals(""))) {
				TutDurum = "Cihaz Kapalý veya Að Kablosu Takýlý Deðil";
				Toast.makeText(Kontrol.this, TutDurum, Toast.LENGTH_SHORT)
						.show();
			}

			if (result.equals("X" + GelenRole + "A01Y")) {
				Btn1.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla1 = 1;
			}

			if (result.equals("X" + GelenRole + "K01Y")) {
				Btn1.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla1 = 0;
			}

			// Ýki
			if (result.equals("X" + GelenRole + "A02Y")) {
				Btn2.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla2 = 1;
			}

			if (result.equals("X" + GelenRole + "K02Y")) {
				Btn2.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla2 = 0;
			}
			// Üç
			if (result.equals("X" + GelenRole + "A03Y")) {
				Btn3.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla3 = 1;
			}

			if (result.equals("X" + GelenRole + "K03Y")) {
				Btn3.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla3 = 0;
			}

			// Dört
			if (result.equals("X" + GelenRole + "A04Y")) {
				Btn4.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla4 = 1;
			}

			if (result.equals("X" + GelenRole + "K04Y")) {
				Btn4.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla4 = 0;
			}

			// Beþ
			if (result.equals("X" + GelenRole + "A05Y")) {
				Btn5.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla5 = 1;
			}

			if (result.equals("X" + GelenRole + "K05Y")) {
				Btn5.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla5 = 0;
			}

			// Altý

			if (result.equals("X" + GelenRole + "A06Y")) {
				Btn6.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla6 = 1;
			}

			if (result.equals("X" + GelenRole + "K06Y")) {
				Btn6.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla6 = 0;
			}

			// Yedi
			if (result.equals("X" + GelenRole + "A07Y")) {
				Btn7.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla7 = 1;
			}

			if (result.equals("X" + GelenRole + "K07Y")) {
				Btn7.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla7 = 0;
			}
			// Sekiz
			if (result.equals("X" + GelenRole + "A08Y")) {
				Btn8.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla8 = 1;
			}

			if (result.equals("X" + GelenRole + "K08Y")) {
				Btn8.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla8 = 0;
			}

			// Dokuz
			if (result.equals("X" + GelenRole + "A09Y")) {
				Btn9.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla9 = 1;
			}

			if (result.equals("X" + GelenRole + "K09Y")) {
				Btn9.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla9 = 0;
			}

			// On
			if (result.equals("X" + GelenRole + "A10Y")) {
				Btn10.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla10 = 1;
			}

			if (result.equals("X" + GelenRole + "K10Y")) {
				Btn10.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla10 = 0;
			}

			// On Bir
			if (result.equals("X" + GelenRole + "A11Y")) {
				Btn11.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla11 = 1;
			}

			if (result.equals("X" + GelenRole + "K11Y")) {
				Btn11.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla11 = 0;
			}

			// On Iki
			if (result.equals("X" + GelenRole + "A12Y")) {
				Btn12.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla12 = 1;
			}

			if (result.equals("X" + GelenRole + "K12Y")) {
				Btn12.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla12 = 0;
			}

			// On Uç
			if (result.equals("X" + GelenRole + "A13Y")) {
				Btn13.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla13 = 1;
			}

			if (result.equals("X" + GelenRole + "K13Y")) {
				Btn13.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla13 = 0;
			}

			// On Dort
			if (result.equals("X" + GelenRole + "A14Y")) {
				Btn14.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla14 = 1;
			}

			if (result.equals("X" + GelenRole + "K14Y")) {
				Btn14.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla14 = 0;
			}

			// On Bes
			if (result.equals("X" + GelenRole + "A15Y")) {
				Btn15.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla15 = 1;
			}

			if (result.equals("X" + GelenRole + "K15Y")) {
				Btn15.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla15 = 0;
			}

			// On Altý
			if (result.equals("X" + GelenRole + "A16Y")) {
				Btn16.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla16 = 1;
			}

			if (result.equals("X" + GelenRole + "K16Y")) {
				Btn16.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla16 = 0;
			}

			if (result.equals("X" + GelenRole + "A00Y")) {

				Btn1.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla1 = 1;

				Btn2.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla2 = 1;

				Btn3.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla3 = 1;

				Btn4.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla4 = 1;

				Btn5.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla5 = 1;

				Btn6.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla6 = 1;

				Btn7.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla7 = 1;

				Btn8.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla8 = 1;

				Btn9.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla9 = 1;

				Btn10.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla10 = 1;

				Btn11.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla11 = 1;

				Btn12.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla12 = 1;

				Btn13.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla13 = 1;

				Btn14.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla14 = 1;

				Btn15.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla15 = 1;

				Btn16.setImageDrawable(getResources().getDrawable(
						R.drawable.yandi));
				BtnTikla16 = 1;
			}

			if (result.equals("X" + GelenRole + "K00Y")) {

				Btn1.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla1 = 0;

				Btn2.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla2 = 0;

				Btn3.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla3 = 0;

				Btn4.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla4 = 0;

				Btn5.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla5 = 0;

				Btn6.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla6 = 0;

				Btn7.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla7 = 0;

				Btn8.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla8 = 0;

				Btn9.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla9 = 0;

				Btn10.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla10 = 0;

				Btn11.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla11 = 0;

				Btn12.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla12 = 0;

				Btn13.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla13 = 0;

				Btn14.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla14 = 0;

				Btn15.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla15 = 0;

				Btn16.setImageDrawable(getResources().getDrawable(
						R.drawable.kapandi));
				BtnTikla16 = 0;
			}
		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	public void Dialog(final String TXTTIP, final TextView Txt) {
		AlertDialog.Builder alert = new AlertDialog.Builder(Kontrol.this);

		alert.setTitle("Yeni Ýsim Giriniz");

		final EditText input = new EditText(Kontrol.this);
		alert.setView(input);

		alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String srt = input.getEditableText().toString();
				MSabit.MTextIsim = new MTextName(MSabit
						.GetirPAyarID(MSabit.MSubeAdi), Integer
						.parseInt(GelenRole), srt, TXTTIP);

				if (!srt.equals("")) {
					if (MSabit.MTextIsim.VarMiTextName()) {
						MSabit.MTextIsim.GuncelleTextName();
						Txt.setText(srt);
					} else {
						MSabit.MTextIsim.EkleTextName();
						Txt.setText(srt);
					}
				} else {
					MHata Sonuc = new MHata();
					Sonuc.setBASLIK("Kontrol");
					Sonuc.setHATAMI(true);
					Sonuc.setMESAJ("Gerekli Alaný Doldurunuz!");
					Sonuc.GosterMesajToast(Kontrol.this);
				}

			}
		});

		alert.setNegativeButton("Çýkýþ", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.cancel();
			}
		});
		AlertDialog alertDialog = alert.create();
		alertDialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.kontrolmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		if (id == R.id.bilgi) {
			Intent MyIntent = new Intent(Kontrol.this, Bilgi.class);
			startActivity(MyIntent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
