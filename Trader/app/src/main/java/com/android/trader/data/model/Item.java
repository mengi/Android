
package com.android.trader.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("PositionID")
    @Expose
    private String positionID;
    @SerializedName("ProtectionOrderID")
    @Expose
    private String protectionOrderID;
    @SerializedName("AccountID")
    @Expose
    private String accountID;
    @SerializedName("RecordDate")
    @Expose
    private String recordDate;
    @SerializedName("ClosingDate")
    @Expose
    private String closingDate;
    @SerializedName("Symbol")
    @Expose
    private String symbol;
    @SerializedName("SymbolID")
    @Expose
    private Integer symbolID;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("Qty_T")
    @Expose
    private Double qtyT;
    @SerializedName("Qty_T1")
    @Expose
    private Double qtyT1;
    @SerializedName("Qty_T2")
    @Expose
    private Double qtyT2;
    @SerializedName("Qty_T3")
    @Expose
    private Double qtyT3;
    @SerializedName("Qty_Long")
    @Expose
    private Double qtyLong;
    @SerializedName("Qty_Short")
    @Expose
    private Double qtyShort;
    @SerializedName("Qty_Net")
    @Expose
    private Double qtyNet;
    @SerializedName("Qty_Available")
    @Expose
    private Double qtyAvailable;
    @SerializedName("LastPx")
    @Expose
    private Double lastPx;
    @SerializedName("OpeningAvgPrice")
    @Expose
    private Double openingAvgPrice;
    @SerializedName("ClosingAvgPrice")
    @Expose
    private Double closingAvgPrice;
    @SerializedName("StopPrice")
    @Expose
    private Double stopPrice;
    @SerializedName("LimitPrice")
    @Expose
    private Double limitPrice;
    @SerializedName("Amount")
    @Expose
    private Double amount;
    @SerializedName("AmountShort")
    @Expose
    private Double amountShort;
    @SerializedName("AmountLong")
    @Expose
    private Double amountLong;
    @SerializedName("AvgCost")
    @Expose
    private Double avgCost;
    @SerializedName("DailyCost")
    @Expose
    private Double dailyCost;
    @SerializedName("PL")
    @Expose
    private Double pL;
    @SerializedName("PL_Percent")
    @Expose
    private Double pLPercent;
    @SerializedName("Credit")
    @Expose
    private Double credit;
    @SerializedName("MarginRequired")
    @Expose
    private Double marginRequired;
    @SerializedName("Swap")
    @Expose
    private Double swap;
    @SerializedName("DailyPL")
    @Expose
    private Double dailyPL;
    @SerializedName("PL_ur")
    @Expose
    private Double pLUr;
    @SerializedName("PL_r")
    @Expose
    private Double pLR;
    @SerializedName("PositionSide")
    @Expose
    private Integer positionSide;
    @SerializedName("SortOrder")
    @Expose
    private Integer sortOrder;

    public String getPositionID() {
        return positionID;
    }

    public void setPositionID(String positionID) {
        this.positionID = positionID;
    }

    public String getProtectionOrderID() {
        return protectionOrderID;
    }

    public void setProtectionOrderID(String protectionOrderID) {
        this.protectionOrderID = protectionOrderID;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getSymbolID() {
        return symbolID;
    }

    public void setSymbolID(Integer symbolID) {
        this.symbolID = symbolID;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getQtyT() {
        return qtyT;
    }

    public void setQtyT(Double qtyT) {
        this.qtyT = qtyT;
    }

    public Double getQtyT1() {
        return qtyT1;
    }

    public void setQtyT1(Double qtyT1) {
        this.qtyT1 = qtyT1;
    }

    public Double getQtyT2() {
        return qtyT2;
    }

    public void setQtyT2(Double qtyT2) {
        this.qtyT2 = qtyT2;
    }

    public Double getQtyT3() {
        return qtyT3;
    }

    public void setQtyT3(Double qtyT3) {
        this.qtyT3 = qtyT3;
    }

    public Double getQtyLong() {
        return qtyLong;
    }

    public void setQtyLong(Double qtyLong) {
        this.qtyLong = qtyLong;
    }

    public Double getQtyShort() {
        return qtyShort;
    }

    public void setQtyShort(Double qtyShort) {
        this.qtyShort = qtyShort;
    }

    public Double getQtyNet() {
        return qtyNet;
    }

    public void setQtyNet(Double qtyNet) {
        this.qtyNet = qtyNet;
    }

    public Double getQtyAvailable() {
        return qtyAvailable;
    }

    public void setQtyAvailable(Double qtyAvailable) {
        this.qtyAvailable = qtyAvailable;
    }

    public Double getLastPx() {
        return lastPx;
    }

    public void setLastPx(Double lastPx) {
        this.lastPx = lastPx;
    }

    public Double getOpeningAvgPrice() {
        return openingAvgPrice;
    }

    public void setOpeningAvgPrice(Double openingAvgPrice) {
        this.openingAvgPrice = openingAvgPrice;
    }

    public Double getClosingAvgPrice() {
        return closingAvgPrice;
    }

    public void setClosingAvgPrice(Double closingAvgPrice) {
        this.closingAvgPrice = closingAvgPrice;
    }

    public Double getStopPrice() {
        return stopPrice;
    }

    public void setStopPrice(Double stopPrice) {
        this.stopPrice = stopPrice;
    }

    public Double getLimitPrice() {
        return limitPrice;
    }

    public void setLimitPrice(Double limitPrice) {
        this.limitPrice = limitPrice;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmountShort() {
        return amountShort;
    }

    public void setAmountShort(Double amountShort) {
        this.amountShort = amountShort;
    }

    public Double getAmountLong() {
        return amountLong;
    }

    public void setAmountLong(Double amountLong) {
        this.amountLong = amountLong;
    }

    public Double getAvgCost() {
        return avgCost;
    }

    public void setAvgCost(Double avgCost) {
        this.avgCost = avgCost;
    }

    public Double getDailyCost() {
        return dailyCost;
    }

    public void setDailyCost(Double dailyCost) {
        this.dailyCost = dailyCost;
    }

    public Double getPL() {
        return pL;
    }

    public void setPL(Double pL) {
        this.pL = pL;
    }

    public Double getPLPercent() {
        return pLPercent;
    }

    public void setPLPercent(Double pLPercent) {
        this.pLPercent = pLPercent;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Double getMarginRequired() {
        return marginRequired;
    }

    public void setMarginRequired(Double marginRequired) {
        this.marginRequired = marginRequired;
    }

    public Double getSwap() {
        return swap;
    }

    public void setSwap(Double swap) {
        this.swap = swap;
    }

    public Double getDailyPL() {
        return dailyPL;
    }

    public void setDailyPL(Double dailyPL) {
        this.dailyPL = dailyPL;
    }

    public Double getPLUr() {
        return pLUr;
    }

    public void setPLUr(Double pLUr) {
        this.pLUr = pLUr;
    }

    public Double getPLR() {
        return pLR;
    }

    public void setPLR(Double pLR) {
        this.pLR = pLR;
    }

    public Integer getPositionSide() {
        return positionSide;
    }

    public void setPositionSide(Integer positionSide) {
        this.positionSide = positionSide;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

}
