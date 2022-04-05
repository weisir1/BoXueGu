package com.example.demo5.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION=1;
    public static String DB_NAME="bxg.db";
    public static final String U_USERINFO="userinfo";//个人资料
    public SQLiteHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建个人信息
        db.execSQL("create table if not exists" + U_USERINFO + "("
                + "_id Integer primary key autoincrement,"
                + "userName varchar,"
                + "nickName varchar,"
                + "sex varchar,"
                + "signature varchar" + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists"+U_USERINFO);
        onCreate(db);
    }
}
