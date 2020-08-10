package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

public class MD_ScoreReport {

    @SerializedName("maxPoint")
    double maxPoint;

    @SerializedName("averagePoint")
    double averagePoint;

    public MD_ScoreReport(double maxPoint, double averagePoint) {
        this.maxPoint = maxPoint;
        this.averagePoint = averagePoint;
    }

    public double getMaxPoint() {
        return maxPoint;
    }

    public void setMaxPoint(double maxPoint) {
        this.maxPoint = maxPoint;
    }

    public double getAveragePoint() {
        return averagePoint;
    }

    public void setAveragePoint(double averagePoint) {
        this.averagePoint = averagePoint;
    }
}
