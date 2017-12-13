package com.android.myapplication.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by menginar on 27.09.2017.
 */

@Entity(tableName = "stockmov")
public class StockMov {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String productCode;
    public String productName;
    public int productNumber;
    public String proState;
    public double statePrice;
    public String dateInOu;
    public String dateInOuHour;
    public String proExplan;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(int productNumber) {
        this.productNumber = productNumber;
    }

    public String getProState() {
        return proState;
    }

    public void setProState(String proState) {
        this.proState = proState;
    }

    public String getDateInOu() {
        return dateInOu;
    }

    public void setDateInOu(String dateInOu) {
        this.dateInOu = dateInOu;
    }

    public String getDateInOuHour() {
        return dateInOuHour;
    }

    public void setDateInOuHour(String dateInOuHour) {
        this.dateInOuHour = dateInOuHour;
    }

    public String getProExplan() {
        return proExplan;
    }

    public void setProExplan(String proExplan) {
        this.proExplan = proExplan;
    }

    public double getStatePrice() {
        return statePrice;
    }

    public void setStatePrice(double statePrice) {
        this.statePrice = statePrice;
    }
}
