package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MD_RequestItemsWast extends ModelResponsePrimary {

    @SerializedName("result")
    List<MD_ItemWaste> ItemsWast;

    public MD_RequestItemsWast(ArrayList<ModelMessage> messages) {
        super(messages);
    }

    public List<MD_ItemWaste> getItemsWast() {
        return ItemsWast;
    }

    public void setItemsWast(List<MD_ItemWaste> itemsWast) {
        ItemsWast = itemsWast;
    }
}
