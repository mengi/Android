package com.example.javier.spoonplayers.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import java.net.ConnectException;

/**
 * Created by menginar on 25.05.2017.
 */

public class ErrorMessage {

    public static void getErrorMessageString(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void getErrorMessage(Context context) {
        Toast.makeText(context, "İşleminiz Gerçekleştirilemiyor!", Toast.LENGTH_SHORT).show();
    }

    public static void isConnectMessage(Context context) {
        Toast.makeText(context, "Ağ Bağlantınızı Kontrol Ediniz!", Toast.LENGTH_SHORT).show();
    }

    public static boolean isConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else
            return false;
    }
}
