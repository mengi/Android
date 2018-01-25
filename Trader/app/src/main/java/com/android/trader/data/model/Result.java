
package com.android.trader.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("State")
    @Expose
    private Boolean state;
    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("SessionKey")
    @Expose
    private String sessionKey;
    @SerializedName("Duration")
    @Expose
    private Integer duration;
    @SerializedName("MsgType")
    @Expose
    private String msgType;
    @SerializedName("Timestamp")
    @Expose
    private Object timestamp;
    @SerializedName("ClOrdID")
    @Expose
    private String clOrdID;

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }

    public String getClOrdID() {
        return clOrdID;
    }

    public void setClOrdID(String clOrdID) {
        this.clOrdID = clOrdID;
    }

}
