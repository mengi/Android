package com.android.afcsocet;

import java.util.ArrayList;

import com.android.fuk.MHata;
import com.android.fuk.MSabit;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

@SuppressWarnings("deprecation")
public class RoleKontrol extends Activity {
	RelativeLayout RoleBir, RoleIki, RoleUc, RoleDort, RoleBes, RelaSubeEkle;

	public RadioButton RdIcIp, RdDisIp, RBAktarilan;

	boolean durum1 = false;
	boolean durum2 = false;
	boolean durum3 = false;
	boolean durum4 = false;
	boolean durum5 = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rolekontrol);

		this.setTitle("");

		RoleBir = (RelativeLayout) findViewById(R.id.RoleBir);
		RoleIki = (RelativeLayout) findViewById(R.id.roleIki);
		RoleUc = (RelativeLayout) findViewById(R.id.RoleUc);
		RoleDort = (RelativeLayout) findViewById(R.id.RoleDort);
		RoleBes = (RelativeLayout) findViewById(R.id.RoleBes);
		RelaSubeEkle = (RelativeLayout) findViewById(R.id.RelaSubeEkle);

		RdIcIp = (RadioButton) findViewById(R.id.RdIcIp);
		RdDisIp = (RadioButton) findViewById(R.id.RdDisIp);

		final ArrayList<String> Subeler = MSabit.GetirSubeler();

		MSabit.Kullanici.GetirKullanici();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getBaseContext(),
				android.R.layout.simple_spinner_dropdown_item, Subeler);

		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		OnNavigationListener navigationListener = new OnNavigationListener() {

			public boolean onNavigationItemSelected(int itemPosition,
					long itemId) {

				MSabit.MSubeAdi = Subeler.get(itemPosition);
				return false;
			}
		};

		getActionBar().setListNavigationCallbacks(adapter, navigationListener);

		final int RoleSayisi = Integer.parseInt(MSabit.Kullanici
				.getKartsayisi());

		for (int i = 1; i <= RoleSayisi; i++) {
			if (i == 1) {
				durum1 = true;
			}

			if (i == 2) {
				durum2 = true;
			}

			if (i == 3) {
				durum3 = true;
			}

			if (i == 4) {
				durum4 = true;
			}

			if (i == 5) {
				durum5 = true;
			}
		}

		View.OnClickListener RadiClick = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onRadioButtonClicked(v);
			}
		};

		RdIcIp.setOnClickListener(RadiClick);
		RdDisIp.setOnClickListener(RadiClick);

		RoleBir.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (RBAktarilan != null) {

					switch (RBAktarilan.getId()) {
					case R.id.RdIcIp:
						MSabit.PAyar.GetirParamAyarSubeAd(MSabit.MSubeAdi);
						if (!MSabit.PAyar.getICIP().equals("")) {
							MSabit.MUrl = MSabit.PAyar.getICIP();
							MSabit.MPort = MSabit.PAyar.getPORTNUMARASI();

							if (durum1) {
								Intent MyIntent = new Intent(RoleKontrol.this,
										Kontrol.class);
								MyIntent.putExtra("KontrolRole", "1");
								startActivity(MyIntent);
							} else {
								MHata Sonuc = new MHata();
								Sonuc.setBASLIK("Role Kontrol");
								Sonuc.setHATAMI(true);
								Sonuc.setMESAJ("Kontrol - "
										+ "1"
										+ " izin yok!");
								Sonuc.GosterMesajToast(RoleKontrol.this);
								return;
							}

						} else {
							MHata Sonuc = new MHata();
							Sonuc.setHATAMI(true);
							Sonuc.setBASLIK("Dýþ, Ýç Ýp, Þube");
							Sonuc.setMESAJ("Þube Ayarlarýný Giriniz!");
							Sonuc.GosterMesajToast(RoleKontrol.this);
							return;
						}
						break;
					case R.id.RdDisIp:
						MSabit.PAyar.GetirParamAyarSubeAd(MSabit.MSubeAdi);

						if (!MSabit.PAyar.getDISIP().equals("")) {

							MSabit.MUrl = MSabit.PAyar.getDISIP();
							MSabit.MPort = MSabit.PAyar.getPORTNUMARASI();

							if (durum1) {
								Intent MyIntent = new Intent(RoleKontrol.this,
										Kontrol.class);
								MyIntent.putExtra("KontrolRole", "1");
								startActivity(MyIntent);
							} else {
								MHata Sonuc = new MHata();
								Sonuc.setBASLIK("Role Kontrol");
								Sonuc.setHATAMI(true);
								Sonuc.setMESAJ("Kontrol - "
										+ "1"
										+ " izin yok!");
								Sonuc.GosterMesajToast(RoleKontrol.this);
								return;
							}
						} else {
							MHata Sonuc = new MHata();
							Sonuc.setHATAMI(true);
							Sonuc.setBASLIK("Dýþ, Ýç Ýp");
							Sonuc.setMESAJ("Þube Ayarlarýný Giriniz!");
							Sonuc.GosterMesajToast(RoleKontrol.this);
							return;
						}
						break;
					default:
						MHata Sonuc = new MHata();
						Sonuc.setHATAMI(true);
						Sonuc.setBASLIK("Parametre Ayarlarý");
						Sonuc.setMESAJ("Parametre Seçiniz!");
						Sonuc.GosterMesajToast(RoleKontrol.this);
						return;

					}
				} else {
					MHata Sonuc = new MHata();
					Sonuc.setHATAMI(true);
					Sonuc.setBASLIK("Parametre Ayarlarý");
					Sonuc.setMESAJ("Ýç veya Dýþ Ip Seçiniz!");
					Sonuc.GosterMesajToast(RoleKontrol.this);
					return;
				}

				// MSabit.SorDisMiIcMi(RoleKontrol.this, durum1,
				// MSabit.MSubeAdi,RoleKontrol.this, "1");
			}
		});

		RoleIki.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (RBAktarilan != null) {

					switch (RBAktarilan.getId()) {
					case R.id.RdIcIp:
						MSabit.PAyar.GetirParamAyarSubeAd(MSabit.MSubeAdi);
						if (!MSabit.PAyar.getICIP().equals("")) {
							MSabit.MUrl = MSabit.PAyar.getICIP();
							MSabit.MPort = MSabit.PAyar.getPORTNUMARASI();

							if (durum2) {
								Intent MyIntent = new Intent(RoleKontrol.this,
										Kontrol.class);
								MyIntent.putExtra("KontrolRole", "2");
								startActivity(MyIntent);
							} else {
								MHata Sonuc = new MHata();
								Sonuc.setBASLIK("Role Kontrol");
								Sonuc.setHATAMI(true);
								Sonuc.setMESAJ("Kontrol - "
										+ "2"
										+ " izin yok!");
								Sonuc.GosterMesajToast(RoleKontrol.this);
								return;
							}

						} else {
							MHata Sonuc = new MHata();
							Sonuc.setHATAMI(true);
							Sonuc.setBASLIK("Dýþ, Ýç Ýp");
							Sonuc.setMESAJ("Þube Ayarlarýný Giriniz!");
							Sonuc.GosterMesajToast(RoleKontrol.this);
							return;
						}
						break;
					case R.id.RdDisIp:
						MSabit.PAyar.GetirParamAyarSubeAd(MSabit.MSubeAdi);

						if (!MSabit.PAyar.getDISIP().equals("")) {

							MSabit.MUrl = MSabit.PAyar.getDISIP();
							MSabit.MPort = MSabit.PAyar.getPORTNUMARASI();

							if (durum2) {
								Intent MyIntent = new Intent(RoleKontrol.this,
										Kontrol.class);
								MyIntent.putExtra("KontrolRole", "2");
								startActivity(MyIntent);
							} else {
								MHata Sonuc = new MHata();
								Sonuc.setBASLIK("Role Kontrol");
								Sonuc.setHATAMI(true);
								Sonuc.setMESAJ("Kontrol - "
										+ "2"
										+ " izin yok!");
								Sonuc.GosterMesajToast(RoleKontrol.this);
								return;
							}

						} else {
							MHata Sonuc = new MHata();
							Sonuc.setHATAMI(true);
							Sonuc.setBASLIK("Dýþ, Ýç Ýp");
							Sonuc.setMESAJ("Þube Ayarlarýný Giriniz!");
							Sonuc.GosterMesajToast(RoleKontrol.this);
							return;
						}
						break;
					default:
						MHata Sonuc = new MHata();
						Sonuc.setHATAMI(true);
						Sonuc.setBASLIK("Parametre Ayarlarý");
						Sonuc.setMESAJ("Parametre Seçiniz!");
						Sonuc.GosterMesajToast(RoleKontrol.this);
						return;

					}
				} else {
					MHata Sonuc = new MHata();
					Sonuc.setHATAMI(true);
					Sonuc.setBASLIK("Parametre Ayarlarý");
					Sonuc.setMESAJ("Ýç veya Dýþ Ip Seçiniz!");
					Sonuc.GosterMesajToast(RoleKontrol.this);
					return;
				}
				// MSabit.SorDisMiIcMi(RoleKontrol.this, durum2,
				// MSabit.MSubeAdi, RoleKontrol.this, "2");
			}
		});

		RoleUc.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (RBAktarilan != null) {

					switch (RBAktarilan.getId()) {
					case R.id.RdIcIp:
						MSabit.PAyar.GetirParamAyarSubeAd(MSabit.MSubeAdi);
						if (!MSabit.PAyar.getICIP().equals("")) {
							MSabit.MUrl = MSabit.PAyar.getICIP();
							MSabit.MPort = MSabit.PAyar.getPORTNUMARASI();

							if (durum3) {
								Intent MyIntent = new Intent(RoleKontrol.this,
										Kontrol.class);
								MyIntent.putExtra("KontrolRole", "3");
								startActivity(MyIntent);
							} else {
								MHata Sonuc = new MHata();
								Sonuc.setBASLIK("Role Kontrol");
								Sonuc.setHATAMI(true);
								Sonuc.setMESAJ("Kontrol - "
										+ "3"
										+ " izin yok!");
								Sonuc.GosterMesajToast(RoleKontrol.this);
								return;
							}

						} else {
							MHata Sonuc = new MHata();
							Sonuc.setHATAMI(true);
							Sonuc.setBASLIK("Dýþ, Ýç Ýp");
							Sonuc.setMESAJ("Þube Ayarlarýný Giriniz!");
							Sonuc.GosterMesajToast(RoleKontrol.this);
							return;
						}
						break;
					case R.id.RdDisIp:
						MSabit.PAyar.GetirParamAyarSubeAd(MSabit.MSubeAdi);

						if (!MSabit.PAyar.getDISIP().equals("")) {

							MSabit.MUrl = MSabit.PAyar.getDISIP();
							MSabit.MPort = MSabit.PAyar.getPORTNUMARASI();

							if (durum3) {
								Intent MyIntent = new Intent(RoleKontrol.this,
										Kontrol.class);
								MyIntent.putExtra("KontrolRole", "3");
								startActivity(MyIntent);
							} else {
								MHata Sonuc = new MHata();
								Sonuc.setBASLIK("Role Kontrol");
								Sonuc.setHATAMI(true);
								Sonuc.setMESAJ("Kontrol - "
										+ "3"
										+ " izin yok!");
								Sonuc.GosterMesajToast(RoleKontrol.this);
								return;
							}

						} else {
							MHata Sonuc = new MHata();
							Sonuc.setHATAMI(true);
							Sonuc.setBASLIK("Dýþ, Ýç Ýp");
							Sonuc.setMESAJ("Þube Ayarlarýný Giriniz!");
							Sonuc.GosterMesajToast(RoleKontrol.this);
							return;
						}
						break;
					default:
						MHata Sonuc = new MHata();
						Sonuc.setHATAMI(true);
						Sonuc.setBASLIK("Parametre Ayarlarý");
						Sonuc.setMESAJ("Parametre Seçiniz!");
						Sonuc.GosterMesajToast(RoleKontrol.this);
						return;

					}
				} else {
					MHata Sonuc = new MHata();
					Sonuc.setHATAMI(true);
					Sonuc.setBASLIK("Parametre Ayarlarý");
					Sonuc.setMESAJ("Ýç veya Dýþ Ip Seçiniz!");
					Sonuc.GosterMesajToast(RoleKontrol.this);
					return;
				}
				// MSabit.SorDisMiIcMi(RoleKontrol.this, durum3,
				// MSabit.MSubeAdi, RoleKontrol.this, "3");
			}
		});

		RoleDort.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (RBAktarilan != null) {

					switch (RBAktarilan.getId()) {
					case R.id.RdIcIp:
						MSabit.PAyar.GetirParamAyarSubeAd(MSabit.MSubeAdi);
						if (!MSabit.PAyar.getICIP().equals("")) {
							MSabit.MUrl = MSabit.PAyar.getICIP();
							MSabit.MPort = MSabit.PAyar.getPORTNUMARASI();

							if (durum4) {
								Intent MyIntent = new Intent(RoleKontrol.this,
										Kontrol.class);
								MyIntent.putExtra("KontrolRole", "4");
								startActivity(MyIntent);
							} else {
								MHata Sonuc = new MHata();
								Sonuc.setBASLIK("Role Kontrol");
								Sonuc.setHATAMI(true);
								Sonuc.setMESAJ("Kontrol - "
										+ "4"
										+ " izin yok!");
								Sonuc.GosterMesajToast(RoleKontrol.this);
								return;
							}

						} else {
							MHata Sonuc = new MHata();
							Sonuc.setHATAMI(true);
							Sonuc.setBASLIK("Dýþ, Ýç Ýp");
							Sonuc.setMESAJ("Þube Ayarlarýný Giriniz!");
							Sonuc.GosterMesajToast(RoleKontrol.this);
							return;
						}
						break;
					case R.id.RdDisIp:
						MSabit.PAyar.GetirParamAyarSubeAd(MSabit.MSubeAdi);

						if (!MSabit.PAyar.getDISIP().equals("")) {

							MSabit.MUrl = MSabit.PAyar.getDISIP();
							MSabit.MPort = MSabit.PAyar.getPORTNUMARASI();

							if (durum4) {
								Intent MyIntent = new Intent(RoleKontrol.this,
										Kontrol.class);
								MyIntent.putExtra("KontrolRole", "4");
								startActivity(MyIntent);
							} else {
								MHata Sonuc = new MHata();
								Sonuc.setBASLIK("Role Kontrol");
								Sonuc.setHATAMI(true);
								Sonuc.setMESAJ("Kontrol - "
										+ "4"
										+ " izin yok!");
								Sonuc.GosterMesajToast(RoleKontrol.this);
								return;
							}

						} else {
							MHata Sonuc = new MHata();
							Sonuc.setHATAMI(true);
							Sonuc.setBASLIK("Dýþ, Ýç Ýp");
							Sonuc.setMESAJ("Þube Ayarlarýný Giriniz!");
							Sonuc.GosterMesajToast(RoleKontrol.this);
							return;
						}
						break;
					default:
						MHata Sonuc = new MHata();
						Sonuc.setHATAMI(true);
						Sonuc.setBASLIK("Parametre Ayarlarý");
						Sonuc.setMESAJ("Parametre Seçiniz!");
						Sonuc.GosterMesajToast(RoleKontrol.this);
						return;

					}
				} else {
					MHata Sonuc = new MHata();
					Sonuc.setHATAMI(true);
					Sonuc.setBASLIK("Parametre Ayarlarý");
					Sonuc.setMESAJ("Ýç veya Dýþ Ip Seçiniz!");
					Sonuc.GosterMesajToast(RoleKontrol.this);
					return;
				}
				// MSabit.SorDisMiIcMi(RoleKontrol.this, durum4,
				// MSabit.MSubeAdi, RoleKontrol.this, "4");
			}
		});

		RoleBes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (RBAktarilan != null) {

					switch (RBAktarilan.getId()) {
					case R.id.RdIcIp:
						MSabit.PAyar.GetirParamAyarSubeAd(MSabit.MSubeAdi);
						if (!MSabit.PAyar.getICIP().equals("")) {
							MSabit.MUrl = MSabit.PAyar.getICIP();
							MSabit.MPort = MSabit.PAyar.getPORTNUMARASI();

							if (durum5) {
								Intent MyIntent = new Intent(RoleKontrol.this,
										Kontrol.class);
								MyIntent.putExtra("KontrolRole", "5");
								startActivity(MyIntent);
							} else {
								MHata Sonuc = new MHata();
								Sonuc.setBASLIK("Role Kontrol");
								Sonuc.setHATAMI(true);
								Sonuc.setMESAJ("Kontrol - "
										+ "5"
										+ " izin yok!");
								Sonuc.GosterMesajToast(RoleKontrol.this);
								return;
							}

						} else {
							MHata Sonuc = new MHata();
							Sonuc.setHATAMI(true);
							Sonuc.setBASLIK("Dýþ, Ýç Ýp");
							Sonuc.setMESAJ("Þube veya Parametre Ayarlarýný Giriniz");
							Sonuc.GosterMesajToast(RoleKontrol.this);
							return;
						}
						break;
					case R.id.RdDisIp:
						MSabit.PAyar.GetirParamAyarSubeAd(MSabit.MSubeAdi);

						if (!MSabit.PAyar.getDISIP().equals("")) {

							MSabit.MUrl = MSabit.PAyar.getDISIP();
							MSabit.MPort = MSabit.PAyar.getPORTNUMARASI();

							if (durum5) {
								Intent MyIntent = new Intent(RoleKontrol.this,
										Kontrol.class);
								MyIntent.putExtra("KontrolRole", "5");
								startActivity(MyIntent);
							} else {
								MHata Sonuc = new MHata();
								Sonuc.setBASLIK("Role Kontrol");
								Sonuc.setHATAMI(true);
								Sonuc.setMESAJ("Kontrol - "
										+ "5"
										+ " izin yok!");
								Sonuc.GosterMesajToast(RoleKontrol.this);
								return;
							}

						} else {
							MHata Sonuc = new MHata();
							Sonuc.setHATAMI(true);
							Sonuc.setBASLIK("Dýþ, Ýç Ýp");
							Sonuc.setMESAJ("Þube veya Parametre Ayarlarýný Giriniz");
							Sonuc.GosterMesajToast(RoleKontrol.this);
							return;
						}
						break;
					default:
						MHata Sonuc = new MHata();
						Sonuc.setHATAMI(true);
						Sonuc.setBASLIK("Parametre Ayarlarý");
						Sonuc.setMESAJ("Parametre Seçiniz");
						Sonuc.GosterMesajToast(RoleKontrol.this);
						return;

					}
				} else {
					MHata Sonuc = new MHata();
					Sonuc.setHATAMI(true);
					Sonuc.setBASLIK("Parametre Ayarlarý");
					Sonuc.setMESAJ("Ýç veya Dýþ Ip Seçiniz");
					Sonuc.GosterMesajToast(RoleKontrol.this);
					return;
				}
				// MSabit.SorDisMiIcMi(RoleKontrol.this, durum5,
				// MSabit.MSubeAdi, RoleKontrol.this, "5");
			}
		});

		RelaSubeEkle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(RoleKontrol.this, Kayit.class);
				startActivity(myIntent);
			}
		});
	}

	public void onRadioButtonClicked(final View view) {

		RdIcIp.setChecked(false);

		RdDisIp.setChecked(false);

		((RadioButton) view).setChecked(true);
		RBAktarilan = (RadioButton) view;

	}

	@Override
	protected void onRestart() {
		super.onRestart();
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
			Intent MyIntent = new Intent(RoleKontrol.this, Bilgi.class);
			startActivity(MyIntent);
			return true;
		}

		if (id == R.id.refresh) {
			finish();
			startActivity(getIntent());
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
