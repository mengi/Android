package com.example.javier.spoonplayers.Connect;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Menginar on 31.3.2017.
 */

public class RetroClient {

    public static final String BASEURL = "http://www.hediyelikcim.com/ArduinoCloud/";

    public static Retrofit getRetrofitClient() {
        return new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static GroupApiService getGroupApiService() {
        return getRetrofitClient().create(GroupApiService.class);
    }
}
