package net.multipi.QrReader.func;

import net.multipi.QrReader.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;


public class Hata {
	
	public String BASLIK, MESAJ;
	public boolean HATAMI;
	
	public Hata() {
		this.BASLIK = "";
		this.MESAJ = "";
		this.HATAMI = false;
	}
	
	public Hata(String BASLIK, String MESAJ, boolean HATAMI) {
		this.BASLIK = BASLIK;
		this.MESAJ = MESAJ;
		this.HATAMI = HATAMI;
	}
	
	public void setBASLIK(String BASLIK) {
		this.BASLIK = BASLIK;
	}
	
	
	public String getBASLIK() {
		return this.BASLIK;
	}
	
	public void setMESAJ(String MESAJ) {
		this.MESAJ = MESAJ;
	}
	
	public String getMESAJ() {
		return this.MESAJ;
	} 
	
	public void setHATAMI(boolean HATAMI) {
		this.HATAMI = HATAMI;
	}
	
	public Boolean getHATAMI() {
		return this.HATAMI;
	}
	
	public String ToString() {
		return "#" + this.BASLIK + "# " + this.MESAJ;
	}
	
    public void GosterMesajToast(Context CONTEXT) {
		
    	if (this.BASLIK != "")
			this.BASLIK = "(" + this.BASLIK + ") ";
		Toast.makeText(CONTEXT, this.BASLIK + this.MESAJ, Toast.LENGTH_SHORT).show();
    }
    
	public void GosterMesajDialog(Context CONTEXT) {
		
		Builder Builder = new AlertDialog.Builder(CONTEXT, R.style.Widget_OpakPlasiyer_Light_AlertDialog);
		Builder.setTitle(this.BASLIK);
		
		if(!this.HATAMI) {
			Builder.setIcon(R.drawable.ic_launcher);
		} else {
			Builder.setIcon(R.drawable.ic_launcher);
		}
		
		Builder.setMessage (this.MESAJ);
		Builder.setPositiveButton("Tamam" ,new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		Builder.show();
	}
}
