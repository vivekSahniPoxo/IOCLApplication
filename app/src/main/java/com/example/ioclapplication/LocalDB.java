package com.example.ioclapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LocalDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DeviceDetails";
    private static final String TABLE_Manfacture = "Device";
    private static final String manufactureColumn = "DeviceName";
    public static final String ID = "ID";

    public LocalDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_Manfacture + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + manufactureColumn + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Manfacture);
        // Create tables again
        onCreate(db);
    }

    public void addContact(DatamodelLocal dataModelClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(manufactureColumn, dataModelClass.getDeviceName()); // Contact Name

        // Inserting Row
        db.insert(TABLE_Manfacture, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public List<DatamodelLocal> getAllContacts() {
        List<DatamodelLocal> contactList = new ArrayList<DatamodelLocal>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_Manfacture;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DatamodelLocal dataModelClass = new DatamodelLocal();
                dataModelClass.setDeviceName(cursor.getString(1));

                // Adding contact to list
                contactList.add(dataModelClass);
            } while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }

    public boolean isTableExists(){
        SQLiteDatabase db=this.getWritableDatabase();
        String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='"+TABLE_Manfacture+"'";
        Cursor mCursor = db.rawQuery(sql, null);
        if (mCursor.getCount() > 0) {
            return true;
        }
        mCursor.close();
        return false;
    }
    //Method for Update
    public int updateContact(DatamodelLocal dataModelClass, String valueID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(manufactureColumn, dataModelClass.getDeviceName());


        // updating row
        return db.update(TABLE_Manfacture, values, ID + " = ?",
                new String[]{String.valueOf(valueID)});
    }
}
