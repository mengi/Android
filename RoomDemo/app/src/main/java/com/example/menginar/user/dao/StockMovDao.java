package com.example.menginar.user.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.menginar.user.model.StockMov;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by menginar on 27.09.2017.
 */

@Dao
public interface StockMovDao {

    @Query("SELECT * FROM stockmov WHERE productCode = :proCode")
    List<StockMov> getAllStockMovCode(String proCode);

    @Insert(onConflict = IGNORE)
    void insertStockMov(StockMov stockMov);

    @Delete
    void deleteStockMov(StockMov stockMov);

    @Update
    void updateStockMov(StockMov stockMov);
}
