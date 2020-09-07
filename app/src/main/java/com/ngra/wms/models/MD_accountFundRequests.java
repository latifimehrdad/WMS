package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class MD_accountFundRequests {

    @SerializedName("id")
    Integer id;

    @SerializedName("credit")
    double credit;

    @SerializedName("note")
    String note;

    @SerializedName("state")
    Byte state;

    @SerializedName("createDate")
    Date date;


    public MD_accountFundRequests(Integer id, double credit, String note, Byte state, Date date) {
        this.id = id;
        this.credit = credit;
        this.note = note;
        this.state = state;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
