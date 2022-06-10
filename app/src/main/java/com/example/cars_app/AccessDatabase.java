package com.example.cars_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLData;
import java.util.ArrayList;

public class AccessDatabase {

    private static AccessDatabase instance;
    private final SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;

    private AccessDatabase(Context context) {
        openHelper = new MyDatabase(context);
    }//end constructor()

    public static AccessDatabase getInstance(Context context) {
        if (instance == null)
            instance = new AccessDatabase(context);
        return instance;
    }//end getInstance()

    public void openDatabase() {
        database = openHelper.getWritableDatabase();
    }//end openDatabase()

    public void closeDatabase() {
        //if database is equal null, it means it's opening. If not it means it's closing
        if (database != null)
            database.close();
    }//end closeDatabase()

    public boolean insertData(Cars cars) {
        ContentValues values = new ContentValues();
        values.put(MyDatabase.MODEL_COLUMN_NAME, cars.getModel());
        values.put(MyDatabase.COLOR_COLUMN_NAME, cars.getColor());
        values.put(MyDatabase.DESCRIPTION_COLUMN_NAME, cars.getDescription());
        values.put(MyDatabase.IMAGE_COLUMN_NAME, cars.getImage());
        values.put(MyDatabase.DPL_COLUMN_NAME, cars.getDpl());
        long result = database.insert(MyDatabase.DATABASE_TABLE_NAME, null, values);
        return result != -1;
    }//end insertData()

    public boolean updateData(Cars cars) {
        ContentValues values = new ContentValues();
        values.put(MyDatabase.MODEL_COLUMN_NAME, cars.getModel());
        values.put(MyDatabase.COLOR_COLUMN_NAME, cars.getColor());
        values.put(MyDatabase.DESCRIPTION_COLUMN_NAME, cars.getDescription());
        values.put(MyDatabase.IMAGE_COLUMN_NAME, cars.getImage());
        values.put(MyDatabase.DPL_COLUMN_NAME, cars.getDpl());
        String[] args = {cars.getId() + ""};
        int result = database.update(MyDatabase.DATABASE_TABLE_NAME, values, MyDatabase.ID_COLUMN_NAME + " = ? ", args);

        //If the update process returns 0, it means there are no rows have been updated,
        //but if this return value is greater than 0, it means there are rows that have been updated.
        return result > 0;
    }//end updateData()

    public boolean deleteData(Cars cars) {
        //If the delete process returns 0, it means there are no rows have been deleted,
        //but if this return value is greater than 0, it means there are rows that have been deleted

        int result = database.delete(MyDatabase.DATABASE_TABLE_NAME, MyDatabase.ID_COLUMN_NAME + " = ? ", new String[]{String.valueOf(cars.getId())});
        return result > 0;
    }//end deleteData()

    public ArrayList<Cars> getAllCars() {
        ArrayList<Cars> carsData = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + MyDatabase.DATABASE_TABLE_NAME, null);
        if (cursor.moveToFirst() && cursor != null) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(MyDatabase.ID_COLUMN_NAME));
                String model = cursor.getString(cursor.getColumnIndex(MyDatabase.MODEL_COLUMN_NAME));
                String color = cursor.getString(cursor.getColumnIndex(MyDatabase.COLOR_COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndex(MyDatabase.DESCRIPTION_COLUMN_NAME));
                String image = cursor.getString(cursor.getColumnIndex(MyDatabase.IMAGE_COLUMN_NAME));
                double dpl = cursor.getDouble(cursor.getColumnIndex(MyDatabase.DPL_COLUMN_NAME));
                Cars allCarsData = new Cars(id, model, color, description, image, dpl);
                carsData.add(allCarsData);
            } while (cursor.moveToNext());
        }//end if()
        return carsData;
    }//end getAllCars()

    public ArrayList<Cars> searchCars(String carModel) {
        ArrayList<Cars> carsData = new ArrayList<>();
        String[] args = {carModel + "%"};
        Cursor cursor = database.rawQuery("SELECT * FROM " + MyDatabase.DATABASE_TABLE_NAME + " WHERE " + MyDatabase.MODEL_COLUMN_NAME + " LIKE ?", args);
        if (cursor.moveToFirst() && cursor != null) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(MyDatabase.ID_COLUMN_NAME));
                String model = cursor.getString(cursor.getColumnIndex(MyDatabase.MODEL_COLUMN_NAME));
                String color = cursor.getString(cursor.getColumnIndex(MyDatabase.COLOR_COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndex(MyDatabase.DESCRIPTION_COLUMN_NAME));
                String image = cursor.getString(cursor.getColumnIndex(MyDatabase.IMAGE_COLUMN_NAME));
                double dpl = cursor.getDouble(cursor.getColumnIndex(MyDatabase.DPL_COLUMN_NAME));
                Cars allCarsData = new Cars(id, model, color, description, image, dpl);
                carsData.add(allCarsData);
            } while (cursor.moveToNext());
        }//end if()
        cursor.close();
        return carsData;
    }//end searchCars()

    public Cars getCar(int carId) {
        String[] args = {String.valueOf(carId)};
        Cursor cursor = database.rawQuery("SELECT * FROM " + MyDatabase.DATABASE_TABLE_NAME + " WHERE " + MyDatabase.ID_COLUMN_NAME + " =? ", args);
        if (cursor.moveToFirst() && cursor != null) {
            int id = cursor.getInt(cursor.getColumnIndex(MyDatabase.ID_COLUMN_NAME));
            String model = cursor.getString(cursor.getColumnIndex(MyDatabase.MODEL_COLUMN_NAME));
            String color = cursor.getString(cursor.getColumnIndex(MyDatabase.COLOR_COLUMN_NAME));
            String description = cursor.getString(cursor.getColumnIndex(MyDatabase.DESCRIPTION_COLUMN_NAME));
            String image = cursor.getString(cursor.getColumnIndex(MyDatabase.IMAGE_COLUMN_NAME));
            double dpl = cursor.getDouble(cursor.getColumnIndex(MyDatabase.DPL_COLUMN_NAME));
            Cars cars = new Cars(id, model, color, description, image, dpl);
            return cars;
        }//end if()
        return null;
    }//end getCar()

    public int getCount() {
        return (int) DatabaseUtils.queryNumEntries(database, MyDatabase.DATABASE_TABLE_NAME);
    }//end getCount()

}//end class
