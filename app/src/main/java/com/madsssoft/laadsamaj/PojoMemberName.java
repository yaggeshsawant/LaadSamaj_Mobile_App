package com.madsssoft.laadsamaj;

import com.google.gson.annotations.SerializedName;

public class PojoMemberName {
    @SerializedName("id")
    public int id;

    @SerializedName("NAME")
    public String nAME;



    public int getId() {
        return id;
    }

    public String getnAME() {
        return nAME;
    }
}
