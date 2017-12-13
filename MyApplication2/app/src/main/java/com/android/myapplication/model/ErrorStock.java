package com.android.myapplication.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by menginar on 07.11.2017.
 */

@Entity(tableName = "errorStock")
public class ErrorStock {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String productCode;
    public String productName;
    public int productNumber;
    public Double pruchasePrice;
    public String proDate;
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

    public Double getPruchasePrice() {
        return pruchasePrice;
    }

    public void setPruchasePrice(Double pruchasePrice) {
        this.pruchasePrice = pruchasePrice;
    }

    public String getProDate() {
        return proDate;
    }

    public void setProDate(String proDate) {
        this.proDate = proDate;
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
}
