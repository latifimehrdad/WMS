package com.ngra.wms.models;

public class MD_WasteEstimate {

    private String Id;

    private Integer Amount;

    private MD_ItemWaste Waste;


    public MD_WasteEstimate(String id) {
        Id = id;
    }

    public MD_WasteEstimate(String id, Integer amount, MD_ItemWaste waste) {
        Id = id;
        Amount = amount;
        Waste = waste;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
    }

    public MD_ItemWaste getWaste() {
        return Waste;
    }

    public void setWaste(MD_ItemWaste waste) {
        Waste = waste;
    }
}
