
package com.sample.foo.simplelocationapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("range")
    @Expose
    private Double range;
    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("time")
    @Expose
    private Integer time;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getRange() {
        return range;
    }

    public void setRange(Double range) {
        this.range = range;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

}
