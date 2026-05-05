package com.madsssoft.laadsamaj;

import com.google.gson.annotations.SerializedName;

public class PojoSlider {



    public int id;
    public String name;
    public String link;
    @SerializedName("CURRENT_DATE")
    public String cURRENT_DATE;
    public String image_formate;
    @SerializedName("STATUS")
    public String sTATUS;

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getcURRENT_DATE() {
        return cURRENT_DATE;
    }

    public void setcURRENT_DATE(String cURRENT_DATE) {
        this.cURRENT_DATE = cURRENT_DATE;
    }

    public String getImage_formate() {
        return image_formate;
    }

    public void setImage_formate(String image_formate) {
        this.image_formate = image_formate;
    }

    public String getsTATUS() {
        return sTATUS;
    }

    public void setsTATUS(String sTATUS) {
        this.sTATUS = sTATUS;
    }
}
