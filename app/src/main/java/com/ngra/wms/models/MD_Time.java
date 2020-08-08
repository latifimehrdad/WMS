package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class MD_Time {

    @SerializedName("id")
    Integer Id;

    @SerializedName("from")
    Date from;

    @SerializedName("to")
    Date to;

    public MD_Time() {
    }

    public MD_Time(Integer id) {
        Id = id;
    }

    public Integer getId() {
        return Id;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }
}
