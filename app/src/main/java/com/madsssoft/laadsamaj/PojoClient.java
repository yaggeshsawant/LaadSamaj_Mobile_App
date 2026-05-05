package com.madsssoft.laadsamaj;

import com.google.gson.annotations.SerializedName;

public class PojoClient {
    public int id;
    @SerializedName("NAME")
    public String nAME;
    public String post;
    @SerializedName("STATUS")
    public String sTATUS;
    public String image;
    public int samiti_id_fk;
    public String current_in_date;
    public String samiti_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getnAME() {
        return nAME;
    }

    public void setnAME(String nAME) {
        this.nAME = nAME;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getsTATUS() {
        return sTATUS;
    }

    public void setsTATUS(String sTATUS) {
        this.sTATUS = sTATUS;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getSamiti_id_fk() {
        return samiti_id_fk;
    }

    public void setSamiti_id_fk(int samiti_id_fk) {
        this.samiti_id_fk = samiti_id_fk;
    }

    public String getCurrent_in_date() {
        return current_in_date;
    }

    public void setCurrent_in_date(String current_in_date) {
        this.current_in_date = current_in_date;
    }

    public String getSamiti_name() {
        return samiti_name;
    }

    public void setSamiti_name(String samiti_name) {
        this.samiti_name = samiti_name;
    }
}
