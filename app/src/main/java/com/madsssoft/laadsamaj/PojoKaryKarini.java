package com.madsssoft.laadsamaj;

public class PojoKaryKarini {
    public String  name;
    public int img;
    public String designation;
    public String address;

    public PojoKaryKarini(String s, int img, String designation, String address) {
        this.name = name;
        this.img = img;
        this.designation = designation;
        this.address = address;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
