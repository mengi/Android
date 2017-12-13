package com.android.myapplication.ui.fragment;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.myapplication.R;
import com.android.myapplication.constant.ReportType;
import com.android.myapplication.database.AppDatabase;
import com.android.myapplication.model.Stock;
import com.android.myapplication.ui.activity.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by menginar on 25.09.2017.
 */

public class FragmentStockSave extends Fragment {

    public View view;

    public EditText productNameEditText, productCodeEditText,
            productNumberEditText, purchasePriceEditText, salePriceEditText;
    public Button saveStockButton, stockBarcodButton;

    private String productCode, productName;
    private int productNumber;
    private double productPurchasePrice, productSalePrice;

    private AppDatabase appDatabase;
    private Stock stock;
    private Bundle arguments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_stock_save, container, false);
        getInit();

        try {

            arguments = getArguments();

            if (arguments != null && arguments.containsKey("proCode")) {
                setUpdateStock(arguments.getString("proCode"));
                stockBarcodButton.setEnabled(false);
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
        productNameEditText = (EditText) view.findViewById(R.id.productNameEditText);
        productCodeEditText = (EditText) view.findViewById(R.id.productCodeEditText);
        productNumberEditText = (EditText) view.findViewById(R.id.productNumberEditText);
        purchasePriceEditText = (EditText) view.findViewById(R.id.purchasePriceEditText);
        salePriceEditText = (EditText) view.findViewById(R.id.salePriceEditText);

        saveStockButton = (Button) view.findViewById(R.id.saveStockButton);
        stockBarcodButton = (Button) view.findViewById(R.id.stockBarcodButton);


        saveStockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getAppDBaseConnect(getContext());

                if (validate()) {
                    if (arguments != null && arguments.containsKey("proCode")) {
                        if (getStockUpdate(productCode, productName, productNumber, productPurchasePrice, productSalePrice)) {
                            Toast.makeText(getContext(), R.string.update_stock_success, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), R.string.update_stock_error, Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        if (appDatabase.stockDao().getStockInfo(productCode) == null) {
                            if (getStockInsert(productCode, productName, productNumber, productPurchasePrice, productSalePrice)) {
                                Toast.makeText(getContext(), R.string.add_stock_success, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), R.string.add_stock_error, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), R.string.register_stock, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        stockBarcodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getChildFragmentManager();

                Bundle bundle = new Bundle();
                bundle.putString("fragmenttype", ReportType.STOCKSAVE);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragmentScan scanFragment = new FragmentScan();
                scanFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.scan_fragment_save, scanFragment);
                fragmentTransaction.commit();
            }
        });
    }

    private boolean validate() {
        boolean valid = true;
        productName = productNameEditText.getText().toString();
        productCode = productCodeEditText.getText().toString();

        try {
            productNumber = Integer.parseInt(productNumberEditText.getText().toString());
            productPurchasePrice = Double.parseDouble(purchasePriceEditText.getText().toString());
            productSalePrice = Double.parseDouble(salePriceEditText.getText().toString());
        } catch (Exception e) {

        }


        if (productName.isEmpty()) {
            productNameEditText.setError(getResources().getString(R.string.valid_proName));
            valid = false;
        } else {
            productNameEditText.setError(null);
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

        if (Double.toString(productPurchasePrice).isEmpty() || productPurchasePrice <= 0) {
            purchasePriceEditText.setError(getResources().getString(R.string.valid_purchase_nu));
            valid = false;
        } else {
            purchasePriceEditText.setError(null);
        }

        if (Double.toString(productSalePrice).isEmpty() || productSalePrice <= 0) {
            salePriceEditText.setError(getResources().getString(R.string.valid_sale_nu));
            valid = false;
        } else {
            salePriceEditText.setError(null);
        }

        return valid;
    }

    private boolean getStockUpdate(String proCode, String proName, int proNumber, double purchasePrice, double salePrice) {
        boolean state = true;

        try {
            getAppDBaseConnect(getContext());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm");

            Stock stock = new Stock();
            stock.setProductCode(proCode);
            stock.setProductName(proName);
            stock.setProductNumber(proNumber);
            stock.setPruchasePrice(purchasePrice);
            stock.setSalePrice(salePrice);
            stock.setDateSave(dateFormat.format(new Date()));

            appDatabase.stockDao().updateStock(stock);
            getSpace();

        } catch (Exception e) {
            state = false;
        }

        return state;
    }

    private boolean getStockInsert(String proCode, String proName, int proNumber, double purchasePrice, double salePrice) {
        boolean state = true;

        try {
            getAppDBaseConnect(getContext());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm");

            Stock stock = new Stock();
            stock.setProductCode(proCode);
            stock.setProductName(proName);
            stock.setProductNumber(proNumber);
            stock.setPruchasePrice(purchasePrice);
            stock.setSalePrice(salePrice);
            stock.setDateSave(dateFormat.format(new Date()));

            appDatabase.stockDao().insertStock(stock);
            getSpace();

        } catch (Exception e) {
            state = false;
        }

        return state;
    }

    private void getSpace() {
        productCodeEditText.setText("");
        productNameEditText.setText("");
        productNumberEditText.setText("0");
        purchasePriceEditText.setText("0.0");
        salePriceEditText.setText("0.0");
    }

    private void setUpdateStock(String proCode) {

        try {
            getAppDBaseConnect(getContext());
            stock = new Stock();
            stock = appDatabase.stockDao().getStockInfo(proCode);
            productCodeEditText.setEnabled(false);
            productCodeEditText.setText(stock.productCode);
            productNameEditText.setText(stock.productName);
            productNumberEditText.setText(Integer.toString(stock.productNumber));
            purchasePriceEditText.setText(Double.toString(stock.pruchasePrice));
            salePriceEditText.setText(Double.toString(stock.salePrice));
            saveStockButton.setText(R.string.stock_update);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAppDBaseConnect(Context context) {
        try {
            appDatabase = AppDatabase.getDatabaseBuilder(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
