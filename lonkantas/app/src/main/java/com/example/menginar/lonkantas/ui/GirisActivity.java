package com.example.menginar.lonkantas.ui;

/**
 * Created by Menginar on 1.7.2016.
 */
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.menginar.lonkantas.Model.Kisi;
import com.example.menginar.lonkantas.R;

import static com.example.menginar.lonkantas.Model.Kisi.KISILER;

public class GirisActivity extends AppCompatActivity {
    private static final String TAG = "GirisActivity";
    private static final int REQUEST_SIGNUP = 0;

    EditText _emailText, _sifreText;
    Button btn_giris;
    TextView link_kayit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _emailText = (EditText) findViewById(R.id.input_email);
        _sifreText = (EditText) findViewById(R.id.input_sifre);
        link_kayit = (TextView) findViewById(R.id.link_kayit);

        btn_giris = (Button) findViewById(R.id.btn_giris);

        btn_giris.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        link_kayit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(),KayitActivity.class);
                startActivity(intent);
            }
        });
    }

    public void login() {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        btn_giris.setEnabled(false);

        final String email = _emailText.getText().toString();
        String password = _sifreText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        _emailText.setText("");
                        _sifreText.setText("");
                        startActivity(intent);
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        btn_giris.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {

        btn_giris.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _sifreText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Email Adres Giriniz !!!");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _sifreText.setError("Şifrenizi Giriniz !!!");
            valid = false;
        } else {
            _sifreText.setError(null);
        }

        if (valid) {
            String Ad = Kisi.KISILER.get(0).getIsim();
            String Email = Kisi.KISILER.get(0).getEmail();
            String Sifre = Kisi.KISILER.get(0).getSifre();

            if (!Email.equals(email)) {
                Toast.makeText(getBaseContext(), "Email Adresiniz Uyuşmuyor", Toast.LENGTH_LONG).show();
                valid = false;
            } else if (!Sifre.equals(password)) {
                Toast.makeText(getBaseContext(), "Sifre Uyuşmuyor", Toast.LENGTH_LONG).show();
                valid = false;

            }
        }
        return valid;
    }
}