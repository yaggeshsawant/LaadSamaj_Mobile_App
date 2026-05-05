package com.madsssoft.laadsamaj;

import com.google.gson.annotations.SerializedName;

public class PojoSamaj {
    public int id;
    @SerializedName("NAME")
    public String nAME;
    public String mobile_no;
    public String address;
    public String email;
    public String image_formate;
    public String current_in_date;
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

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage_formate() {
        return image_formate;
    }

    public void setImage_formate(String image_formate) {
        this.image_formate = image_formate;
    }

    public String getCurrent_in_date() {
        return current_in_date;
    }

    public void setCurrent_in_date(String current_in_date) {
        this.current_in_date = current_in_date;
    }

    public String getsTATUS() {
        return sTATUS;
    }

    public void setsTATUS(String sTATUS) {
        this.sTATUS = sTATUS;
    }
}
