package com.example.photomap.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by ss on 19.7.2017.
 */

public class SharedPreferencesUtil {

    private static final String MY_PREFERENCES = "MY_PREFERENCES";
    public static final String PRE_ACCESS_TOKEN = "PRE_ACCESS_TOKEN";
    public static final String PRE_ID = "PRE_ID";
    public static final String PRE_NAME = "PRE_NAME";
    public static final String PRE_EMAIL = "PRE_EMAIL";
    public static final String PRE_URL = "PRE_URL";
    public static final String PRE_GENDER = "PRE_GENDER";
    public static final String PRE_BIRTHDAY = "PRE_BIRTHDAY";

    public static String getFromSharedPrefs(Context context, String key) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
            return sharedPreferences.getString(key, null);
        } catch (Exception e) {
            Log.d("SharedPreferencesUtil", "getFromSharedPrefs", e);
            return null;
        }
    }

    public static boolean deleteFromSharedPrefs(Context context, String key) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
            sharedPreferences.edit().remove(key).apply();
        } catch (Exception e) {
            Log.d("SharedPreferencesUtil", "deleteFromSharedPrefs", e);
            return false;
        }
        return true;
    }

    public static void setToSharedPreferences(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).apply();
    }
}
