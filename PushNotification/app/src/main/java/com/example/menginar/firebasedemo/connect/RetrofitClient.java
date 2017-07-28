package com.example.menginar.firebasedemo.connect;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by menginar on 13.06.2017.
 */

public class RetrofitClient {

    public static final String BASEURL = "http://192.168.1.175/firebase/";

    public static Retrofit getRetrofitClient() {
        return new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static FireBaseApiService getGroupApiService() {
        return getRetrofitClient().create(FireBaseApiService.class);
    }
}
