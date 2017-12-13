package com.android.myapplication.ui.fragment;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.myapplication.R;
import com.android.myapplication.constant.ReportType;
import com.android.myapplication.database.AppDatabase;
import com.android.myapplication.model.ErrorStock;
import com.android.myapplication.model.Stock;
import com.android.myapplication.model.StockMov;
import com.android.myapplication.ui.activity.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by menginar on 25.09.2017.
 */

public class FragmentStockInOu extends Fragment {

    public View view;
    public EditText productCodeEditText, productNumberEditText, productExplanEditText;
    public Button stockInputButton, stockOutputButton, stockSearchButton, stockBarcodButton;
    public RadioButton notErrorStockRadio, errorStockRadio;

    private String productCode, proExplan;
    private int productNumber;

    private AppDatabase appDatabase;
    private Stock stock;
    private StockMov stockMov;

    private boolean state = true;
    private Bundle arguments;

    private NotificationCompat.Builder mBuilder = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            view = inflater.inflate(R.layout.fragment_stock_input_output, container, false);
            getAppDBaseConnect(getContext());
            getInit();

            arguments = getArguments();

            if (arguments != null && arguments.containsKey("searchFragmentProCode")) {
                productCodeEditText.setText(arguments.getString("searchFragmentProCode"));
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
        productNumberEditText = (EditText) view.findViewById(R.id.productNumberEditText);
        productExplanEditText = (EditText) view.findViewById(R.id.productExplanEditText);

        stockInputButton = (Button) view.findViewById(R.id.stockInputButton);
        stockOutputButton = (Button) view.findViewById(R.id.stockOutputButton);
        stockSearchButton = (Button) view.findViewById(R.id.stockSearchButton);
        stockBarcodButton = (Button) view.findViewById(R.id.stockBarcodButton);

        errorStockRadio = (RadioButton) view.findViewById(R.id.errorStockRadio);
        notErrorStockRadio = (RadioButton) view.findViewById(R.id.notErrorStockRadio);


        stockInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    if (getStock(productCode)) {
                        if (getStockInput(productCode, productNumber)) {
                            Toast.makeText(getContext(), R.string.add_stock_success, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), R.string.add_stock_error, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), R.string.not_found_stock, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        stockOutputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (errorStockRadio.isChecked()) {
                    if (validate()) {
                        if (getStock(productCode)) {
                            if (getErrorStockSave(productCode, productNumber)) {

                                errorStockRadio.setChecked(false);
                                notErrorStockRadio.setChecked(true);

                                Toast.makeText(getContext(), R.string.error_stock_success, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), R.string.error_stock_alert, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), R.string.not_found_stock, Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    if (validate()) {
                        if (getStock(productCode)) {
                            if (getStockOutput(productCode, productNumber)) {
                                Toast.makeText(getContext(), R.string.extra_stock_success, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), R.string.extra_stock_error, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), R.string.not_found_stock, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        stockSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new FragmentSearch();

                Bundle bundle = new Bundle();
                bundle.putString("searchFragment", "fragmentInOu");
                fragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        stockBarcodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getChildFragmentManager();

                Bundle bundle = new Bundle();
                bundle.putString("fragmenttype", ReportType.STOCKINOU);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragmentScan scanFragment = new FragmentScan();
                scanFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.scan_fragment_input_output, scanFragment);
                fragmentTransaction.commit();
            }
        });

        errorStockRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorStockRadio.setChecked(true);
                notErrorStockRadio.setChecked(false);
            }
        });

        notErrorStockRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorStockRadio.setChecked(false);
                notErrorStockRadio.setChecked(true);
            }
        });
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

    private boolean getStockInput(String proCode, int proNumber) {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormatTwo = new SimpleDateFormat("yyyy-MM-dd-HH:mm");

            stock = new Stock();
            stockMov = new StockMov();

            stock = appDatabase.stockDao().getStockInfo(proCode);

            stockMov.setProductName(stock.getProductName());
            stockMov.setProductCode(proCode);
            stockMov.setProState(ReportType.INPUT);
            stockMov.setProductNumber(proNumber);
            stockMov.setStatePrice(stock.getPruchasePrice());
            stockMov.setDateInOu(dateFormat.format(new Date()));
            stockMov.setDateInOuHour(dateFormatTwo.format(new Date()));
            stockMov.setProExplan(proExplan);

            appDatabase.stockMovDao().insertStockMov(stockMov);
            stock.setProductNumber(stock.getProductNumber()+ proNumber);

            appDatabase.stockDao().updateStock(stock);

            getSpace();

        } catch (Exception e) {
            e.printStackTrace();
            state = false;
        }

        return state;
    }

    private boolean getErrorStockSave(String proCode, int proNumber) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormatTwo = new SimpleDateFormat("yyyy-MM-dd-HH:mm");

            stock = new Stock();
            stock = appDatabase.stockDao().getStockInfo(proCode);
            if (stock.getProductNumber() >= proNumber) {
                ErrorStock errorStock = new ErrorStock();
                errorStock.setProductCode(stock.getProductCode());
                errorStock.setProductName(stock.getProductName());
                errorStock.setProductNumber(proNumber);
                errorStock.setPruchasePrice(stock.getPruchasePrice());
                errorStock.setProDate(dateFormat.format(new Date()));
                errorStock.setDateInOuHour(dateFormatTwo.format(new Date()));
                errorStock.setProExplan(proExplan);

                appDatabase.errorStockDao().insertErrorStock(errorStock);
                stock.setProductNumber(stock.getProductNumber()- proNumber);
                appDatabase.stockDao().updateStock(stock);

                getSpace();
            } else {
                Toast.makeText(getContext(), R.string.not_number_stock, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private boolean getStockOutput(String proCode, int proNumber) {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormatTwo = new SimpleDateFormat("yyyy-MM-dd-HH:mm");

            stock = new Stock();
            stockMov = new StockMov();

            stock = appDatabase.stockDao().getStockInfo(proCode);

            if (stock.getProductNumber() >= proNumber) {
                stockMov.setProductName(stock.getProductName());
                stockMov.setProductCode(proCode);
                stockMov.setProState(ReportType.OUTPUT);
                stockMov.setProductNumber(proNumber);
                stockMov.setStatePrice(stock.getSalePrice());
                stockMov.setDateInOu(dateFormat.format(new Date()));
                stockMov.setDateInOuHour(dateFormatTwo.format(new Date()));
                stockMov.setProExplan(proExplan);

                appDatabase.stockMovDao().insertStockMov(stockMov);
                stock.setProductNumber(stock.getProductNumber()- proNumber);

                appDatabase.stockDao().updateStock(stock);

                if (stock.getProductNumber() <= 5) {
                    getPushNotification(stock.getProductName());
                }

                getSpace();
            } else {
                Toast.makeText(getContext(), R.string.not_number_stock, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            state = false;
        }

        return state;
    }

    private boolean getStock(String proCode) {
        try {
            stock = new Stock();
            stock = appDatabase.stockDao().getStockInfo(proCode);

            if (stock == null) {
                state = false;
                getSpace();
            }
        } catch (Exception e) {
            state = false;
        }

        return state;
    }

    private void getAppDBaseConnect(Context context) {
        try {
            appDatabase = AppDatabase.getDatabaseBuilder(context);
        } catch (Exception e) {
            Toast.makeText(context, R.string.database_connection_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void getSpace() {
        productCodeEditText.setText("");
        productNumberEditText.setText("");
        productExplanEditText.setText("");
    }

    private void getPushNotification(String proName) {

        try {
            String message = proName + " " + getResources().getString(R.string.notification_text);

            mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(getContext())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(getResources().getString(R.string.app_name))
                    .setContentText(message);

            Intent notificationIntent = new Intent(getContext(), MainActivity.class);

            PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0, notificationIntent, 0);

            mBuilder.setContentIntent(contentIntent);

            NotificationManager manager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, mBuilder.build());

        } catch (Exception e) {

        }
    }
}
