package com.alexqzhang.discover.manage;

public class DiscoverPic {
    private int picRes;
    private String picPath;

    public DiscoverPic(int picRes, String picPath) {
        this.picRes = picRes;
        this.picPath = picPath;
    }

    public int getPicRes() {
        return picRes;
    }

    public void setPicRes(int picRes) {
        this.picRes = picRes;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}
