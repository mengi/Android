package com.android.myapplication.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.myapplication.R;
import com.android.myapplication.adapter.StockReportAdapter;
import com.android.myapplication.adapter.StockSearchAdapter;
import com.android.myapplication.constant.ReportType;
import com.android.myapplication.database.AppDatabase;
import com.android.myapplication.model.Stock;
import com.android.myapplication.model.StockMov;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by menginar on 01.10.2017.
 */

public class ProductDetailActivity extends AppCompatActivity {

    private TextView productNumberTextView, productNameTextView, purchaseTextView,
            saleTextView, outTextView, inputTextView, totalTextView;
    private RecyclerView recyclerReportStokList;

    private StockReportAdapter stockReportAdapter;
    private AppDatabase appDatabase;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        try {

            getInit();
            getAppDatabaseConnect(getApplicationContext());
            bundle = getIntent().getExtras();

            getProductInfo(bundle.getString("proCodeList"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_error_stock, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_error_stock:

                getAppDatabaseConnect(getApplicationContext());
                if (appDatabase.errorStockDao().getAllErrorStockCode(bundle.getString("proCodeList")).size() > 0) {
                    Intent intent = new Intent(this, ErrorStockActivity.class);
                    intent.putExtra("productCodeError", bundle.getString("proCodeList"));
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.error_stock_size_alert, Toast.LENGTH_SHORT).show();
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getInit() {

        productNumberTextView = (TextView) findViewById(R.id.productNumberTextView);
        productNameTextView = (TextView) findViewById(R.id.productNameTextView);
        purchaseTextView = (TextView) findViewById(R.id.purchaseTextView);
        saleTextView = (TextView) findViewById(R.id.saleTextView);

        outTextView = (TextView) findViewById(R.id.outTextView);
        inputTextView = (TextView) findViewById(R.id.inputTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);

        recyclerReportStokList = (RecyclerView) findViewById(R.id.recyclerReportStokList);
    }

    private void getProductInfo(String proCodes) {

        try {

            DecimalFormat decimalFormat = new DecimalFormat("0.000");

            double outputTotal = 0;
            double inputTotal = 0;

            int output = 0;
            int input = 0;

            Stock stock = appDatabase.stockDao().getStockInfo(proCodes);
            List<StockMov> stockMovs = appDatabase.stockMovDao().getStockAllGenarallyCode(proCodes);

            for (StockMov stockMov : stockMovs) {
                if (stockMov.getProState().equals(ReportType.OUTPUT)) {
                    outputTotal += (stockMov.getStatePrice() * stockMov.getProductNumber());
                    output += stockMov.getProductNumber();
                }

                if (stockMov.getProState().equals(ReportType.INPUT)) {
                    inputTotal += (stockMov.getStatePrice() * stockMov.getProductNumber());
                    input += stockMov.getProductNumber();
                }
            }

            outTextView.setText(Integer.toString(output));
            inputTextView.setText(Integer.toString(input));

            productNameTextView.setText(stock.getProductName());
            productNumberTextView.setText(Integer.toString(stock.getProductNumber()));
            purchaseTextView.setText(Double.toString(stock.getPruchasePrice()));
            saleTextView.setText(Double.toString(stock.getSalePrice()));

            totalTextView.setText(decimalFormat.format(outputTotal - inputTotal) + " TL");

            stockReportAdapter = new StockReportAdapter(stockMovs, getApplicationContext());
            recyclerReportStokList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerReportStokList.setAdapter(stockReportAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAppDatabaseConnect(Context context) {
        try {
            appDatabase = AppDatabase.getDatabaseBuilder(context);
        } catch (Exception e) {
            Toast.makeText(context, R.string.database_connection_error, Toast.LENGTH_SHORT).show();
        }
    }
}
