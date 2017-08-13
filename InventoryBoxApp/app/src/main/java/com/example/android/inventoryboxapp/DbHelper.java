package com.example.android.inventoryboxapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private Context context;

    public DbHelper(Context context) {
        super(context, DbContract.DB_NAME, null, DbContract.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DbContract.productTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL(DbContract.productTable.DELETE_TABLE);
        onCreate(database);
    }

    public void deleteDatabase() {
        context.deleteDatabase(DbContract.DB_NAME);
    }
}
