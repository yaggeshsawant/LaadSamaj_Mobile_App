package com.madsssoft.laadsamaj;

import com.google.gson.annotations.SerializedName;

public class PojoRegistrationInfo {
    public int registration_id;
    public int category_id_fk;
    public String owner_name;
    public String company_name;
    public String address;
    public String mobile;
    public String regdate;
    public String website_link;
    public String r_status;
    public String product;
    public String image1;
    public String image2;
    public String whatsapp_no;
    public String email;
    public String facebook_link;
    public String instagram_link;
    public String youtube_link;
    public String about;
    @SerializedName("NAME")
    public String nAME;
    @SerializedName("PASSWORD")
    public String pASSWORD;
    public String live_location;
    public int member_id_fk;


    public int getRegistration_id() {
        return registration_id;
    }

    public void setRegistration_id(int registration_id) {
        this.registration_id = registration_id;
    }

    public int getCategory_id_fk() {
        return category_id_fk;
    }

    public void setCategory_id_fk(int category_id_fk) {
        this.category_id_fk = category_id_fk;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getWebsite_link() {
        return website_link;
    }

    public void setWebsite_link(String website_link) {
        this.website_link = website_link;
    }

    public String getR_status() {
        return r_status;
    }

    public void setR_status(String r_status) {
        this.r_status = r_status;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getWhatsapp_no() {
        return whatsapp_no;
    }

    public void setWhatsapp_no(String whatsapp_no) {
        this.whatsapp_no = whatsapp_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebook_link() {
        return facebook_link;
    }

    public void setFacebook_link(String facebook_link) {
        this.facebook_link = facebook_link;
    }

    public String getInstagram_link() {
        return instagram_link;
    }

    public void setInstagram_link(String instagram_link) {
        this.instagram_link = instagram_link;
    }

    public String getYoutube_link() {
        return youtube_link;
    }

    public void setYoutube_link(String youtube_link) {
        this.youtube_link = youtube_link;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getnAME() {
        return nAME;
    }

    public void setnAME(String nAME) {
        this.nAME = nAME;
    }

    public String getpASSWORD() {
        return pASSWORD;
    }

    public void setpASSWORD(String pASSWORD) {
        this.pASSWORD = pASSWORD;
    }

    public String getLive_location() {
        return live_location;
    }

    public void setLive_location(String live_location) {
        this.live_location = live_location;
    }

    public int getMember_id_fk() {
        return member_id_fk;
    }

    public void setMember_id_fk(int member_id_fk) {
        this.member_id_fk = member_id_fk;
    }
}