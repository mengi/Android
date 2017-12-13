package com.android.myapplication.ui.fragmentanalysis;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.myapplication.R;
import com.android.myapplication.adapter.StockMovDailyAdapter;
import com.android.myapplication.adapter.StockMovMonthLyAdapter;
import com.android.myapplication.constant.ReportType;
import com.android.myapplication.database.AppDatabase;
import com.android.myapplication.model.Stock;
import com.android.myapplication.model.StockMov;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by menginar on 29.09.2017.
 */

public class FragmentMonthly extends Fragment {

    private View view;

    private StockMovMonthLyAdapter stockMovMonthLyAdapter;
    private AppDatabase appDatabase;
    private RecyclerView recyclerStokList;
    private String beforeMonthDate, beforeDayDate;

    private TextView outTextView, inputTextView, totalTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_monthly, container, false);
        getAppDBaseConnect(getContext());

        getInit();

        return view;
    }


    private void getInit() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        beforeMonthDate = dateFormat.format(calendar.getTime());

        Calendar calendarOne = Calendar.getInstance();
        calendarOne.add(Calendar.DATE, -1);
        beforeDayDate = dateFormat.format(calendarOne.getTime());


        outTextView = (TextView) view.findViewById(R.id.outTextView);
        inputTextView = (TextView) view.findViewById(R.id.inputTextView);
        totalTextView = (TextView) view.findViewById(R.id.totalTextView);
        recyclerStokList = (RecyclerView) view.findViewById(R.id.recyclerMonthlyStokList);

        getInfo(appDatabase.stockMovDao().getAllStockMovBetween(beforeMonthDate, beforeDayDate));

        stockMovMonthLyAdapter = new StockMovMonthLyAdapter(appDatabase.stockMovDao()
                .getStockMovWeeklyMonthlyCode(beforeMonthDate, beforeDayDate), getContext());
        recyclerStokList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerStokList.setAdapter(stockMovMonthLyAdapter);
    }

    private void getInfo(List<StockMov> stockMovs) {

        int numberInput = 0;
        int numberOutput = 0;

        double totalInput = 0;
        double totalOutput = 0;

        try {

            DecimalFormat decimalFormat = new DecimalFormat("0.000");

            for (StockMov stockMov : stockMovs) {

                if (stockMov.getProState().equals(ReportType.OUTPUT)) {
                    numberOutput += stockMov.getProductNumber();
                    totalOutput += (stockMov.getProductNumber() * stockMov.getStatePrice());
                }

                if (stockMov.getProState().equals(ReportType.INPUT)){
                    numberInput += stockMov.getProductNumber();
                    totalInput += (stockMov.getProductNumber() * stockMov.getStatePrice());
                }
            }

            outTextView.setText(Integer.toString(numberOutput));
            inputTextView.setText(Integer.toString(numberInput));
            totalTextView.setText(decimalFormat.format(totalOutput - totalInput) + " TL");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAppDBaseConnect(Context context) {
        try {
            appDatabase = AppDatabase.getDatabaseBuilder(context);
        } catch (Exception e) {
            Toast.makeText(context, R.string.database_connection_error, Toast.LENGTH_SHORT).show();
        }
    }

}

