package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

public class MD_ChartReport {

    @SerializedName("monthName")
    String monthName;

    @SerializedName("month")
    Integer month;

    @SerializedName("scorePoint")
    double scorePoint;

    @SerializedName("year")
    Integer year;

    public MD_ChartReport(String monthName, Integer month, double scorePoint, Integer year) {
        this.monthName = monthName;
        this.month = month;
        this.scorePoint = scorePoint;
        this.year = year;
    }


    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public double getScorePoint() {
        return scorePoint;
    }

    public void setScorePoint(double scorePoint) {
        this.scorePoint = scorePoint;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
