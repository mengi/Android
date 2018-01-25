package com.android.trader.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.trader.R;
import com.android.trader.data.adapter.PortfoyRecyclerViewAdapter;
import com.android.trader.connection.RetroClient;
import com.android.trader.connection.TraderApiService;
import com.android.trader.constants.PortfoyType;
import com.android.trader.data.model.Item;
import com.android.trader.data.model.PortfoyResult;
import com.android.trader.util.DisplayNumberFormat;
import com.android.trader.util.NetworkConnection;
import com.android.trader.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by menginar on 11.01.2018.
 */

public class PortfoyActivity extends AppCompatActivity {

    @BindView(R.id.textViewTotal)
    TextView txtTotal;

    @BindView(R.id.recyclerViewPortfoy)
    RecyclerView rcyclerPortfoy;

    @BindView(R.id.swipeRefreshLayoutContent)
    SwipeRefreshLayout swipeContent;

    private ProgressDialog progressDialog;
    private PortfoyRecyclerViewAdapter portfoyRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.portfoy_activity);
        ButterKnife.bind(this);
        swipeToRefresh(); // Refresh Durumunda Çağrılan Fonksiyon
        getPortfoyData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                // Çıkış Yapıldığında Giriş Sayfasına Yönlendiriidiği Kısım (Kısaca Logout)
                if (SharedPreferencesUtil.deleteFromSharedPrefs(PortfoyActivity.this, SharedPreferencesUtil.PRE_ACCOUNTID)) {
                    SharedPreferencesUtil.deleteFromSharedPrefs(PortfoyActivity.this, SharedPreferencesUtil.PRE_USERNAME);
                    SharedPreferencesUtil.deleteFromSharedPrefs(PortfoyActivity.this, SharedPreferencesUtil.PRE_PASSWORD);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));// MainActivity Yani Giriş Ekranı Yönlendirme

                    finish(); // PortfoyActivity Sonlandrılır
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Veri Web Servisden Çekilmeden Kontrollerin Yapıldığı Fonksiyon
    private void getPortfoyData() {
        try {
            if (NetworkConnection.isNetworkAvailable(getApplicationContext())) { // Netwprk Bağlantı Kontrolu
                progressDialog = new ProgressDialog(PortfoyActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Lütfen Bekleyiniz...");
                progressDialog.show();

                swipeContent.setRefreshing(false); // Refresh unvisible

                getPortfoyResult();
            } else {
                Snackbar.make(getCurrentFocus(), "Üzgünüm ! Ağ Bağlantısı Yok", Snackbar.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            progressDialog.dismiss();
            Log.d("Error-PortfoyActivity", e.getMessage().toString());
        }
    }

    // Verinin Web Servisten Okunduğu Fonksiyon
    private void getPortfoyResult() {

        try {

            TraderApiService traderApiService = RetroClient.getTraderApiService();
            String username = SharedPreferencesUtil.getFromSharedPrefs(getApplicationContext(),SharedPreferencesUtil.PRE_USERNAME);
            String password = SharedPreferencesUtil.getFromSharedPrefs(getApplicationContext(), SharedPreferencesUtil.PRE_PASSWORD);
            int accountId = Integer.parseInt(SharedPreferencesUtil.getFromSharedPrefs(getApplicationContext(),SharedPreferencesUtil.PRE_ACCOUNTID));

            Call<PortfoyResult> portfoyResultCall = traderApiService
                    .getPortfoyDetail(
                            "AN", PortfoyType.CUSTOMERNO,
                            username, password, accountId,
                            PortfoyType.EXCHANGEID, PortfoyType.OUTPUTTYPE);

            portfoyResultCall.enqueue(new Callback<PortfoyResult>() {
                @Override
                public void onResponse(Call<PortfoyResult> call, Response<PortfoyResult> response) {
                    if (response.body().getResult().getState()) {
                        // İşlem Başarılı
                        portfoyRecyclerViewAdapter = new PortfoyRecyclerViewAdapter(response.body().getItem(), getApplicationContext());
                        rcyclerPortfoy.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        rcyclerPortfoy.setAdapter(portfoyRecyclerViewAdapter);
                        txtTotal.setText(DisplayNumberFormat.displayNumberDoubleTotal(getItemTotal(response.body().getItem())));
                        progressDialog.dismiss();
                    } else {
                        // İşlem Başarısız Olması Durumunda RecyclerView Boşaltıyoruz.
                        List<Item> ıtemList = new ArrayList<>();
                        portfoyRecyclerViewAdapter = new PortfoyRecyclerViewAdapter(ıtemList, getApplicationContext());
                        rcyclerPortfoy.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        rcyclerPortfoy.setAdapter(portfoyRecyclerViewAdapter);

                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), response.body().getResult().getDescription(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<PortfoyResult> call, Throwable t) {
                    // İşlem Başarısız
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });

        } catch (Exception e) {
            progressDialog.dismiss();
            Log.d("Error", e.getMessage().toString());
        }
    }

    // Item Toplam Tutar İçin Kullanılan Fonksiyon
    private double getItemTotal(List<Item> itemList) {
        double total = 0;
        try {
            for (Item item : itemList) {
                total += (item.getLastPx() * item.getQtyT2());
            }
        } catch (Exception e) {
            Log.d("Error-getTotal", e.getMessage().toString());
        }

        return total;
    }


    // Refresh İçin Kullanılan Fonksiyon
    @SuppressLint("ResourceAsColor")
    private void swipeToRefresh() {
        swipeContent.setColorSchemeColors(R.color.md_light_blue_900, R.color.md_deep_orange_900,
                R.color.md_light_green_900, R.color.md_grey_900);

        swipeContent.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPortfoyData();
            }
        });
    }
}
