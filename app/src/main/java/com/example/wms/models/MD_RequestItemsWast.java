package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MD_RequestItemsWast extends ModelResponsePrimary {

    @SerializedName("result")
    List<MD_ItemWast> ItemsWast;

    public List<MD_ItemWast> getItemsWast() {
        return ItemsWast;
    }

    public void setItemsWast(List<MD_ItemWast> itemsWast) {
        ItemsWast = itemsWast;
    }
}
