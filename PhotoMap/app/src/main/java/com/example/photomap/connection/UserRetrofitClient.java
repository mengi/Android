package com.example.photomap.connection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ss on 19.7.2017.
 */

public class UserRetrofitClient {
    private static final String ROOT_URL = "http://192.168.1.187/imagemap/";



    private static Retrofit getRetrofitInstance() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static UserService getApiService() {
        return getRetrofitInstance().create(UserService.class);
    }
}
