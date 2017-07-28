package com.example.photomap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by menginar on 26.07.2017.
 */

public class Image {

    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("imagePath")
    @Expose
    private String imagePath;

    @SerializedName("latitude")
    @Expose
    private String latitude;


    @SerializedName("longitude")
    @Expose
    private String longitude;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
