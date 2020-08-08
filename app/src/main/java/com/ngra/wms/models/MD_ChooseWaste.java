package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

public class MD_ChooseWaste {

    MD_ItemWaste Waste;

    Integer Amount;


    public MD_ChooseWaste(MD_ItemWaste waste, Integer amount) {
        Waste = waste;
        Amount = amount;
    }

    public MD_ItemWaste getWaste() {
        return Waste;
    }

    public void setWaste(MD_ItemWaste waste) {
        Waste = waste;
    }

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
    }
}
