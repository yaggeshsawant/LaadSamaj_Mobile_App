package com.madsssoft.laadsamaj;

public class PojoOnlineDirectory {

    private int onlineDirectoryImg;
    private String onlineDirectoryName;


    public PojoOnlineDirectory(int onlineDirectoryImg, String onlineDirectoryName) {
        this.onlineDirectoryImg = onlineDirectoryImg;
        this.onlineDirectoryName = onlineDirectoryName;

    }

    public int getOnlineDirectoryImg() {
        return onlineDirectoryImg;
    }

    public void setOnlineDirectoryImg(int onlineDirectoryImg) {
        this.onlineDirectoryImg = onlineDirectoryImg;
    }

    public String getOnlineDirectoryName() {
        return onlineDirectoryName;
    }

    public void setOnlineDirectoryName(String onlineDirectoryName) {
        this.onlineDirectoryName = onlineDirectoryName;
    }


}
