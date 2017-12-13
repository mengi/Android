package com.example.menginar.user.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.menginar.user.dao.StockDao;
import com.example.menginar.user.dao.StockMovDao;
import com.example.menginar.user.model.Stock;
import com.example.menginar.user.model.StockMov;
import com.example.menginar.user.util.Converter;


/**
 * Created by menginar on 27.09.2017.
 */


@Database(entities = {Stock.class, StockMov.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    public abstract StockDao stockDao();
    public abstract StockMovDao stockMovDao();


    public static AppDatabase getDatabaseBuilder (Context context) {
        if (INSTANCE == null) {

            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "stock_db").allowMainThreadQueries().build();
        }

        return INSTANCE;
    }

    public static AppDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
