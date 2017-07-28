package com.example.menginar.firebasedemo.service;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.menginar.firebasedemo.connect.FireBaseApiService;
import com.example.menginar.firebasedemo.connect.FireBaseConfig;
import com.example.menginar.firebasedemo.connect.RetrofitClient;
import com.example.menginar.firebasedemo.model.Message;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by menginar on 13.06.2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        sendRegistrationToServer(refreshedToken);
        Intent registrationComplete = new Intent(FireBaseConfig.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(String token) {
        getTokenRegister(token);
        SharedPreferences pref = getApplicationContext().getSharedPreferences(FireBaseConfig.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        Log.e("REGISTERID", "Firebase reg id: " + token);
        editor.putString("regId", token);
        editor.commit();

    }

    public void getTokenRegister(String tokenID) {
        FireBaseApiService apiService = RetrofitClient.getGroupApiService();

        Call<Message> messageCall = apiService.getInsertMessaging(tokenID);

        messageCall.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.body().getSuccess() == 0) {

                }

                if (response.body().getSuccess() == 1) {

                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

            }
        });
    }
}
