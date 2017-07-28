package com.example.photomap.connection;

import com.example.photomap.model.Image;
import com.example.photomap.model.Message;
import com.example.photomap.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by ss on 19.7.2017.
 */

public interface UserService {

    @FormUrlEncoded
    @POST("getInsertUser.php")
    Call<Message> getUserInsert(
            @Field("accessToken") String accessToken,
            @Field("userName") String userName,
            @Field("userEmail") String userEmail,
            @Field("userBirthday") String userBirthday,
            @Field("userGender") String userGender,
            @Field("userUrl") String userUrl,
            @Field("userId") String userId);

    @FormUrlEncoded
    @POST("getUserData.php")
    Call<User> getUserData(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("getUpdateUser.php")
    Call<User> getUserUpdate(
            @Field("userId") String userId,
            @Field("userName") String userName,
            @Field("userEmail") String userEmail,
            @Field("userBirthday") String userBirthday,
            @Field("userGender") String userGender);

    @FormUrlEncoded
    @POST("getComplainMessage.php")
    Call<Message> getComplainMessage(
            @Field("complaintName") String complaintName,
            @Field("complaintEmail") String complaintEmail,
            @Field("complaintTitle") String complaintTitle,
            @Field("complaintSubject") String complaintSubject,
            @Field("complainDate") String complainDate );

    @FormUrlEncoded
    @POST("getInsertImage.php")
    Call<Message> getInsertImage(
            @Field("userId") String userId,
            @Field("imagePath") String imagePath,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude);


    @FormUrlEncoded
    @POST("getDeleteImage.php")
    Call<Message> getDeleteImage(
            @Field("userId") String userId,
            @Field("imagePath") String imagePath);

    @FormUrlEncoded
    @POST("getAllImage.php")
    Call<List<Image>> getAllImage(
            @Field("userId") String userId);

    @FormUrlEncoded
    @POST("getImageInfo.php")
    Call<Image> getImageInfo(
            @Field("userId") String userId,
            @Field("imagePath") String imagePath);
}
