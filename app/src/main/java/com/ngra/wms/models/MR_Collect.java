package com.ngra.wms.models;

public class MR_Collect {

    MD_ItemWaste waste;

    float amount;


    public MR_Collect(MD_ItemWaste Waste, Integer Amount) {
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
