package com.android.myapplication.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.myapplication.R;
import com.android.myapplication.adapter.ErrorStockAdapter;
import com.android.myapplication.adapter.StockSearchAdapter;
import com.android.myapplication.database.AppDatabase;

/**
 * Created by menginar on 07.11.2017.
 */

public class ErrorStockActivity extends AppCompatActivity {

    public EditText productCodeEditText;
    public Button stockSearchButton, stockBarcodButton;
    public LinearLayout errorStockLayout, errorStockLayoutButton;

    private RecyclerView recyclerErrorStockList;
    private AppDatabase appDatabase;
    private ErrorStockAdapter errorStockAdapter;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_stock);

        productCodeEditText = (EditText) findViewById(R.id.productCodeEditText);
        stockSearchButton = (Button) findViewById(R.id.stockSearchButton);
        stockBarcodButton = (Button) findViewById(R.id.stockBarcodButton);
        recyclerErrorStockList = (RecyclerView) findViewById(R.id.recyclerErrorStockList);

        errorStockLayoutButton = (LinearLayout) findViewById(R.id.errorStockLayoutButton);
        errorStockLayout = (LinearLayout) findViewById(R.id.errorStockLayout);

        getAppDBaseConnect(getApplicationContext());

        try {
            bundle = getIntent().getExtras();

            if (bundle != null || !bundle.getString("productCodeError").isEmpty()) {
                getInit(bundle.getString("productCodeError"));
            } else {
                getInitAll();
            }

        } catch (Exception e) {
            getInitAll();
        }
    }

    private void getInit(String productCode) {
        try {
            errorStockLayoutButton.setVisibility(View.GONE);
            errorStockLayout.setVisibility(View.GONE);

            errorStockAdapter = new ErrorStockAdapter(appDatabase.errorStockDao()
                    .getAllErrorStockCode(productCode), getApplicationContext());
            recyclerErrorStockList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerErrorStockList.setAdapter(errorStockAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getInitAll() {
        try {

            productCodeEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    getSearch(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            errorStockAdapter = new ErrorStockAdapter(appDatabase.errorStockDao().getAllErrorStock(), getApplicationContext());
            recyclerErrorStockList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerErrorStockList.setAdapter(errorStockAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAppDBaseConnect(Context context) {
        try {
            appDatabase = AppDatabase.getDatabaseBuilder(context);
        } catch (Exception e) {
            Toast.makeText(context, R.string.database_connection_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void getSearch(String searchName) {
        try {

            if (!searchName.isEmpty()) {
                errorStockAdapter = new ErrorStockAdapter(appDatabase.errorStockDao().getAllTwoErrorStock(searchName), getApplicationContext());
                recyclerErrorStockList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerErrorStockList.setAdapter(errorStockAdapter);
            } else {
                errorStockAdapter = new ErrorStockAdapter(appDatabase.errorStockDao().getAllErrorStock(), getApplicationContext());
                recyclerErrorStockList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerErrorStockList.setAdapter(errorStockAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        getAppDBaseConnect(getApplicationContext());

        errorStockAdapter = new ErrorStockAdapter(appDatabase.errorStockDao().getAllErrorStock(), getApplicationContext());
        recyclerErrorStockList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerErrorStockList.setAdapter(errorStockAdapter);
    }
}
