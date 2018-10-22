package com.aoao.jinian.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by asus on 2018/9/5.
 */

public class BillBeen {
    private static Context mContext;
    private static SQLiteDatabase db;
    private static BillBeen sBillBeen;


    public static BillBeen getBeen(Context context) {
        if (sBillBeen == null) {
            sBillBeen = new BillBeen(context);
        }
        return sBillBeen;
    }

    private BillBeen(Context context) {
        mContext = context.getApplicationContext();
        db = new MyDatabaseHelper(mContext).getWritableDatabase();
    }

    public List<BillBean> getmBiilBeen() {
        List<BillBean> been= new ArrayList<>();
        Cursor cursor = db.query(MyDatabaseHelper.tableName3, null, null, null, null, null, null, null);
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String uuid = cursor.getString(cursor.getColumnIndex("uuid"));
                Long time = cursor.getLong(cursor.getColumnIndex("time"));
                int expenses = cursor.getInt(cursor.getColumnIndex("expenses"));
                int revenue = cursor.getInt(cursor.getColumnIndex("revenue"));
                int profit = cursor.getInt(cursor.getColumnIndex("profit"));
                BillBean billBean = new BillBean(UUID.fromString(uuid));
                billBean.setTime(new Date(time));
                billBean.setExpenses(expenses);
                billBean.setRevenue(revenue);
                billBean.setProfit(profit);
                been.add(billBean);
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return been;
    }



    private static ContentValues getContentValues(BillBean bean) {
        ContentValues values = new ContentValues();
        values.put("uuid",bean.getId().toString());
        values.put("expenses",bean.getExpenses());
        values.put("time", bean.getTime().getTime());
        values.put("revenue", bean.getRevenue());
        values.put("profit", bean.getProfit());
        return values;
    }
    public static void addData(BillBean bean) {
        ContentValues values = getContentValues(bean);
        db.insert(MyDatabaseHelper.tableName3,null,values);
    }

    public  int count(String string) {
            int sum=0;
            String sum_dbString="select sum("+string+") from "+MyDatabaseHelper.tableName3;
            Cursor cur=db.rawQuery(sum_dbString, null);
            if (cur!=null)
            {
                if (cur.moveToFirst())
                {
                    do
                    {
                        sum=cur.getInt(0);
                    } while (cur.moveToNext());
                }
            }
            return sum;

    }


    public static void deleteData(BillBean bean) {
        String id = bean.getId().toString();
        db.delete(MyDatabaseHelper.tableName3,  "uuid = ?", new String[]{id});
    }
    public static void deleteDataAll() {
        db.execSQL("delete from "+MyDatabaseHelper.tableName3);
    }
}
