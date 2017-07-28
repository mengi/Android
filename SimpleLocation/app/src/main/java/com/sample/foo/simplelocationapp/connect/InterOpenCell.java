package com.sample.foo.simplelocationapp.connect;

import com.sample.foo.simplelocationapp.model.LocationData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Menginar on 21.4.2017.
 */

public interface InterOpenCell {

    // Location Data
    @GET("cell?v=1.1&data=open")
    Call<LocationData> getLocationData(@Query("mcc") String mcc,
                                      @Query("mnc") String mnc,
                                      @Query("lac") String lac,
                                      @Query("cellid") String cellId);
}
