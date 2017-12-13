package com.example.menginar.user.model;

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
}
