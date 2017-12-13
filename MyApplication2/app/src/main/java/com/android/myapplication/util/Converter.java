package com.android.myapplication.util;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by menginar on 27.09.2017.
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
