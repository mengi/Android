package com.sample.foo.simplelocationapp.connect;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Menginar on 21.4.2017.
 */

public class RetroClient {
    public static final String BASE_URL = "https://api.mylnikov.org/geolocation/";

    public static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static InterOpenCell getInterOpenCell() {
        return getRetrofitInstance().create(InterOpenCell.class);
    }
}
