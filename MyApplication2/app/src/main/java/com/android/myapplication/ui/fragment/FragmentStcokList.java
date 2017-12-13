package com.android.myapplication.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.myapplication.R;
import com.android.myapplication.adapter.StockAdapter;
import com.android.myapplication.adapter.StockSearchAdapter;
import com.android.myapplication.database.AppDatabase;


/**
 * Created by menginar on 26.09.2017.
 */

public class FragmentStcokList extends Fragment {

    public View view;

    public EditText productCodeEditText;
    public RecyclerView recyclerStokList;
    public Button upStockButton, downStockButton;

    private StockAdapter stockAdapter;
    private AppDatabase appDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_stock_list, container, false);
        getAppDBaseConnect(getContext());
        getInit();
        return view;
    }

    private void getInit() {
        productCodeEditText = (EditText) view.findViewById(R.id.productCodeEditText);
        recyclerStokList = (RecyclerView) view.findViewById(R.id.recyclerStokList);

        downStockButton = (Button) view.findViewById(R.id.downStockButton);
        upStockButton = (Button) view.findViewById(R.id.upStockButton);

        productCodeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getSearc(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        upStockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stockAdapter = new StockAdapter(appDatabase.stockDao().getAllStockUp(), getContext());
                recyclerStokList.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerStokList.setAdapter(stockAdapter);
            }
        });

        downStockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stockAdapter = new StockAdapter(appDatabase.stockDao().getAllStockDown(), getContext());
                recyclerStokList.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerStokList.setAdapter(stockAdapter);
            }
        });

        stockAdapter = new StockAdapter(appDatabase.stockDao().getAllStock(), getContext());
        recyclerStokList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerStokList.setAdapter(stockAdapter);
    }

    public void getAppDBaseConnect(Context context) {
        try {
            appDatabase = AppDatabase.getDatabaseBuilder(context);
        } catch (Exception e) {
            Toast.makeText(context, R.string.database_connection_error, Toast.LENGTH_SHORT).show();
        }
    }

    public void getSearc(String searchName) {
        try {
            stockAdapter = new StockAdapter(appDatabase.stockDao().getSearchStock(searchName), getContext());
            recyclerStokList.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerStokList.setAdapter(stockAdapter);
        } catch (Exception e) {

        }
    }
}
