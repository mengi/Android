package com.android.myapplication.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.myapplication.R;
import com.android.myapplication.database.AppDatabase;
import com.android.myapplication.model.ErrorStock;
import com.android.myapplication.model.Stock;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by menginar on 07.11.2017.
 */

public class ErrorStockDetailActivity extends AppCompatActivity {


    public EditText productCodeEditText, productNumberEditText, productExplanEditText;
    public Button errorStockDelete, errorStockUpdate;

    private AppDatabase appDatabase;
    private Bundle bundle;
    private int errorStockId;
    private String productCode, proExplan;
    private int productNumber;
    private ErrorStock errorStock;
    private Stock stock;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_stock_detail);

        getInit();
    }

    private void getInit() {
        try {
            getAppDBaseConnect(getApplicationContext());

            bundle = getIntent().getExtras();
            errorStockId = bundle.getInt("proCodeId");
            errorStock = appDatabase.errorStockDao().getErrorStock(errorStockId);

            productCodeEditText = (EditText) findViewById(R.id.productCodeEditText);
            productNumberEditText = (EditText) findViewById(R.id.productNumberEditText);
            productExplanEditText = (EditText) findViewById(R.id.productExplanEditText);

            productCodeEditText.setText(errorStock.getProductCode());
            productNumberEditText.setText(Integer.toString(errorStock.getProductNumber()));
            productExplanEditText.setText(errorStock.getProExplan());

            errorStockDelete = (Button) findViewById(R.id.errorStockDelete);
            errorStockUpdate = (Button) findViewById(R.id.errorStockUpdate);

            errorStockDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (validate()) {
                        if (getDeleteErrorStock()) {
                            Toast.makeText(getApplicationContext(), R.string.app_error_stock_success_delete, Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.app_error_stock_warning_delete, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            errorStockUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validate()) {
                        getUpdateErrorStock(productNumber);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validate() {

        boolean valid = true;
        productCode = productCodeEditText.getText().toString();
        proExplan = productExplanEditText.getText().toString();

        try {
            productNumber = Integer.parseInt(productNumberEditText.getText().toString());
        } catch (Exception e) {
            productNumber = 0;
        }


        if (productCode.isEmpty()) {
            productCodeEditText.setError(getResources().getString(R.string.valid_proCode));
            valid = false;
        } else {
            productCodeEditText.setError(null);
        }

        if (Integer.toString(productNumber).isEmpty() || productNumber <= 0) {
            productNumberEditText.setError(getResources().getString(R.string.valid_proNum_nu));
            valid = false;
        } else {
            productNumberEditText.setError(null);
        }
        return valid;
    }

    private void getUpdateErrorStock(int proNumber) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormatTwo = new SimpleDateFormat("yyyy-MM-dd-HH:mm");

            stock = appDatabase.stockDao().getStockInfo(errorStock.getProductCode());
            int totalProduct = stock.getProductNumber() + errorStock.getProductNumber() - proNumber;

            if (totalProduct > 0 ) {
                stock.setProductNumber(totalProduct);
                appDatabase.stockDao().updateStock(stock);

                errorStock = appDatabase.errorStockDao().getErrorStock(errorStockId);
                errorStock.setProductCode(productCode);
                errorStock.setProExplan(proExplan);
                errorStock.setPruchasePrice(stock.getPruchasePrice());
                errorStock.setProductName(stock.getProductName());
                errorStock.setProductNumber(proNumber);
                errorStock.setProDate(dateFormat.format(new Date()));
                errorStock.setDateInOuHour(dateFormatTwo.format(new Date()));
                appDatabase.errorStockDao().updateErrorStock(errorStock);

                Toast.makeText(getApplicationContext(), R.string.app_error_stock_success_update, Toast.LENGTH_SHORT).show();

                productCodeEditText.setText(errorStock.getProductCode());
                productNumberEditText.setText(Integer.toString(errorStock.getProductNumber()));
                productExplanEditText.setText(errorStock.getProExplan());

                return;
            } else {
                Toast.makeText(getApplicationContext(), R.string.app_error_stock_number, Toast.LENGTH_SHORT).show();
                return;
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), R.string.app_error_stock_warning_update, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return;
        }
    }

    private boolean getDeleteErrorStock() {
        try {

            stock = appDatabase.stockDao().getStockInfo(errorStock.getProductCode());
            stock.setProductNumber(stock.getProductNumber() + errorStock.getProductNumber());
            appDatabase.errorStockDao().deleteErrorStock(errorStock);
            appDatabase.stockDao().updateStock(stock);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private void getAppDBaseConnect(Context context) {
        try {
            appDatabase = AppDatabase.getDatabaseBuilder(context);
        } catch (Exception e) {
            Toast.makeText(context, R.string.database_connection_error, Toast.LENGTH_SHORT).show();
        }
    }
}
