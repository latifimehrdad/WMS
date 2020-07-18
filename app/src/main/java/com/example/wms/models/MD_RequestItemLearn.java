package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MD_RequestItemLearn extends ModelResponsePrimary {

    @SerializedName("result")
    List<MD_ItemLearn> itemLearns;


    public MD_RequestItemLearn(ArrayList<ModelMessage> messages) {
        super(messages);
    }

    public List<MD_ItemLearn> getItemLearns() {
        return itemLearns;
    }

    public void setItemLearns(List<MD_ItemLearn> itemLearns) {
        this.itemLearns = itemLearns;
    }
}
