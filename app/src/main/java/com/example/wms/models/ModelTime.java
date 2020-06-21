package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ModelTime {

    @SerializedName("id")
    Integer Id;

    @SerializedName("title")
    String title;

    @SerializedName("date")
    Date date;

    @SerializedName("from")
    Date from;

    @SerializedName("to")
    Date to;

    public Integer getId() {
        return Id;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }
}
