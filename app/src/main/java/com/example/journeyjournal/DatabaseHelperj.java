package com.example.journeyjournal;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.example.journeyjournal.helper.Jinfo;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DatabaseHelperj extends SQLiteOpenHelper {

    static String name = "jdata";
    static int version = 1;

    String CreateTableSql = "CREATE TABLE if not EXISTS `detailj` (\n" +
            "\t'id'\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t'title'\tTEXT,\n" +
            "\t'details'\tTEXT,\n" +
            "\t'image'\tBLOB, \n"+
            "\t'date'\tTEXT, \n"+
            "\t'address'\tTEXT\n" +
            ")";


    public DatabaseHelperj(@Nullable Context context) {
        super(context, name, null, version);
        getWritableDatabase().execSQL(CreateTableSql);


    }

    public void insertDetail(ContentValues contentValues) {
        getWritableDatabase().insert("detailj", "", contentValues);
    }

    public void updateDetail(String id, ContentValues contentValues) {
        getWritableDatabase().update("detailj", contentValues, "id=" + id, null);
//        getWritableDatabase().update("user",contentValues,"id=?",new String[]{id});
    }

    public void deleteDetail(String id) {
        getWritableDatabase().delete("detailj", "id=?", new String[]{id});
    }


    @SuppressLint("Range")
    public ArrayList<Jinfo> getDetailList() {
        String sql = "Select * from detailj";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        ArrayList<Jinfo> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            Jinfo info = new Jinfo();
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.title = cursor.getString(cursor.getColumnIndex("title"));
            info.details = cursor.getString(cursor.getColumnIndex("details"));
            info.image = cursor.getBlob(cursor.getColumnIndex("image"));
            info.address = cursor.getString(cursor.getColumnIndex("address"));
            info.date = cursor.getString(cursor.getColumnIndex("date"));
            list.add(info);

        }
        cursor.close();

        return list;
    }

    @SuppressLint("Range")
    public Jinfo getDetailInfo(String id) {
        String sql = "Select * from detailj where id=" + id;
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        Jinfo info = new Jinfo();
        while (cursor.moveToNext()) {
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.title = cursor.getString(cursor.getColumnIndex("title"));
            info.details = cursor.getString(cursor.getColumnIndex("details"));
            info.image = cursor.getBlob(cursor.getColumnIndex("image"));
            info.address = cursor.getString(cursor.getColumnIndex("address"));
            info.date = cursor.getString(cursor.getColumnIndex("date"));
        }
        cursor.close();


        return info;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public static byte[] getBlob(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bArray = bos.toByteArray();
        return bArray;
    }

    public static Bitmap getBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
}