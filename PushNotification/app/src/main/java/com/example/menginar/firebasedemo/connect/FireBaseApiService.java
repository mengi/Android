package com.example.menginar.firebasedemo.connect;

import com.example.menginar.firebasedemo.model.Message;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by menginar on 13.06.2017.
 */

public interface FireBaseApiService {

    @FormUrlEncoded
    @POST("tokenRegister.php")
    Call<Message> getInsertMessaging(@Field("token") String tokenID);
}
