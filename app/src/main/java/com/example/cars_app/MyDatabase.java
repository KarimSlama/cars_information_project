package com.example.cars_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDatabase extends SQLiteAssetHelper {

    public static final String DATABASE_NAME = "cars.db";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_TABLE_NAME = "cars";

    //These are keys i did to use them in all project
    public static final String ID_COLUMN_NAME = "id";
    public static final String COLOR_COLUMN_NAME = "color";
    public static final String MODEL_COLUMN_NAME = "model";
    public static final String DESCRIPTION_COLUMN_NAME = "description";
    public static final String IMAGE_COLUMN_NAME = "image";
    public static final String DPL_COLUMN_NAME = "distance_per_liter";

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }//end constructor()
}//end class
