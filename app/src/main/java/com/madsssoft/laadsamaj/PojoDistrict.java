package com.madsssoft.laadsamaj;

import com.google.gson.annotations.SerializedName;

public class PojoDistrict {
    public int id;
    @SerializedName("NAME")
    public String nAME;
    @SerializedName("STATUS")
    public String sTATUS;

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

    public String getsTATUS() {
        return sTATUS;
    }

    public void setsTATUS(String sTATUS) {
        this.sTATUS = sTATUS;
    }
}
