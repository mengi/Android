package com.example.menginar.user.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by menginar on 01.06.2017.
 */

@Entity(tableName = "personelTable")
public class Personel {

    @PrimaryKey
    public String emailAdress;

    public String fullName;
    public String phoneNumber;
    public String message;
}
