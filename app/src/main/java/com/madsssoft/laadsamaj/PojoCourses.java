package com.madsssoft.laadsamaj;

import com.google.gson.annotations.SerializedName;

public class PojoCourses {

    public int id;
    public String name;
    public String duration;
    @SerializedName("STATUS")

    public String sTATUS;

    public String image_formate;
    @SerializedName("CURRENT_DATE")
    public String cURRENT_DATE;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getsTATUS() {
        return sTATUS;
    }

    public void setsTATUS(String sTATUS) {
        this.sTATUS = sTATUS;
    }

    public String getImage_formate() {
        return image_formate;
    }

    public void setImage_formate(String image_formate) {
        this.image_formate = image_formate;
    }

    public String getcURRENT_DATE() {
        return cURRENT_DATE;
    }

    public void setcURRENT_DATE(String cURRENT_DATE) {
        this.cURRENT_DATE = cURRENT_DATE;
    }
}
