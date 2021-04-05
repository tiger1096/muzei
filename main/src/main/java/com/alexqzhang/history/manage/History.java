package com.alexqzhang.history.manage;

import java.sql.Timestamp;

public class History {
    private int id;
    private String name;
    public int resId;
    private String picPath;
    private Timestamp timestamp;

    public History(int id, String name, int resId, Timestamp tm) {
        this.id = id;
        this.name = name;
        this.resId = resId;
        this.timestamp = tm;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
