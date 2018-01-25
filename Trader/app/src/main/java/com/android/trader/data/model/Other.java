
package com.android.trader.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Other {

    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Key")
    @Expose
    private String key;
    @SerializedName("Value")
    @Expose
    private String value;
    @SerializedName("Timestamp")
    @Expose
    private Integer timestamp;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

}
