package com.example.journeyjournal;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.example.journeyjournal.helper.Userinfo;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    static String name = "AMDdb";
    static int version = 1;

    String CreateTableSql = "CREATE TABLE if not exists `user` (\n" +
            "\t`id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`username`\tTEXT,\n" +
            "\t`password`\tTEXT,\n" +
            "\t`email`\tTEXT,\n" +
            "\t`address`\tTEXT,\n" +
            "\t`phone`\tTEXT,\n" +
            "\t`gender`\tTEXT\n" +
            ")";


    public DatabaseHelper(@Nullable Context context) {
        super(context, name, null, version);
        getWritableDatabase().execSQL(CreateTableSql);


    }

    public void insertUser(ContentValues contentValues) {
        getWritableDatabase().insert("user", "", contentValues);
    }

    public void updateUser(String id, ContentValues contentValues) {
        getWritableDatabase().update("user", contentValues, "id=" + id, null);
//        getWritableDatabase().update("user",contentValues,"id=?",new String[]{id});
    }

    public void deleteUser(String id) {
//        getWritableDatabase().delete("user","id="+id,null);
        getWritableDatabase().delete("user", "id=?", new String[]{id});
    }

    public boolean isLoginSuccessful(String username, String password) {
        String sql = "Select count(*) from user where username='" + username + "' and password='" + password + "'";
        SQLiteStatement statement = getReadableDatabase().compileStatement(sql);
        long l = statement.simpleQueryForLong();
        if (l == 1) {
            return true;
        } else
            return false;
    }


    @SuppressLint("Range")
    public ArrayList<Userinfo> getUserList() {
        String sql = "Select * from user";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        ArrayList<Userinfo> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            Userinfo info = new Userinfo();
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.username = cursor.getString(cursor.getColumnIndex("username"));
            info.password = cursor.getString(cursor.getColumnIndex("password"));
            info.email = cursor.getString(cursor.getColumnIndex("email"));
            info.address = cursor.getString(cursor.getColumnIndex("address"));
            info.phone = cursor.getString(cursor.getColumnIndex("phone"));
            info.gender = cursor.getString(cursor.getColumnIndex("gender"));
            list.add(info);

        }
        cursor.close();


        return list;
    }

    @SuppressLint("Range")
    public Userinfo getUserinfo(String id) {
        String sql = "Select * from user where id=" + id;
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        Userinfo info = new Userinfo();
        while (cursor.moveToNext()) {
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.username = cursor.getString(cursor.getColumnIndex("username"));
            info.password = cursor.getString(cursor.getColumnIndex("password"));
            info.email = cursor.getString(cursor.getColumnIndex("email"));
            info.address = cursor.getString(cursor.getColumnIndex("address"));
            info.phone = cursor.getString(cursor.getColumnIndex("phone"));
            info.gender = cursor.getString(cursor.getColumnIndex("gender"));

        }
        cursor.close();


        return info;
    }

    public Boolean updatepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);
        long result = MyDB.update("user",contentValues,"username = ?", new String[] {username});
        if(result == -1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username){

        String sql = "Select count(*) from user where username='" + username + "'";
        SQLiteStatement statement = getReadableDatabase().compileStatement(sql);
        long l = statement.simpleQueryForLong();
        if (l == 1) {
            return true;
        } else
            return false;

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("alter table user add `image` blob");
    }

}





