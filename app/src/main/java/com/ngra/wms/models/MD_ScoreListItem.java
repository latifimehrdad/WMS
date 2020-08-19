package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

public class MD_ScoreListItem {

    @SerializedName("waste")
    MD_ItemWaste waste;

    @SerializedName("amount")
    double amount;

    @SerializedName("value")
    double value;


    @SerializedName("wastePrice")
    MD_wastePrice md_wastePrice;


    public MD_ScoreListItem(MD_ItemWaste waste, double amount, double value, MD_wastePrice md_wastePrice) {
        this.waste = waste;
        this.amount = amount;
        this.value = value;
        this.md_wastePrice = md_wastePrice;
    }

    public MD_ItemWaste getWaste() {
        return waste;
    }

    public void setWaste(MD_ItemWaste waste) {
        this.waste = waste;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public MD_wastePrice getMd_wastePrice() {
        return md_wastePrice;
    }
}
