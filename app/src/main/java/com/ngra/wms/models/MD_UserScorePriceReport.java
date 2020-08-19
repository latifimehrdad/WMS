package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

public class MD_UserScorePriceReport {

    @SerializedName("totalWeights")
    double totalWeights;

    @SerializedName("totalPrice")
    double totalPrice;

    @SerializedName("totalPoint")
    double totalPoint;

    public MD_UserScorePriceReport(double totalWeights, double totalPrice, double totalPoint) {
        this.totalWeights = totalWeights;
        this.totalPrice = totalPrice;
        this.totalPoint = totalPoint;
    }

    public double getTotalWeights() {
        return totalWeights;
    }

    public void setTotalWeights(double totalWeights) {
        this.totalWeights = totalWeights;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(double totalPoint) {
        this.totalPoint = totalPoint;
    }
}
