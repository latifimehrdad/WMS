package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

public class MD_ScoreListItem {

    @SerializedName("waste")
    MD_ItemWaste waste;

    @SerializedName("amount")
    double amount;

    @SerializedName("value")
    double value;


    public MD_ScoreListItem(MD_ItemWaste waste, double amount, double value) {
        this.waste = waste;
        this.amount = amount;
        this.value = value;
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
}
