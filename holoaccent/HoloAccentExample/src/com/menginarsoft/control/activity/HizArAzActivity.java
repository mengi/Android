package com.menginarsoft.control.activity;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.menginarsoft.control.R;
import com.menginarsoft.control.function.Sabit;
import com.menginarsoft.control.model.ParamAyar;
import com.negusoft.holoaccent.activity.AccentActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class HizArAzActivity extends AccentActivity {

	Button BtnArtir, BtnAzalt;
	public String TutDurum = "";
	ProgressBar ProgressBarArtirAzalt;
	public int progressStatus = 0;
	public int progressbarMax = 100;
	public String HizArtir = "G";
	public String HizAzalt = "H";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_artirazalt);

		ProgressBarArtirAzalt = (ProgressBar) findViewById(R.id.ProgressBarArtirAzalt);
		Sabit.MParamAyar = new ParamAyar();
		Sabit.MParamAyar.GetirParamAyar();

		/*
		 * barProgressDialog = new ProgressDialog(.this);
		 * 
		 * barProgressDialog.setTitle("Lütfen Bekleyiniz ...");
		 * barProgressDialog.setMessage("Parametreler Alýnýyor ...");
		 * barProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		 * barProgressDialog.setProgress(0); barProgressDialog.setMax(20)
		 */
		BtnArtir = (Button) findViewById(R.id.BtnArtir);
		BtnAzalt = (Button) findViewById(R.id.BtnAzalt);

		BtnArtir.setOnClickListener(new View.OnClickListener() {

			
			@Override
			public void onClick(View v) {
				if ((progressStatus >= 0) && (progressStatus < 100)) {
					progressStatus += 10;
					ProgressBarArtirAzalt.setProgress(progressStatus);
					
					HizArtirAzalt MyTaskHizArtirAzalt = new HizArtirAzalt();
					MyTaskHizArtirAzalt.execute(HizArtir);
				}

				if (progressStatus == 100) {
					ProgressBarArtirAzalt.setProgress(progressbarMax);
				}
			}
		});

		BtnAzalt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (progressStatus > 0) {
					progressStatus -= 10;
					ProgressBarArtirAzalt.setProgress(progressStatus);
					HizArtirAzalt MyTaskHizArtirAzalt = new HizArtirAzalt();
					MyTaskHizArtirAzalt.execute(HizAzalt);
				} else {
					progressStatus = 0;
					ProgressBarArtirAzalt.setProgress(progressStatus);
				}
			}
		});
	}

	private class HizArtirAzalt extends AsyncTask<String, String, String> {
		String dstAddress = Sabit.MParamAyar.getIPADRES();
		int dstPort = Integer.parseInt(Sabit.MParamAyar.getPORT());
		
		protected String doInBackground(String... params) {

			Socket socket = null;
			OutputStream os = null;
			InputStream inputStream = null;

			try {
				socket = new Socket(dstAddress, dstPort);

				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
						1024);
				byte[] buffer = new byte[1024];

				int bytesRead;
				inputStream = socket.getInputStream();
				os = socket.getOutputStream();
				DataOutputStream dos = new DataOutputStream(os);
				dos.writeUTF(params[0]);

				while ((bytesRead = inputStream.read(buffer)) != 'Y') {
					byteArrayOutputStream.write(buffer, 0, bytesRead);
					if (byteArrayOutputStream.toString("UTF-8").length() == 3) {
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
				TutDurum = "UnknownHostException: " + e.toString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				TutDurum = "IOException: " + e.toString();
			}

			return TutDurum;
		}
		
		
		@Override
		protected void onPostExecute(String result) {
			//String a = result;
		}
	}
}
