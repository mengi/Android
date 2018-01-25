
package com.android.trader.data.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TraderResult {

    @SerializedName("Result")
    @Expose
    private Result result;
    @SerializedName("AccountList")
    @Expose
    private List<String> accountList = null;
    @SerializedName("DefaultAccount")
    @Expose
    private String defaultAccount;
    @SerializedName("CustomerID")
    @Expose
    private String customerID;
    @SerializedName("UserRights")
    @Expose
    private List<UserRight> userRights = null;
    @SerializedName("AccountItems")
    @Expose
    private List<AccountItem> accountItems = null;
    @SerializedName("MarketDataToken")
    @Expose
    private String marketDataToken;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("ExCode")
    @Expose
    private Integer exCode;
    @SerializedName("AccountListEx")
    @Expose
    private List<Object> accountListEx = null;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public List<String> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<String> accountList) {
        this.accountList = accountList;
    }

    public String getDefaultAccount() {
        return defaultAccount;
    }

    public void setDefaultAccount(String defaultAccount) {
        this.defaultAccount = defaultAccount;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public List<UserRight> getUserRights() {
        return userRights;
    }

    public void setUserRights(List<UserRight> userRights) {
        this.userRights = userRights;
    }

    public List<AccountItem> getAccountItems() {
        return accountItems;
    }

    public void setAccountItems(List<AccountItem> accountItems) {
        this.accountItems = accountItems;
    }

    public String getMarketDataToken() {
        return marketDataToken;
    }

    public void setMarketDataToken(String marketDataToken) {
        this.marketDataToken = marketDataToken;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getExCode() {
        return exCode;
    }

    public void setExCode(Integer exCode) {
        this.exCode = exCode;
    }

    public List<Object> getAccountListEx() {
        return accountListEx;
    }

    public void setAccountListEx(List<Object> accountListEx) {
        this.accountListEx = accountListEx;
    }

}
