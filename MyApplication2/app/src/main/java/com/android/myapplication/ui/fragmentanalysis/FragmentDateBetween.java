package com.android.myapplication.ui.fragmentanalysis;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.myapplication.R;
import com.android.myapplication.adapter.StockDateAdapter;
import com.android.myapplication.constant.ReportType;
import com.android.myapplication.database.AppDatabase;
import com.android.myapplication.model.Stock;
import com.android.myapplication.model.StockMov;
import com.android.myapplication.util.DatabaseToExel;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by menginar on 09.10.2017.
 */

public class FragmentDateBetween extends Fragment {

    public View view;
    private LayoutInflater layoutInflater;
    private StockDateAdapter stockDateAdapter;

    private TextView outDateTextView, inputDateTextView, totalDateTextView;
    private EditText firstDateEditText, secondDateEditText;
    private Button firstDateButton, secondDateButton, stockDateButton, stockReportCreateButton;
    private RecyclerView recyclerDateStokMovList;

    private int year, month, day;
    private String firstDate, secondDate;
    private AppDatabase appDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_stock_analysis_date, container, false);
        layoutInflater = inflater;
        getAppDBaseConnect(getContext());
        getInit();
        return view;
    }

    private void getInit() {
        outDateTextView = (TextView) view.findViewById(R.id.outDateTextView);
        inputDateTextView = (TextView) view.findViewById(R.id.inputDateTextView);
        totalDateTextView = (TextView) view.findViewById(R.id.totalDateTextView);

        firstDateEditText = (EditText) view.findViewById(R.id.firstDateEditText);
        secondDateEditText = (EditText) view.findViewById(R.id.secondDateEditText);

        firstDateButton = (Button) view.findViewById(R.id.firstDateButton);
        secondDateButton = (Button) view.findViewById(R.id.secondDateButton);
        stockDateButton = (Button) view.findViewById(R.id.stockDateButton);
        stockReportCreateButton = (Button) view.findViewById(R.id.stockReportCreateButton);

        stockReportCreateButton.setEnabled(false);

        recyclerDateStokMovList = (RecyclerView) view.findViewById(R.id.recyclerDateStokMovList);

        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        firstDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog firstDialog = new DatePickerDialog(getContext(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        firstDateEditText.setText(year + "-" + getNewMonthDay((month + 1)) + "-" + getNewMonthDay(dayOfMonth));
                        firstDate = firstDateEditText.getText().toString();
                    }
                }, year, month, day);
                firstDialog.show();
            }
        });

        secondDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog secondDialog = new DatePickerDialog(getContext(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        secondDateEditText.setText(year + "-" + getNewMonthDay((month + 1)) + "-" + getNewMonthDay(dayOfMonth));
                        secondDate = secondDateEditText.getText().toString();
                    }
                }, year, month, day);
                secondDialog.show();

            }
        });

        stockDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    if (appDatabase.stockMovDao().getAllStockMovBetween(firstDate, secondDate).size() > 0) {
                        getStockMovList(appDatabase.stockMovDao().getAllStockMovBetween(firstDate, secondDate));
                        stockDateAdapter = new StockDateAdapter(appDatabase.stockMovDao()
                                .getAllStockMovBetween(firstDate, secondDate), getContext());
                        recyclerDateStokMovList.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerDateStokMovList.setAdapter(stockDateAdapter);

                        stockReportCreateButton.setEnabled(true);
                    } else {
                        Toast.makeText(getContext(), R.string.not_stockmov_size, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        stockReportCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialogEdittext(appDatabase.stockMovDao().getAllStockMovBetween(firstDate, secondDate));
            }
        });
    }

    private void getStockMovList(List<StockMov> stockMovs) {

        double totalInput = 0;
        double totalOutput = 0;

        try {

            DecimalFormat decimalFormat = new DecimalFormat("0.000");

            for (StockMov stockMov : stockMovs) {

                if (stockMov.getProState().equals(ReportType.OUTPUT)) {
                    totalOutput += (stockMov.getProductNumber() * stockMov.getStatePrice());
                }

                if (stockMov.getProState().equals(ReportType.INPUT)) {
                    totalInput += (stockMov.getProductNumber() * stockMov.getStatePrice());
                }
            }

            outDateTextView.setText(Double.toString(totalOutput) + " TL");
            inputDateTextView.setText(Double.toString(totalInput) + " TL");
            totalDateTextView.setText(decimalFormat.format(totalOutput - totalInput) + " TL");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validate() {
        boolean valid = true;
        String firstDate = firstDateEditText.getText().toString();
        String secondDate = secondDateEditText.getText().toString();

        if (firstDate.isEmpty()) {
            firstDateEditText.setError(getResources().getString(R.string.valid_date));
            valid = false;
        } else {
            firstDateEditText.setError(null);
        }

        if (secondDate.isEmpty()) {
            secondDateEditText.setError(getResources().getString(R.string.valid_date));
            valid = false;
        } else {
            secondDateEditText.setError(null);
        }

        return valid;
    }

    private void getAppDBaseConnect(Context context) {
        try {
            appDatabase = AppDatabase.getDatabaseBuilder(context);
        } catch (Exception e) {
            Toast.makeText(context, R.string.database_connection_error, Toast.LENGTH_SHORT).show();
        }
    }

    private String getNewMonthDay(int monthDay) {
        String newMonthDay = "";
        try {
            if (monthDay < 10) {
                newMonthDay = "0" + Integer.toString(monthDay);
            } else {
                newMonthDay = Integer.toString(monthDay);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newMonthDay;
    }

    private void getDialogEdittext(final List<StockMov> stockMovs) {
        try {
            View mView = layoutInflater.inflate(R.layout.dialog_edittext, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(mView);

            final EditText reportInputDialog = (EditText) mView.findViewById(R.id.reportInputDialog);
            builder
                    .setCancelable(false)
                    .setPositiveButton(R.string.okey, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogBox, int id) {
                            String reportName = reportInputDialog.getText().toString();

                            if (!reportName.isEmpty()) {
                                reportName = reportName + ".xls";

                                if (stockMovs.size() != 0) {
                                    DatabaseToExel databaseToExel = new DatabaseToExel(reportName, null
                                            , stockMovs, ReportType.BETWEENDATE);

                                    if (databaseToExel.setReport()) {
                                        stockReportCreateButton.setEnabled(false);
                                        Toast.makeText(getContext(), R.string.report_created, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), R.string.not_report_create, Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getContext(), R.string.not_report_created_stock, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), R.string.report_name_select, Toast.LENGTH_SHORT).show();
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
