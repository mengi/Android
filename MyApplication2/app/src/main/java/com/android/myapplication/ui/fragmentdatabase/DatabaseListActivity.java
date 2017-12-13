package com.android.myapplication.ui.fragmentdatabase;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.myapplication.R;
import com.android.myapplication.adapter.StockAdapter;
import com.android.myapplication.adapter.StockDataAdapter;
import com.android.myapplication.constant.ReportType;
import com.android.myapplication.database.AppDatabase;
import com.android.myapplication.model.Stock;
import com.android.myapplication.util.DatabaseToExel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by menginar on 02.10.2017.
 */

public class DatabaseListActivity extends AppCompatActivity {

    public RecyclerView recyclerStokDataList;
    public EditText productCodeEditText;

    private StockDataAdapter stockDataAdapter;
    private AppDatabase appDatabase;
    private String reportName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_list);

        getInit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_check:

                getAppDatabaseConnect(getApplicationContext());

                List<Stock> stockList = appDatabase.stockDao().getAllStock();
                List<Stock> stocks = new ArrayList<>();

                for (int i = 0; i < stockList.size(); i++) {

                    if (ReportType.isChecked[i] == true) {
                        stocks.add(stockList.get(i));
                    }
                }

                getDialogEdittext(stocks);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getInit() {

        getAppDatabaseConnect(getApplicationContext());

        productCodeEditText = (EditText) findViewById(R.id.productCodeEditText);
        recyclerStokDataList = (RecyclerView) findViewById(R.id.recyclerStokDataList);

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

        stockDataAdapter = new StockDataAdapter(appDatabase.stockDao().getAllStock(), getApplicationContext());
        recyclerStokDataList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerStokDataList.setAdapter(stockDataAdapter);
    }

    public void getAppDatabaseConnect(Context context) {
        try {
            appDatabase = AppDatabase.getDatabaseBuilder(context);
        } catch (Exception e) {
            Toast.makeText(context, R.string.database_connection_error, Toast.LENGTH_SHORT).show();
        }
    }

    public void getSearch(String searchName) {
        try {
            stockDataAdapter = new StockDataAdapter(appDatabase.stockDao().getSearchStock(searchName), getApplicationContext());
            recyclerStokDataList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerStokDataList.setAdapter(stockDataAdapter);
        } catch (Exception e) {

        }
    }

    private void getDialogEdittext(final List<Stock> stocks) {
        try {
            LayoutInflater layoutInflater = getLayoutInflater();
            View mView = layoutInflater.inflate(R.layout.dialog_edittext, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(mView);

            final EditText reportInputDialog = (EditText) mView.findViewById(R.id.reportInputDialog);
            builder
                    .setCancelable(false)
                    .setPositiveButton(R.string.okey, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogBox, int id) {
                            reportName = reportInputDialog.getText().toString();

                            if (!reportName.isEmpty()) {
                                reportName = reportName + ".xls";

                                if (stocks.size() != 0) {
                                    DatabaseToExel databaseToExel = new DatabaseToExel(reportName, stocks
                                            , null, ReportType.PRIVATEREPORT);

                                    ReportType.isChecked = new boolean[appDatabase.stockDao().getAllStock().size()];

                                    if (databaseToExel.setReport()) {

                                        Toast.makeText(getApplicationContext(), R.string.backup_success, Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), R.string.backup_error, Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), R.string.report_created_stock, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), R.string.report_name_select, Toast.LENGTH_SHORT).show();
                            }
                        }
                    })

                    .setNegativeButton(R.string.out,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogBox, int id) {
                                    dialogBox.cancel();
                                }
                            });

            AlertDialog alertDialogAndroid = builder.create();
            alertDialogAndroid.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
