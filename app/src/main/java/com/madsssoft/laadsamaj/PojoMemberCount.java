package com.madsssoft.laadsamaj;

import com.google.gson.annotations.SerializedName;

public class PojoMemberCount {
    @SerializedName("Male")
    public int male;


    @SerializedName("Female")
    public int female;
    @SerializedName("Family")
    public int family;
    @SerializedName("Unmarried_male")
    public int unmarried_male;
    @SerializedName("Unmarried_FeMale")
    public int unmarried_FeMale;


    public int getMale() {
        return male;
    }

    public void setMale(int male) {
        this.male = male;
    }

    public int getFemale() {
        return female;
    }

    public void setFemale(int female) {
        this.female = female;
    }

    public int getFamily() {
        return family;
    }

    public void setFamily(int family) {
        this.family = family;
    }

    public int getUnmarried_male() {
        return unmarried_male;
    }

    public void setUnmarried_male(int unmarried_male) {
        this.unmarried_male = unmarried_male;
    }

    public int getUnmarried_FeMale() {
        return unmarried_FeMale;
    }

    public void setUnmarried_FeMale(int unmarried_FeMale) {
        this.unmarried_FeMale = unmarried_FeMale;
    }
}
