package com.android.trader.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by menginar on 11.01.2018.
 */

public class SharedPreferencesUtil {

    private static final String MY_PREFERENCES = "MyPrefs" ;
    public static final String PRE_USERNAME = "Username";
    public static final String PRE_PASSWORD = "Password";
    public static final String PRE_ACCOUNTID = "AccountID";


    public static String getFromSharedPrefs(Context context, String key) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
            return sharedPreferences.getString(key, null);
        } catch (Exception e) {
            Log.d("SharedPreferencesUtil","getFromSharedPrefs",e);
            return null;
        }
    }

    public static boolean deleteFromSharedPrefs(Context context, String key) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
            sharedPreferences.edit().remove(key).apply();
        } catch (Exception e) {
            Log.d("SharedPreferencesUtil","deleteFromSharedPrefs",e);
            return false;        }

        return true;
    }


    public static void setToSharedPreferences(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).apply();

    }
}
