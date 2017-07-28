package com.example.menginar.user.util;



import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by menginar on 01.06.2017.
 */

public class Converter {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}
