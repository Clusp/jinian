package com.aoao.jinian.database;


import java.util.Date;
import java.util.UUID;

/**
 * Created by asus on 2018/8/29.
 */

public class Bean {
    private UUID mID;
    private String title;
    private String content;
    private Date mDate;

    public Bean() {
        this(UUID.randomUUID());
    }
    public Bean(UUID uuid) {
        mID = uuid;
        mDate = new Date();
    }


    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Date getDate() {
        return mDate;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getId() {
        return mID;
    }


}
