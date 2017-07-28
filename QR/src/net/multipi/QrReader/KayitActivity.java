package net.multipi.QrReader;

import net.multipi.QrReader.func.Hata;
import net.multipi.QrReader.func.Sabit;
import net.multipi.QrReader.model.KullaniciAyar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class KayitActivity extends Activity {
	
	EditText TxtKullaniciAdi, TxtParola, TxtKullaniciKodu;
	Button BtnAyar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayar);
        
        TxtKullaniciAdi = (EditText) findViewById(R.id.TxtKullaniciAdi);
        TxtParola = (EditText) findViewById(R.id.TxtParola);
        TxtKullaniciKodu = (EditText) findViewById(R.id.TxtKullaniciKodu);
        
        BtnAyar = (Button) findViewById(R.id.BtnAyar);
        
        BtnAyar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Kaydet();
				Intent myIntent = new Intent(KayitActivity.this, GirisActivity.class);
				startActivity(myIntent);
				finish();
			}
		});
        
        VeriYukle();
    }
    
    public void VeriYukle() {
    	Sabit.Ayar.GetirKullaniciAyar();
    	
    	boolean Durum = (Sabit.Ayar.getId() != -1); 
    	if (Durum) {
        	TxtKullaniciAdi.setText(Sabit.Ayar.getKullaniciAdi());
        	TxtKullaniciKodu.setText(Sabit.Ayar.getKullaniciKodu());
		}
    }
    
    public void Kaydet() {
    	try {
    		
    		if (TxtKullaniciAdi.getText().toString().equals("")) {
    			Hata Sonuc = new Hata();
    			Sonuc.setBASLIK("Ayar");
    			Sonuc.setHATAMI(true);		
    			Sonuc.setMESAJ("Gerekli Alanlarý Doldurunuz!");
    			Sonuc.GosterMesajDialog(KayitActivity.this);
    			return;
			}
    		
    		if (TxtKullaniciKodu.getText().toString().equals("")) {
    			Hata Sonuc = new Hata();
    			Sonuc.setBASLIK("Ayar");
    			Sonuc.setHATAMI(true);
    			Sonuc.setMESAJ("Gerekli Alanlarý Doldurunuz!");
    			Sonuc.GosterMesajDialog(KayitActivity.this);
    			return;
			}
    		
    		if (TxtParola.getText().toString().equals("")) {
    			Hata Sonuc = new Hata();
    			Sonuc.setBASLIK("Ayar");
    			Sonuc.setHATAMI(true);
    			Sonuc.setMESAJ("Gerekli Alanlarý Doldurunuz!");
    			Sonuc.GosterMesajDialog(KayitActivity.this);
    			return;
			}
    		
    		Sabit.Ayar = new KullaniciAyar();
    		Sabit.Ayar.setKullaniciAdi(TxtKullaniciAdi.getText().toString());
    		Sabit.Ayar.setKullaniciKodu(TxtKullaniciKodu.getText().toString());
    		Sabit.Ayar.setParola(TxtParola.getText().toString());
    		Sabit.Ayar.KayitKullaniciAyar();

		} catch (Exception e) {
			Hata Sonuc = new Hata();
			Sonuc.setBASLIK("Ayar");
			Sonuc.setHATAMI(true);		
			Sonuc.setMESAJ("Kayýt Baþarýsýz!");
			Sonuc.GosterMesajDialog(KayitActivity.this);
			return;
		}
    }
}
