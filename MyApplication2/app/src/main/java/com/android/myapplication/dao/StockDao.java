package com.android.myapplication.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.android.myapplication.model.Stock;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by menginar on 27.09.2017.
 */

@Dao
public interface StockDao {

    @Query("SELECT * FROM stock LIMIT 100")
    List<Stock> getAllStock();

    @Query("SELECT * FROM stock ORDER BY productNumber DESC LIMIT 100")
    List<Stock> getAllStockUp();

    @Query("SELECT * FROM stock ORDER BY productNumber ASC LIMIT 100")
    List<Stock> getAllStockDown();

    @Query("SELECT * FROM stock WHERE productCode = :proCode")
    Stock getStockInfo(String proCode);

    @Query("SELECT * FROM stock WHERE productCode LIKE '%' || :proCodeName ||'%' " +
            "OR productName LIKE '%' || :proCodeName ||'%'")
    List<Stock> getSearchStock(String proCodeName);

    @Query("DELETE FROM stock WHERE productCode = :proCode")
    void deleteStockCode(String proCode);

    @Query("DELETE FROM stock")
    void deleteStocAllk();

    @Insert(onConflict = IGNORE)
    void insertStock(Stock stock);

    @Update
    void updateStock(Stock stock);

    @Delete
    void deleteStock(Stock stock);
}
