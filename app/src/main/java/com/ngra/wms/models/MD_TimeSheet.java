package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MD_TimeSheet {

    @SerializedName("date")
    Date date;

    @SerializedName("times")
    List<MD_Time> times;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<MD_Time> getTimes() {
        return times;
    }

    public void setTimes(List<MD_Time> times) {
        this.times = times;
    }
}
