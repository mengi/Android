package com.example.menginar.user.model;

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
    public String dateInOu;
}
