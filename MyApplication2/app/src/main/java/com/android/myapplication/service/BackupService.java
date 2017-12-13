package com.android.myapplication.service;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.android.myapplication.R;
import com.android.myapplication.constant.ReportType;
import com.android.myapplication.database.AppDatabase;
import com.android.myapplication.util.DatabaseToExel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by menginar on 06.10.2017.
 */

public class BackupService extends Service {

    private AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    new Database().execute();
                }
            }, 0, 86400000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BackupService() {
        super();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void getAppDatabaseConnect(Context context) {
        try {
            appDatabase = AppDatabase.getDatabaseBuilder(context);
        } catch (Exception e) {
            Toast.makeText(context, R.string.database_connection_error, Toast.LENGTH_SHORT).show();
        }
    }

    public class Database extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            getAppDatabaseConnect(getApplicationContext());

            try {
                DatabaseToExel databaseToExel = new DatabaseToExel(null,
                        appDatabase.stockDao().getAllStock(), null, ReportType.AUTOMATIC);

                databaseToExel.setReport();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean  result) {
            super.onPostExecute(result);

            if (!result) {
                Toast.makeText(getApplicationContext(), R.string.backup_error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
