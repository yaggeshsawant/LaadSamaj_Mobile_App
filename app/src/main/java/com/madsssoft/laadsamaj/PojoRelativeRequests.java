package com.madsssoft.laadsamaj;

import com.google.gson.annotations.SerializedName;

public class PojoRelativeRequests {
    public String id;
    public String member_id_fk;
    public String relative_id_fk;
    public String relation_with_relative;
    public String relative_relation_you;
    @SerializedName("NAME")
    public String nAME;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMember_id_fk() {
        return member_id_fk;
    }

    public void setMember_id_fk(String member_id_fk) {
        this.member_id_fk = member_id_fk;
    }

    public String getRelative_id_fk() {
        return relative_id_fk;
    }

    public void setRelative_id_fk(String relative_id_fk) {
        this.relative_id_fk = relative_id_fk;
    }

    public String getRelation_with_relative() {
        return relation_with_relative;
    }

    public void setRelation_with_relative(String relation_with_relative) {
        this.relation_with_relative = relation_with_relative;
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
