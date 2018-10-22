package com.aoao.jinian.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by asus on 2018/8/29.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper{
    private Context mContext;
    public static final String tableName = "jinian_note";
    public static final String tableName2 = "jinian_diary";
    public static final String tableName3 = "jinian_finance";
    private static final int VERSION=1;
    private static final String DATABASE_NAME = "jinianbase.db";


    public MyDatabaseHelper(Context context) {
        super(context,DATABASE_NAME, null, VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists "+tableName+" ("
        +"id integer primary key autoincrement, "
        +"uuid, "
        +"content, "
        +"time)");
        db.execSQL("create table if not exists "+tableName2+" ("
        +"id integer primary key autoincrement, "
        +"uuid, "
        +"content, "
        +"time)");
        db.execSQL("create table if not exists "+tableName3+" ("
                +"id integer primary key autoincrement, "
                +"uuid, "
                +"expenses, "
                +"revenue, "
                +"profit, "
                +"time)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
