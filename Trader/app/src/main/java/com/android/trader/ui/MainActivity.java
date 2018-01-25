package com.android.trader.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.trader.R;
import com.android.trader.connection.RetroClient;
import com.android.trader.connection.TraderApiService;
import com.android.trader.constants.PortfoyType;
import com.android.trader.data.model.TraderResult;
import com.android.trader.util.NetworkConnection;
import com.android.trader.util.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.android.trader.util.SharedPreferencesUtil.PRE_ACCOUNTID;
import static com.android.trader.util.SharedPreferencesUtil.PRE_PASSWORD;
import static com.android.trader.util.SharedPreferencesUtil.PRE_USERNAME;
import static com.android.trader.util.SharedPreferencesUtil.setToSharedPreferences;

public class MainActivity extends AppCompatActivity {

    private String username, password;
    ProgressDialog progressDialog;

    @BindView(R.id.editTextUsername)
    EditText editTxtUsername;
    @BindView(R.id.editTextPassword)
    EditText editTxtPassword;
    @BindView(R.id.buttonLogin)
    Button btnLogin;

    @OnClick(R.id.buttonLogin)
    void getUserLogin() {

        if (NetworkConnection.isNetworkAvailable(getApplicationContext())) { // Network Kontrol Ediliyor
            getLogin();
        } else {
            Snackbar.make(getCurrentFocus(), "Üzgünüm ! Ağ Bağlantısı Yok", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Daha Önceden Login Yapıldıysa, Tekrar Login Olmadan Hesap Özeti Activity Geçiş Yapılır
        if (SharedPreferencesUtil.getFromSharedPrefs(getApplicationContext(), PRE_ACCOUNTID) != null &&
                SharedPreferencesUtil.getFromSharedPrefs(getApplicationContext(), PRE_USERNAME) != null &&
                SharedPreferencesUtil.getFromSharedPrefs(getApplicationContext(), PRE_PASSWORD) != null) {
            startActivity(new Intent(getApplicationContext(), PortfoyActivity.class));
            finish();
        }
    }

    // Login Olmadan Önce Kontrollerin Yapıldığı Fonksiyon
    private void getLogin() {

        try {

            if (!validate()) return; // Kullanıcı Adı, Parola Boş veya Eksik Girildiği Durumda

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Oturum Açılıyor...");
            progressDialog.show();

            getPortfoyLogin();
        } catch (Exception e) {
            Log.d("Error-getLogin", e.getMessage().toString());
        }
    }

    // Login İşlemi Olduktan Sonra EditTextlerin Boşalmak İçin Çağrılan Fonksiyon
    private void getSpace() {
        editTxtUsername.setText("");
        editTxtPassword.setText("");
    }

    // Girilen Kullanıcı Adi ve Parola Değerlerin Web Servise Gönderildiği ve Sonucunun Dönüldüğü Fonksiyon
    private void getPortfoyLogin() {
        TraderApiService traderApiService = RetroClient.getTraderApiService();

        try {

            Call<TraderResult> traderResultCall = traderApiService
                    .getPortfoyLogin(
                            "A", PortfoyType.CUSTOMERNO,
                            username, password, 0,
                            PortfoyType.EXCHANGEID,
                            PortfoyType.OUTPUTTYPE);


            traderResultCall.enqueue(new Callback<TraderResult>() {
                @Override
                public void onResponse(Call<TraderResult> call, Response<TraderResult> response) {
                    if (response.body().getResult().getState()) {
                        // İşlem Başarılı
                        // Kullanıcı Adı, Parola ve DefaultAccount Değerlerinin SharedPreferences Tutulması
                        setToSharedPreferences(getApplicationContext(), PRE_USERNAME, username);
                        setToSharedPreferences(getApplicationContext(), PRE_PASSWORD, password);
                        setToSharedPreferences(getApplicationContext(), PRE_ACCOUNTID, response.body().getDefaultAccount());

                        progressDialog.dismiss();
                        getSpace();
                        startActivity(new Intent(MainActivity.this, PortfoyActivity.class));
                        finish(); // Giriş Ekran Kısmı Sonlandırılır

                    } else {
                        // İşlem Başarısız
                        progressDialog.dismiss();
                        getSpace();
                        Toast.makeText(getApplicationContext(), response.body().getResult().getDescription().toString(),
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<TraderResult> call, Throwable t) {
                    // İşlem Başarısız
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), t.getMessage().toString(),
                            Toast.LENGTH_LONG).show();
                }
            });

        } catch (Exception e) {
            Log.d("Error-TraderApiService", e.getMessage().toString());
        }
    }

    // Kullanıcı Adı ve Parola Değerlerin Kontrolünü Sağlayan Fonksiyon
    private boolean validate() {
        boolean valid = true;

        try {
            username = editTxtUsername.getText().toString();
            password = editTxtPassword.getText().toString();

            if (username.isEmpty()) {
                editTxtUsername.setError("Kullanıcı Adını Giriniz");
                valid = false;
            } else {
                editTxtUsername.setError(null);
            }

            if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                editTxtPassword.setError("Parola 4 ile 10 Karakter Arasında Giriniz");
                valid = false;
            } else {
                editTxtPassword.setError(null);
            }

        } catch (Exception e) {
            Log.d("Error-Validate", e.getMessage().toString());
        }

        return valid;
    }
}
