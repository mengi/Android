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
import android.widget.EditText;
import android.widget.Toast;

import com.android.myapplication.R;
import com.android.myapplication.adapter.StockSearchAdapter;
import com.android.myapplication.constant.ReportType;
import com.android.myapplication.database.AppDatabase;


/**
 * Created by menginar on 29.09.2017.
 */

public class FragmentSearch extends Fragment {
    private View view;

    public StockSearchAdapter stockSearchAdapter;
    public AppDatabase appDatabase;

    public EditText productCodeEditText;
    public RecyclerView recyclerStokList;

    private Bundle arguments;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_search, container, false);
        getAppDBaseConnect(getContext());
        getInit();

        try {

            arguments = getArguments();

            if (arguments != null && arguments.containsKey("searchFragment")) {
                ReportType.SEARCHTYPE = arguments.getString("searchFragment");
            }

            if (arguments != null && arguments.containsKey("barCodeReader")) {
                productCodeEditText.setText(arguments.getString("barCodeReader"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void getInit() {
        productCodeEditText = (EditText) view.findViewById(R.id.productCodeEditText);
        recyclerStokList = (RecyclerView) view.findViewById(R.id.recyclerStokList);

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

        stockSearchAdapter = new StockSearchAdapter(appDatabase.stockDao().getAllStock(), getContext());
        recyclerStokList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerStokList.setAdapter(stockSearchAdapter);
    }

    private void getAppDBaseConnect(Context context) {
        try {
            appDatabase = AppDatabase.getDatabaseBuilder(context);
        } catch (Exception e) {
            Toast.makeText(context, R.string.database_connection_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void getSearc(String searchName) {
        try {
            stockSearchAdapter = new StockSearchAdapter(appDatabase.stockDao().getSearchStock(searchName), getContext());
            recyclerStokList.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerStokList.setAdapter(stockSearchAdapter);
        } catch (Exception e) {

        }
    }
}
