package com.android.trader.connection;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by menginar on 11.01.2018.
 */

public class RetroClient {

    private static final String ROOT_URL = "https://tb.matriksdata.com/9999/";

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static TraderApiService getTraderApiService() {
        return getRetrofitInstance().create(TraderApiService.class);
    }
}
