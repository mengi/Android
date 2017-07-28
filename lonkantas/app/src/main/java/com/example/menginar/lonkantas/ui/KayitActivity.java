package com.example.menginar.lonkantas.ui;

/**
 * Created by Menginar on 1.7.2016.
 */

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.menginar.lonkantas.R;

public class KayitActivity extends AppCompatActivity {
    private static final String TAG = "KayitActivity";

    EditText _isimText;
    EditText _emailText;
    EditText _sifreText;
    Button _kayitButton;
    TextView _girisLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);

        _isimText = (EditText) findViewById(R.id.input_isim);
        _emailText = (EditText) findViewById(R.id.input_email);
        _sifreText = (EditText) findViewById(R.id.input_sifre);

        _kayitButton = (Button) findViewById(R.id.btn_kaydet);
        _girisLink = (TextView) findViewById(R.id.link_giris);

        _kayitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _girisLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _kayitButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(KayitActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _isimText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _sifreText.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        _kayitButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _kayitButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _isimText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _sifreText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _isimText.setError("En az 3 Karakter Olmalı !!!");
            valid = false;
        } else {
            _isimText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Email Adres Giriniz !!!");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _sifreText.setError("4 ile 10 Karakter Arası Şifre Giriniz");
            valid = false;
        } else {
            _sifreText.setError(null);
        }

        return valid;
    }
}
