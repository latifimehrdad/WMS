package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

public class MD_GameReport {

    @SerializedName("currentUserMaxPoint")
    double currentUserMaxPoint;

    @SerializedName("lastMonthMaxPoint")
    double lastMonthMaxPoint;

    @SerializedName("currentUserName")
    String currentUserName;

    @SerializedName("lastMonthMaxPointName")
    String lastMonthMaxPointName;


    public MD_GameReport(double currentUserMaxPoint, double lastMonthMaxPoint, String currentUserName, String lastMonthMaxPointName) {
        this.currentUserMaxPoint = currentUserMaxPoint;
        this.lastMonthMaxPoint = lastMonthMaxPoint;
        this.currentUserName = currentUserName;
        this.lastMonthMaxPointName = lastMonthMaxPointName;
    }

    public double getCurrentUserMaxPoint() {
        return currentUserMaxPoint;
    }

    public void setCurrentUserMaxPoint(double currentUserMaxPoint) {
        this.currentUserMaxPoint = currentUserMaxPoint;
    }

    public double getLastMonthMaxPoint() {
        return lastMonthMaxPoint;
    }

    public void setLastMonthMaxPoint(double lastMonthMaxPoint) {
        this.lastMonthMaxPoint = lastMonthMaxPoint;
    }

    public String getCurrentUserName() {
        return currentUserName;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    public String getLastMonthMaxPointName() {
        return lastMonthMaxPointName;
    }

    public void setLastMonthMaxPointName(String lastMonthMaxPointName) {
        this.lastMonthMaxPointName = lastMonthMaxPointName;
    }
}
