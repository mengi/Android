package com.android.myapplication.ui.fragmentdatabase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.myapplication.R;
import com.android.myapplication.constant.ReportType;
import com.android.myapplication.database.AppDatabase;
import com.android.myapplication.model.Stock;
import com.android.myapplication.util.DatabaseToExel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by menginar on 25.09.2017.
 */

public class FragmentDatabase extends Fragment {

    public View view;
    public RelativeLayout uploadRelativeLayout, downloadRelativeLayout, listInputRelativeLayout, emailRelativeLayout;
    private AppDatabase appDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_database, container, false);
        getInit();
        return view;
    }

    private void getInit() {
        uploadRelativeLayout = (RelativeLayout) view.findViewById(R.id.uploadRelativeLayout);
        downloadRelativeLayout = (RelativeLayout) view.findViewById(R.id.downloadRelativeLayout);
        listInputRelativeLayout = (RelativeLayout) view.findViewById(R.id.listInputRelativeLayout);
        emailRelativeLayout = (RelativeLayout) view.findViewById(R.id.emailRelativeLayout);

        uploadRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertdialog = new AlertDialog.Builder(getActivity());
                alertdialog.setMessage(R.string.database_to_exel);

                alertdialog.setCancelable(false).setPositiveButton(R.string.yes_app, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            getAppDBaseConnect(getContext());
                            DatabaseToExel databaseToExel = new DatabaseToExel(null,
                                    appDatabase.stockDao().getAllStock(), null, ReportType.DATABASEREPORT);

                            if (databaseToExel.setReport()) {

                                Toast.makeText(getContext(), R.string.backup_success, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), R.string.backup_error, Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).setNegativeButton(R.string.no_app, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alert = alertdialog.create();
                alert.show();


            }
        });

        downloadRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alertdialog = new AlertDialog.Builder(getActivity());
                alertdialog.setMessage(R.string.exel_to_database);

                alertdialog.setCancelable(false).setPositiveButton(R.string.yes_app, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseToExel databaseToExel = new DatabaseToExel();

                        if (databaseToExel.getExcelToDatabase(getContext(), ReportType.DATABASEREPORT)) {
                            Toast.makeText(getContext(), R.string.exel_to_database_success, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), R.string.exel_to_database_error, Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton(R.string.no_app, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alert = alertdialog.create();
                alert.show();


            }
        });

        listInputRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAppDBaseConnect(getContext());
                ReportType.isChecked = new boolean[appDatabase.stockDao().getAllStock().size()];
                Intent intent = new Intent(getActivity(), DatabaseListActivity.class);
                startActivity(intent);
            }
        });

        emailRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialogReport();
            }
        });
    }

    private void getAppDBaseConnect(Context context) {
        try {
            appDatabase = AppDatabase.getDatabaseBuilder(context);
        } catch (Exception e) {
            Toast.makeText(context, R.string.database_connection_error, Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<String> getFiles() {
        String myFilePath = Environment.getExternalStorageDirectory().toString() + "/StokTakipApp/";
        ArrayList<String> fileNames = new ArrayList<String>();
        File file = new File(myFilePath);
        File[] allfiles = file.listFiles();
        if (allfiles.length == 0) {
            return null;
        } else {
            for (int i = 0; i < allfiles.length; i++) {
                fileNames.add(allfiles[i].getName());
            }
        }
        return fileNames;
    }

    private void shareIt(String reportName) {
        String myFilePath = Environment.getExternalStorageDirectory().toString() + "/StokTakipApp/" + reportName;
        Intent intentShareFile = new Intent(Intent.ACTION_SEND);
        File fileWithinMyDir = new File(myFilePath);

        if (fileWithinMyDir.exists()) {
            intentShareFile.setType("application/pdf");
            intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + myFilePath));

            intentShareFile.putExtra(Intent.EXTRA_SUBJECT, R.string.report);
            intentShareFile.putExtra(Intent.EXTRA_TEXT, reportName);

            startActivity(Intent.createChooser(intentShareFile,
                    getResources().getString(R.string.file_share)));
        }
    }

    private void getDialogReport() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.report_and_reports_select);

        try {
            ArrayList<String> strings = new ArrayList<>();
            strings = getFiles();

            if (strings != null) {
                final String[] reports = new String[strings.size()];
                for (int i = 0; i < strings.size(); i++) {
                    reports[i] = strings.get(i);
                }

                final boolean[] checkedItems = new boolean[strings.size()];
                builder.setSingleChoiceItems(reports, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkedItems[which] = true;
                    }
                });

                builder.setPositiveButton(R.string.okey, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            if (checkedItems[i]) {
                                shareIt(reports[i]);
                            }
                        }
                    }
                });

                builder.setNegativeButton(R.string.out, null);
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                Toast.makeText(getContext(), R.string.not_report_email, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
