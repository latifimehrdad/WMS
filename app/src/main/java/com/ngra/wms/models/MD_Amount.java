package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

public class MD_Amount {

    @SerializedName("waste")
    MD_ItemWaste waste;

    @SerializedName("amount")
    float amount;

    @SerializedName("weightTitle")
    String weightTitle;

    public MD_Amount(MD_ItemWaste waste, float amount, String weightTitle) {
        this.waste = waste;
        this.amount = amount;
        this.weightTitle = weightTitle;
    }

    public MD_ItemWaste getWaste() {
        return waste;
    }

    public void setWaste(MD_ItemWaste waste) {
        this.waste = waste;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getWeightTitle() {
        return weightTitle;
    }

    public void setWeightTitle(String weightTitle) {
        this.weightTitle = weightTitle;
    }
}
