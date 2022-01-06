package com.praktikum.datateman;


import android.provider.BaseColumns;

public class DatabaseSetting {

    private DatabaseSetting() {}

    public static final class DatabaseEntry implements BaseColumns{
        public static final String TABLE_NAME = "temanList";
        public static final String COLUMN_NAME = "nama";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_ADDRESS = "alamat";
        public static final String COLUMN_PHOTO = "foto";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
