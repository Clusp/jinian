package com.aoao.jinian.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by asus on 2018/8/29.
 */

public class Been {
    private static Context mContext;
    private static SQLiteDatabase db;
    private static Been sBeen;


    public static Been getBeen(Context context) {
        if (sBeen == null) {
            sBeen = new Been(context);
        }
        return sBeen;
    }

    private Been(Context context) {
        mContext = context.getApplicationContext();
        db = new MyDatabaseHelper(mContext).getWritableDatabase();
    }

    public List<Bean> getmBeen(String table) {
        List<Bean> been= new ArrayList<>();
        Cursor cursor = db.query(table, null, null, null, null, null, null, null);
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String uuid = cursor.getString(cursor.getColumnIndex("uuid"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                Long time = cursor.getLong(cursor.getColumnIndex("time"));
                Bean bean = new Bean(UUID.fromString(uuid));
                bean.setContent(content);
                bean.setDate(new Date(time));
                been.add(bean);
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return been;
    }



    public static Bean getBean(String table,UUID id) {
        for (Bean bean :sBeen.getmBeen(table)) {
            if (bean.getId().equals(id)) {
                return  bean;
            }
        }
        return null;
    }

    public StringBuilder getdiary() throws UnsupportedEncodingException {
        StringBuilder stringBuilder=new StringBuilder();
        String sum_dbString="select time,content from "+MyDatabaseHelper.tableName2;
        Cursor cur=db.rawQuery(sum_dbString, null);
        try{
            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                byte[] val = cur.getBlob(cur.getColumnIndex("content"));
                String content = new String(val, "utf-8");
                content = content.trim();
                Long time = cur.getLong(cur.getColumnIndex("time"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = sdf.format(time);
                stringBuilder.append("纪年"+String.valueOf(dateString) + "\n" + content+"\n\r");
                cur.moveToNext();
            }
        }finally {
            cur.close();
        }
        return stringBuilder;

    }


    private static ContentValues getContentValues(Bean bean) {
        ContentValues values = new ContentValues();
        values.put("uuid",bean.getId().toString());
        values.put("content",bean.getContent());
        values.put("time", bean.getDate().getTime());
        return values;
    }
    public static void addData(String table,Bean bean) {
        ContentValues values = getContentValues(bean);
        db.insert(table,null,values);
    }

    public static void upData(String table,Bean bean) {
        String uuid = bean.getId().toString();
        ContentValues values = getContentValues(bean);
        db.update(table, values,  "uuid =?", new String[]{uuid});
    }


    public static void deleteData(String table,Bean bean) {
        String id = bean.getId().toString();
        db.delete(table,  "uuid = ?", new String[]{id});
    }
    public static void deleteDataAll(String table) {
        db.execSQL("delete from "+table);
    }

}
