package com.scanez.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.scanez.model.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aspl on 28/3/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "code_datas.db";
    public static final String TABLE_NAME = "code_Tables";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NOTE";
    public static final String COL_3 = "TYPE";
    public static final String COL_4 = "DATETIME";
    public static final String COL_5 = "IMAGEPATH";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,NOTE TEXT,TYPE TEXT,DATETIME TEXT,IMAGEPATH TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public void addProduct(Data product){
        ContentValues values = new ContentValues();
        values.put(COL_3, product.getType());
        values.put(COL_2, product.getNote());
        values.put(COL_4, product.getDate_time());
        values.put(COL_5,product.getImagePath());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
    }


    public boolean insertData(String NOTE, String TYPE, String DATETIME) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, NOTE);
        contentValues.put(COL_3, TYPE);
        contentValues.put(COL_4, DATETIME);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public List<Data> listcodes(){
        String sql = "select * from " + TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        List<Data> storeProducts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String type= cursor.getString(2);
                String date_time= cursor.getString(3);
                String image_path= cursor.getString(4);
                storeProducts.add(new Data(id, name,type,date_time,image_path));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeProducts;
    }

    public Data findProduct(String NOTE){
        //String query = "Select * FROM "	+ TABLE_NAME + " WHERE " + COL_2 + " = " + NOTE;

        String query = "select * from " + TABLE_NAME + " where NOTE=?";

        SQLiteDatabase db = getWritableDatabase();
        Data mProduct = null;
        if (checkIfExist(NOTE)) {

            Cursor cursor = db.rawQuery(query, new String[] {NOTE});

            if (cursor.moveToFirst()) {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COL_1)));
                String names = cursor.getString(cursor.getColumnIndex(COL_2));
                String type = cursor.getString(cursor.getColumnIndex(COL_3));
                String date_time = cursor.getString(cursor.getColumnIndex(COL_4));
                String image_path = cursor.getString(cursor.getColumnIndex(COL_5));

                mProduct = new Data(id, names, type, date_time, image_path);
            }
            cursor.close();
        }else {

        }
        return mProduct;
    }

    boolean checkIfExist(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { COL_1,
                        COL_2, COL_3,COL_4 }, COL_2 + "=?",
                new String[] { name }, null, null, null, null);


        if (cursor.getCount() > 0)
            return true;

        else
            return false;

    }

    public boolean  isMasterEmpty() {

        boolean flag;
        String quString = "select exists(select * from " + TABLE_NAME  + ");";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(quString, null);
        cursor.moveToFirst();
        int count= cursor.getInt(0);
        if (count ==0) {
            flag =  false;
        } else {
            flag = true;
        }
        cursor.close();
        db.close();

        return flag;
    }


    public boolean updateData(String id, String NOTE, String TYPE, String DATETIME) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, NOTE);
        contentValues.put(COL_3, TYPE);
        contentValues.put(COL_4, DATETIME);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }
    public void deleteProduct(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_1	+ "	= ?", new String[] { String.valueOf(id)});
    }
}