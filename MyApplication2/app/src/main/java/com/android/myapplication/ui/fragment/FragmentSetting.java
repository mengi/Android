package com.android.myapplication.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.myapplication.R;
import com.android.myapplication.constant.ReportType;
import com.android.myapplication.database.AppDatabase;
import com.android.myapplication.model.Stock;
import com.android.myapplication.ui.activity.ErrorStockActivity;
import com.android.myapplication.util.DatabaseToExel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by menginar on 28.09.2017.
 */

public class FragmentSetting extends Fragment {

    public View view;
    public RelativeLayout caseRelativeLayout, informationRelativeLayout,
            reportDeleteRelativeLayout, deleteDataBaseRelativeLayout,
            backupRelativeLayout, errorStockRelativeLayout;
    private AppDatabase appDatabase;
    private String dateDaily, beforeWeekDate, beforeDayDate, beforeMonthDate;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_setting, container, false);
        getInit();
        return view;
    }

    private void getInit() {
        caseRelativeLayout = (RelativeLayout) view.findViewById(R.id.caseRelativeLayout);
        informationRelativeLayout = (RelativeLayout) view.findViewById(R.id.informationRelativeLayout);
        reportDeleteRelativeLayout = (RelativeLayout) view.findViewById(R.id.reportDeleteRelativeLayout);
        deleteDataBaseRelativeLayout = (RelativeLayout) view.findViewById(R.id.deleteDataBaseRelativeLayout);
        backupRelativeLayout = (RelativeLayout) view.findViewById(R.id.backupRelativeLayout);
        errorStockRelativeLayout = (RelativeLayout) view.findViewById(R.id.errorStockRelativeLayout);


        caseRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentCase();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        informationRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialogInformation();
            }
        });

        reportDeleteRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialogReportDelete();
            }
        });

        deleteDataBaseRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppDBaseConnect(getContext());

                AlertDialog.Builder alertdialog = new AlertDialog.Builder(getActivity());
                alertdialog.setMessage(R.string.delete_database);

                alertdialog.setCancelable(false).setPositiveButton(R.string.yes_app, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        appDatabase.stockDao().deleteStocAllk();
                        appDatabase.stockMovDao().deleteStockmovAll();
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

        backupRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBackupDaily();
            }
        });

        errorStockRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ErrorStockActivity.class);
                startActivity(intent);
            }
        });
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

    private void getDialogReportDelete() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.report_and_reports_delete);

        try {
            ArrayList<String> strings = new ArrayList<>();
            strings = getFiles();

            if (strings != null) {
                final String[] reports = new String[strings.size()];
                for (int i = 0; i < strings.size(); i++) {
                    reports[i] = strings.get(i);
                }

                final boolean[] checkedItems = new boolean[strings.size()];
                builder.setMultiChoiceItems(reports, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checkedItems[which] = isChecked;
                    }
                });

                builder.setPositiveButton(R.string.okey, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        boolean state = true;

                        if (checkedItems.length != 0) {

                            int count = 0;
                            try {
                                for (int i = 0; i < checkedItems.length; i++) {
                                    if (checkedItems[i]) {
                                        getDeleteReport(reports[i]);

                                        count++;
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                state = false;
                            }

                            if (count != 0) {
                                if (state) {
                                    Toast.makeText(getContext(), R.string.delete_success, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), R.string.delete_error, Toast.LENGTH_SHORT).show();
                                }

                                count = 0;
                            } else {
                                Toast.makeText(getContext(), R.string.report_select, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), R.string.report_select, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton(R.string.out, null);
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                Toast.makeText(getContext(), R.string.not_report_delete, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean getDeleteReport(String reportName) {

        boolean statu = false;
        try {

            String myFilePath = Environment.getExternalStorageDirectory().toString() + "/StokTakipApp/" + reportName;
            File file = new File(myFilePath);

            if (file.exists()) {
                file.delete();
            }

            statu = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return statu;
    }

    private void getDialogInformation() {
        try {
            LayoutInflater layoutInflater = getActivity().getLayoutInflater();
            View mView = layoutInflater.inflate(R.layout.dialog_text, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(mView);

            final TextView infromationTextView = (TextView) mView.findViewById(R.id.infromationTextView);
            infromationTextView.setText(Html.fromHtml(
                    "<br><b>Ürün Listeleme Sayfasından</b>" +
                            "<br> - Ürün Adı ve Ürün Kodundan Arama Yapabilir " +
                            "<br> - Ürüne Ait Ayrıntılı Rapor Sayfasına Geçebilir " +
                            "<br> - Artan Sıralama ile Stoğunuzda Biten Ürünleri Liste Başına Çekebilir " +
                            "<br> - Azalan Sıralama ile Stoğunuzda Fazla Bulunan Ürünleri Hızlıca Görebilirsiniz. <br>" +

                            "<br><b>Ürünün Ayrıntılı Rapor Sayfasından </b>" +
                            "<br> - Ürünün Güncel Stoğuna " +
                            "<br> - Ürüne Ait alış ve Satış Fiyatlarına " +
                            "<br> - Ürünün Toplam Stok Giriş ve Çıkışına " +
                            "<br> - Ürünün Son Hareket Raporlarına Ulaşabilirsiniz. <br>" +

                            "<br><b>Stok Analiz Sayfasından </b>" +
                            "<br> - Günlük, Haftalık ve Aylık Olarak Ne Kadar Stok" +
                            "<br> Girişi ve Çıkışı Yaptığınızı Gözlemleyebilirsiniz. <br>" +

                            "<br><b>Veritabanı Sayfanızdan</b>" +
                            "<br> - Yedekleme Seçeneği ile Birlikte Stok Listenizi," +
                            "<br> Günlük Stok Analizini, Haftalık Stok Analizini ve " +
                            "<br> Aylık Stok Analizini Excel Formatında Telefonunuza Kayıt Edebilirsiniz." +
                            "<br> - Yedeklediğiniz Stok Listesini Bilgisayar Üzerinde" +
                            "<br> Değişiklik Yaparak Değişiklikleri Geri Yükle " +
                            "<br> Seçeneği ile Programa Entegre Edebilirsiniz." +
                            "<br> - Listenizden Seçtiğiniz Ürünleri Excel Dosyası Olarak Kayıt Edebilirsiniz." +
                            "<br> - Dosyalarınızı Email Olarak Gönderebilirsiniz."));
            builder
                    .setCancelable(false)

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

    private void getBackupDaily() {
        try {
            getAppDBaseConnect(getContext());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateDaily = dateFormat.format(new Date());

            DatabaseToExel databaseToExel = new DatabaseToExel(null,
                    null, appDatabase.stockMovDao().getAllStockMovDate(dateDaily),
                    ReportType.DAILYREPORT);

            if (databaseToExel.setReportDayWeekMonth()) {
                getBackupWeekly();
            } else {
                Toast.makeText(getContext(), R.string.backup_error, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getBackupWeekly() {
        try {
            getAppDBaseConnect(getContext());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -7);
            beforeWeekDate = dateFormat.format(calendar.getTime());

            Calendar calendarOne = Calendar.getInstance();
            calendarOne.add(Calendar.DATE, -1);
            beforeDayDate = dateFormat.format(calendarOne.getTime());


            DatabaseToExel databaseToExel = new DatabaseToExel(null,
                    null, appDatabase.stockMovDao().getAllStockMovBetween(beforeWeekDate,
                    beforeDayDate), ReportType.WEEKLYREPORT);

            if (databaseToExel.setReportDayWeekMonth()) {
                getBackupMonthly();
            } else {
                Toast.makeText(getContext(), R.string.backup_error, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getBackupMonthly() {
        try {
            getAppDBaseConnect(getContext());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -1);
            beforeMonthDate = dateFormat.format(calendar.getTime());

            Calendar calendarOne = Calendar.getInstance();
            calendarOne.add(Calendar.DATE, -1);
            beforeDayDate = dateFormat.format(calendarOne.getTime());

            DatabaseToExel databaseToExel = new DatabaseToExel(null,
                    null, appDatabase.stockMovDao().getAllStockMovBetween(beforeWeekDate,
                    beforeDayDate), ReportType.MONTHLYYREPORT);

            if (databaseToExel.setReportDayWeekMonth()) {
                Toast.makeText(getContext(), R.string.backup_success, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), R.string.backup_error, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAppDBaseConnect(Context context) {
        try {
            appDatabase = AppDatabase.getDatabaseBuilder(context);
        } catch (Exception e) {
            Toast.makeText(context, R.string.database_connection_error, Toast.LENGTH_SHORT).show();
        }
    }
}
