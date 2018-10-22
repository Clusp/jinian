package com.aoao.jinian.database;

import java.util.Date;
import java.util.UUID;

/**
 * Created by asus on 2018/9/5.
 */

public class BillBean {
    private UUID mUUID;
    private Date mDate;
    private int expenses;
    private int revenue;
    private int profit;


    public BillBean() {
        this(UUID.randomUUID());
    }
    public BillBean(UUID uuid) {
        mUUID = uuid;
        mDate = new Date();
    }
    public Date getTime() {
        return mDate;
    }

    public void setTime(Date time) {
        this.mDate = time;
    }

    public UUID getId() {
        return mUUID;
    }

    public int getExpenses() {
        return expenses;
    }

    public void setExpenses(int expenses) {
        this.expenses = expenses;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }


}
