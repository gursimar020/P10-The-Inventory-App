package com.example.android.inventoryboxapp;

import android.provider.BaseColumns;

public class DbContract {

    //Default Constructor
    public DbContract() {
    }

    //Database NAME  and VERSION
    public static final String DB_NAME = "InventoryBox.db";
    public static final int DB_VERSION = 1;

    public static abstract class productTable implements BaseColumns {

        public static final String TABLE_NAME = "Inventory";
        public static final String COL_PRODUCT_NAME = "name";
        public static final String COL_QUANTITY = "quantity";
        public static final String COL_PRICE = "price";
        public static final String COL_IMAGE = "imageUrl";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_PRODUCT_NAME +
                " TEXT NOT NULL," + COL_QUANTITY + " INT NOT NULL," + COL_PRICE +
                " DOUBLE NOT NULL," + COL_IMAGE + " TEXT NOT NULL);";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

}
