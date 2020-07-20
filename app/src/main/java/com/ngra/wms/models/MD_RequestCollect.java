package com.ngra.wms.models;

public class MD_RequestCollect {

    MD_ItemWaste waste;

    float amount;


    public MD_RequestCollect(MD_ItemWaste Waste, Integer Amount) {
        waste = Waste;
        amount = Amount;
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
}
