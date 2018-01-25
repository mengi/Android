
package com.android.trader.data.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PortfoyResult {

    @SerializedName("Result")
    @Expose
    private Result result;
    @SerializedName("Header")
    @Expose
    private Object header;
    @SerializedName("Item")
    @Expose
    private List<Item> item = null;
    @SerializedName("Other")
    @Expose
    private List<Other> other = null;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Object getHeader() {
        return header;
    }

    public void setHeader(Object header) {
        this.header = header;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public List<Other> getOther() {
        return other;
    }

    public void setOther(List<Other> other) {
        this.other = other;
    }

}
