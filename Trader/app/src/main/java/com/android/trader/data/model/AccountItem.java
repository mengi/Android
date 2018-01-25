
package com.android.trader.data.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountItem {

    @SerializedName("AccountID")
    @Expose
    private String accountID;
    @SerializedName("ExchangeAccountID")
    @Expose
    private ExchangeAccountID exchangeAccountID;
    @SerializedName("AccountRights")
    @Expose
    private List<Object> accountRights = null;
    @SerializedName("TransactionTypeRights")
    @Expose
    private List<Object> transactionTypeRights = null;

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public ExchangeAccountID getExchangeAccountID() {
        return exchangeAccountID;
    }

    public void setExchangeAccountID(ExchangeAccountID exchangeAccountID) {
        this.exchangeAccountID = exchangeAccountID;
    }

    public List<Object> getAccountRights() {
        return accountRights;
    }

    public void setAccountRights(List<Object> accountRights) {
        this.accountRights = accountRights;
    }

    public List<Object> getTransactionTypeRights() {
        return transactionTypeRights;
    }

    public void setTransactionTypeRights(List<Object> transactionTypeRights) {
        this.transactionTypeRights = transactionTypeRights;
    }

}
