package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MR_ItemsWast extends ModelResponsePrimary {

    @SerializedName("result")
    List<MD_ItemWaste> ItemsWast;

    public MR_ItemsWast(ArrayList<ModelMessage> messages) {
        super(messages);
    }

    public List<MD_ItemWaste> getItemsWast() {
        return ItemsWast;
    }

    public void setItemsWast(List<MD_ItemWaste> itemsWast) {
        ItemsWast = itemsWast;
    }
}
