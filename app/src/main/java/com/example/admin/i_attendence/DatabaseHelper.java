package com.example.admin.i_attendence;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper  extends SQLiteOpenHelper{

    public DatabaseHelper(Context context) {
        super(context, "students",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table students (id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL,enrollment TEXT NOT NULL,password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTs students");
        onCreate(db);

    }
    public boolean insert(String device_id,String password,String description)
    {
        Log.e("target","aaa"+device_id+password);

        SQLiteDatabase db=this.getWritableDatabase();
        Log.e("target","aaa2"+device_id+password);
        ContentValues contentValues=new ContentValues();
        Log.e("target","aaa3"+device_id+password);
        contentValues.put("device_id",device_id);
        Log.e("target","aaa4"+device_id+password);
        contentValues.put("password",password);
        contentValues.put("description",description);
        Log.e("target","aaa");
        long  res= db.insert("allvehicle",null,contentValues);
        Log.e("target","aaa5"+device_id+password+"  "+res);
        if(res==-1)
            return true;
        else
            return false;
    }
    public boolean isexist()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name='students'",null);
        if(cursor.getCount()>0)
        {
            return true;
        }
        else
            return false;


    }
    public Cursor getalldata()
    {

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from students",null);

        return res;
    }

    public Integer deletebike(int id ){
        SQLiteDatabase db=this.getWritableDatabase();
        Log.e("target","delete");
        return db.delete("students","id =?",new String[]{String.valueOf(id)});

    }





}
