package com.example.javier.spoonplayers.Model;

/**
 * Created by Menginar on 31.3.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("PictureName")
    @Expose
    private String pictureName;
    @SerializedName("PicturePath")
    @Expose
    private String picturePath;

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

}