package com.example.menginar.user.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.example.menginar.user.model.Stock;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by menginar on 27.09.2017.
 */

@Dao
public interface StockDao {

    @Query("SELECT * FROM stock")
    List<Stock> getAllStock();

    @Query("DELETE FROM stock WHERE productCode = :proCode")
    void deleteStockCode(String proCode);

    @Insert(onConflict = IGNORE)
    void insertStock(Stock stock);

    @Update
    void updateStock(Stock stock);

    @Delete
    void deleteStock(Stock stock);
}
