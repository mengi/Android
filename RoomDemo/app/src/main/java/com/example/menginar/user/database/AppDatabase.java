package com.example.menginar.user.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.menginar.user.dao.PersonelDao;
import com.example.menginar.user.model.Personel;
import com.example.menginar.user.util.Converter;

/**
 * Created by menginar on 01.06.2017.
 */

@Database(entities = {Personel.class}, version = 2)
@TypeConverters({Converter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    public abstract PersonelDao personelDao();


    public static AppDatabase getDatabaseBuilder (Context context) {
        if (INSTANCE == null) {

            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "person_db").allowMainThreadQueries().build();
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
