package com.example.menginar.user;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.menginar.user.database.AppDatabase;
import com.example.menginar.user.model.Stock;
import com.example.menginar.user.model.StockMov;


public class MainActivity extends AppCompatActivity {

    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAppDatabaseConnect(getBaseContext());

        try {
            Stock stock = new Stock();
            stock.productCode = "2";
            stock.productName = "a";
            stock.productNumber = 11;
            stock.pruchasePrice = 1.25;
            stock.salePrice = 1.55;

            StockMov stockMov = new StockMov();
            stockMov.productCode = "2";
            stockMov.productName = "a";
            stockMov.productNumber = 11;
            stockMov.proState = "Giriş";
            stockMov.dateInOu = "12/12/2016";


            appDatabase.stockDao().insertStock(stock);
            appDatabase.stockMovDao().insertStockMov(stockMov);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAppDatabaseConnect(Context context) {
        try {
            appDatabase = AppDatabase.getDatabaseBuilder(context);
        } catch (Exception e) {
            Log.d("error", e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

}
