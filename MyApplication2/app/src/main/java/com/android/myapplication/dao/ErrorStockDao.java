package com.android.myapplication.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.android.myapplication.model.ErrorStock;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by menginar on 07.11.2017.
 */

@Dao
public interface ErrorStockDao {

    @Query("SELECT * FROM errorStock WHERE id = :id")
    ErrorStock getErrorStock(int id);

    @Query("SELECT * FROM errorStock")
    List<ErrorStock> getAllErrorStock();

    @Query("SELECT * FROM errorStock WHERE productCode = :productCode")
    List<ErrorStock> getAllErrorStockCode(String productCode);

    @Query("SELECT * FROM errorStock WHERE productCode = :productNameCode OR productName = :productNameCode")
    List<ErrorStock> getAllTwoErrorStock(String productNameCode);

    @Query("SELECT * FROM errorStock WHERE proDate BETWEEN :firstDate AND :lastDate")
    List<ErrorStock> getDateErrorStock(String firstDate, String lastDate);

    @Insert(onConflict = IGNORE)
    void insertErrorStock(ErrorStock errorStock);

    @Update
    void updateErrorStock(ErrorStock errorStock);

    @Delete
    void deleteErrorStock(ErrorStock errorStock);
}
