package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

public class MR_Collect {


    MD_ItemWaste waste;

    float amount;

    String WeightTitle;


    public MR_Collect(MD_ItemWaste waste, float amount, String weightTitle) {
        this.waste = waste;
        this.amount = amount;
        WeightTitle = weightTitle;
    }

    public MD_ItemWaste getWaste() {
        return waste;
    }

    public void setWaste(MD_ItemWaste Waste) {
        waste = Waste;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float Amount) {
        amount = Amount;
    }

    public String getWeightTitle() {
        return WeightTitle;
    }

    public void setWeightTitle(String weightTitle) {
        WeightTitle = weightTitle;
    }
}
