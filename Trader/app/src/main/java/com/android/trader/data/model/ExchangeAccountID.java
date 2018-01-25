
package com.android.trader.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExchangeAccountID {

    @SerializedName("4")
    @Expose
    private String _4;
    @SerializedName("9")
    @Expose
    private String _9;

    public String get4() {
        return _4;
    }

    public void set4(String _4) {
        this._4 = _4;
    }

    public String get9() {
        return _9;
    }

    public void set9(String _9) {
        this._9 = _9;
    }

}
