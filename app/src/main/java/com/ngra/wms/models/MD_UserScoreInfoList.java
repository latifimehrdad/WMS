package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MD_UserScoreInfoList {

    @SerializedName("monthName")
    String monthName;

    @SerializedName("month")
    int month;

    @SerializedName("year")
    int year;

    @SerializedName("items")
    List<MD_GiveScoreItem> items;

    public MD_UserScoreInfoList(String monthName, int month, int year, List<MD_GiveScoreItem> items) {
        this.monthName = monthName;
        this.month = month;
        this.year = year;
        this.items = items;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<MD_GiveScoreItem> getItems() {
        return items;
    }

    public void setItems(List<MD_GiveScoreItem> items) {
        this.items = items;
    }
}
