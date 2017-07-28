package com.example.javier.spoonplayers.Connect;

import com.example.javier.spoonplayers.Model.Actor;
import com.example.javier.spoonplayers.Model.Image;
import com.example.javier.spoonplayers.Model.Message;
import com.example.javier.spoonplayers.Model.Notice;
import com.example.javier.spoonplayers.Model.Video;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Menginar on 31.3.2017.
 */

public interface GroupApiService {

    @GET("getPictureList.php")
    Call<ArrayList<Image>> getImageList();

    @GET("getActorList.php")
    Call<ArrayList<Actor>> getActorList();

    @GET("getNoticeList.php")
    Call<ArrayList<Notice>> getNoticeList();

    @GET("getVideoList.php")
    Call<ArrayList<Video>> getVideoList();

    @FormUrlEncoded
    @POST("insertMessage.php")
    Call<Message> getInsertMessaging(@Field("FullName") String fullName,
                                     @Field("Email") String email,
                                     @Field("Phone") String phone,
                                     @Field("Subject") String subject,
                                     @Field("Date") String date);
}
