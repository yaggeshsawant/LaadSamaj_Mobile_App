package com.madsssoft.laadsamaj;

public class PojoCounts {
    public String text;
    public int number;
    public int img;


    public PojoCounts(String text,int number,int img) {
        this.text = text;
        this.number = number;
        this.img = img;

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }



}
