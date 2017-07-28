package com.menginarsoft.control.activity;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.menginarsoft.control.R;
import com.menginarsoft.control.function.Sabit;
import com.negusoft.holoaccent.activity.AccentActivity;

public class AyKontrolActivity extends AccentActivity{
	
	ImageButton Role1, Role2, Led1, Led2, Led3;
	public String TutDurum = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aykontrol);
		
		Role1 = (ImageButton) findViewById(R.id.Role1);
		Role2 = (ImageButton) findViewById(R.id.Role2);
		Led1 = (ImageButton) findViewById(R.id.Led1);
		Led2 = (ImageButton) findViewById(R.id.Led2);
		Led3 = (ImageButton) findViewById(R.id.Led3);
		
		Role1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RoleLed MyTaskRoleLed = new RoleLed();
				MyTaskRoleLed.execute("A");
			}
		});
		
		Role2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RoleLed MyTaskRoleLed = new RoleLed();
				MyTaskRoleLed.execute("B");
			}
		});
		
		Led1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RoleLed MyTaskRoleLed = new RoleLed();
				MyTaskRoleLed.execute("C");
			}
		});
		
		Led2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RoleLed MyTaskRoleLed = new RoleLed();
				MyTaskRoleLed.execute("D");
			}
		});
		
		Led3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RoleLed MyTaskRoleLed = new RoleLed();
				MyTaskRoleLed.execute("E");
			}
		});
	}
	
	private class RoleLed extends AsyncTask<String, String, String> {
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
			Toast.makeText(AyKontrolActivity.this,"Dönen Cevap : " + result, Toast.LENGTH_LONG).show();
		}
	}
	
	
}
