package com.example.menginar.user.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.menginar.user.model.Personel;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by menginar on 01.06.2017.
 */

@Dao
public interface PersonelDao {
    @Query("SELECT * FROM personelTable")
    List<Personel> getAllPersonel();

    @Query("SELECT * FROM personelTable WHERE emailAdress = :emailAdress")
    Personel getInfoPesonel(String emailAdress);

    @Query("DELETE FROM personelTable")
    void getDeleteAll();

    @Insert(onConflict = IGNORE)
    void insertPersonel(Personel personel);

    @Delete
    void deletePersonel(Personel personel);

    @Update
    void update(Personel personel);

}
