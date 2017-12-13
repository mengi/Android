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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.myapplication.R;
import com.android.myapplication.constant.ReportType;
import com.android.myapplication.database.AppDatabase;
import com.android.myapplication.ui.activity.MainActivity;

/**
 * Created by menginar on 25.09.2017.
 */

public class FragmentStockDelUp extends Fragment {

    public View view;
    public EditText productCodeEditText;
    public Button stockDeleteButton, stockUpdateButton, stockSearchButton, stockBarcodButton;
    private String productCode;

    private AppDatabase appDatabase;
    private boolean state = true;

    private Bundle arguments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        try {

            view = inflater.inflate(R.layout.fragment_stock_delete_update, container, false);
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

        }

        return view;
    }

    private void getInit() {
        productCodeEditText = (EditText) view.findViewById(R.id.productCodeEditText);

        stockDeleteButton = (Button) view.findViewById(R.id.stockDeleteButton);
        stockUpdateButton = (Button) view.findViewById(R.id.stockUpdateButton);
        stockSearchButton = (Button) view.findViewById(R.id.stockSearchButton);
        stockBarcodButton = (Button) view.findViewById(R.id.stockBarcodButton);

        stockDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    if (appDatabase.stockDao().getStockInfo(productCode) != null) {
                        if (setDeleteStock(productCode)) {
                            Toast.makeText(getContext(), R.string.delete_stock_success, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), R.string.delete_stock_error, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), R.string.not_found_stock, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        stockUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    try {
                        if (appDatabase.stockDao().getStockInfo(productCode) != null) {
                            Fragment fragment = new FragmentStockSave();

                            Bundle bundle = new Bundle();
                            bundle.putString("proCode", productCode);
                            fragment.setArguments(bundle);

                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.content_frame, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        } else {
                            Toast.makeText(getContext(), R.string.not_found_stock, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        stockSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new FragmentSearch();
                Bundle bundle = new Bundle();
                bundle.putString("searchFragment", "fragmentDelUp");
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
                bundle.putString("fragmenttype", ReportType.STOCKDELUP);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragmentScan scanFragment = new FragmentScan();
                scanFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.scan_fragment_delete_update,scanFragment);
                fragmentTransaction.commit();
            }
        });
    }

    private boolean validate() {
        boolean valid = true;
        productCode = productCodeEditText.getText().toString();

        if (productCode.isEmpty()) {
            productCodeEditText.setError(getResources().getString(R.string.valid_proCode));
            valid = false;
        } else {
            productCodeEditText.setError(null);
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

    private boolean setDeleteStock(String proCode) {

        try {
            appDatabase.stockDao().deleteStockCode(proCode);
            appDatabase.stockMovDao().deleteStockMovCode(proCode);
            getSpace();
        } catch (Exception e) {
            e.printStackTrace();
            state = false;
        }

        return state;
    }

    private void getSpace() {
        productCodeEditText.setText("");
    }
}
