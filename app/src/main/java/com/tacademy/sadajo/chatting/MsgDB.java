package com.tacademy.sadajo.chatting;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by EUNZY on 2016. 12. 9..
 */

public class MsgDB extends SQLiteOpenHelper{

    public MsgDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE MESSAGE (id INTEGER PRIMARY KEY AUTOINCREMENT, message TEXT);");



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {




    }
     public void insert(String message) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO MESSAGE VALUES(null, '" + message + "');");
        db.close();
    }

    public String getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";


        Cursor cursor = db.rawQuery("SELECT * FROM MESSAGE", null);
        while(cursor.moveToNext()) {
            result +=
              cursor.getString(1);
        }

        return result;
    }



}
