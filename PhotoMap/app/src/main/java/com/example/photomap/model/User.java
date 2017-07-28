package com.example.photomap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ss on 19.7.2017.
 */

public class User {

    @SerializedName("accessToken")
    @Expose
    public String accessToken;

    @SerializedName("userName")
    @Expose
    public String userName;

    @SerializedName("userEmail")
    @Expose

    public String userEmail;

    @SerializedName("userBirthday")
    @Expose
    public String userBirthday;

    @SerializedName("userGender")
    @Expose
    public String userGender;

    @SerializedName("userUrl")
    @Expose
    public String userUrl;

    @SerializedName("userId")
    @Expose
    public String userId;

    @SerializedName("success")
    @Expose
    private Integer success;

    @SerializedName("message")
    @Expose
    private String message;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
