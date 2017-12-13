package com.android.myapplication.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.myapplication.R;
import com.android.myapplication.constant.ReportType;
import com.android.myapplication.database.AppDatabase;
import com.android.myapplication.model.Stock;
import com.android.myapplication.model.StockMov;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by menginar on 30.09.2017.
 */

public class FragmentCase extends Fragment {

    public View view;
    public TextView outCase, inputCase, totalCase;
    private AppDatabase appDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_case, container, false);
        getAppDBaseConnect(getContext());
        getInit();
        return view;
    }

    private void getInit() {
        outCase = (TextView) view.findViewById(R.id.outCase);
        inputCase = (TextView) view.findViewById(R.id.inputCase);
        totalCase = (TextView) view.findViewById(R.id.totalCase);

        getCaseTotal();
    }

    private void getCaseTotal() {
        try {

            DecimalFormat decimalFormat = new DecimalFormat("0.000");

            double inputTotal = 0;
            double outTotal = 0;


            for (StockMov stockMov : appDatabase.stockMovDao().getStockMovGenarallyHar()) {

                if (stockMov.getProState().equals(ReportType.INPUT)) {
                    inputTotal += (stockMov.getProductNumber()* stockMov.getStatePrice());
                }

                if (stockMov.getProState().equals(ReportType.OUTPUT)) {
                    outTotal += (stockMov.getProductNumber()* stockMov.getStatePrice());
                }
            }

            outCase.setText(decimalFormat.format(outTotal) + " TL");
            inputCase.setText(decimalFormat.format(inputTotal) + " TL");
            totalCase.setText(decimalFormat.format(outTotal - inputTotal) + " TL");

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