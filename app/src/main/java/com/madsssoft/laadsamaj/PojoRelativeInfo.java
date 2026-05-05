package com.madsssoft.laadsamaj;

import com.google.gson.annotations.SerializedName;

public class PojoRelativeInfo {
    public String id;
    public int member_id_fk;
    public int relative_id_fk;
    public String relation_with_relative;
    public String relative_relation_you;
    @SerializedName("NAME")
    public String nAME;
    public String image_formate;

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String father;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMember_id_fk() {
        return member_id_fk;
    }

    public void setMember_id_fk(int member_id_fk) {
        this.member_id_fk = member_id_fk;
    }

    public int getRelative_id_fk() {
        return relative_id_fk;
    }

    public void setRelative_id_fk(int relative_id_fk) {
        this.relative_id_fk = relative_id_fk;
    }

    public String getRelation_with_relative() {
        return relation_with_relative;
    }

    public void setRelation_with_relative(String relation_with_relative) {
        this.relation_with_relative = relation_with_relative;
    }

    public String getImage_formate() {
        return image_formate;
    }

    public void setImage_formate(String image_formate) {
        this.image_formate = image_formate;
    }

    public String getRelative_relation_you() {
        return relative_relation_you;
    }

    public void setRelative_relation_you(String relative_relation_you) {
        this.relative_relation_you = relative_relation_you;
    }

    public String getnAME() {
        return nAME;
    }

    public void setnAME(String nAME) {
        this.nAME = nAME;
    }




}
