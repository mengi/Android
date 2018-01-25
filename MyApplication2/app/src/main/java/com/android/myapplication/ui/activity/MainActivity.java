package com.android.myapplication.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.myapplication.R;
import com.android.myapplication.constant.ReportType;
import com.android.myapplication.database.AppDatabase;
import com.android.myapplication.scan.NoScanResultException;
import com.android.myapplication.scan.ScanResultReceiver;
import com.android.myapplication.service.BackupService;
import com.android.myapplication.ui.fragment.FragmentCase;
import com.android.myapplication.ui.fragmentanalysis.FragmentDateBetween;
import com.android.myapplication.ui.fragmentdatabase.FragmentDatabase;
import com.android.myapplication.ui.fragment.FragmentSearch;
import com.android.myapplication.ui.fragment.FragmentSetting;
import com.android.myapplication.ui.fragment.FragmentStcokList;
import com.android.myapplication.ui.fragmentanalysis.FragmentStockAnalysis;
import com.android.myapplication.ui.fragment.FragmentStockDelUp;
import com.android.myapplication.ui.fragment.FragmentStockInOu;
import com.android.myapplication.ui.fragment.FragmentStockSave;

import java.util.ArrayList;

import rebus.permissionutils.AskAgainCallback;
import rebus.permissionutils.FullCallback;
import rebus.permissionutils.PermissionEnum;
import rebus.permissionutils.PermissionManager;

public class MainActivity extends AppCompatActivity implements ScanResultReceiver, FullCallback {

    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView ımageView;
    TextView txtFullName;
    View view;

    private AppDatabase appDatabase;
    public String barCode, barFormatCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = getWindow().getDecorView().getRootView();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        getAppDatabaseConnect(getBaseContext());

        //Backup Aoutmatic
        Intent intent = new Intent(getApplicationContext(), BackupService.class);
        startService(intent);


        // Service Not Working
        if (!serviceWork()) {
            intent = new Intent(getApplicationContext(), BackupService.class);
            startService(intent);
        }


        ReportType.isChecked = new boolean[appDatabase.stockDao().getAllStock().size()];

        try {
            View header = navigationView.getHeaderView(0);
            txtFullName = (TextView) header.findViewById(R.id.txt_name);
            ımageView = (ImageView) header.findViewById(R.id.profile_image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setupToolbar();

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        int id = menuItem.getItemId();

                        if (id == R.id.nav_stock_input_outut) {
                            homePage();
                        } else if (id == R.id.nav_stock_list) {
                            stockList();
                        } else if (id == R.id.nav_stock_analysis) {
                            stockAnalysis();
                        } else if (id == R.id.nav_stock_delete_update) {
                            stockDeleteUpdate();
                        } else if (id == R.id.nav_new_stock_register) {
                            stockSave();
                        } else if (id == R.id.nav_stock_database) {
                            stockDatabase();
                        } else if (id == R.id.nav_setting) {
                            settingApp();
                        } else if (id == R.id.nav_stock_analysis_date) {
                            stockMovsAnalysis();
                        }

                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
        homePage();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setMessage(R.string.exit_app);
        alertdialog.setCancelable(false).setPositiveButton(R.string.yes_app, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }).setNegativeButton(R.string.no_app, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alert = alertdialog.create();
        alert.show();
    }

    private void setupToolbar() {
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            final ActionBar ab = getSupportActionBar();
            ab.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            ab.setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void homePage() {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new FragmentStockInOu()).commit();
    }

    private void stockList() {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new FragmentStcokList()).commit();
    }

    private void stockAnalysis() {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new FragmentStockAnalysis()).commit();
    }

    private void stockDeleteUpdate() {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new FragmentStockDelUp()).commit();
    }

    private void stockSave() {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new FragmentStockSave()).commit();
    }

    private void stockDatabase() {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new FragmentDatabase()).commit();
    }

    private void stockMovsAnalysis() {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new FragmentDateBetween()).commit();
    }

    private void settingApp() {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new FragmentSetting()).commit();
    }

    public void getAppDatabaseConnect(Context context) {
        try {
            appDatabase = AppDatabase.getDatabaseBuilder(context);
        } catch (Exception e) {
            Toast.makeText(context, R.string.database_connection_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();

        Intent intent = new Intent(getApplicationContext(), BackupService.class);
        startService(intent);
    }

    @Override
    public void scanResultData(String codeFormat, String codeContent, String typeFragment) {
        barCode = codeContent;
        barFormatCode = codeFormat;

        if (typeFragment.equals(ReportType.SEARCH)) {
            Fragment fragment = new FragmentSearch();

            Bundle bundle = new Bundle();
            bundle.putString("barCodeReader", barCode);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

        }

        if (typeFragment.equals(ReportType.STOCKLIST)) {
            Fragment fragment = new FragmentStcokList();

            Bundle bundle = new Bundle();
            bundle.putString("barCodeReader", barCode);
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
        }

        if (typeFragment.equals(ReportType.STOCKDELUP)) {
            Fragment fragment = new FragmentStockDelUp();

            Bundle bundle = new Bundle();
            bundle.putString("barCodeReader", barCode);
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
        }

        if (typeFragment.equals(ReportType.STOCKINOU)) {
            Fragment fragment = new FragmentStockInOu();

            Bundle bundle = new Bundle();
            bundle.putString("barCodeReader", barCode);
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
        }

        if (typeFragment.equals(ReportType.STOCKSAVE)) {
            Fragment fragment = new FragmentStockSave();

            Bundle bundle = new Bundle();
            bundle.putString("barCodeReader", barCode);
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
        }
    }

    @Override
    public void scanResultData(NoScanResultException noScanData) {
        Toast toast = Toast.makeText(this, noScanData.getMessage(), Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        ArrayList<PermissionEnum> permissionEnumArrayList = new ArrayList<>();
        permissionEnumArrayList.add(PermissionEnum.CAMERA);
        permissionEnumArrayList.add(PermissionEnum.WRITE_EXTERNAL_STORAGE);
        permissionEnumArrayList.add(PermissionEnum.READ_EXTERNAL_STORAGE);


        PermissionManager.Builder()
                .key(9000)
                .permissions(permissionEnumArrayList)
                .askAgain(true)
                .askAgainCallback(new AskAgainCallback() {
                    @Override
                    public void showRequestPermission(UserResponse response) {
                        showDialog(response);
                    }
                })
                .callback(MainActivity.this)
                .ask(MainActivity.this);
    }

    public boolean serviceWork() {

        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (BackupService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionManager.handleResult(this, requestCode, permissions, grantResults);
    }

    @Override
    public void result(ArrayList<PermissionEnum> arrayList, ArrayList<PermissionEnum> arrayList1, ArrayList<PermissionEnum> arrayList2, ArrayList<PermissionEnum> arrayList3) {

    }

    private void showDialog(final AskAgainCallback.UserResponse response) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("İzin Gereksinimleri")
                .setMessage("Uygulama için gerekli izinleri veriniz!!")
                .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        response.result(true);
                    }
                })
                .setNegativeButton("Şimdi Değil", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        response.result(false);
                    }
                })
                .setCancelable(false)
                .show();
    }

}