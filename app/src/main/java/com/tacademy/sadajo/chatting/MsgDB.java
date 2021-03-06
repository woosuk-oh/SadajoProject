package com.tacademy.sadajo.chatting;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by EUNZY on 2016. 12. 9..
 */

public class MsgDB extends SQLiteOpenHelper {

    public MsgDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE MESSAGESTORE (id INTEGER PRIMARY KEY AUTOINCREMENT, message TEXT, roomNum INTEGER, user INTEGER);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }

    public void insert(int roomNum, String message, int user) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO MESSAGESTORE VALUES(null, '" + message + "','" + roomNum + "','" + user + "');");
        db.close();
    }

    public ArrayList<MsgDBEntity> getResult(int roomNum) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<MsgDBEntity> msgDBEntity = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM MESSAGESTORE WHERE roomNum = " + roomNum + "", null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                MsgDBEntity msgDBEntity1 = new MsgDBEntity();
                msgDBEntity1.id = cursor.getInt(0);
                msgDBEntity1.message = cursor.getString(1);
                msgDBEntity1.roomNum = cursor.getInt(2);
                msgDBEntity1.user = cursor.getInt(3);
                msgDBEntity.add(msgDBEntity1);

            }
        }

        return msgDBEntity;
    }


}
