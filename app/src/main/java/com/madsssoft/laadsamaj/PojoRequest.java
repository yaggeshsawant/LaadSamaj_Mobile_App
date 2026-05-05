package com.madsssoft.laadsamaj;

public class PojoRequest {
    public int img;

    public String name;
    public String relation;

    public PojoRequest(int img, String name, String relation) {
        this.img = img;
        this.name = name;
        this.relation = relation;
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

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
