package com.android.myapplication.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.android.myapplication.model.StockMov;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by menginar on 27.09.2017.
 */

@Dao
public interface StockMovDao {

    @Query("SELECT DISTINCT productCode FROM stockmov")
    List<String> getStockAllGenarally();

    @Query("SELECT * FROM stockmov")
    List<StockMov> getStockMovGenarallyHar();

    @Query("SELECT * FROM stockmov WHERE productCode = :proCode")
    List<StockMov> getStockAllGenarallyCode(String proCode);

    // Daily Stock List and Stock Code
    @Query("SELECT DISTINCT productCode FROM stockmov WHERE dateInOu = :proDateInOu")
    List<String> getStockMovDailyCode(String proDateInOu);

    @Query("SELECT * FROM stockmov WHERE productCode = :proCode AND dateInOu = :dateInOu")
    List<StockMov> getStockMovDailyCodeDate(String proCode, String dateInOu);

    // Weekly, Monthly Stock List and Stock Code
    @Query("SELECT DISTINCT productCode FROM stockmov WHERE dateInOu BETWEEN :firstDate AND :lastDate")
    List<String> getStockMovWeeklyMonthlyCode(String firstDate, String lastDate);

    @Query("SELECT * FROM stockmov WHERE productCode = :proCode AND dateInOu BETWEEN :firstDate AND :lastDate")
    List<StockMov> getStockMovWeeklyMonthlyCodeDate(String proCode, String firstDate, String lastDate);

    @Query("SELECT * FROM stockmov WHERE dateInOu = :proDateInOu")
    List<StockMov> getAllStockMovDate(String proDateInOu);

    @Query("SELECT * FROM stockmov WHERE dateInOu BETWEEN :firstDate AND :lastDate")
    List<StockMov> getAllStockMovBetween(String firstDate, String lastDate);

    @Query("DELETE FROM stockmov WHERE productCode = :proCode")
    void deleteStockMovCode(String proCode);

    @Query("DELETE FROM stockmov")
    void deleteStockmovAll();

    @Insert(onConflict = IGNORE)
    void insertStockMov(StockMov stockMov);

    @Delete
    void deleteStockMov(StockMov stockMov);

    @Update
    void updateStockMov(StockMov stockMov);
}