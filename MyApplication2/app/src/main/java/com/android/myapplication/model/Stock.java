package com.android.myapplication.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by menginar on 27.09.2017.
 */

@Entity(tableName = "stock")
public class Stock {

    @PrimaryKey
    public String productCode;

    public String productName;
    public int productNumber;
    public double pruchasePrice;
    public double salePrice;
    public String dateSave;

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

    public double getPruchasePrice() {
        return pruchasePrice;
    }

    public void setPruchasePrice(double pruchasePrice) {
        this.pruchasePrice = pruchasePrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public String getDateSave() {
        return dateSave;
    }

    public void setDateSave(String dateSave) {
        this.dateSave = dateSave;
    }
}
